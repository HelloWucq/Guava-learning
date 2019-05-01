package com.wucq.basic;

import com.google.common.base.Optional;
import org.junit.Test;

public class OptionalDemoTest {

    private static OptionalDemo optionaldemo = new OptionalDemo();

    @Test
    public void testSum() {
        Integer invalidInput = null;
        System.out.println("hello world");
        Optional<Integer> a = Optional.of(invalidInput);
        Optional<Integer> b = Optional.of(new Integer(10));
        System.out.println(optionaldemo.sum(a, b));
    }
}