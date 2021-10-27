##  单例（Singleton）

### Intent

某个类在程序运行过程中只被实例化一次，也就是说该类在程序的生存周期里只有一个实例对象。
好处：由于这个类只实例化一次，不管多少个类中用到了这个类，也都只有一个该类的对象。因此，减少了类实例对象的创建-->减小了GC压力-->提升了程序的性能。

### Class Diagram

使用一个私有构造函数、一个私有静态变量以及一个公有静态函数来实现。

私有构造函数保证了不能通过构造函数来创建对象实例，只能通过公有静态函数返回唯一的私有静态变量。

![img](https://www.xuxueli.com/blog/static/images/img_176.png)

#### Ⅰ 懒汉式-线程不安全 LazySingleton

#### Ⅱ 饿汉式-线程安全 HungerSingleton

#### Ⅲ 懒汉式-线程安全 SyncLazySingleton

#### Ⅳ 双重校验锁-线程安全 DoubleLockLazySingleton

#### Ⅴ 静态内部类实现 StaticInteriorClassSingleton

#### Ⅵ 枚举实现 EnumSingleton



### Examples

- Logger Classes 日志
- Configuration Classes 配置类
- Accesing resources in shared mode 共享资源
- Factories implemented as Singletons 单例工厂
- 线程池
- 处理连接对象

### JDK
- [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime())
- [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
- [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)