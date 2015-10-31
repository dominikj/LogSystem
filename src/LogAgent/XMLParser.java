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

	public XMLParser() {
		ArrayList<List<String>> list = getLogs();
		for(int i = 0; i < list.size(); ++i){
				System.out.println("nazwa dziennika : " + list.get(i).get(0));
				 try{
			         File file = new File("./" + list.get(i).get(0) + ".xml");
			         if(file.createNewFile()){
			         //System.out.println("Success!");
			        	 BufferedWriter out = new 
			        	         BufferedWriter(new FileWriter("./" + list.get(i).get(0) + ".xml"));
			        	         out.write("<" + list.get(i).get(0) + " counter = \"0\" > </" + list.get(i).get(0) + ">");
			        	         out.close();
			         }
			     }
			     catch(Exception ex) {
			        ex.printStackTrace();
			     }
	}
	}
	@Override
	public ArrayList<List<String>> getLogs() {
	 	ArrayList<List<String>> logs = new ArrayList<List<String>>();
		 try {
	
				File fXmlFile = new File("./logs.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
						list.add(eElement.getElementsByTagName("liczbapol").item(0).getTextContent());
						list.add(eElement.getElementsByTagName("pola").item(0).getTextContent());
						logs.add(list);
				
						
					}
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
		 return logs;
	}

	@Override
	public void saveEvent(String type, List<String> fields) {

		try{
		File fXmlFile = new File(type +".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
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
		
		for(int i = 0; i < fields.size(); ++i){
		String[] field = fields.get(0).split("=");
		Element element = doc.createElement(field[0]);
		element.setTextContent(field[1]);
		event.appendChild(element);
		}
		events.appendChild(event);
		nodeAttr.setTextContent(Integer.toString(counter));
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(type +".xml"));
		transformer.transform(source, result);
		}
		catch(Exception ex){
		}
	}

	@Override
	public void deleteEvent(String type, String ID) {
		 try {
				File fXmlFile = new File(type +".xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
						
				doc.getDocumentElement().normalize();
						
				NodeList nList = doc.getElementsByTagName("event");
				Node events = doc.getElementsByTagName(type).item(0);
				
				NamedNodeMap attr = events.getAttributes();
				Node nodeAttr = attr.getNamedItem("counter");
				
				
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
										
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						if (ID.equals(eElement.getAttribute("id"))){
						events.removeChild(nNode);
						int counter = Integer.parseInt(nodeAttr.getNodeValue()); 
						--counter;
						nodeAttr.setTextContent(Integer.toString(counter));
						}
					else
							if(temp == nList.getLength()-1)
								throw new Exception();
					}
					
				}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(type +".xml"));
				transformer.transform(source, result);
			    } catch (Exception e) {
				System.out.println("Nie udało się usunąć podanej pozycji");
			    }
		
	}

	@Override
	public ArrayList<String>  getEvents(String type) {
		ArrayList<String> events = new ArrayList<String>();
		 try {
	
				File fXmlFile = new File(type +".xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
						
				doc.getDocumentElement().normalize();
						
				NodeList nList = doc.getElementsByTagName("event");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
							
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						events.add(eElement.getTextContent());
					}
				}
			    } catch (Exception e) {
				e.printStackTrace();
			    }
		 return events;
	}

}
