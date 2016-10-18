package com.wangyi.task;

import com.wangyi.define.ReqInfo;
import com.wangyi.server.GFW;
import com.wangyi.server.ReqParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by eason on 16-10-17.
 */
public class SocketThread extends Thread {
    private Socket socketIn,socketOut;
    private InputStream isIn,isOut;
    private OutputStream osIn,osOut;
    private byte[] bytes = new byte[2048];

    public SocketThread(Socket socket) {
        this.socketIn = socket;
    }

    public void run() {
        try {
            System.out.println("\n\na client connect ");
            isIn = socketIn.getInputStream();
            osIn = socketIn.getOutputStream();

            int len;
            ReqInfo reqInfo = null;
            if ((len = isIn.read(bytes)) != -1&&len > 0){
                reqInfo = ReqParser.parse(bytes,len);
                reqInfo.ip = socketIn.getLocalAddress().getHostAddress();
            }

            if(GFW.c(reqInfo,osIn)){
                socketOut = new Socket(reqInfo.host,reqInfo.port);
                isOut = socketOut.getInputStream();
                osOut = socketOut.getOutputStream();

                System.out.println(new String(reqInfo.bytes, 0, reqInfo.len));
                osOut.write(reqInfo.bytes, 0, reqInfo.len);
                osOut.flush();

                if(GFW.a(reqInfo)&&GFW.b(reqInfo)){
                    SocketThreadOutput out = new SocketThreadOutput(isIn, osOut);
                    out.start();
                    SocketThreadInput in = new SocketThreadInput(isOut, osIn);
                    in.start();

                    out.join();
                    in.join();
                }
            }
        } catch (Exception e) {
            System.out.println("a client leave?"+e.getMessage());
        } finally {
            try {
                if (socketIn != null) {
                    socketIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
