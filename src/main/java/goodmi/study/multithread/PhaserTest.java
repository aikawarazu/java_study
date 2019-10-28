package goodmi.study.multithread;

import java.security.SecureRandom;
import java.util.concurrent.Phaser;

public class PhaserTest {


  private void sleepNs(int millis) {
    try {
      Thread.sleep(millis * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class Racer implements Runnable {

    private final Phaser phaser;
    private int studentId;

    private Racer(Phaser phaser) {
      this.phaser = phaser;
      phaser.register();
      studentId = phaser.getRegisteredParties();
    }

    @Override
    public void run() {
      int cost1 = waitRandom(500);
      System.out.println("student " + studentId + " 入场 end" + cost1 + "秒");
      phaser.arriveAndAwaitAdvance();
      int cost2 = waitRandom(700);
      System.out.println("student " + studentId + " 准备 end" + cost2 + "秒");
      phaser.arriveAndAwaitAdvance();
      int cost3 = waitRandom(200);
      System.out.println("student " + studentId + "比赛 end" + cost3 + "秒");
      phaser.arriveAndAwaitAdvance();
      int cost4 = waitRandom(200);
      System.out.println("student " + studentId + "颁奖 end" + cost4 + "秒");
      phaser.arriveAndAwaitAdvance();
    }
  }

  private static int waitRandom(int min) {
    try {
      int cost = min + secureRandom.nextInt(min*2);
      Thread.sleep(cost);
      return cost;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return 99999;
  }

  private static final SecureRandom secureRandom = new SecureRandom();

  /**
   * 假设赛跑比赛，6位选手，分为入场、准备、完成比赛、颁奖四步。
   */
  public static void main(String[] args) throws InterruptedException {
    Thread[] threads = new Thread[5];
    Phaser phaser = new Phaser() {
      @Override
      protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
          case 0:
            System.out.println("入场~complete");
            return false;
          case 1:
            System.out.println("准备~complete");
            return false;
          case 2:
            System.out.println("比赛~complete");
            return false;
          case 3:
            System.out.println("颁奖~complete");
            return true;
          default:
            return true;
        }
      }
    };
    for (int i = 0; i < threads.length; i++) {
      Thread thread = new Thread(new Racer(phaser));
      thread.setDaemon(true);
      thread.start();
      threads[i] = thread;
    }
    System.out.println("before，is over?"+phaser.isTerminated());
    for (Thread thread : threads) {
      thread.join();
    }
    System.out.println("after，is over?"+phaser.isTerminated());

  }
}
