package com.wangyi;

import com.wangyi.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
