package ro.endava.hackathon.util;

import ro.endava.hackathon.core.ProcessPerson;
import ro.endava.hackathon.core.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OutputTransform {

	public static List<Result> getOutput(List<ProcessPerson> persons, Integer index) {
		List<Result> res = new ArrayList<Result>();
		for (ProcessPerson p : persons) {
			Result r = new Result();
			if (p.getAssignedToProcessActivity() != null) {
				r.setActivity(p.getAssignedToProcessActivity().getActivity());
			}
			r.setPerson(p.getPerson());
			r.setIndex(index);
			res.add(r);
		}
		return res;
	}
	
	public static void writeResult(List<Result> results, Long totalAmount) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("output");
			Attr xmlnsAttr = doc.createAttribute("xmlns");
			xmlnsAttr.setValue("http://www.endava.com/hackathon/journey");
			rootElement.setAttributeNode(xmlnsAttr);
			Attr teamAttr = doc.createAttribute("teamId");
			teamAttr.setValue("byteMe");
			rootElement.setAttributeNode(teamAttr);
			doc.appendChild(rootElement);
			
			Element amount = doc.createElement("totalAmountSpent");
			amount.appendChild(doc.createTextNode(totalAmount.toString()));
			rootElement.appendChild(amount);
			
			for (Result r : results) {
				appendEntry(doc, rootElement, r);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("D:\\output.xml"));
			transformer.transform(source, result);
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private static void appendEntry(Document doc, Element rootElement, Result r) {
		Element entry = doc.createElement("entry");
		
		Element personId = doc.createElement("personId");
		personId.appendChild(doc.createTextNode(r.getPerson().getName()));
		entry.appendChild(personId);
		
		Element hourIndex = doc.createElement("hourIndex");
		hourIndex.appendChild(doc.createTextNode(r.getIndex().toString()));
		entry.appendChild(hourIndex);
		
		if (r.getActivity() != null) {
			Element activity = doc.createElement("activity");
			activity.appendChild(doc.createTextNode(r.getActivity().getName()));
			entry.appendChild(activity);
		}
		
		rootElement.appendChild(entry);
	}
}
