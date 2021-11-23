import java.util.concurrent.*;
/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 3、Future实现方式
 */
public class FutureMethod implements Callable<Integer> {

    private int sum(int sum) {
        return fibo(sum);
    }


    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
    public Integer call() throws Exception {
        return sum(43);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

//        int result = fibo(43); // 单线程

        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Integer> future = executorService.submit(new FutureMethod());
        Integer result = future.get();


        System.out.println("拿到计算结果为：" + result); // 拿到计算结果为：701408733

        System.out.println("执行时间:" + (System.currentTimeMillis() - start)); // 执行时间:2270毫秒
//        executorService.shutdown();//

    }
}
