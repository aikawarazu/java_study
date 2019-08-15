package goodmi.study.multithread.atomic;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class FieldUpdaterTest {

  static class Model {

    volatile String f1;
    volatile Integer f2 = 1;

    Model() {

    }
  }

  public static void main(String[] args) {
    AtomicReferenceFieldUpdater<Model, String> f1 = AtomicReferenceFieldUpdater.newUpdater(Model.class, String.class, "f1");
    AtomicReferenceFieldUpdater<Model, Integer> f2 = AtomicReferenceFieldUpdater.newUpdater(Model.class, Integer.class, "f2");

    Model obj = new Model();
    f1.getAndAccumulate(obj, "iii", (s, s2) -> s + s2);
    System.out.println(obj.f1);
    f1.getAndAccumulate(obj, "iii", (s, s2) -> s + s2);
    System.out.println(obj.f1);
    f1.getAndAccumulate(obj, "iii", (s, s2) -> s + s2);
    System.out.println(obj.f1);
    f2.getAndAccumulate(obj, 1, (s, s2) -> s + s2);
    System.out.println(obj.f2);
    f2.getAndAccumulate(obj, 1, (s, s2) -> s + s2);
    System.out.println(obj.f2);
    f2.getAndAccumulate(obj, 1, (s, s2) -> s + s2);
    System.out.println(obj.f2);
  }
}
