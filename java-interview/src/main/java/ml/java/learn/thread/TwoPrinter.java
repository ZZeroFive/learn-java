package ml.java.learn.thread;

/**
 * @Auther: jiml
 * @Date: 2024/7/22 21:24
 * @Description: 实现两个线程交替打印奇偶数
 */


public class TwoPrinter {

    // 打印奇数
    public static void printOdd() {
        for (int i = 1; i < 100; i++) {
            if ( i % 2 == 1) {
                synchronized (TwoPrinter.class) {
                    System.out.println(Thread.currentThread().getName()+" "+ i); // 打印奇数
                    TwoPrinter.class.notifyAll(); // 唤醒其他等待当前锁的线程
                    try {
                        TwoPrinter.class.wait();// 进入等待模式
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        /*synchronized (TwoPrinter.class) {
            TwoPrinter.class.notifyAll();
        }*/
    }
    // 打印偶数
    public static void printEven() {
        for (int i = 1; i < 100; i++) {
            if ( i % 2 == 0) {
                synchronized (TwoPrinter.class) {
                    System.out.println(Thread.currentThread().getName()+" "+ i);
                    TwoPrinter.class.notifyAll();
                    try {
                        TwoPrinter.class.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        synchronized (TwoPrinter.class) {
            TwoPrinter.class.notifyAll();
        }

    }

    public static void main(String[] args) {

        Thread evenThread = new Thread(TwoPrinter::printEven, "even");
        evenThread.start();

        Thread oddThread = new Thread(TwoPrinter::printOdd, "odd");
        oddThread.start();
    }
}
