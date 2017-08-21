package com.stuffle.Index;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Load {
	public void loadPart() {
		try {
			String filename = PartStats.saveSource + "\\partConfig.xml";
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = domFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(filename);

			NodeList list = document.getElementsByTagName("stats").item(0).getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				switch (node.getNodeName()) {
				case "isDead":
					if (node.getTextContent().equalsIgnoreCase("true"))
						PartStats.isDead = true;
					else
						PartStats.isDead = false;
					break;
				case "iType":
					PartStats.iType = Integer.parseInt(node.getTextContent());
					break;
				case "strType":
					PartStats.strType = node.getTextContent();
					break;
				case "iLife":
					PartStats.iLife = Integer.parseInt(node.getTextContent());
					break;
				case "iLifeMax":
					PartStats.iLifeMax = Integer.parseInt(node.getTextContent());
					break;
				case "iPower":
					PartStats.iPower = Integer.parseInt(node.getTextContent());
					break;
				case "iPowerMax":
					PartStats.iPowerMax = Integer.parseInt(node.getTextContent());
					break;
				case "iAttaque":
					PartStats.iAttaque = Integer.parseInt(node.getTextContent());
					break;
				case "flSpeedAtt":
					PartStats.flSpeedAtt = Float.parseFloat(node.getTextContent());
					break;
				case "iLevel":
					PartStats.iLevel = Integer.parseInt(node.getTextContent());
					break;
				case "iNewLevel":
					PartStats.iNewLevel = Integer.parseInt(node.getTextContent());
					break;
				case "iExp":
					PartStats.iExp = Integer.parseInt(node.getTextContent());
					break;
				case "iExpMax":
					PartStats.iExpMax = Integer.parseInt(node.getTextContent());
					break;
				case "iGold":
					PartStats.iGold = Integer.parseInt(node.getTextContent());
					break;
				}
			}
		} catch (Exception e) {
		}
	}

	public ArrayList<ArrayList<Article>> loadArticle(ArrayList<ArrayList<Article>> articles) {
		try {
			String filename = PartStats.saveSource + "/article.xml";
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = domFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(filename);

			NodeList list = document.getElementsByTagName("status");
			int iCpt = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 4; j++) {
					String strStatus = list.item(iCpt).getTextContent();
					articles.get(i).get(j).changeStatus(strStatus);
					iCpt++;
				}
			}

		} catch (Exception e) {
		}

		return articles;
	}

	public void loadUpgrade() {
		try {
			String filename = PartStats.saveSource + "/upgrade.xml";
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = domFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(filename);

			NodeList list = document.getElementsByTagName("classePerso").item(0).getChildNodes();
			boolean elementFind;
			int iCpt = 0;
			for (int i = 0; i < list.getLength(); i++) {
				elementFind = false;
				Node upgrade = list.item(i);
				switch (upgrade.getNodeName()) {
				case "Attaque":
					elementFind = true;
					iCpt = 0;
					break;
				case "Defense":
					elementFind = true;
					iCpt = 1;
					break;
				case "AttaqueSpe":
					elementFind = true;
					iCpt = 2;
					break;
				case "Power":
					elementFind = true;
					iCpt = 3;
					break;
				}
				if (elementFind)
					PartStats.lUpgrades.set(iCpt, Integer.parseInt(upgrade.getTextContent()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}