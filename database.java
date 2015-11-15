package LogAgent;

import java.util.ArrayList;
import java.util.List;

public interface database {
	ArrayList<List<String>> getLogs(); //pobiera listę dzienników i ich parametrów
	void saveEvent(String type, String source, List<String> fields); //zapisuje zdarzenie w określonym dzienniku	
	void deleteEvent(String type, String ID); //kasuje zdarzenie o danym ID w określonym dzienniku
	ArrayList<String>  getEvents(String type); //pobiera zdarzenia z wybranego dziennika
}
