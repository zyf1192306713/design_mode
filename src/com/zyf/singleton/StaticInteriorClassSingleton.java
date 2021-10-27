package com.zyf.singleton;

/**
 * 静态内部类实现  懒加载的同时保证了线程安全
 * 当 Singleton 类被加载时，静态内部类 SingletonHolder 没有被加载进内存。
 * 只有当调用 getUniqueInstance() 方法从而触发 SingletonHolder.INSTANCE 时 SingletonHolder 才会被加载，此时初始化 INSTANCE 实例，并且 JVM 能确保 INSTANCE 只被实例化一次。
 * * 这种方式不仅具有延迟初始化的好处，而且由 JVM 提供了对线程安全的支持（静态资源只被加载一次）。
 * @author zhongyufeng
 * @date 2021-10-27 17:16
 */
public class StaticInteriorClassSingleton {

    private StaticInteriorClassSingleton() {
        System.out.println("对象被创建了");
    }

    private static class SingletonHolder {
        static {
            System.out.println("SingletonHolder 被加载了");
        }
        private static final StaticInteriorClassSingleton INSTANCE = new StaticInteriorClassSingleton();
    }

    public static StaticInteriorClassSingleton getUniqueInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        //线程任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                StaticInteriorClassSingleton uniqueInstance = StaticInteriorClassSingleton.getUniqueInstance();
                System.out.println("获取对象" + uniqueInstance);
            }
        };

        //启用100个线程
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
