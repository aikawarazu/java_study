package goodmi.study.multithread.communication;

import goodmi.study.common.BaseService;

public class Consumer extends BaseService {

  private final ProductRepository productRepository;

  public Consumer(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void consume() {
    try {
      synchronized (productRepository) {
        while (productRepository.productSize() == 0) {
          System.out.println(Thread.currentThread().getName()+("等待"));
          productRepository.wait();
        }
        Object product = productRepository.take();
        System.out.println(Thread.currentThread().getName()+" consume product:" + product.toString());
        productRepository.notifyAll();
      }
      Thread.sleep(1);
    } catch (Exception e) {
      logger.error("", e);
    }
  }

}
