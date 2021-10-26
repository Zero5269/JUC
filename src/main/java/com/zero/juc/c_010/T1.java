/**
 * һ��ͬ���������Ե�������һ��ͬ��������һ���߳��Ѿ�ӵ��ĳ������������ٴ������ʱ����Ȼ��õ��ö������.
 * Ҳ����˵synchronized��õ����ǿ������
 * �����Ǽ̳����п��ܷ��������Σ�������ø����ͬ������
 *
 * @author mashibing
 */
package com.zero.juc.c_010;

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
