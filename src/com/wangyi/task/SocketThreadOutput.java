package com.wangyi.task;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by eason on 16-10-17.
 */

public class SocketThreadOutput extends Thread {
    private InputStream isIn;
    private OutputStream osOut;

    public SocketThreadOutput(InputStream isIn, OutputStream osOut) {
        this.isIn = isIn;
        this.osOut = osOut;
    }

    private byte[] buffer = new byte[1024];

    public void run() {
        try {
            int len;
            while ((len = isIn.read(buffer)) != -1) {
                if (len > 0) {
                    System.out.println(new String(buffer, 0, len));
                    osOut.write(buffer, 0, len);
                    osOut.flush();
                }
            }
            byte[] bytes = new String("if-modified-since: lalala").getBytes();
            osOut.write(bytes, 0, bytes.length);
            osOut.flush();
            System.out.println(new String(bytes, 0, len));
        } catch (Exception e) {
            System.out.println("SocketThreadOutput leave");
        }
    }
}
