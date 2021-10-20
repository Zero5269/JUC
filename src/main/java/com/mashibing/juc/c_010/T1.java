/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 *
 * @author mashibing
 */
package com.mashibing.juc.c_010;

import java.util.concurrent.TimeUnit;

public class T1 {
    void m() {
        System.out.println(this);
        synchronized (this) {
            System.out.println("m start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m end");
        }
    }

    public static void main(String[] args) {
        new TT1().m();
    }

}

class TT1 extends T1 {
    @Override
     void m() {
        System.out.println(this);
        synchronized (this) {
            System.out.println("child m start");
            super.m();
            System.out.println("child m end");
        }
    }
}
