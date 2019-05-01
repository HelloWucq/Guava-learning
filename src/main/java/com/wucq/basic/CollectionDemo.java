package com.wucq.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeSet;
import com.google.common.collect.Multiset.Entry;

import org.checkerframework.checker.units.qual.s;

public class CollectionDemo {

    public static void main(String[] args) {
        testImmutable();
        testNew();
        testMultimap();
        testTable();
        testRangeSet();
    }

    public static void testImmutable() {
        ArrayList<String> list = new ArrayList<>();
        list.add("wucq-1");
        list.add("wucq-2");
        list.add("wucq-3");
        System.out.println(list);

        ImmutableList<String> list2 = ImmutableList.copyOf(list);
        System.out.println(list2);
        ImmutableList<String> list3 = ImmutableList.of("wucq-1", "wucq-2", "wucq-3");
        System.out.println(list3);
        ImmutableList<String> list4 = ImmutableList.<String>builder().add("abc").add("cde").add("as", "cd", "ca")
                .build();
        System.out.println(list4);
        ImmutableList<String> list5 = list4.asList();
        System.out.println(list5.get(4));

    }

    public static void testNew() {
        HashMultiset<String> hset = HashMultiset.create();
        hset.add("wucq-1");
        hset.add("wucq-1");
        hset.add("wucq-2");

        System.out.println("hset.size(): " + hset.size());
        System.out.println("hset.elementSet().size(): " + hset.elementSet().size());
        ;

        Iterator<String> itor = hset.iterator();

        while (itor.hasNext()) {
            System.out.println(itor.next());
        }
        Set<Entry<String>> set = hset.entrySet();
        System.out.println(hset.count("wucq-1"));
        System.out.println(set);
    }

    public static void testMultimap() {
        ArrayListMultimap<String, Integer> asm = ArrayListMultimap.create();
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5, 6);
        asm.putAll("wucq", arr);
        asm.put("wuchenqi", 8888);
        asm.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String t, Integer u) {
                System.out.println(t + "  " + u);
            }
        });
        System.out.println(asm.get("wucq"));
    }

    public static void testTable() {
        Table<String, String, Integer> table1 = HashBasedTable.create();
        table1.put("zt", "wucq", 1);
        table1.put("zt", "wucq1", 1);
        table1.put("zt1", "wucq1", 1);
        table1.put("zt2", "wucq1", 1);

        Map<String, Map<String, Integer>> map = table1.rowMap();
        System.out.println(map);
        Set<String> set = table1.rowKeySet();
        System.out.println(set);
        Set<String> s = table1.columnKeySet();
        System.out.println(s);

        Map<String, Integer> map1 = table1.row("zt");
        System.out.println(map1);

        Map<String, Map<String, Integer>> map2 = table1.columnMap();
        System.out.println(map2);

        Map<String, Integer> map3 = table1.column("wucq1");
        System.out.println(map3);
    }

    public static void testRangeSet() {
        RangeSet<Integer> rs = TreeRangeSet.create();
        rs.add(Range.closed(0, 10));
        rs.add(Range.closed(11, 15));
        rs.add(Range.closed(1, 16));
        rs.add(Range.closedOpen(15, 20));
        System.out.println(rs);

        RangeSet<String> rs1 = TreeRangeSet.create();
        rs1.add(Range.closed("a", "d"));
        rs1.add(Range.closed("h", "y"));
        rs1.add(Range.closed("aa", "ac"));
        System.out.println(rs1);

        RangeSet<Integer> rsc = rs.complement();

        System.out.println(rsc);

        RangeSet<Integer> rsubr = rs.subRangeSet(Range.closed(15, 50));
        System.out.println(rsubr);

        rs.add(Range.closed(30, 50));
        Set<Range<Integer>> rsset = rs.asRanges();
        System.out.println(rs.asRanges());

        Range<Integer> ri = rs.span();
        System.out.println(ri);
    }
}