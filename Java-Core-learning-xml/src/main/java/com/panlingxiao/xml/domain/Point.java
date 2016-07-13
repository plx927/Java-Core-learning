package com.panlingxiao.xml.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by panlingxiao on 2016/7/13.
 */
//aa指定根元素的名字
@XmlRootElement(name = "aa")
public class Point {

    private int x;
    private int y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
