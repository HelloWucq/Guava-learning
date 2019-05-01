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

        try {
            System.out.println(optionalDemo.sqrt(-3.0));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(optionalDemo.getValue(6));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        Optional<Integer> a = Optional.of(invalidInput);
        System.out.println(optionalDemo.sum(a, b));
    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        return a.get() + b.get();
    }

    public double sqrt(double input) throws IllegalArgumentException {
        Preconditions.checkArgument(input > 0.0, "Illegal Argument passed: Negative value %s", input);
        return Math.sqrt(input);
    }

    public int getValue(int input) {
        int[] data = { 1, 2, 3, 4 };
        Preconditions.checkElementIndex(input, data.length, "Illegal Argument passed: Invalid index.");
        return 0;
    }
}