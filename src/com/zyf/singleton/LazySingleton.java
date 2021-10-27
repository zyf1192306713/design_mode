package com.zyf.singleton;

/**
 * 懒汉式-线程不安全
 * 私有静态变量 uniqueInstance 被延迟实例化，这样做的好处是，如果没有用到该类，那么就不会实例化 uniqueInstance，从而节约资源。
 *
 * 这个实现在多线程环境下是不安全的，
 * 如果多个线程能够同时进入 if (uniqueInstance == null) ，
 * 并且此时 uniqueInstance 为 null，
 * 那么会有多个线程执行 uniqueInstance = new Singleton(); 语句，
 * 这将导致实例化多次 uniqueInstance。
 *
 * @author zhongyufeng
 * @date 2021-10-27 15:17
 */
public class LazySingleton {

    //将自身类型作为静态属性
    private static LazySingleton uniqueInstance;

    //私有化构造器，使其无法被外部实例化，保证对象只能在内部创建
    private LazySingleton() {

        //这里测试多线程情况下是否会创建多个对象，这里可以看到，在多个线程进入的情况下，该对象会被创建多次
        System.out.println("对象被创建了。。。");
    }

    /**
     * 外部只能通过这个类的静态方法获取这个类的实例对象
     * 并且在这个方法中，只会在第一次被调用时，创建一个实例对象，
     * 后续再被调用时，直接使用之前创建过的对象，即为懒汉单例模式
     * @param
     * @return com.zyf.singleton.LazySingleton
     * @author zhongyufeng
     * @date 2021/10/27 15:20
     */
    public static LazySingleton getUniqueInstance() throws InterruptedException {

        //第一次被调用时，对象未被创建，所以在此创建一次对象
        if (uniqueInstance == null) {

            //故意在此处阻塞1秒，模拟多个线程进入的情况
            Thread.sleep(10000);

            uniqueInstance = new LazySingleton();
        }

        //后续调用时，对象不为nll，直接返回同一个对象
        return uniqueInstance;
    }


    public static void main(String[] args) {

        //线程任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    LazySingleton uniqueInstance = LazySingleton.getUniqueInstance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        //启用100个线程
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
