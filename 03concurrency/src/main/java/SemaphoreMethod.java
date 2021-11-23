import java.util.concurrent.Semaphore;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 *
 * 8、Semaphore方式
 */
public class SemaphoreMethod {
    private volatile Integer value = null;
    private final Semaphore semaphore = new Semaphore(1);

     //    生成初始化的时候先把锁给拿了
     //      而sum和get方法中，get需要锁，sum不需要锁
     //      sum不需要锁就可以执行，执行完之后释放锁
     //      get在没有锁释放的情况下，一定不能执行，也就是只有在sum释放锁后才能执行
    SemaphoreMethod() throws InterruptedException {
        semaphore.acquire();
    }

    public void sum(int a) {
//        Thread.sleep(5000);
        value = fibo(a);
        semaphore.release();
    }

    private int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }

    public int getValue() throws InterruptedException {
        int result;
        semaphore.acquire();
        result = this.value;
        semaphore.release();
        return result;
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final SemaphoreMethod semaphoreMethod = new SemaphoreMethod();
        Thread thread = new Thread(() -> {
            semaphoreMethod.sum(45);
        });
        thread.start();
        int result = semaphoreMethod.getValue(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

}
