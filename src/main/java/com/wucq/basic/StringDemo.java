package com.wucq.basic;

import java.util.Arrays;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringDemo {
    public static void main(String[] args) {
        System.out.println(Joiner.on(",").join(Arrays.asList(1, 2, 3, 7)));
        System.out.println(JoinerTest());

        String inpuString = "the ,quick, , brown         , fox,              jumps, over, the, lazy, little dog.";
        String outpuString = SplitterTest(inpuString);
        System.out.println(outpuString);

        String input = "the, test,   1,2,3, over  ";
        CharMatcherTest(input);
    }

    public static String JoinerTest() {
        Joiner joiner = Joiner.on(";").skipNulls();
        return joiner.join("Harry", null, "Ron", "Hermione");
    }

    public static String SplitterTest(String inputString) {
        return Splitter.on(',').trimResults().omitEmptyStrings().split(inputString).toString();
    }

    public static void CharMatcherTest(String input) {
        System.out.println(CharMatcher.javaIsoControl().removeFrom(input));
        System.out.println(CharMatcher.digit().retainFrom(input));
        System.out.println(CharMatcher.anyOf("aeiou").removeFrom(input));
    }

}