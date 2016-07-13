package com.panlingxiao.xml.jaxb;

import com.panlingxiao.xml.domain.Country;
import com.panlingxiao.xml.domain.Point;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Created by panlingxiao on 2016/7/13.
 */
public class JaxbTest {


    @Test
    public void testMarshalling1() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Point.class);
        Marshaller marshaller = jaxbContext.createMarshaller();

        //设置将XML以格式良好的方式输出
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        Point point = new Point(1, 2);
        marshaller.marshal(point,System.out);
    }


    /**
     * 将Java对象装换Xml
     */
    @Test
    public void testMarshalling() throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);

        //创建序列化工具
        Marshaller marshaller = jaxbContext.createMarshaller();

        Country country = new Country();
        marshaller.marshal(country,System.out);


    }







}
