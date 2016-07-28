# 个人对Java基础的一些复习与总结


### JAXB学习
参考：https://www.javacodegeeks.com/2013/02/jaxb-tutorial-getting-started.html
#### JAXB是什么
JAXB是Java平台对于XML数据绑定的一套体系结构。它用于将XML与Java对象之间的互相转换。
JAXB定义了一套统一的API用于从XML文档中读取然后转换成Java对象，将Java对象转换成XML再写入文件中。对于JAXB技术，我们关注的是数据绑定，而不是XML内容的解析。

There are two operations you can perform using JAXB

Marshalling :Converting a java object to XML

UnMarshalling :Converting a XML to java object


### NIO学习

#### Buffer
NIO 中的Buffer其实就是用于存储特定`原生数据类型`数据的容器。
Buffer有3个核心的属性:`capacity`、`limit`、`position`。

##### capacity:
Buffer的capacity属性是指定该Buffer最大所能存储的字节数。capacity属性必须是一个非负数，且一旦创建就无法改变。

##### limit:
Buffer的limit属性是Buffer中第一个不能被操作(读/写)的元素的索引位置。该值可以被修改，limit<=capacity。
在默认情况下，limit等于capacity。

##### position:
Buffer的position属性是Buffer下一个将要被读/写的元素所在的索引位置。该值可以被修改，position<=limit,我们在每一次对Buffer进行读写操作时，Buffer内部都会去修改
position的值，从而标记一下次读写操作时，应该从哪个元素开始读取或者从哪个位置开始写入。
在默认情况下，position等于0。

##### mark:


> Buffer的不变性原则: mark <= position <= limit <= capacity



#### Channel

#### Selector