package LogAgent;

import java.util.ArrayList;
import java.util.List;

public class LogAgent {

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		protocol prot = new protocol();
		prot.listen(Integer.parseInt(args[0]));
	//	if (prot.getRequest() == Request.GETID)
			//prot.sendID(IDcounter++);
		while(true){
		if (prot.getRequest() == Request.GETLOGSLIST){
			System.out.println("aaaa");
			prot.sendLogsList(parser.getLogs());
		}
		if (prot.getRequest() == Request.ADDEVENT){
			System.out.println("nazwa dziennika : " + prot.getLogName());
			List<String> tmp = prot.writeEvent();
			parser.saveEvent(prot.getLogName(), prot.owner, tmp);
		}
		if(prot.getRequest() == Request.GETLOG){
			prot.sendLog(parser.getEvents(prot.getLogName()));
		}
		if (prot.getRequest() == Request.DELETEEVENT){
			parser.deleteEvent(prot.getLogName(), prot.getIDEventDele());
		}
		if (prot.getRequest() == Request.CLOSE){
			prot.listen(0); 
		}
		}
	}

}
