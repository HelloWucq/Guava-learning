package com.wucq.basic;

import com.google.common.base.*;

public class OptionalDemo {

    public static void main(String[] args) {
        OptionalDemo optionalDemo = new OptionalDemo();
        Optional<Integer> possible = Optional.fromNullable(null);
        System.out.println(possible.isPresent());
        System.out.println(possible.orNull());

        Integer invalidInput = null;
        Optional<Integer> c = Optional.of(new Integer(10));
        Optional<Integer> b = Optional.of(new Integer(10));
        System.out.println(optionalDemo.sum(c, b));

        Optional<Integer> a = Optional.of(invalidInput);
        System.out.println(optionalDemo.sum(a, b));
    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        return a.get() + b.get();
    }
}