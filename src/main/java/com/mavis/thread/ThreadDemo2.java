package com.mavis.thread;

/**
 * 加入线程：join()，让调用的线程先执行指定时间或执行完
 * <p>
 * 中断线程：
 * 1.interrupt()：设置一个中断标记
 * 2.使用自定义标记的方式（推荐）
 */
public class ThreadDemo2 {

    public static void main(String[] args) {
        MyRunnable1 mr1 = new MyRunnable1();
        Thread t1 = new Thread(mr1);
        t1.start();
        MyRunnable2 mr2 = new MyRunnable2();
        Thread t2 = new Thread(mr2);
//        t2.start();
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 20) {
//                try {
//                    t1.join();//让t线程执行完毕
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                t1.interrupt();//中断线程，只是做了一个中断标记
                mr2.flag = false;
            }
        }
    }

}

class MyRunnable1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            if (Thread.interrupted()) {//测试中断状态，此方法会把中断状态清除
                break;
            }
            System.out.println(Thread.currentThread().getName() + "->" + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

}

class MyRunnable2 implements Runnable {

    public boolean flag = true;

    public MyRunnable2() {
        flag = true;
    }

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println(Thread.currentThread().getName() + "->" + (i++));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
