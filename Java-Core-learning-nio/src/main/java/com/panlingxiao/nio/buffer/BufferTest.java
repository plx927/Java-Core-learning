package com.panlingxiao.nio.buffer;

import java.nio.ByteBuffer;

/**
 * Created by panlingxiao on 2016/7/28.
 */
public class BufferTest {

    public static void main(String[] args) {
        //指定buffer最大的存储容量为10,即capacity为10
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //默认所创建的Buffer为非DirectBuffer，即HeapByteBuffer
        System.out.println("buffer is direct : "+ buffer.isDirect());

        //buffer的capacity值为100,且该值一旦确定就无法被修改
        System.out.println("buffer capacity : "+ buffer.capacity());

        //在默认情况下，Buffer的limit==capacity
        System.out.println("buffer limit : " + buffer.limit());

        //默认情况下,Buffer的position等于0
        System.out.println("buffer position : " + buffer.position());


        //往Buffer存储3个元素,此时buffer的position会发送改变,position会被修改为3
        buffer.put((byte)1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);

        System.out.println("buffer position: " + buffer.position());

        /*
         * 从Buffer中读取数据,由于现在的position为3，那么其实需要将position设置为0，并且读取3个元素
         * 如果直接读取，那么会直接从buffer索引为4的位置开始读取，从而造成数据读取的错误。
         * 在Buffer中为我们提供了一个flip方法，它是将当前Buffer的position设置为0，将Buffer的limit设置为之前的position值
         */
        buffer.flip();
        System.out.println("------------flip buffer--------------------------------");
        System.out.println("buffer position: " + buffer.position());
        System.out.println("buffer limit : " + buffer.limit());

        System.out.println("------------read data from buffer----------------------");
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

        System.out.println("buffer position: "+ buffer.position());
        System.out.println("buffer limit : "+buffer.limit());



    }
}
