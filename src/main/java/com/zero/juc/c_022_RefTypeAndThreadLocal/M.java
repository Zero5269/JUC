package com.zero.juc.c_022_RefTypeAndThreadLocal;

public class M {
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
