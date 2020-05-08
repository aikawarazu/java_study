package goodmi.study.multithread.communication;

/**
 * 懒加载实现
 */
public class LazyInitImplDemo {

  private LazyInitImplDemo() {
  }

  class Context {

  }

  private volatile Context context;

  public Context getContext() {
    if (context == null) {
      synchronized (this) {
        if (context == null) {
          context = new Context();
        }
      }
    }
    return context;
  }
}
