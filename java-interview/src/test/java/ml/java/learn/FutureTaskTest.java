package ml.java.learn;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Auther: jiml
 * @Date: 2024/7/28 10:15
 * @Description: FutureTask异步计算学习
 */


public class FutureTaskTest {

    // 通过FutureTask计算1到100的和
    public static int calc() {
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * FutureTask传递给线程使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testCreatFutureTask() throws ExecutionException, InterruptedException {
        // 创建FutureTask异步任务
        FutureTask<Integer> ft = new FutureTask<Integer>(FutureTaskTest::calc);
        // ft可以当作Runnable实例传递给thread
        Thread thread = new Thread(ft, "thread-run");
        // thread执行时将调用ft.run()方法，ft.run()方法调用成员变量callable.call()方法
        // 这里也就是FutureTaskTest::calc()
        thread.start();

        // 阻塞test线程获取任务结果
        Assert.assertNotNull(ft.get());
        System.out.printf("%s线程获取结果:%d\n", Thread.currentThread().getName(), ft.get());
    }

    /**
     * Callable实例直接传递给线程池执行
     */
    @Test
    public void testUseExecutors1() throws ExecutionException, InterruptedException {
        // 这里简单使用一个固定大小的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 直接传递Callable实例
        // Future<Integer> ft = executorService.submit(FutureTaskTest::calc);

        // 传递FutureTask实例
        FutureTask<Integer> ft = new FutureTask<Integer>(FutureTaskTest::calc);
        Future<?> submit = executorService.submit(ft);
        // 阻塞test线程获取任务结果
        Assert.assertNotNull(ft.get());
        System.out.printf("%s线程获取结果:%d\n", Thread.currentThread().getName(), ft.get());

        executorService.shutdown();
    }

    /**
     * 传递FutureTask实例
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testUseExecutors2() throws ExecutionException, InterruptedException {
        // 这里简单使用一个固定大小的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 传递FutureTask实例
        FutureTask<Integer> ft = new FutureTask<Integer>(FutureTaskTest::calc);
        // FutureTask可以传递给线程池的原因是因为 FutureTask是一个Runnable实例
        Future<?> submit = executorService.submit(ft);

        // 通过FutureTask异步计算本身是可以获取计算结果的
        Assert.assertNotNull(ft.get());
        System.out.printf("%s线程获取结果:%d\n", Thread.currentThread().getName(), ft.get());
    }


    @Test
    public void testUseExecutors3() throws ExecutionException, InterruptedException {
        // 这里简单使用一个固定大小的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 传递FutureTask实例
        FutureTask<Integer> ft = new FutureTask<Integer>(FutureTaskTest::calc);
        // FutureTask可以传递给线程池的原因是因为 FutureTask是一个Runnable实例
        Future<?> submit = executorService.submit(ft);

        // 但是通过submit却无法获取结果
        // 原因是因为什么传递的是一个Runnable实例，Runnable默认是没返回值的
        Assert.assertNotNull(submit.get());
        System.out.printf("%s线程获取结果:%d\n", Thread.currentThread().getName(), submit.get());
        executorService.shutdown();
    }

    @Test
    public void testUseExecutors4() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        FutureTask<Integer> ft = new FutureTask<Integer>(FutureTaskTest::calc);
        // FutureTask可以传递给线程池的原因是因为 FutureTask是一个Runnable实例
        int result = 99;
        Future<Integer> submit = executorService.submit(ft, result);
        // submit.get()返回的是给定的结果
        // The Future's get method will return the given result upon successful completion.
        Assert.assertNotNull(submit.get());
        System.out.printf("%s线程获取结果:%d\n", Thread.currentThread().getName(), result);
        executorService.shutdown();
    }

}
