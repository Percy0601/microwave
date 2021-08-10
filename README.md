# microwave

## 1、介绍
Microwave(微波)是基于Micronaut(微电子)和Thrift的一个轻量级服务治理框架。

https://github.com/Percy0601/microwave

## 2、特性
### 2.1、云本地原生支持-Cloud Native
Micronaut服务框架本身就是cloud native框架，比spring boot更轻量级的实现。

### 2.2、更轻量框架-Low Memory Consumption
Microwave基于Micronaut启动速度和占用内存方面远低于spring boot, 对标的框架是spring boot native。Thrift框架对依赖很少，所以整体的结构非常轻量，自身Jar大小仅仅几百Kb。

启动速度快，整个服务框架启动在秒级内可以启动。

### 2.3、本地镜像的支持-Native Image
Microwave可以使用GraalVM构建成本地二进制代码，构建完成后的对于目标服务器无需安装JDK。

### 2.4、零反射-Zero Refelection
Microwave使用了JSR 269 Java Annotation Compile API，在编译前就已经生成了该有的代码，所以后续无需任何反射代码。

### 2.5、节能-Reduce Energy
因为可以构建在GraalVM上，所以理论上可以运行在ARM AArch64芯片上(安卓手机或树莓派)，总功率低于3W的服务器可以流畅运行服务。

## 3、服务治理框架具备的特性
### 3.1、服务发现
没有调研zookeeper/eureka等类似的中间件是否支持native image特性，不过可以使用thrift协议在这些中间件基础上做一个适配来实现服务发现。

### 3.2、负载均衡
客户端使用commons-pool技术，连接池在makeObject时候可以实现负载均衡。

### 3.3、隐式传参
服务治理框架能够透明化实现链路追踪，核心技术就是隐式传参。把当前的TraceId和每个服务节点的SpanId能够在协议头中隐式传递。

### 3.4、泛化调用
Dubbo Octo都是支持的

### 3.5、限流降级
可选，可以在网关中处理


## 4、参考指南
### 4.1、调研JSR269 Java Annotation Compile API
https://www.cnblogs.com/throwable/p/9139908.html

https://blog.csdn.net/jjxojm/article/details/90349756

### 4.2、调研原生Thrift和Swift
https://github.com/facebookarchive/swift

Swift是一个开源的基于注解方式实现的Thrift RPC框架

### 4.3、调研Thrift隐式传参
具体参见这篇文档，核心内容为thrift在生成代码时候，默认保留了第0号字段，我们可以使用这个字段来传输自定义变量。

https://blog.csdn.net/ITer_ZC/article/details/45153889

### 4.4、调研基于Thrift的服务治理实现
https://blog.csdn.net/zhu_tianwei/article/details/44115667

### 4.5、调研Thrift本地化可行性-GraalVM Native Image
使用GraalVM直接构建和启动Thrift框架服务，编译成Native二进制在Linux上可以正常运行。

https://github.com/Percy0601/training-thrift


```
    native-image --no-fallback --allow-incomplete-classpath -jar target/training-thrift-server-1.0-SNAPSHOT-jar-with-dependencies.jar
```
### 4.6、调研Micronaut与Spring Boot对比
1、启动速度

在启动速度上，Micronaut要远快于Spring boot框架启动速度，并且依赖的Jar远小于Spring boot。

Micronaut启动速度一般在5s以内，生成的Jar低于10Mb，并且不会因为项目越大启动越慢。

Spring boot启动速度一般在10s（20s左右甚至更长）以上，生成的运行jar一般都在30mb

2、功能特性

都支持IoC/DI AOP MVC 以及访问数据库或存储特定。

3、学习成本

掌握Spring boot框架基础上，切换使用Micronaut非常平滑，在官方指导下常用的特性几乎零成本。

4、迁移成本

Micronaut提供了迁移工具，这里没有做实际操作，应该成本不大。

### 4.7、调研GraalVM
https://www.zhihu.com/question/274042223/answer/1270829173

### 4.8、调研Thrift单端口多接口
https://vimsky.com/examples/detail/java-class-org.apache.thrift.TMultiplexedProcessor.html