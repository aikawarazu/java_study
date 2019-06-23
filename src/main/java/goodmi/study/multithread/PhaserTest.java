package goodmi.study.multithread;

import java.security.SecureRandom;
import java.util.concurrent.Phaser;

public class PhaserTest {


  private void sleepNs(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static class Racer implements Runnable {

    private final Phaser phaser;

    private Racer(Phaser phaser) {
      this.phaser = phaser;
      phaser.register();
    }

    @Override
    public void run() {
      int cost1 = waitRandom(1000);
      System.out.println(Thread.currentThread().getName() + " 入场 start"+ cost1 + "秒");
      phaser.arriveAndAwaitAdvance();
      System.out.println("~~~"+Thread.currentThread().getName());
      int cost2 = waitRandom(7000);
      System.out.println(Thread.currentThread().getName() + " 准备 start"+ cost2 + "秒");
      phaser.arriveAndAwaitAdvance();
      System.out.println("~~~"+Thread.currentThread().getName());
      int cost3 = waitRandom(2000);
      System.out.println(Thread.currentThread().getName() + "开始比赛 start"+ cost3 + "秒");
      phaser.arriveAndAwaitAdvance();
      System.out.println("颁奖了");
    }
  }

  private static int waitRandom(int min) {
    try {
      int cost = min + secureRandom.nextInt(5000);
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
  public static void main(String[] args) {
    Phaser phaser = new Phaser();
    Racer racer1 = new Racer(phaser);
    Racer racer2 = new Racer(phaser);
    Racer racer3 = new Racer(phaser);
    new Thread(racer1).start();
    new Thread(racer2).start();
    new Thread(racer3).start();
  }
}
