package com.example.estemplate.queue;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class  MyData{
    int num =1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void printc1() {
        lock.lock();
        try {
            while (num != 1) {
                c1.await();
            }
            //满足条件 干活
            for (int i = 0; i < 3; i++) {
                System.out.println("c1：" + i);
            }
            num  = 2;
            //唤醒c2
            c2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void printc2() {
        lock.lock();
        try {
            while (num != 2) {
                c2.await();
            }
            //满足条件 干活
            for (int i = 0; i < 5; i++) {
                System.out.println("c2:" + i);
            }
            num  = 3;
            //唤醒c3
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void printc3() {
        lock.lock();
        try {
            while (num != 3) {
                c3.await();
            }
            //满足条件 干活
            for (int i = 0; i < 10; i++) {
                System.out.println("c3：" + i);
            }
            num  = 1;
            //唤醒c3
            c1.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }



}
public class VolatileDemo {



    public static void main(String[] args) {

        MyData myData = new MyData();
        new Thread(() ->{

            for (int i = 0; i < 10; i++) {
                myData.printc1();
            }
        },"A").start();

        new Thread(() ->{

            for (int i = 0; i < 10; i++) {
                myData.printc2();
            }
        },"B").start();

        new Thread(() ->{

            for (int i = 0; i < 10; i++) {
                myData.printc3();
            }
        },"C").start();




    }

}



































