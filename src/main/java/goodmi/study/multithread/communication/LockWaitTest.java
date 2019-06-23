package goodmi.study.multithread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockWaitTest {

  private static ReentrantLock reentrantLock = new ReentrantLock();
  private static Condition condition = reentrantLock.newCondition();
  private static Condition condition2 = reentrantLock.newCondition();

  static class WaitTask implements Runnable {

    public void run() {
      while (true) {
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        reentrantLock.lock();
        try {
          System.out.println("condition2 await start");
          condition.await();
          System.out.println("condition2 await end");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        reentrantLock.unlock();
      }
    }
  }

  static class SignalTask implements Runnable {

    public void run() {
      while (true) {
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        reentrantLock.lock();
        System.out.println("condition signal start");
        condition.signalAll();
        System.out.println("condition signal end");
        reentrantLock.unlock();
      }
    }
  }

  public static void main(String[] args) {
    new Thread(new WaitTask()).start();
    new Thread(new WaitTask()).start();
    new Thread(new SignalTask()).start();
  }
}
