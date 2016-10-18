package com.wangyi.server;

import com.wangyi.define.ReqInfo;

/**
 * Created by eason on 16-10-17.
 */
public class ReqParser {
    public static ReqInfo parse(byte[] buffer,int len){
        String str = new String(buffer,0,len);
        if(str.charAt(0) == 'C') str = "GET"+str.substring(7);
        String[] source = str.split("\\r\\n");

        ReqInfo reqInfo = new ReqInfo();

        String[] firstLine = source[0].split(" ");
        reqInfo.method = firstLine[0];
        reqInfo.protocol = firstLine[2];
        reqInfo.url = firstLine[1];
        for(String line:source){
            String[] map = line.split(":");
            switch (map[0]){
                case "Host":
                    int i = 0;
                    if(map[i+1].startsWith("http")) i++;
                    reqInfo.host = map[i+1].substring(1).replace("/","");
                    if(map.length>i+2) reqInfo.port = Integer.parseInt(map[i+2]);
                    break;
                case "Cookie":
                    reqInfo.cookie = map[1].substring(1);
                    break;
            }
        }
        reqInfo.bytes = str.getBytes();
        reqInfo.len = reqInfo.bytes.length;
        return reqInfo;
    }
}
