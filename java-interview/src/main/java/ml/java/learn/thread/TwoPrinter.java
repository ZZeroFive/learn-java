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
        synchronized (TwoPrinter.class) {
            TwoPrinter.class.notifyAll();
        }
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

    /*
    * 分析一下为什么先运行打印偶数的线程，程序最终无法停止
    * 先运行偶数线程，线程执行的逻辑：
    * i = 1，不需要打印
    * i = 2，需要打印，争占锁资源后打印，然后唤醒打印奇数线程，自己进入阻塞状态
    *
    * 此时奇数线程开始执行：<奇数线程不一定是偶数线程wait后才开始执行，可能提前已经执行了，但是需要获取锁资源打印数字1>
    * i = 1，需要打印，争占锁资源后打印，然后唤醒偶数线程，自己进入阻塞状态
    *
    * 所以先打印偶数，程序输出是 2 1 4 3 6 5...98 97 _ 99
    *
    * 当偶数线程打印完98，奇数线程此刻阻塞在打印完95后，
    * 偶数线程打印完98，奇数线程被唤醒，
    * 奇数线程执行逻辑：
    * i = 96，不需要打印
    * i = 97，需要打印，争占锁资源后，进入阻塞状态
    *
    * 偶数线程被唤醒，继续执行，执行逻辑：
    * i = 99，不需要打印
    * i = 100，跳出循环。此时偶数已经跳出循环，而奇数还在i=97处等待
    * 所以偶数线程先执行，那么偶数线程就会先执行完毕，奇数线程就还会处于阻塞状态，所以需要偶数线程在跳出循环后，再次唤醒一次奇数线程。
    * 即使这样还不够? 可以先提交一次代码记录，看看会发生什么
    *
    * 程序输出的结果：
    * odd 97
    * odd 99
    * 正如上面分析的，奇数线程打印完97后，偶数线程其实98已经打印完了
    * 奇数此时让出锁资源，偶数线程不会打印100，并唤醒处于阻塞的奇数线程
    * 打印奇数的线程从97开始运行，98不打印，99打印完后又进入阻塞状态，所以此时打印奇数的线程仍然无法结束
    *
    * */
    public static void main(String[] args) {

        Thread evenThread = new Thread(TwoPrinter::printEven, "even");
        evenThread.start();

        Thread oddThread = new Thread(TwoPrinter::printOdd, "odd");
        oddThread.start();
    }
}
