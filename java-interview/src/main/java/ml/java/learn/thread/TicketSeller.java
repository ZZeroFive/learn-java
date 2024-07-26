package ml.java.learn.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.java.learn.common.Ticket;

/**
 * @Auther: jiml
 * @Date: 2024/7/26 08:31
 * @Description: 多线程安全演示
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSeller implements Runnable {
    // 共享的实体类
    private Ticket ticket;

    /**
     * 非线程安全的代码demo
     * 对一个含有100个座位的票务，开启三个站台的独立线程进行售卖
     * 查看没看线程售卖的情况
     * 线程安全的输出情形应该是 1000-999-998这种有序的
     */
    public void unSafetyThreads() {
        while (ticket.getWholeNum() > 0) {
            System.out.println(String.format("%-20s售出第%d张票", Thread.currentThread().getName(), ticket.getWholeNum()));
            ticket.setWholeNum(ticket.getWholeNum() - 1);
        }
    }

    /**
     * 线程安全的代码demo
     */
    public void safetyThreads() {
        while (ticket.getWholeNum() > 0) {
            synchronized (TicketSeller.class) {
                if (ticket.getWholeNum() == 0) {break;}
                System.out.printf("%-20s售出第%d张票%n", Thread.currentThread().getName(), ticket.getWholeNum());
                ticket.setWholeNum(ticket.getWholeNum() - 1);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void run() {
        // this.unSafetyThreads();
        this.safetyThreads();
    }


    public static void main(String[] args) {
        // 多线程共享的票务
        Ticket ticket = new Ticket("Peking", "Wuhan", 100);

        Thread t1 = new Thread(new TicketSeller(ticket), "Peking Station");
        Thread t2 = new Thread(new TicketSeller(ticket), "WuHan Station");
        Thread t3 = new Thread(new TicketSeller(ticket), "ChangSha Station");

        t1.start();
        t2.start();
        t3.start();
    }
}
