package goodmi.study.multithread.executors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    // 执行shutdown，如果线程池的线程没执行完，则isTerminated 返回false；isShutdown 返回true
    // 会发送interrupt信号给线程池的线程
    // 如果有在wait、sleep的线程，则会抛出interruptException
    testTerminal(ExecutorService::shutdownNow);
  }


  /**
   * 开启一个线程，该线程等待3秒，同时在主线程执行 shutdownFunction 方法。
   */
  private static void testTerminal(Consumer<ExecutorService> shutdownFunction) throws Exception {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    CountDownLatch countDownLatch = new CountDownLatch(1);
    executorService.submit(() -> {
      try {

        countDownLatch.countDown();
        System.out.println("#### Executing:" + Thread.currentThread());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("#### Executing Over:" + Thread.currentThread());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    countDownLatch.await();
    System.out.println("before shutdown,isTerminated:" + executorService.isTerminated());
    shutdownFunction.accept(executorService);
    System.out.println("after shutdown,isTerminated:" + executorService.isTerminated() + ",is shutdown:" + executorService.isShutdown());
    TimeUnit.SECONDS.sleep(1);
    System.out.println("after shutdown and wait,isTerminated:" + executorService.isTerminated() + ",is shutdown:" + executorService.isShutdown());

  }
}
