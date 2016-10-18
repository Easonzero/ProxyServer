package com.wangyi.cache;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by eason on 16-10-18.
 */
public class Cache {
    public static final String PATH = "/cache/";
    byte[] buffer = new byte[40480];
    int cur = 0;
    int len = 0;

    public void write(byte[] bytes,int pass,int len){
        for(int i=0;i<len;i++,cur++){
            buffer[cur+pass] = bytes[i];
        }
        this.len += len;
    }

    public void cache(String url){
        cur = 0;
        len = 0;
        saveFile(buffer,PATH,url);
    }

    public byte[] read(String url) throws IOException {
        return toByteArray(PATH + url);
    }

    public static void saveFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists()&&dir.isDirectory()){
                dir.mkdirs();
            }
            file = new File(filePath+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static byte[] toByteArray(String filename)throws IOException {
        File f = new File(filename);
        if(!f.exists()){
            throw new FileNotFoundException(filename);
        }

        FileChannel channel = null;
        FileInputStream fs = null;
        try{
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());
            while((channel.read(byteBuffer)) > 0){}
            return byteBuffer.array();
        }catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally{
            try{
                channel.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            try{
                fs.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
