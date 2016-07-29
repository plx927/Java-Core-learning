package com.panlingxiao.nio.buffer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
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

        System.out.println("------------transfer data into buffer--------------------------------");
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
        printEssentailProeprtes(buffer);

        System.out.println("------------read data from buffer----------------------");
        while(buffer.hasRemaining()){
            //基于相对位置从Buffer中获取数据,每一次get操作都会引发Buffer的position加1
            System.out.println(buffer.get());
        }

        printEssentailProeprtes(buffer);

        //此时Buffer的position已经等于limit，如果再从Buffer中获取元素,则抛出异常
        try {
            buffer.get();
        } catch (BufferUnderflowException e) {
            System.out.println("Get data from buffer,but position==limit,so BufferUnderFlow");
        }

        //此时Buffer的position已经等于limit，如果向Buffer中添加元素,同样会抛出异常
        try {
            buffer.put((byte) 4);
        } catch (BufferOverflowException e) {
            System.out.println("Put data into buffer,but position==limit,so BufferOverflow");
        }


        /*
         * 通过Buffer可以根据绝对位置获取数据,这里获取索引为1个元素
         */
         byte data = buffer.get(1);
         System.out.println("index[1] : "+data);

        //根据绝对位置来获取元素时，所指定的position不能大于limit，否则会引发IndexOutOfBoundsException
        try {
            buffer.get(6);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Get data from buffer by absolute operation,but index >= limit,so IndexOutOfBoundException");
        }

        System.out.println("------------bulk read data from buffer----------------------");
        /*
         * 通过一个字节数组批量读取数据，当字节的数量大于缓冲区中剩余的字节数时，会抛出BufferUnderflowException
         * 这种API是属于逆天设计
         */
        byte[] dest = new byte[5];
        //由于此时的position为0，limit为3，Buffer没有足够的数据填充给定的字节数组，因此会抛出异常
        try {
            ((ByteBuffer)buffer.flip()).get(dest);
        } catch (BufferUnderflowException e) {
            System.out.println("Buffer's remaining byte < given array's length");
        }

        printEssentailProeprtes(buffer);

        //比较合适的做法，填充字节数组的时候指定填充的长度
        int remainingLength = buffer.remaining();
        buffer.get(dest,0,remainingLength);
        for(int i = 0;i < dest.length;i++){
            System.out.println("dest["+i+"] : "+dest[i]);
        }

        printEssentailProeprtes(buffer);




    }

    /**
     * 显示Buffer的重要属性
     * @param buffer
     */
    private static void printEssentailProeprtes(ByteBuffer buffer) {
        System.out.println("buffer position: "+ buffer.position());
        System.out.println("buffer limit : "+buffer.limit());
    }
}
