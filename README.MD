# Java基础---朝花夕拾~


## JAXB
参考：https://www.javacodegeeks.com/2013/02/jaxb-tutorial-getting-started.html

### 1.JAXB是什么
JAXB是Java平台对于XML数据绑定的一套体系结构。它用于将XML与Java对象之间的互相转换。
JAXB定义了一套统一的API用于从XML文档中读取然后转换成Java对象，将Java对象转换成XML再写入文件中。对于JAXB技术，我们关注的是数据绑定，而不是XML内容的解析。

There are two operations you can perform using JAXB

Marshalling :Converting a java object to XML

UnMarshalling :Converting a XML to java object


## NIO

### 1.Buffer是什么?
NIO 中的Buffer其实就是用于存储特定`基本数据类型`数据的容器。其底层本质上是一个数组，其同时还提供了底层数据的结构化访问，而且还可以跟踪程序对其的读写情况。
Buffer是一个抽象类，每一种基本数据类型都有其对应的一种Buffer，比如ByteBuffer,CharBuffer等等(boolean类型除外)。

### 2.Buffer的核心属性
Buffer中有3个核心的属性:`capacity`、`limit`、`position`是我们必须所理解的，下面来分析下这3个核心的属性。

##### capacity:
Buffer的capacity属性是指定该Buffer最大所能存储的元素个数。capacity属性必须是一个非负数，且一旦创建就无法改变。

##### limit:
Buffer的limit属性是Buffer中第一个不能被操作(读/写)的元素所在的索引位置。该值可以被修改，limit<=capacity,在默认情况下，limit等于capacity。

##### position:
Buffer的position属性是Buffer下一个将要被读/写的元素所在的索引位置。该值可以被修改，position<=limit;我们在每一次对Buffer进行读写操作时，Buffer内部都会去修改
position的值，从而标记一下次读写操作时应该从哪个元素开始读取或者从哪个位置开始写入,在默认情况下，position等于0。

##### mark:
Buffer的mark是一个临时的下标位置，其用于临时记录在某个时刻下Buffer的position，当之后进行读写操作后，position发生了改变，我们可以通过其reset()方法可以将当前的position重新恢复到之前记录的位置。
mark并不是所有的Buffer实现类都支持，但是Buffer实现类支持mark的话，那么mark也是一个非负数，并且mark<=position。当Buffer的mark被定义时,一旦Buffer的position或者limit被调整为大于mark值时，
此时mark就会变得无效。当一个Buffer不支持mark属性时，我们调用Buffer的mark()方法会引发InvalidMarkException。

通过上面对Buffer的4个属性的分析，我们可以得出如下的结论:
> Buffer的不变性原则: mark <= position <= limit <= capacity

对于新创建的一个Buffer,其position默认等于0，mark值是未被定义的;而limit的具体指取决于Buffer的具体实现类，通常情况下该值等于capacity,，Buffer中的元素在默认情况下的值都等于0。

### 3.Buffer的数据传输
Buffer为我们提供了基于相对位置的读写和基于绝对位置两种对数据的操作方式。

#### 相对位置操作(Relative Operation)
基于`相对位置`的读写是相对于当前Buffer当前的position而言的，当Buffer读写了N个数据后，那么position也会相应的增加N。
当Buffer的position已经达到了limit时，如果再执行get()操作(从Buffer中获取元素),则会抛出BufferUnderflowException;如果再执行put()操作(向Buffer中放置元素),则会抛出BufferOverflowException。

#### 绝对位置操作(Absolute Operation)
基于`相对位置`的读写需要用户显示指定对Buffer中的哪个位置的元素进行读取或者写入，**Buffer的绝对位置的读写操作不会修改Buffer的position的值。**
当指定的索引参数值大于limit值时，Buffer底层会抛出IndexOutOfBoundsException。

*在实际的使用中，我们其实很少使用对Buffer的绝对位置的操作，因此我们在使用Channel对将数据读写到Buffer中时，其都是使用的是对Buffer的相对位置的操作。*

### 4.Buffer中的重要方法

#### clear():

#### flip():

#### rewind():


### 5.Read-Only Buffer

### 6.Buffer的线程安全性

















#### Channel

#### Selector