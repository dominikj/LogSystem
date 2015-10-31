package LogAgent;

import java.util.ArrayList;
import java.util.List;

public class LogAgent {

	public static void main(String[] args) {
		XMLParser parser = new XMLParser();
		ArrayList<List<String>> list = parser.getLogs();
		for(int i = 0; i < list.size(); ++i){
				System.out.println("nazwa dziennika : " + list.get(i).get(0));
				System.out.println("liczba dodatkowych pól : " + list.get(i).get(1));
				System.out.println("pola: " + list.get(i).get(2));
		}

	}

}
