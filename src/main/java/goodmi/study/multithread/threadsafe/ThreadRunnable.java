package goodmi.study.multithread.threadsafe;

public class ThreadRunnable implements Runnable {
  private int i = 0;
  @Override
  public void run() {
    System.out.println(i++);
    try {
      Thread.sleep(i);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  protected int getI() {
    return i;
  }
}
