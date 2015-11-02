package LogAgent;

import java.util.ArrayList;
import java.util.List;

public class LogAgent {

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		int IDcounter = 10; //FIXME: Wywalić licznik ID; zastąpić czymś bardziej uniwersalnym
		//ArrayList<List<String>> list = parser.getLogs();
		/*for(int i = 0; i < list.size(); ++i){
				System.out.println("nazwa dziennika : " + list.get(i).get(0));
				System.out.println("liczba dodatkowych pól : " + list.get(i).get(1));
				System.out.println("pola: " + list.get(i).get(2));
		}*/
		//parser.deleteEvent("Zdarzenie1", "2");
		//System.out.println("-----------------");
		//ArrayList<String> events = parser.getEvents("Zdarzenie1");
		//for(int i = 0; i < events.size(); ++i){
		//	System.out.println(events.get(i));
		//}
		//ArrayList<String> values = new ArrayList<String>();
		//values.add("czas=19:40");
		//parser.saveEvent("Testowe2", values);
		protocol prot = new protocol();
		prot.listen(10999);
		if (prot.getRequest() == Request.GETID)
			prot.sendID(IDcounter++);
		if (prot.getRequest() == Request.GETLOGSLIST){
			prot.sendLogsList(parser.getLogs());
		}
		if (prot.getRequest() == Request.ADDEVENT){
			parser.saveEvent(prot.getLogName(), prot.writeEvent());
		}
		if(prot.getRequest() == Request.GETLOG){
			prot.sendLog(parser.getEvents(prot.getLogName()));
		}
		if (prot.getRequest() == Request.DELETEEVENT){
			parser.deleteEvent(prot.getLogName(), prot.getIDEventDele());
		}
	}

}
