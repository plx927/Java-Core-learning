# 个人对Java基础的一些复习与总结


### JAXB学习
参考：https://www.javacodegeeks.com/2013/02/jaxb-tutorial-getting-started.html
#### JAXB是什么
JAXB是Java平台对于XML数据绑定的一套体系结构。它用于将XML与Java对象之间的互相转换。
JAXB定义了一套统一的API用于从XML文档中读取然后转换成Java对象，将Java对象转换成XML再写入文件中。对于JAXB技术，我们关注的是数据绑定，而不是XML内容的解析。

There are two operations you can perform using JAXB

Marshalling :Converting a java object to XML

UnMarshalling :Converting a XML to java object