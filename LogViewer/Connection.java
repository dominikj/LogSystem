package LogViewer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.Spring;

public class Connection {
	
	private BufferedReader input;
	private DataOutputStream output;
	private String dataIn;
	private String dataOut;
	private Socket soc;
	
	public boolean connect(String address, int port) {
	try {
		 soc = new Socket(address, port);
		input = new BufferedReader (new InputStreamReader(soc.getInputStream()));
		output = new DataOutputStream (soc.getOutputStream());
		output.writeBytes("HELLO\r\n");
		dataIn = input.readLine();
		
		if(dataIn.equals("OK"))
			return true;
		return false;
	} catch (Exception ex) {
		ex.printStackTrace();
		return false;
	} 
	
	}
	public ArrayList<ArrayList<String>> getLogs() throws IOException{
		ArrayList<ArrayList<String>> logs = new ArrayList<ArrayList<String>>();

			output.writeBytes("GETLOGSLIST\r\n");
			String[] tmp;
			while (!((dataIn = input.readLine()).equals("ENDLOGS"))){
				tmp = dataIn.split(",");
				if(tmp[0].equals("LOG")){
				logs.add(new ArrayList<String>());
				logs.get(logs.size() -1).add(tmp[1]);
				}
				else if (tmp[0].equals("FIELDS")){
					for(int i =1; i < tmp.length; ++i){
						logs.get(logs.size() -1).add(tmp[i]);
					}
				}
				else if(tmp[0].equals("ERROR"))
					throw (new IOException());
			}
			close();
		return logs;
	}
	
	ArrayList<ArrayList<String>> getLog(String log) throws IOException{
		output.writeBytes("GETLOG"+ log+"\r\n");
		ArrayList<ArrayList<String>> events = new ArrayList<ArrayList<String>>();
		input.readLine();
		while(!(dataIn = input.readLine()).equals("ENDEVENTS")){
			System.out.println("AAA:" + dataIn);
			events.add(new ArrayList<String>());
			events.get(events.size() -1).add(dataIn);
			while(!(dataIn = input.readLine()).equals("ENDEVENT")){
				events.get(events.size() -1).add(dataIn);
			}
			
		}

			close();
		return events;
	}
	public void deleteEvent(String log, String id) throws IOException{
		output.writeBytes("DELETEEVENT " + log.trim() + " " + id.trim() + "\r\n");
		/*if(!(input.readLine().equals("OK")))
			throw new IOException();*/
		close();
	}
	

	void close(){
		try {
			if(!soc.isClosed())
				soc.close();
		} catch (IOException e) {
		}
	}
	
}
