package com.zero;

import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Tets {
    public static void main(String[] args) throws NoSuchFieldException {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("1", "msb");
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(AtomicInteger.class.getDeclaredField("value"));
        System.out.println(unsafe.objectFieldOffset(AtomicInteger.class.getDeclaredField("value")));
    }
}
