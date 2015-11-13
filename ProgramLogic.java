package LogClient;

import java.io.IOException;
import java.util.ArrayList;

public class ProgramLogic {
	private MainWindow mainW;
	private ConnectionSettings ConSet;
	public int port;
	public String ipAddress;
	public Connection con;
	public ArrayList<ArrayList<String>> logs;
 public ProgramLogic() {
	mainW = new MainWindow(this);
	con = new Connection();
}
 public void runConSetWin(){
	 ConSet = new ConnectionSettings(this);
 }
 public void getLogs(){
	 try {
		logs = con.getLogs();
	} catch (IOException e) {
		mainW.info.setText("Nie udało się pobrać logów");
	}
	 con.close();
	 mainW.chooseEvent.removeAllItems();
	 for(ArrayList<String> s : logs){
	 mainW.chooseEvent.addItem(s.get(0));
	 }
	 mainW.chooseEvent.setEnabled(true);
	 mainW.info.setText("Pobrano listę zdarzeń"); 
 }
 public void connect(String ip, int port){
		if(!(con.connect(ip, port))){
			mainW.info.setText("Nie udało się połączyć");
		}
 }
}
