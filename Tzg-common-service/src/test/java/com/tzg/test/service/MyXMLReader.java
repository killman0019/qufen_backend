package com.tzg.test.service;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class MyXMLReader {
	public static void main(String arge[]) {

		long lasting = System.currentTimeMillis();

		try {
			File f = new File("要解析的.xml文件");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("VALUE");
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.print("标签名:" + doc.getElementsByTagName("标签名").item(i).getFirstChild().getNodeValue());
				System.out.println("标签名2:" + doc.getElementsByTagName("标签名2").item(i).getFirstChild().getNodeValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}