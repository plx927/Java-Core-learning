package com.panlingxiao.nio.channle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by panlingxiao on 2016/7/28.
 */
public class FileChannelTest {

    public static void main(String[] args) {
        FileInputStream fis = null;
        FileChannel fileChannel = null;
        try {
            fis = new FileInputStream("README.MD");
            fileChannel  = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int len = 0;
            while (-1 != (len = fileChannel.read(buffer))){
                System.out.println(new String(buffer.array()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fileChannel != null){
                    fileChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
