package goodmi.study.multithread.communication;

import java.io.IOException;
import java.io.PipedWriter;

public class PipedSender extends Thread {

  private PipedWriter pipedWriter;

  public PipedSender(PipedWriter pipedWriter) {
    super("Sender");
    this.pipedWriter = pipedWriter;
  }

  @Override
  public void run() {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < 1000; i++) {
        String x = String.valueOf(i);
        stringBuilder.append(x);
        pipedWriter.write(x);
        System.out.println("send:"+x);
      }
      String str = stringBuilder.toString();
      System.out.println("str.length():"+str.length());
      pipedWriter.write(str);
    } catch (IOException e) {
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
