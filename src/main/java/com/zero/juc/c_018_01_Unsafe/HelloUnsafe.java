package com.zero.juc.c_018_01_Unsafe;

//import sun.misc.*;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class HelloUnsafe {
    static class M {
        private M() {
        }

        int i = 7;
    }

    public static void main(String[] args) throws Exception {
//        Unsafe unsafe = Unsafe.getUnsafe();
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        M m = (M) unsafe.allocateInstance(M.class);
        System.out.println(m.i);
        m.i = 9;
        System.out.println(m.i);
    }
}


