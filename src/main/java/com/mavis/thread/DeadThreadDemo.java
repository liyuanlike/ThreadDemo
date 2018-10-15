package com.mavis.thread;

/**
 * 线程死锁：在一个同步方法中调用了另一个对象的同步方法，可能产生死锁
 */
public class DeadThreadDemo {

    public static void main(String[] args) {
        new DeadThread();
    }

}

//顾客
class Customer {

    public synchronized void say(Waiter waiter) {
        System.out.println("顾客说，先吃饭再买单！");
        waiter.doService();
    }

    public synchronized void doService() {
        System.out.println("同意了，买完单再吃饭！");
    }

}

//服务员
class Waiter {

    public synchronized void say(Customer customer) {
        System.out.println("服务员说，先买单再吃饭！");
        customer.doService();
    }

    public synchronized void doService() {
        System.out.println("同意了，吃完饭再买单！");
    }

}

class DeadThread implements Runnable {

    Customer customer = new Customer();
    Waiter waiter = new Waiter();

    public DeadThread() {
        Thread thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        customer.say(waiter);
    }

    @Override
    public void run() {
        waiter.say(customer);
    }

}
