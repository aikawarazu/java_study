package goodmi.study.multithread.communication;

public class BackupAThread implements Runnable {

  private DBService dbService;

  public BackupAThread(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public void run() {
    dbService.backupA();
  }
}
