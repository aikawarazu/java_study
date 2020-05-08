package goodmi.study.multithread.communication.test;

import goodmi.study.multithread.communication.SingletonUnsafeImplDemo3;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个线程写，一个线程读。 想测试新建对象时，对象初始化的重排序。却没测试出问题。 待改进
 */
public class TestNotSafe {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    executorService.submit(() -> {
      for (int j = 0; j < 10000; j++) {
        SingletonUnsafeImplDemo3.resetInstance();
      }
    });
    for (int i = 0; i < 10; i++) {
      executorService.submit(() -> {
        for (int j = 0; j < 10000; j++) {
          TestNotSafe.testGetI();
        }
      });
    }
    executorService.shutdown();
  }

  public static void testGetI() {
    SingletonUnsafeImplDemo3 instance = SingletonUnsafeImplDemo3.instance();
    if (instance == null) {
      return;
    }
    if (instance.getI() != 19) {
      System.out.println(Thread.currentThread().getName() + ":" + instance.getI());
    }
  }

}
