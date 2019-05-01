package com.wucq.basic;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.common.io.ByteProcessor;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class IODemo {

    public static void main(String[] args) {
        File file = new File("E:\\Java\\guava\\src\\resource\\input.txt");
        charSourceTest(file);
        System.out.println("byteSourceTest: ");
        byteSourceTest(file);
        System.out.println("======================");
        try {
            countFile(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("======================");
        URL url;
        try {
            url = new URL("https://www.baidu.com/");
            resourceTest(url, file);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("======================");
        File output = new File("E:\\Java\\guava\\src\\resource\\output.txt");
        charSinkTest(file, output);
    }

    public static void charSourceTest(File file) {
        try {
            ImmutableList<String> lines = Files.asCharSource(file, Charsets.UTF_8).readLines();
            System.out.println(lines.toString());
            System.out.println(lines.get(0));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void charSinkTest(File input, File output) {
        CharSource inputSource = Files.asCharSource(input, Charsets.UTF_8);
        CharSink outSink = Files.asCharSink(output, Charsets.UTF_8, FileWriteMode.APPEND);
        try {
            inputSource.copyTo(outSink);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // TODO 注意编码格式
    public static void byteSourceTest(File file) {
        try {
            ArrayList<String> list = Files.asByteSource(file).read(new ByteProcessor<ArrayList<String>>() {
                ArrayList<String> result = new ArrayList<>();

                @Override
                public boolean processBytes(byte[] buf, int off, int len) throws IOException {
                    while (len > 0) {
                        String ts = new String(buf, off, 2);
                        result.add(new String(buf, off, 2));
                        off = off + 2;
                        len = len - 2;
                        System.out.println(ts);
                    }
                    return false;
                }

                @Override
                public ArrayList<String> getResult() {
                    return result;
                }
            });
            System.out.println(list.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void countFile(File file) throws IOException {
        Multiset<String> wordOccurences = HashMultiset.create(Splitter.on(CharMatcher.whitespace()).trimResults()
                .omitEmptyStrings().split(Files.asCharSource(file, Charsets.UTF_8).read()));
        ;
        System.out.println(wordOccurences.toString());
    }

    public static void resourceTest(URL url, File file) {
        try {
            Resources.asByteSource(url).copyTo(Files.asByteSink(file, FileWriteMode.APPEND));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}