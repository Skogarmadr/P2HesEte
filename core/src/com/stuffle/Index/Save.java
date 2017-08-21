package com.stuffle.Index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Save {
	public void partWriter() {
		try {
			String filepath = PartStats.saveSource + "/partConfig.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node stats = doc.getFirstChild();

			NodeList list = stats.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				switch (node.getNodeName()) {
				case "isDead":
					if (PartStats.isDead)
						node.setTextContent("true");
					else
						node.setTextContent("false");
					break;
				case "iType":
					node.setTextContent("" + PartStats.iType);
					break;
				case "strType":
					node.setTextContent("" + PartStats.strType);
					break;
				case "iLife":
					node.setTextContent("" + PartStats.iLife);
					break;
				case "iLifeMax":
					node.setTextContent("" + PartStats.iLifeMax);
					break;
				case "iPower":
					node.setTextContent("" + PartStats.iPower);
					break;
				case "iPowerMax":
					node.setTextContent("" + PartStats.iPowerMax);
					break;
				case "iAttaque":
					node.setTextContent("" + PartStats.iAttaque);
					break;
				case "flSpeedAtt":
					node.setTextContent("" + PartStats.flSpeedAtt);
					break;
				case "iLevel":
					node.setTextContent("" + PartStats.iLevel);
					break;
				case "iNewLevel":
					node.setTextContent("" + PartStats.iNewLevel);
					break;
				case "iExp":
					node.setTextContent("" + PartStats.iExp);
					break;
				case "iExpMax":
					node.setTextContent("" + PartStats.iExpMax);
					break;
				case "iGold":
					node.setTextContent("" + PartStats.iGold);
					break;
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));

			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void articleWriter(ArrayList<ArrayList<Article>> articles) {
		try {
			String filepath = PartStats.saveSource + "/article.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			NodeList status = doc.getElementsByTagName("status");

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					status.item(j + i * 4).setTextContent(articles.get(i).get(j).strStatus);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void upgradeWriter() {
		try {
			String filepath = PartStats.saveSource + "/upgrade.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node stats = doc.getFirstChild();

			NodeList list = stats.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);

				switch (node.getNodeName()) {
				case "Attaque":
					node.setTextContent(PartStats.lUpgrades.get(0) + "");
					break;
				case "Defense":
					node.setTextContent(PartStats.lUpgrades.get(1) + "");
					break;
				case "AttaqueSpe":
					node.setTextContent(PartStats.lUpgrades.get(2) + "");
					break;
				case "Power":
					node.setTextContent(PartStats.lUpgrades.get(3) + "");
					break;
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (TransformerConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (TransformerException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
