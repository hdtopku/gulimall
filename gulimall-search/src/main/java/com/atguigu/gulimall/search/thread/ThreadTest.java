package com.atguigu.gulimall.search.thread;

import java.util.concurrent.*;

/**
 * @author lxh
 * @createTime 2022-03-26 23:14:39
 */
public class ThreadTest {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main...start");
        System.out.println("主线程："+Thread.currentThread().getId());
        CompletableFuture<String> future01 = CompletableFuture.supplyAsync(()->{
            System.out.println("查询当前商品的图片信息");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello.jpg";
        }, executor);
        CompletableFuture<String> future02 = CompletableFuture.supplyAsync(()->{
            System.out.println("查询当前商品的属性信息");
            return "黑色+256G";
        }, executor);
        CompletableFuture<String> future03 = CompletableFuture.supplyAsync(()->{
            System.out.println("查询当前商品的品牌信息");
            return "华为";
        }, executor);
        CompletableFuture<Object> future = CompletableFuture.anyOf(future01, future02, future03);
        System.out.println("执行结果：" + future.get());
        System.out.println("future01:"+ future01.get());
        System.out.println("future02:"+ future02.get());
        System.out.println("future03:"+ future03.get());
        System.out.println("main...end");
    }
}