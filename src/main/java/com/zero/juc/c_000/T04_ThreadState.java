package com.zero.juc.c_000;

public class T04_ThreadState {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());
            for(int i=0; i<10; i++) {
                try {
                    Thread.sleep(500);
                    System.out.println(this.getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();

        System.out.println(t.getState());

        t.start();
        System.out.println(t.getState());

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getState());

    }
}
