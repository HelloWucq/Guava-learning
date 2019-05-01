package com.wucq.basic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.Weigher;

public class CacheDemo {
    public static void main(String[] args) {
        try {
            testSize();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("==================");
        try {
            testWeight();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("=====================");

        try {
            testEvictionByAccessTime();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("==============");
        try {
            testWeakKey();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("==============");
        testCacheRemovedNotification();

        System.out.println("==============");
        try {
            testCallable();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void testSize() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(3)
                .build(new CacheLoader<String, Employee>() {
                    public Employee load(String key) throws Exception {
                        return new Employee(key, key + "dept", key + "id");
                    }
                });
        cache.getUnchecked("wuchenqi");
        cache.getUnchecked("wucqwucq");
        cache.getUnchecked("old wucq");
        assert (cache.size() == (3L));

        cache.getUnchecked("new wucq");
        Employee employee = cache.getIfPresent("wuchenqi");
        if (employee == null) {
            System.out.println("Yes");
        } else {
            System.out.println(employee.toString());
        }

        Employee employee2 = cache.getIfPresent("new wucq");
        if (employee2 == null) {
            System.out.println("Yes");
        } else {
            System.out.println(employee2.toString());
        }
    }

    public static void testWeight() throws ExecutionException, InterruptedException {

        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumWeight(200)
                .weigher(new Weigher<String, Employee>() {
                    int size = 0;

                    @Override
                    public int weigh(String key, Employee employee) {
                        int weight = employee.getKey().length() + employee.getID().length()
                                + employee.getDepartment().length();
                        size = size + weight;
                        System.out.println(size);
                        return weight;
                    }
                }).build(new CacheLoader<String, Employee>() {
                    public Employee load(String key) throws Exception {
                        return new Employee(key, key + "dept", key + "id");
                    }
                });

        cache.get("wuchenqi");
        System.out.println(cache.size());
        cache.get("wucqwucq");
        System.out.println(cache.size());
        cache.get("old wucq");
        System.out.println(cache.size());
        cache.get("new wucq");
        System.out.println(cache.size());
    }

    public static void testEvictionByAccessTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Employee>() {
                    public Employee load(String key) throws Exception {
                        return new Employee(key, key + "dept", key + "id");
                    }
                });

        cache.getUnchecked("wuchenqi");
        TimeUnit.SECONDS.sleep(3);
        Employee employee = cache.getIfPresent("wuchenqi");
        if (employee == null) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

        cache.getUnchecked("guava");
        TimeUnit.SECONDS.sleep(2);
        employee = cache.getIfPresent("guava");
        if (employee == null) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public static void testWeakKey() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().weakValues().weakKeys()
                .build(new CacheLoader<String, Employee>() {
                    public Employee load(String key) throws Exception {
                        return new Employee(key, key + "dept", key + "id");
                    }
                });

        cache.getUnchecked("guava");
        cache.getUnchecked("wuchenqi");

        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        Employee employee = cache.getIfPresent("guava");
        if (employee == null) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    public static void testCacheRemovedNotification() {
        CacheLoader<String, String> loader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                System.out.println(cause.toString());
                System.out.println(notification.getKey() + "+" + notification.getValue());
            }
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).removalListener(listener)
                .build(loader);
        cache.getUnchecked("wuchenqi");
        cache.getUnchecked("wucqwucq");
        cache.getUnchecked("guava");
        cache.getUnchecked("test");
        cache.getUnchecked("test1");
    }

    public static void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader.from(k -> {
            counter.incrementAndGet();
            return System.currentTimeMillis();
        });

        LoadingCache<String, Long> cache = CacheBuilder.newBuilder().refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);

        Long result1 = cache.getUnchecked("guava");
        TimeUnit.SECONDS.sleep(3);
        Long result2 = cache.getUnchecked("guava");
        if (result1.longValue() != result2.longValue())
            System.out.println("Yes");
    }

    public static void testCallable() throws Exception {
        Cache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        Employee resultVal = cache.get("jerry", new Callable<Employee>() {

            @Override
            public Employee call() {
                Employee employee = new Employee("hello", " jerry", "!");
                return employee;
            }
        });
        System.out.println("jerry value:" + resultVal);
    }
}
