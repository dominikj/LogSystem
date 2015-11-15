package LogAgent;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class XMLParser implements database {

/**
 * Konstruktor sprawdza listę dzienników, jeśli istnieje taki, który nie ma jeszcze pliku, tworzy go 
 */
 public XMLParser() {
		ArrayList<List<String>> list = getLogs();
		for (int i = 0; i < list.size(); ++i) {
			System.out.println("nazwa dziennika : " + list.get(i).get(0));
			try {
				File file = new File("./" + list.get(i).get(0) + ".xml");
				if (file.createNewFile()) { // Jeżeli  udało się utworzyć plik, to wypełniany jest początkową treścią
					BufferedWriter out = new BufferedWriter(new FileWriter("./"
							+ list.get(i).get(0) + ".xml"));
					out.write("<" + list.get(i).get(0)
							+ " counter = \"0\" > </" + list.get(i).get(0)
							+ ">");
					out.close();
					System.out.print("Utworzono plik dziennika");
				}
			} catch (Exception ex) {
				System.out.print("Nie udało się odczytać ustawień LogAgenta");
			}
		}
	}
/**
 * Metoda usuwa wpis o podanym ID w podanym dzienniku
 * @param type nazwa dziennika 
 * @param ID ID wpisu w dzienniku
 */
	@Override
	public void deleteEvent(String type, String ID) {
		try {
			File fXmlFile = new File(type + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("event"); //lista wpisów typu event
			Node events = doc.getElementsByTagName(type).item(0); // uchwyt do korzenia drzewka wpisów, elementu nadrzędnego nad wszystkimi zdarzeniami

			NamedNodeMap attr = events.getAttributes(); //
			Node nodeAttr = attr.getNamedItem("counter"); // Pobiera uchwyt do atrybutu licznika wpisów w dzienniku, bo trzeba go modyfikować

			for (int temp = 0; temp < nList.getLength(); temp++) { //iteruje po wszystkich wpisach w dzienniku

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					if (ID.equals(eElement.getAttribute("id"))) { //jeżeli id zgadza się, skasuj wpis
						events.removeChild(nNode);
						int counter = Integer.parseInt(nodeAttr.getNodeValue());
						--counter;
						nodeAttr.setTextContent(Integer.toString(counter)); // zmniejsz i zapisz licznik wpisów 
					} else if (temp == nList.getLength() - 1) // jeżeli wpisu nie odnaleziono rzuć wyjątek
						throw new Exception();
				}

			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(type + ".xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			System.out.println("Nie udało się usunąć podanej pozycji");
		}

	}
/**
 * Pobiera wpisy z podanego dziennika
 * @param type nazwa dziennika
 * @return zwraca listę stringów wpisów; każdy wpis to jeden string
 */
	@Override
	public ArrayList<String> getEvents(String type) {
		ArrayList<String> events = new ArrayList<String>();
		try {

			File fXmlFile = new File(type + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("event"); //lista zdarzeń 

			for (int temp = 0; temp < nList.getLength(); temp++) { //iteruje po zdarzeniach i zapisuje je do listy

				Node nNode = nList.item(temp);
				Element el = (Element) nNode;
				
				String val = "EVENT " + el.getAttribute("id") + "\n";
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					NodeList nList2 = eElement.getChildNodes();
				
					for(int temp2 = 0; temp2 < nList2.getLength(); ++temp2){
						Node nNode2 = nList2.item(temp2);
						if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
							val += nNode2.getTextContent() + "\n"  ;
						}
					}
					events.add(val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
/**
 * Pobiera listę dzienników wraz z informacją o ilości dodatkowych pól i ich nazwami 
 */
	@Override
	public ArrayList<List<String>> getLogs() {
		ArrayList<List<String>> logs = new ArrayList<List<String>>();
		try {

			File fXmlFile = new File("./logs.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("log");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					List<String> list = new ArrayList<String>();
					list.add(eElement.getAttribute("nazwa"));
					list.add(eElement.getElementsByTagName("liczbapol").item(0)
							.getTextContent());
					list.add(eElement.getElementsByTagName("pola").item(0)
							.getTextContent());
					logs.add(list);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

	@Override
	public void saveEvent(String type, String owner, List<String> fields) {

		try {
			File fXmlFile = new File(type + ".xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			Node events = doc.getElementsByTagName(type).item(0);
			NamedNodeMap attr = events.getAttributes();
			Node nodeAttr = attr.getNamedItem("counter");
			int counter = Integer.parseInt(nodeAttr.getNodeValue());
			++counter;

			Element event = doc.createElement("event");
			event.setAttribute("id", Integer.toString(counter));
			
			Element element1 = doc.createElement("zrodlo");
			element1.setTextContent(owner);
			event.appendChild(element1);
			System.out.println("oo: " + owner);
			for (int i = 0; i < fields.size(); ++i) {
				String[] field = fields.get(i).split("=");
				Element element = doc.createElement(field[0].replace(" ", "_"));
				element.setTextContent(field[1]);
				event.appendChild(element);
			}
			events.appendChild(event);
			nodeAttr.setTextContent(Integer.toString(counter));

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(type + ".xml"));
			transformer.transform(source, result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
