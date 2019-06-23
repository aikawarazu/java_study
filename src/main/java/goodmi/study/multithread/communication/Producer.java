package goodmi.study.multithread.communication;

import goodmi.study.common.BaseService;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer extends BaseService {

  private final ProductRepository productRepository;

  public Producer(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  private AtomicInteger atomicInteger = new AtomicInteger();

  public void produce() {
    try {
      synchronized (productRepository){
        while (productRepository.productSize() == 10) {
          System.out.println(Thread.currentThread().getName()+("满了，等待"));
          productRepository.wait();
        }
        Map<String, Object> product = new HashMap<>();
        int andIncrement = atomicInteger.getAndIncrement();
        product.put("productKey", andIncrement);
        productRepository.addProduct(product);
        System.out.println(Thread.currentThread().getName()+" add : "+ product.toString());
        productRepository.notifyAll();
      }
      Thread.sleep(10);
    }catch (Exception e){
      logger.error("",e);
    }
  }
}
