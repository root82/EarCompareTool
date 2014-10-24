package com.murali.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLRead {
	
	public void readTiboGVs(File xmlFiles[]){
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		
		try{
			dBuilder = builderFactory.newDocumentBuilder();
			
			HashMap<String, String> file1 = new HashMap<String, String>(), 
									file2 = new HashMap<String, String>(),
									tempFile = new HashMap<String, String>(),
									result = new HashMap<String, String>();
			
			for (File f : xmlFiles) {
				Document xmlDoc = dBuilder.parse(new FileInputStream(f));
				
				xmlDoc.getDocumentElement().normalize();
	
				//System.out.println("Root element : " + xmlDoc.getDocumentElement().getNodeName());
				
				XPath xPath = XPathFactory.newInstance().newXPath();
				String expr = "/DeploymentDescriptors/NameValuePairs[name='Global Variables']/*[name != 'Global Variables']";
				
				NodeList nodeList = (NodeList)xPath.compile(expr).evaluate(xmlDoc, XPathConstants.NODESET);
				
				//System.out.println("--------------------------");
				
				for (int temp = 0; temp < nodeList.getLength(); temp++){
					Node n = nodeList.item(temp);
					if (n.getNodeType() == Node.ELEMENT_NODE){ 
						String tStr = n.getNodeName();
						if (tStr.startsWith("NameValuePair")){
							String s = tStr.replaceFirst("NameValuePair", "");
							//System.out.println(((Element)n).getElementsByTagName("name").item(0).getTextContent() 
							//		+ ((s.length() != 0)? " : " + s : ""));
							
							if (xmlFiles[0].toString().compareTo(f.toString()) == 0) 
								file1.put( ((Element)n).getElementsByTagName("name").item(0).getTextContent(), 
										(s.length() != 0)? s : "String");
							else 
								file2.put( ((Element)n).getElementsByTagName("name").item(0).getTextContent(), 
										(s.length() != 0)? s : "String"); 
						}
					}
				}
			}
			
			tempFile.putAll(file1);

			for (Entry<String, String> entry : tempFile.entrySet()){
				//System.out.println(entry.getKey() + "......................" + entry.getValue());
				if (file2.containsKey(entry.getKey())){
					if (file2.get(entry.getKey()).compareTo(entry.getValue()) != 0){
						result.put(entry.getKey(), "Modified");
					}
					file2.remove(entry.getKey());
					file1.remove(entry.getKey());
				}
				else{
					result.put(entry.getKey(), "Added");
					file1.remove(entry.getKey());
				}
			}
			
			for (Entry<String, String> entry : file2.entrySet()){
				result.put(entry.getKey(), "Deleted");
			}

			for (Entry<String, String> entry : result.entrySet()){
				System.out.println(entry.getKey() + "......." + entry.getValue());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void search(File file, FilenameFilter fnf, List<File> lf){
		
		if (file.isDirectory() && file.canRead()){
			for (File f : file.listFiles(fnf)) lf.add(f);
			for (File tmp : file.listFiles())
				if (tmp.isDirectory()) search(tmp, fnf,lf);
		}
	}
	
	public void input(File tmpDir){
		
		List<File> lf = new ArrayList<File>();
		File[] xmlFile = new File[2];
		
		FilenameFilter fnf = new FilenameFilter() {			
			public boolean accept(File dir, String name) 
				{return name.toUpperCase().startsWith("NEWCOMPARE_") && name.toUpperCase().endsWith("EAR");}
			};
		
		search(tmpDir,fnf, lf);
		
		fnf = new FilenameFilter() {			
			public boolean accept(File dir, String name) 
				{return name.toUpperCase().startsWith("OLDWITH_") && name.toUpperCase().endsWith("EAR");}
			};
		
		search(tmpDir,fnf, lf);
		
		fnf = new FilenameFilter() {			
			public boolean accept(File dir, String name) 
				{return name.equalsIgnoreCase("TIBCO.XML");}
			};
		
		
		if (lf.size() > 0) {
			xmlFile[0] = lf.get(0).listFiles(fnf)[0];
			xmlFile[1] = lf.get(1).listFiles(fnf)[0];
		}
		
		for (File f : xmlFile) 
			System.out.println(f.getAbsolutePath());
		
		readTiboGVs(xmlFile);

	}
}
