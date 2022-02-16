package com.zyf;

/**
 * @author zhongyufeng
 * @date 2021-10-28 14:27
 */
public class Singleton {

    private static Singleton singleton;
    private Singleton() {
        System.out.println("对象被创建了");
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Singleton.class) {

                if (singleton == null) {

                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Singleton singleton = Singleton.getSingleton();
                }
            }).start();
        }
    }
}
