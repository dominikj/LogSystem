package LogAgent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class protocol implements connection{
	private BufferedReader input;
	private DataOutputStream output;
	private String dataIn;
	private String dataOut;
	private ServerSocket serSoc;
	public Socket socket;
	private Request request;
	private String logName; 
	private String idDeletedEvent;
	
	@Override
	public void listen(int port) {
		try {
			if(port != 0)
				serSoc = new ServerSocket(port);
			System.out.println("Czekam na połączenie");
			socket = serSoc.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new DataOutputStream(socket.getOutputStream());
			System.out.println("Połączono");
			dataIn = input.readLine();
			if(dataIn.equals("HELLO")){
				System.out.println("Mam powitanie");
			  dataOut = "OK \n\r";
			  output.writeBytes(dataOut);
			  dataIn = input.readLine();
			  String[] splittedData = dataIn.split(" ");
				System.out.println("Info: " +splittedData[0]);
			  switch(splittedData[0]){
			  case "GETID": request = Request.GETID;break;
			  case "ADDEVENT" : { request = Request.ADDEVENT; logName = splittedData[1]; break;} 
			  case "GETLOG" : request = Request.GETLOG; logName = splittedData[1]; break;
			  case "GETLOGSLIST" : request = Request.GETLOGSLIST;break;
			  case "CLOSE" : request = Request.CLOSE;break;
			  case "DELETEEVENT" : request = Request.DELETEEVENT; logName = splittedData[1]; idDeletedEvent = splittedData[2];break;
			  }
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void sendLogsList(ArrayList<List<String>> list) {
		for(List<String> l : list){
			dataOut = "LOG, " + l.get(0) + "\n\r" + "FIELDS, " + l.get(2) + "\n\r";
			try {
				output.writeBytes(dataOut);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dataOut = "ENDLOGS";
		try {
			output.writeBytes(dataOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
			request = Request.CLOSE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void sendID(int id) {
		dataOut = Integer.toString(id);
		try {
			output.writeBytes(dataOut + "\n\r");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void sendLog(ArrayList<String> log) {
		
			try {
				for( String s : log){
				System.out.println(s);
				output.writeBytes(s + "\r\n");
				}
				output.writeBytes("ENDEVENTS");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    try {
				socket.close();
				request = Request.CLOSE;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}

	@Override
	public void sendSuccessMsg() {
		dataOut = "OK";
		try {
			output.writeBytes(dataOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String getIDEventDele() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request = Request.CLOSE;
		return idDeletedEvent;
	}

	@Override
	public String getLogName() {
		return logName;
	}

	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public List<String> writeEvent() {
		ArrayList<String> fields = new ArrayList<String>();
		try {
			while(!((dataIn = input.readLine()).equals("ENDEVENT")))
			{
				System.out.println(dataIn);
				fields.add(dataIn);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
			request = Request.CLOSE;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fields;
	}

}
