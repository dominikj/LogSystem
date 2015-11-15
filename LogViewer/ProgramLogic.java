package LogViewer;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
	 mainW.chooseEvent.removeAllItems();
	 for(ArrayList<String> s : logs){
	 mainW.chooseEvent.addItem(s.get(0));
	 }
	 mainW.chooseEvent.setEnabled(true);
	 mainW.info.setText("Pobrano listę zdarzeń"); 
	 mainW.send.setEnabled(true);
	 con.close();
 }
 public void connect(){
	 
		if(!(con.connect(ipAddress, port))){
			mainW.info.setText("Nie udało się połączyć");
		}
 }

 public ArrayList<ArrayList<String>> getLog(String log){
	 con.connect(ipAddress, port);
	 ArrayList<ArrayList<String>> data = null;
	try {
		data = con.getLog(log);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return data;
 }
 public void deleteEvent(String log, String ID){
	 try {
		 connect();
		con.deleteEvent(log, ID);
		mainW.chooseSelector();
		
	} catch (IOException e) {
		mainW.info.setText("Nie wybrałeś wpisu do skasowania lub nie udało się połączyć");
	}
 }
}
