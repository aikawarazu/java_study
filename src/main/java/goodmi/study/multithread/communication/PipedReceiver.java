package goodmi.study.multithread.communication;

import java.io.IOException;
import java.io.PipedReader;

public class PipedReceiver extends Thread {

  private PipedReader pipedReader;

  public PipedReceiver(PipedReader pipedReader) {
    super("Receiver");
    this.pipedReader = pipedReader;
  }

  @Override
  public void run() {
    try {
      char[] cbuf = new char[1024];
      int readLen;
      while ((readLen = pipedReader.read(cbuf)) != -1) {
        String x = new String(cbuf, 0, readLen);
        System.out.println("received:"+x);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        pipedReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
