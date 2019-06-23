package goodmi.study.security;

import java.util.Random;
import org.junit.Test;

public class RandomTest {
  private Random random = new Random(1111);
  private Random random2 = new Random(1111);
  @Test
  public void testRandom1(){
    System.out.println("test 1:"+random);
    System.out.println("random1:"+random.nextInt(100));
    System.out.println("random1:"+random.nextInt(100));
    System.out.println("random1:"+random.nextInt());
    System.out.println("random2:"+random2.nextInt());
    System.out.println("random2:"+random2.nextInt());
    System.out.println("random2:"+random2.nextInt());
  }
}
