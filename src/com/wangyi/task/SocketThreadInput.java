package com.wangyi.task;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by eason on 16-10-17.
 */

public class SocketThreadInput extends Thread {
    private InputStream isOut;
    private OutputStream osIn;

    public SocketThreadInput(InputStream isOut, OutputStream osIn) {
        this.isOut = isOut;
        this.osIn = osIn;
    }

    private byte[] buffer = new byte[1024];

    public void run() {
        try {
            int len;
            while ((len = isOut.read(buffer)) != -1) {
                if (len > 0) {
                    System.out.println(new String(buffer,0,len));
                    osIn.write(buffer, 0, len);
                    osIn.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("SocketThreadInput leave");
        }
    }
}