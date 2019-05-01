package com.wucq.basic;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;

public class RangeDemo {
    public static void main(String[] args) {
        RangeDemo rangeDemo = new RangeDemo();
        rangeDemo.testRange();
    }

    private void testRange() {
        Range<Integer> range1 = Range.closed(0, 9);
        printRange(range1);

        System.out.println("5 is present: " + range1.contains(5));
        System.out.println("(1,2,3) is present: " + range1.containsAll(Ints.asList(1, 2, 3)));
        System.out.println("Lower Bound: " + range1.lowerEndpoint());
        System.out.println("Upper Bound: " + range1.upperEndpoint());

        Range<Integer> range2 = Range.open(0, 9);
        System.out.println("(0,9):");
        printRange(range2);

        Range<Integer> range3 = Range.greaterThan(9);
        System.out.println("(9,infinity): ");
        System.out.println("Lower Bound: " + range3.lowerEndpoint());
        System.out.println("Upper Bound present: " + range3.hasUpperBound());

        Range<Integer> range8 = Range.closed(5, 15);
        printRange(range1.intersection(range8));
        printRange(range1.span(range8));
    }

    private void printRange(Range<Integer> range) {
        System.out.print("[");
        for (int grade : ContiguousSet.create(range, DiscreteDomain.integers())) {
            System.out.print(grade + " ");
        }
        System.out.println("]");
    }
}