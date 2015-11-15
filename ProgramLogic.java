package LogClient;

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
 public void sendEvent(String logName){
	 try{
	mainW.table.getCellEditor().stopCellEditing();
	 }catch(Exception ex){
	 }
	 ArrayList<String> dataToSend  = new ArrayList<String>();
		String tmp =(String) mainW.chooseEvent.getSelectedItem();
		System.out.println(tmp);
		for(ArrayList<String> s: logs ){
			if(s.get(0).equals(tmp)){
				int i;
			    for ( i = 0; i < mainW.modelTable.getColumnCount()-2; i++) {
			         String result = (String) mainW.table.getModel().getValueAt(0, i);
			         dataToSend.add(s.get(i +1).trim() + "=" + result);
			     }
			    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			    Calendar cal = Calendar.getInstance();
			    dataToSend.add("data_i_czas=" + dateFormat.format(cal.getTime()));
			    dataToSend.add("kod_przyczyny=" + (String) mainW.table.getModel().getValueAt(0, i));
			    dataToSend.add("przewidywana_groznosc=" + (String) mainW.table.getModel().getValueAt(0, i+1));
				break;
			}
		}
		try {
			con.sendEvent(tmp, dataToSend);
			mainW.info.setText("Wysłano zdarzenie");
		} catch (IOException e) {
			mainW.info.setText("Nie udało się wysłać zdarzenia");
		}
	
 }
}
