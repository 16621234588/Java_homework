import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 4、Lock Condition方式实现
 */
public class LockConditionMethod {
    private volatile Integer value = null;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void sum(int a) {
        lock.lock();
        try {
            value = fibo(a);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public int getValue() throws InterruptedException {
        lock.lock();
        try {
            while (value == null) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        return value;
    }

    private int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final LockConditionMethod conditionMethod = new LockConditionMethod();
        Thread thread = new Thread(() -> {
            conditionMethod.sum(45);
        });
        thread.start();
        int result = conditionMethod.getValue(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

}
