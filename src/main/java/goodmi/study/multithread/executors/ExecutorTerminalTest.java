package goodmi.study.multithread.executors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 测试线程池的terminal方法
 */
public class ExecutorTerminalTest {

  public static void main(String[] args) throws Exception {

    // 执行shutdown，如果线程池的线程没执行完，则isTerminated 返回false；isShutdown 返回true
    // 不会发送interrupt信号给子线程
    testTerminal(ExecutorService::shutdown);
    printBlabla();
    // 执行shutdownNow，如果线程池的线程没执行完，则isTerminated 返回false；isShutdown 返回true
    // 会发送interrupt信号给线程池的线程
    // 如果有在wait、sleep的线程，则会抛出 interruptException
    // 它们的区别是一个不会发送interrupt信号，另一个会发送。
    testTerminal(ExecutorService::shutdownNow);
    printBlabla();
    System.out.println("--------------- 模拟线程池里有任务时，线程池调用 ExecutorService::shutdown");
    testTerminal2(ExecutorService::shutdown);
    printBlabla();
    System.out.println("--------------- 模拟线程池里有任务时，线程池调用 ExecutorService::shutdownNow");
    testTerminal2(ExecutorService::shutdownNow);
  }

  private static void printBlabla() {
    System.out.println();
    System.out.println();
    System.out.println();
    System.out.println();
  }

  /**
   * 模拟以下情况：<br> 一个线程池，里面有任务在排队。<br> 此时线程池关闭<br> 为了观察<br> 1. 里面已有在排队的的任务会如何处理。<br> 2. 新加入的task会如何处理
   * <br> 结论： 执行关闭线程后，里面已有在排队的的任务 和 新加入的task都会 被分配给线程池的RejectedExecutionHandler处理
   *
   * @param shutdownFunction 关闭线程池的回调方法。
   */
  private static void testTerminal2(Consumer<ExecutorService> shutdownFunction) throws InterruptedException {
    ExecutorService executorService = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
        (r, executor) -> System.out.println("rejected:" + r));
    // 模拟四个耗时任务
    int i = 0;
    executorService.submit(newTask("任务" + (++i)));
    executorService.submit(newTask("任务" + (++i)));
    executorService.submit(newTask("任务" + (++i)));
    executorService.submit(newTask("任务" + (++i)));
    // 停止线程池
    System.out.println("to stop thread pool");
    shutdownFunction.accept(executorService);
    System.out.println("thread pool stopped");

    // 在shutdown后，再添加任务，此时会调用 RejectedHandler 。
    executorService.submit(newTask("shutdown后新加的任务"));

    executorService.awaitTermination(10, TimeUnit.SECONDS);
  }

  private static Runnable newTask(String rName) {
    return new Runnable() {
      @Override
      public String toString() {
        return rName;
      }

      @Override
      public void run() {
//      HttpClient httpClient = new HttpClientImpl();
        // 模拟耗时操作
        System.out.println(Thread.currentThread().getName() + "start task");
        try {
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
          System.err.println(Thread.currentThread().getName() + "InterruptedException>>>>");
        }
        System.out.println(Thread.currentThread().getName() + "task executed");
      }
    };
  }


  /**
   * 开启一个线程，该线程等待3秒，同时在主线程执行 shutdownFunction 方法。
   */
  private static void testTerminal(Consumer<ExecutorService> shutdownFunction) throws Exception {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    CountDownLatch countDownLatch = new CountDownLatch(3);
    for (int i = 0; i < 3; i++) {
      executorService.submit(() -> {
        try {
          countDownLatch.countDown();
          System.out.println("#### start Executing:" + Thread.currentThread());
          TimeUnit.SECONDS.sleep(3);
          System.out.println("#### Executing Over:" + Thread.currentThread());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
    }
    countDownLatch.await();
    System.out.println("before shutdown,isTerminated:" + executorService.isTerminated());
    shutdownFunction.accept(executorService);
    System.out.println("after shutdown,isTerminated:" + executorService.isTerminated() + ",is shutdown:" + executorService.isShutdown());
    TimeUnit.SECONDS.sleep(10);
    System.out.println("after shutdown and wait,isTerminated:" + executorService.isTerminated() + ",is shutdown:" + executorService.isShutdown());
    executorService.awaitTermination(10, TimeUnit.SECONDS);

  }
}
