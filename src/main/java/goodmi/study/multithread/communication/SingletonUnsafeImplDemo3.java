package goodmi.study.multithread.communication;

import java.io.File;

public class SingletonUnsafeImplDemo3 {

  private int i;

  private SingletonUnsafeImplDemo3() {
    if (!new File("D:\\notExists").exists()) {
      i = 19;
    }
  }

  private static SingletonUnsafeImplDemo3 singletonLazyLoadHolderImplDemo;

  public static SingletonUnsafeImplDemo3 instance() {
    return singletonLazyLoadHolderImplDemo;
  }

  public static void resetInstance() {
    singletonLazyLoadHolderImplDemo = new SingletonUnsafeImplDemo3();
  }

  public int getI() {
    return i;
  }
}
