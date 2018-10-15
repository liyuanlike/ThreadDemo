package com.mavis.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.多线程共享数据时，会发生线程不安全的情况
 * 2.多线程共享数据必须使用同步
 * 3.实现同步的三种方式
 * 1).使用同步代码块
 * 2).使用同步方法
 * 3).使用Lock（更灵活的代码控制）
 * 4.多线程共享数据会有安全问题，使用同步可以解决安全问题，但同时会牺牲性能，所以同步的代码块要尽量保持简短，把不随线程变化的代码移出同步块，不要阻塞
 */
public class ThreadDemo4 {

    public static void main(String[] args) {
        MyRunnable4 mr4 = new MyRunnable4();
        Thread t1 = new Thread(mr4);
        Thread t2 = new Thread(mr4);
        t1.start();
        t2.start();
    }

}

class MyRunnable4 implements Runnable {

    private int ticket = 10;

    @Override
    public void run() {
        method2();
    }

    //同步方法：同步的对象是当前对象
    public synchronized void method() {
        for (int i = 0; i < 300; i++) {
            if (ticket > 0) {
                ticket--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "您购买的票剩余" + ticket + "张");
            }
        }
    }

    //互斥锁
    ReentrantLock reentrantLock = new ReentrantLock();

    //lock()实现同步
    public void method2() {
        for (int i = 0; i < 300; i++) {
            reentrantLock.lock();
            try {
                if (ticket > 0) {
                    ticket--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "您购买的票剩余" + ticket + "张");
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }

}
