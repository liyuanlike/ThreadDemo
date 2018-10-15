package com.mavis.thread;

/**
 * 两个线程协同工作，先生产，再消费
 * 面试题：
 * sleep(long millis)与wait()的区别？
 * sleep(long millis)：让线程进入休眠状态，让出CPU的时间片，不释放对象监视器的所有权
 * wait()：让线程进入等待状态，让出CPU的时间片，并释放对象监视器的所有权，等待其他线程通过notify()方法来唤醒
 */
public class ProducerConsumerDemo {

    public static void main(String[] args) {
        Food food = new Food();
        ProducerThread pt = new ProducerThread(food);
        ConsumerThread ct = new ConsumerThread(food);
        Thread t1 = new Thread(pt);
        Thread t2 = new Thread(ct);
        t1.start();
        t2.start();
    }

}

/**
 * 食物
 */
class Food {

    private String name;
    private String desc;
    private boolean flag = true;//true表示可以生产，false表示可以消费

    public Food() {
    }

    public Food(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 生产产品
     */
    public synchronized void set(String name, String desc) {
        if (!flag) {//不能生产
            try {
                this.wait();//线程进入等待状态，释放对象监视器的所有权
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setName(name);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setDesc(desc);
        flag = !flag;
        this.notify();//唤醒等待的线程（随机的其中一个）
    }

    /**
     * 消费产品
     */
    public synchronized void get() {
        if (flag) {//不能消费
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + "->" + this.getDesc());
        flag = !flag;
        this.notify();
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}

/**
 * 消费者
 */
class ConsumerThread implements Runnable {

    private Food food;

    public ConsumerThread(Food food) {
        this.food = food;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            food.get();
        }
    }

}

/**
 * 生产者
 */
class ProducerThread implements Runnable {

    private Food food;

    public ProducerThread(Food food) {
        this.food = food;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                food.set("锅包肉", "酸甜口，爽");
            } else {
                food.set("佛跳墙", "大补，滋阴补阳");
            }
        }
    }

}
