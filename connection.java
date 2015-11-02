package LogAgent;

import java.util.ArrayList;
import java.util.List;

enum Request {GETID, GETLOGSLIST, ADDEVENT, GETLOG, DELETEEVENT, CLOSE} 

public interface connection {
	void listen(int port);
	void sendLogsList(ArrayList<List<String>> list);
	void sendID(int id);
	void sendLog(ArrayList<String> log);
	void sendSuccessMsg();
	String getIDEventDele();
	String getLogName();
	Request getRequest();
	List<String> writeEvent();

}
