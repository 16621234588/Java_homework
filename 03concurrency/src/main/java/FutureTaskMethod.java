import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 2、FutureTask方式实现
 */
public class FutureTaskMethod {
    static class Get implements Callable<Integer> {
        private FutureTask<Integer> sum;

        public Get(FutureTask<Integer> sum) {
            this.sum = sum;
        }


        public Integer call() throws Exception {
            return sum.get();
        }
    }
    static class Sum implements Callable<Integer> {

        public Integer call() throws Exception {
            return fibo(45);
        }

        private int fibo(int n) {
            if (n < 2) {
                return 1;
            }
            return fibo(n - 1) + fibo(n -2);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        FutureTask<Integer> sum = new FutureTask<Integer>(new Sum());
        FutureTask<Integer> get = new FutureTask<Integer>(new Get(sum));
        new Thread(sum).start();
        new Thread(get).start();
        int result = get.get();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间为：" + (System.currentTimeMillis() - start) + "ms");

    }
}
