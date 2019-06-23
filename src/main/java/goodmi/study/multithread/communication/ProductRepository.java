package goodmi.study.multithread.communication;

import com.google.common.collect.Lists;
import java.util.List;

public class ProductRepository {

  private final List<Object> products = Lists.newLinkedList();

  void addProduct(Object object) {
    products.add(object);
  }

  Object take() {
    return products.remove(0);
  }

  int productSize() {
    return products.size();
  }

}
