package com.wangyi.server;

import com.wangyi.task.SocketThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by eason on 16-10-17.
 */

public class Server {
    private ServerSocket server=null;

    public Server(int port) throws IOException {
        server=new ServerSocket(port);
        while(true){
            Socket socket=null;
            try{
                socket=server.accept();
                new SocketThread(socket).start();
            }catch(Exception e) {
                System.out.println("Error."+e);
            }
        }
    }

    public void close() throws IOException {
        if(server!=null) server.close();
    }
}
