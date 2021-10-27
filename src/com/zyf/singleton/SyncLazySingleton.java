package com.zyf.singleton;

/**
 * 懒汉式-线程安全
 * <p>
 * 针对懒汉式的线程安全问题，可以通过对方法加锁的方式来解决
 * 只需要对 getUniqueInstance() 方法加锁，那么在一个时间点只能有一个线程能够进入该方法，从而避免了实例化多次 uniqueInstance。
 * 但是当一个线程进入该方法之后，其它试图进入该方法的线程都必须等待，
 * 即使 uniqueInstance 已经被实例化了。
 * 这会让线程阻塞时间过长，因此该方法有性能问题，不推荐使用。
 *
 * @author zhongyufeng
 * @date 2021-10-27 15:52
 */
public class SyncLazySingleton {

    private static SyncLazySingleton uniqueInstance;

    private SyncLazySingleton() {
        System.out.println("对象被创建了");
    }

    //方法加锁synchronized
    public synchronized static SyncLazySingleton getUniqueInstance() {

        //这样同一时间只会有一条线程进入这个方法，不会出现多个线程同时进入判断了
        if (uniqueInstance == null) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            uniqueInstance = new SyncLazySingleton();
        }

        return uniqueInstance;
    }

    public static void main(String[] args) {
        //线程任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                SyncLazySingleton uniqueInstance = SyncLazySingleton.getUniqueInstance();
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