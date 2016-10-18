package com.wangyi.server;

import com.wangyi.define.ReqInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by eason on 16-10-18.
 */
public class GFW {
    final static String[] gfw_url_list = {
        //"qq.com"
    };
    final static String[] gfw_ip_list = {
           // "127.0.0.1"
    };
    final static String[] gfw_redirect_list = {
            "qq.com"
    };
    final static String redirect = "HTTP/1.1 200 OK\n" +
                                   "Server: nginx\n" +
                                   "Content-Type: text/html\n\n" +
                                   "hello world";
    public static boolean a(ReqInfo reqInfo){
        for(String url:gfw_url_list){
            if(reqInfo.url.contains(url)){
                return false;
            }
        }
        return true;
    }

    public static boolean b(ReqInfo reqInfo){
        for(String ip:gfw_ip_list){
            if(reqInfo.ip.contains(ip)){
                return false;
            }
        }
        return true;
    }

    public static boolean c(ReqInfo reqInfo,OutputStream osIn) throws IOException {
        for(String url:gfw_redirect_list){
            if(reqInfo.url.contains(url)){
                byte[] bytes = redirect.getBytes();
                osIn.write(bytes,0,bytes.length);
                osIn.flush();
                return false;
            }
        }
        return true;
    }
}
