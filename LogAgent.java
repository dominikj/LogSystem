package LogAgent;

import java.util.List;

public class LogAgent {

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		protocol prot = new protocol();
		prot.listen(Integer.parseInt(args[0]));
		while(true){
		if (prot.getRequest() == Request.GETLOGSLIST){
			prot.sendLogsList(parser.getLogs());
		}
		if (prot.getRequest() == Request.ADDEVENT){
			System.out.println("nazwa dziennika : " + prot.getLogName());
			List<String> tmp = prot.writeEvent();
			parser.saveEvent(prot.getLogName(), tmp);
		}
		if(prot.getRequest() == Request.GETLOG){
			prot.sendLog(parser.getEvents(prot.getLogName()));
		}
		if (prot.getRequest() == Request.DELETEEVENT){
			System.out.println("nazwa dziennika : " + prot.getLogName());
			System.out.println("id wpisu : " + prot.getIDEventDele());
			parser.deleteEvent(prot.getLogName().trim(), prot.getIDEventDele().trim());
		}
		if (prot.getRequest() == Request.CLOSE){
			prot.listen(0); 
		}
		}
	}

}
