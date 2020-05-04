package goodmi.study.multithread.communication;

import java.io.IOException;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PipedSender extends Thread {

  private PipedWriter pipedWriter;

  public PipedSender(PipedWriter pipedWriter) {
    super("Sender");
    this.pipedWriter = pipedWriter;
  }
  Random random = new Random();

  @Override
  public void run() {
    try {
      int timeout = random.nextInt(5);
      TimeUnit.SECONDS.sleep(timeout);
      pipedWriter.write(timeout+"");
      System.out.println("send:"+timeout);
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }finally {
      try {
        pipedWriter.close();
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }
}
