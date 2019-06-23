package goodmi.study.multithread.communication;

public class DBService {

  private volatile boolean prevIsA = false;

  public synchronized void backupA() {
    try {
      while (prevIsA) {
        wait();
      }
      prevIsA = true;
      notifyAll();
      System.out.println();
      for (int i = 0; i < 5; i++) {
        System.out.print("★");
      }
      System.out.println(Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public synchronized void backupB() {
    try {
      while (!prevIsA) {
        wait();
      }
      notifyAll();
      prevIsA = false;
      for (int i = 0; i < 5; i++) {
        System.out.print("✰");
      }
      System.out.println(Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
