package goodmi.study.multithread.executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

public class ExecutorTest {

  public static void testFixedThreadPool() throws InterruptedException {

    ExecutorService executorService = Executors.newFixedThreadPool(5, new ThreadFactory() {
      int i = 0;

      @Override
      public Thread newThread(Runnable r) {
        String name = "Thread-" + (++i);
        System.out.println(name + "created");
        return new Thread(r, name);
      }
    });
    executeTask(executorService);

    System.out.println("task put to end ");
//    Thread.sleep(200);
    executorService.shutdown();
    System.out.println("task put ended ");


  }

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    // 线程池

    System.out.println("==========testFixedThreadPool()");
    testFixedThreadPool();
    Thread.sleep(1000);

    System.out.println("==========testThreadPoolRejectPolicy()");
    testThreadPoolRejectPolicy();
    Thread.sleep(1000);

    System.out.println("==========testSingleThreadPool()");
    testSingleThreadPool();
//    System.out.println("testScheduleThreadPool()");
//    testScheduleThreadPool();
    System.out.println("==========testCompleteService()");
    testCompleteService();
  }

  private static void testThreadPoolRejectPolicy() {
    ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, 1, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),
        new ThreadFactoryBuilder().setNameFormat("test_reject-pool-%d").build(), (r, executor) -> {
      if (!executor.isShutdown()) {
        r.run();
      }
    });
    executeTask(executorService);

    System.out.println("task put to end ");
//    Thread.sleep(200);
    executorService.shutdown();
    System.out.println("task put ended ");


  }

  /**
   * 等待完成线程池，该线程池能按照先后顺序给返回执行结果
   */
  private static void testCompleteService() throws InterruptedException, ExecutionException {
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, Integer.MAX_VALUE,
        60L, TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(3, false));
    ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<>(threadPoolExecutor);
    Random random = new Random();
    int taskSize = 10;
    for (int i = 0; i < taskSize; i++) {
      int finalI = i;
      executorCompletionService.submit(() -> {
        int i1 = random.nextInt(10);
        Thread.sleep(i1 * 1000);
//        if (i1 < 5) {
//          throw new RuntimeException();
//        }
        return Thread.currentThread().getName() + "task- " + finalI + " complete";
      });
    }
    threadPoolExecutor.shutdown();
    for (int i = 0; i < taskSize; i++) {
      System.out.println(executorCompletionService.take().get());
    }
  }

  /**
   * 单线程池
   */
  private static void testSingleThreadPool() throws InterruptedException {

    ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
      int i = 0;

      @Override
      public Thread newThread(@Nonnull Runnable r) {
        String name = "SingleThread-" + (++i);
        System.out.println(name + "created");
        return new Thread(r, name);
      }
    });
    executeTask(executorService);

    System.out.println("  task put to end");
    Thread.sleep(500);
    executorService.shutdownNow();

    System.out.println("  task put ended ");

  }

  private static void testScheduleThreadPool() {

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3, new ThreadFactory() {
      int i = 0;

      @Override
      public Thread newThread(@Nonnull Runnable r) {
        String name = "ScheduledExecutorService-" + (++i);
        System.out.println(name + "created");
        return new Thread(r, name);
      }
    });
    executeTask(runnable -> scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 2, TimeUnit.SECONDS));
  }

  private static void executeTask(ExecutorService executorService) {
    executeTask(executorService::submit);
  }

  // 模拟实际运行内容，sleep 0.1s
  private static void executeTask(Consumer<Runnable> consumer) {
    for (int i2 = 0; i2 < 10; i2++) {
      int finalI = i2;
      System.out.println("put::::task:" + finalI);
      consumer.accept(() -> {
        try {
          Thread.sleep(100);
          System.out.println(Thread.currentThread().getName() + "::::task:" + finalI);
        } catch (InterruptedException e) {
          System.err.println(Thread.currentThread().getName() + "::::task:" + finalI + " ERRRRRRRR");
        }
      });
    }
  }


}
