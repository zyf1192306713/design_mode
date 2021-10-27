package com.zyf.singleton;

/**
 * 双重校验锁-线程安全
 * uniqueInstance 只需要被实例化一次，之后就可以直接使用了。
 * 加锁操作只需要对实例化那部分的代码进行，只有当 uniqueInstance 没有被实例化时，才需要进行加锁。
 * 没必要对整个方法加锁，这样会严重影响效率
 * @author zhongyufeng
 * @date 2021-10-27 16:06
 */
public class DoubleLockLazySingleton {

    /*
        加volatile  是为了防止指令重排序
        uniqueInstance = new Singleton();这段代码其实是分为三步执行：
            1、为 uniqueInstance 分配内存空间
            2、初始化 uniqueInstance
            3、将 uniqueInstance 指向分配的内存地址
        但是由于 JVM 具有指令重排的特性，执行顺序有可能变成 1>3>2。
        指令重排在单线程环境下不会出现问题，但是在多线程环境下会导致一个线程获得还没有初始化的实例。
        例如：
            线程 T1 执行了 1 和 3，此时 T2 调用 getUniqueInstance() 后发现 uniqueInstance 不为空，因此返回 uniqueInstance，但此时 uniqueInstance 还未被初始化。
        使用 volatile 可以禁止 JVM 的指令重排，保证在多线程环境下也能正常运行。
     */
    private volatile static DoubleLockLazySingleton uniqueInstance;

    private DoubleLockLazySingleton() {
        System.out.println("对象被创建了");
    }

    public static DoubleLockLazySingleton getUniqueInstance() {

        //第一个判断是为了实现懒加载
        if (uniqueInstance == null) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //这里上锁是为了解决多个线程同时进入方法时，出现同时创建多个对象的问题
            //静态方法不可使用对象作为锁，可以使用字符串，但是额外开辟一个字符串浪费空间，直接使用当前类对象也可以上锁
            synchronized (DoubleLockLazySingleton.class){
                //这里再次判断是因为，如果多个线程已经同时通过第一个非空判断了，就算加锁，也会创建多个线程，只是不是同时进行而已
                //这里再次判断之后就完全解决了懒汉模式多线程安全问题了，但是要注意，变量要使用volatile修饰，禁止jvm指令重排序
                if (uniqueInstance == null) {
                    uniqueInstance = new DoubleLockLazySingleton();
                }
            }
        }

        return uniqueInstance;
    }

    public static void main(String[] args) {
        //线程任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DoubleLockLazySingleton uniqueInstance = DoubleLockLazySingleton.getUniqueInstance();
                if (uniqueInstance == null) {
                    System.out.println("获取对象" + uniqueInstance);
                }
            }
        };

        //启用100个线程
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

}
