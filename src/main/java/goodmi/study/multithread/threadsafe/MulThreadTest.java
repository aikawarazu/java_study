package goodmi.study.multithread.threadsafe;

import com.google.common.primitives.Longs;
import org.junit.After;
import org.junit.Test;

public class MulThreadTest {

  class InterruptRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println(Thread.currentThread() + "isInterrupted:" + Thread.currentThread().isInterrupted());
      Thread.currentThread().interrupt();
      System.out.println(Thread.currentThread() + "isInterrupted:" + Thread.currentThread().isInterrupted());
      System.out.println(Thread.currentThread() + "isInterrupted:" + Thread.currentThread().isInterrupted());
      System.out.println(Thread.currentThread() + "Thread.interrupted:" + Thread.interrupted());
      System.out.println(Thread.currentThread() + "Thread.interrupted:" + Thread.interrupted());
    }
  }

  @Test
  public void testInterruptStatus() {
    Thread thread = new Thread(new InterruptRunnable());
    thread.setDaemon(false);
    thread.start();
  }

  @After
  public void tearDown() throws Exception {
    try {
      System.out.println("waiting tear down"+Thread.currentThread().getThreadGroup().getName()+":"+Thread.currentThread().getName()+Thread.currentThread().getThreadGroup().activeCount());
      Thread.sleep(10000);
      System.out.println("over");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testInterruptWaitThread() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (this) {
          try {
            long i = 0L;
            long wasteTimeResult = 1L;
            while (i++ < 100L) {
              wasteTimeResult = (wasteTimeResult+i)*i;
            }
            System.out.println("wasteTimeResult:"+wasteTimeResult);
            System.out.println(Thread.currentThread().getName()+"--wait at :" + System.currentTimeMillis());
            // 在wait之前调用interrupt，wait之后，会马上抛出interruptedException
            Thread.currentThread().interrupt();
            wait(100);
//            Thread.sleep(1);
            System.out.println("wait over");
          } catch (InterruptedException e) {
            System.out.println("interrupted Exception interrupted:" + Thread.currentThread().isInterrupted());
            e.printStackTrace(System.out);
          }
        }
      }
    });
    thread.setDaemon(false);
    thread.start();

//    System.out.println("toInterrupt"+"--wait");
////    thread.interrupt();
//    System.out.println("main to do interrupted at :"+ System.currentTimeMillis());
  }

  @Test
  public void testAlive() {
    ThreadRunnable threadRunnable = new ThreadRunnable();
    for (int i = 0; i < 10; i++) {
      Thread thread = new Thread(threadRunnable);
      System.out.println("before:" + thread.isAlive());
      thread.start();
      System.out.println("start:" + thread.isAlive());
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      thread.start();
      System.out.println("after sleep:" + thread.isAlive());
    }
    System.out.println("i111:" + threadRunnable.getI());

  }

}
