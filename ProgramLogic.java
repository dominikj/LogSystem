package LogClient;

public class ProgramLogic {
	private MainWindow mainW;
	private ConnectionSettings ConSet;
	private int port;
	private String ipAddress;
 public ProgramLogic() {
	mainW = new MainWindow(this);
}
 public void runConSetWin(){
	 ConSet = new ConnectionSettings();
 }
}
