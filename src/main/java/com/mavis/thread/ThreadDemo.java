package com.mavis.thread;

/**
 * 线程的休眠：在当前线程的执行过程中，暂停指定的毫秒数，释放CPU的时间片
 */
public class ThreadDemo {

    public static void main(String[] args) {
        MyThread mt = new MyThread();
        mt.start();//启动线程
        MyRunnable mr = new MyRunnable();
        Thread t = new Thread(mr);
        t.start();
    }

}

/**
 * 实现线程的第一种方式：继承Thread类
 */
class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * 实现线程的第二种方式：实现Runnable接口
 */
class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
