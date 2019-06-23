package goodmi.study.multithread.communication.test;

import goodmi.study.multithread.communication.BackupAThread;
import goodmi.study.multithread.communication.BackupBThread;
import goodmi.study.multithread.communication.DBService;

public class BackupTest {

  public static void main(String[] args) {
    DBService dbService = new DBService();
    for (int i = 0; i < 50; i++) {
      new Thread(new BackupAThread(dbService)).start();
      new Thread(new BackupBThread(dbService)).start();
    }
  }
}
