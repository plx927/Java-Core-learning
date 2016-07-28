package com.panlingxiao.nio.channel;

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
        readFileByNIO();
    }


    /**
     * 通过NIO的读取文件数据数据
     */
    static void readFileByNIO() {
        FileInputStream fis = null;
        FileChannel fileChannel = null;
        try {
            fis = new FileInputStream("README.MD");
            /*
             * NIO是基于Channel和Buffer为基础来对数据进行读写
             */
            fileChannel  = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //通过Channel将数据读取到Buffer中
            fileChannel.read(buffer);
            System.out.println(new String(buffer.array()));
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
