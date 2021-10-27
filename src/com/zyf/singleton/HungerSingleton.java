package com.zyf.singleton;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 饿汉式-线程安全
 * 线程不安全问题主要是由于 uniqueInstance 被实例化多次，
 * 采取直接实例化 uniqueInstance 的方式就不会产生线程不安全问题，即在类被加载时就创建一次对象，不管方法会不会被调用，都会先创建一次对象。
 * 但是直接实例化的方式也丢失了延迟实例化带来的节约资源的好处。
 *
 * @author zhongyufeng
 * @date 2021-10-27 15:41
 */
public class HungerSingleton {

    //类被加载时就创建一次
    private static HungerSingleton uniqueInstance = new HungerSingleton();

    private HungerSingleton() {
        System.out.println("对象被创建了");
    }

    public static HungerSingleton getUniqueInstance() {
        //故意在此处阻塞1秒，模拟多个线程进入的情况
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return uniqueInstance;
    }

    public static void main(String[] args) {
        //这里直接运行空的main方法，可以看到对象被创建了，因为这里运行会加载当前类，类被加载时即会创建一次对象



        //多线程的情况下可以看到只创建了一次对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("准备获取对象");
                HungerSingleton uniqueInstance = HungerSingleton.getUniqueInstance();
            }
        };
        //但是在多线程的情况下就不会出现多个对象被创建了
        for (int i = 0; i < 20; i++) {
            new Thread(runnable).start();
        }
    }

}
