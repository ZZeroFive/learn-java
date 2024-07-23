package ml.java.learn.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: jiml
 * @Date: 2024/7/23 20:44
 * @Description:
 */


public class ThreePrinter {
    // 实现三个线程交替打印 1 2 3 4 5 6
    
    // 思路：和两个线程交替打印一样，只不过需要三个锁实现线程间的通信与协作
    
    // 线程A获得锁1，打印完后，释放锁2，等待锁1；
    // 线程B获得锁2，打印完后，释放锁3，等待锁2；
    // 线程C获得锁3，打印完后，释放锁1，等待锁3
    
    private static String lockA = "A";
    private static String lockB = "B";
    private static String lockC = "C";
    
    public static void printOne()  {
        for (int i = 1; i < 100; i++) {
            if ( i % 3 == 1) {
                synchronized (lockC) {
                    // 其实是释放了两把锁 一个执行完释放 一个是主动让出锁，程序阻塞在wait()方法处
                    synchronized (lockA) {
                        System.out.println(i);
                        lockA.notifyAll();
                    }

                    try {
                        lockC.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void printTwo()  {
        for (int i = 1; i < 100; i++) {
            if ( i % 3 == 2) {
                // B线程要等待A线程打印完打印
                synchronized (lockA) {
                    // 唤醒等待锁B的C线程 且释放锁
                    // 执行notify前必须拥有锁
                    synchronized (lockB) {
                        System.out.println(i);
                        lockB.notifyAll();
                    }
                    if (i == 98) {break;}
                    try {
                        // 本身释放锁A 等待下一次执行
                        lockA.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void printThree()  {
        for (int i = 1; i < 100; i++) {
            if ( i % 3 == 0) {
                synchronized (lockB) {
                    synchronized (lockC) {
                        System.out.println(i);
                        lockC.notifyAll();
                    }
                    if (i == 99) {break;}
                    try {
                        lockB.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(ThreePrinter::printOne, "one");
        t1.start();
        Thread t2 = new Thread(ThreePrinter::printTwo, "two");
        t2.start();
        Thread t3 = new Thread(ThreePrinter::printThree, "three");
        t3.start();
    }
}
