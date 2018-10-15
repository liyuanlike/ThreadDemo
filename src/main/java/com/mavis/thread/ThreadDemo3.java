package com.mavis.thread;

public class ThreadDemo3 {

    public static void main(String[] args) {
        MyRunnable3 mr3 = new MyRunnable3();
        Thread t = new Thread(mr3);
        //线程可以分成守护线程和用户线程，当进程中没有用户线程时，JVM会退出
        t.setDaemon(true);//把线程设置为守护线程
        //优先级高可以提高该线程抢占CPU时间片的概率
        t.setPriority(Thread.MAX_PRIORITY);
        //main线程为normal优先级，数字为5
        System.out.println("main线程的优先级为：" + Thread.currentThread().getPriority());
        System.out.println(t.isAlive());
        t.start();
        System.out.println(t.isAlive());
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 5) {
                Thread.yield();//让出本次CPU执行的时间片
            }
        }
    }

}

class MyRunnable3 implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setName("我的线程");
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
