package goodmi.study.multithread.communication;

public class BackupBThread implements Runnable {

  private DBService dbService;

  public BackupBThread(DBService dbService) {
    this.dbService = dbService;
  }

  @Override
  public void run() {
    dbService.backupB();
  }
}
