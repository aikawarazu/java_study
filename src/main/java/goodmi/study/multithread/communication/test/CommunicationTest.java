package goodmi.study.multithread.communication.test;


import goodmi.study.multithread.communication.Consumer;
import goodmi.study.multithread.communication.PipedReceiver;
import goodmi.study.multithread.communication.PipedSender;
import goodmi.study.multithread.communication.Producer;
import goodmi.study.multithread.communication.ProductRepository;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管道流
 */
public class CommunicationTest {

  public static void main(String[] args) {
//    testConsumer();
    testPiped();
  }

  private static void testPiped() {
    PipedReader pipedReader = new PipedReader();
    PipedWriter pipedWriter = new PipedWriter();
    try {
      pipedWriter.connect(pipedReader);
    } catch (IOException e) {
      e.printStackTrace();
    }
    new PipedReceiver(pipedReader).start();
    new PipedSender(pipedWriter).start();
  }

  private static void testConsumer() {
    ProductRepository productRepository = new ProductRepository();
    Consumer consumer = new Consumer(productRepository);
    Producer producer = new Producer(productRepository);

    for (int c = 0; c < 8; c++) {

      new Thread(() -> {
        while (true) {
          consumer.consume();
        }
      }, "消费者线程" + c).start();
      new Thread(() -> {
        while (true) {
          producer.produce();
        }
      }, "生产者线程" + c).start();
    }
  }
}
