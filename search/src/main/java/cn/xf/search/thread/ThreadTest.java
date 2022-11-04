package cn.xf.search.thread;

import org.elasticsearch.threadpool.ThreadPool;

import java.util.concurrent.*;

public class ThreadTest {
    // 使用工具类生成线程池
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * ---------继承Thread类实现线程创建---------
         * ExtendThread thread = new ExtendThread();
         * thread.start();
         */

        /**
         * --------- 实现Runnable接口创建线程 ---------
         *
         * ImplRunnable runnable = new ImplRunnable();
         * new Thread(runnable).start();
         */

        /**
         * --------- 实现Callable接口创建线程 ---------
         * 借助FutrureTash
         * FutureTask<String> task = new FutureTask<>(new ImplCallable());
         * new Thread(task).start();
         * try {
         *     System.out.println(task.get());
         * } catch (InterruptedException e) {
         *     throw new RuntimeException(e);
         * } catch (ExecutionException e) {
         *     throw new RuntimeException(e);
         * }
         */

        /**
         * 普通创建线程池方法
         * 七大参数：
         *  1.corePoolSize：线程核心数量，默认创建的线程数量，且线程池空闲也会保留
         *  2.maximimPoolSize：最大线程数，线程池最大能同时执行的线程数量
         *  3.keepAliveTime：保持存活时间，非核心线程空闲时允许存活时间
         *  4.unit：时间单位
         *  5.workQueue：用于保存未执行线程的队列
         *  6.threadFactory：用于执行创建新线程
         *  7.handler：拒绝执行策略
         *<p>
         * 执行顺序：
         *  1.创建线程池，准备核心线程数量，接收任务
         *  2.当核心线程数被占满后，将其他待执行任务放入执行队列中
         *  3.若执行队列放满后，开启新线程，最大只能开到max
         *  4.如果max也存满，则使用拒绝策略拒绝任务
         *  5.任务执行完成后，除核心线程外，空闲线程在指定存活时间后进行回收
         *
         *  在创建执行队列时，默认使用Integer最大值
         */
        /*ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,
                10,
                20,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );*/

        // 线程池执行方法
        // executor.execute(new ImplRunnable());

        // ---------------------- 异步编排 ----------------------
        // 无返回值
//        CompletableFuture.runAsync(()->{
//            System.out.println("asdfasf");
//        },executor);

        // 有返回值
        // 方法执行完成后回调与感知
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int res = 10 / 1;
            return res;
        }, executor).whenComplete((res, error) -> { // 执行完成后的回调方法
            System.out.println("使用whenComplete获取返回结果" + res + "出现的异常为：" + error);
        }).exceptionally(throwable -> {
            System.out.println("出现异常赋予默认值：" + 10);
            return 1;
        });
        // 方法执行完成后的处理--handle
        CompletableFuture<Integer> handle = CompletableFuture.supplyAsync(() -> {
            int res = 10 / 1;
            return res;
        }, executor).handle((res, error) -> {
            if (res != null) {
                res = res * 3;
            }
            if (error != null) {
                res = 0;
            }
            return res;
        });



        System.out.println(completableFuture.get());

    }

    public static class ExtendThread extends Thread {
        @Override
        public void run() {
            System.out.printf("继承Thread类%s", Thread.currentThread().getId());
        }
    }

    public static class ImplRunnable implements Runnable {
        @Override
        public void run() {
            System.out.printf("实现Runnable接口%s", Thread.currentThread().getId());
        }
    }

    public static class ImplCallable implements Callable {

        @Override
        public String call() throws Exception {
            return "支持返回结果";
        }
    }
}
