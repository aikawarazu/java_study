package goodmi.study.multithread.communication;

/**
 * 单例实现类
 */
public class SingletonLazyLoadHolderImplDemo {

  private SingletonLazyLoadHolderImplDemo() {
  }

  private static class InstanceHolder {

    /**
     * 这里final的作用是？实际测试，没有final也不会有问题。但大家都这么写。
     */
    private static final SingletonLazyLoadHolderImplDemo singletonLazyLoadHolderImplDemo = newInstance();

    private static SingletonLazyLoadHolderImplDemo newInstance() {
      return new SingletonLazyLoadHolderImplDemo();
    }
  }

  public static SingletonLazyLoadHolderImplDemo instance() {
    return InstanceHolder.singletonLazyLoadHolderImplDemo;
  }
}
