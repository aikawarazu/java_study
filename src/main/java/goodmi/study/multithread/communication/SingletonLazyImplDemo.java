package goodmi.study.multithread.communication;

/**
 * 懒加载单例实现
 */
public class SingletonLazyImplDemo {

  private SingletonLazyImplDemo() {
  }

  private static volatile SingletonLazyImplDemo singletonLazyImplDemo;

  private static SingletonLazyImplDemo newInstance() {
    if (singletonLazyImplDemo == null) {
      synchronized (SingletonLazyImplDemo.class) {
        if (singletonLazyImplDemo == null) {
          singletonLazyImplDemo = new SingletonLazyImplDemo();
        }
      }
    }
    return singletonLazyImplDemo;
  }
}
