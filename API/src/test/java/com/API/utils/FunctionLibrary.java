package com.API.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jayway.jsonpath.JsonPath;

public class FunctionLibrary {
	static Logger logger=Logger.getLogger(FunctionLibrary.class);

	
	
	
	
	/*
	 * convert content of a file to String
	 */
	public String generateStringFromResource(String filePath) throws IOException {
		String retStrValue=FileUtils.readFileToString(new File(filePath)).trim();
		return retStrValue;
	}
	
	
	
	/*
	 * write content to a text file
	 */
	public void writeToTextFile(String filepath, String content) throws IOException {
		FileWriter writer=new FileWriter(filepath, false);
		writer.write(content);
		logger. info("Content written to: "+filepath);
		writer.close();
	}
	
	
	/*
	 * Reads the content from a file
	 * 
	 */
	public String readTextFile(String filePath) throws IOException {
		StringBuilder sb=new StringBuilder();
		FileReader fileReader=new FileReader(filePath);
		BufferedReader bufferedReader=new BufferedReader(fileReader);
		String line=bufferedReader.readLine();
		while(line !=null) {
			sb.append(line).append("\n");
			line=bufferedReader.readLine();
		}
		bufferedReader.close();
		logger.info("Content read from : "+filePath);
		return sb.toString();
	}
	
	
	
	
	
	/*
	 * Copy content from one file to another one
	 */
	
	public void copyFileUsingFileStreams(String sourceFilePath, String targetFilepath) throws IOException {
		InputStream input=null;
		OutputStream output=null;
		try {
			input=new FileInputStream(sourceFilePath);
			output=new FileOutputStream(targetFilepath);
			logger.info("File copied from "+input+" to "+output);
			byte[] buf=new byte[1024];
			int byteRead;
			while((byteRead=input.read(buf))>0) {
				output.write(buf, 0, byteRead);
			}
		}finally {
			input.close();
			output.close();
		}
	}
	
	
	
	
	
	
	/*
	 * retrieve Json value from JSON content using JSON Path
	 */
	public Object retrieveJsonValue(String jsonContent, String jsonPath) {
		Object jsonValue=null;
		String strJsonValue=null;
		try {
			jsonValue=JsonPath.read(jsonContent, jsonPath);
		} catch (Exception e) {
			logger.info("Json Path not found  : "+jsonPath);
			logger.error(e);
		}		
	strJsonValue=(jsonValue !=null)? jsonValue.toString() : (String)jsonValue;
	return strJsonValue;
	}
	
	
	
	
	/*
	 * retrieve XML value from XML file using XML Path
	 * 
	 */
	
	 public String retrieveXMLvalueFromFile(String filePath, String XMLpath) {
		 String retXpath=null;
		 String strXpathVal=null;
		 String completeXpath_Val=null;
		 try {
			 DocumentBuilder parser;
			 parser=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			 Document doc=parser.parse(new java.io.File(filePath));
			 XPath xpath=XPathFactory.newInstance().newXPath();
			 strXpathVal=(xpath.evaluate(XMLpath, doc));
			 NodeList nodes=(NodeList) xpath.evaluate(XMLpath, doc, XPathConstants.NODESET);
			 
			 for (int i = 0, n= nodes.getLength(); i<n; i++) {
				Node node=nodes.item(i);
				retXpath=getXPath(node);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		 if(strXpathVal !=null && retXpath !=null) {
			 completeXpath_Val=strXpathVal;
		 }
		 return completeXpath_Val;
	 }
	
	 
	 
	 public String getXPath(Node node) {
		 Node parent=node.getParentNode();
		 if(parent==null) {
			 return "/";
			 
		 }
		 return getXPath(parent)+"/"+node.getNodeName();
	 }
	 
	 
	/*
	 * retrieve XML value from String content with XML path
	 *  
	 */
	 public String getXMLValueFromString(String xmlContent, String xmlPath) {
		 try {
			InputSource source=new InputSource(new StringReader(xmlContent));
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			DocumentBuilder db=dbf.newDocumentBuilder();
			Document document=db.parse(source);
			XPathFactory xpathFactory=XPathFactory.newInstance();
			XPath xpath=xpathFactory.newXPath();
			return xpath.evaluate(xmlPath, document);
		} catch (Exception e) {
			logger.error(e);
			return null;
		
		}
	 }
	 
	 
	 /*
	  * Convert date to UnixTimeStamp/Epoch
	  * @param String dateTime- (Example: 2017-03-11'T'12:38:00)
	  * 
	  * @param String dateTimeFormat - (Example: yyyy-MM-dd'T'HH:mm:ss)
	  * 
	  * @return String unixTimeStamp
	  */
	 
	 public String convertDateToUnixTimeStamp(String dateTime,String dateTimeFormat) {
		 if(dateTime==null) { return null;}
		 try {
			 SimpleDateFormat sdf=new SimpleDateFormat(dateTimeFormat);
			 Date date=sdf.parse(dateTime);
			 long epoch=date.getTime();
			 return String.valueOf(epoch); 
		 }catch(java.text.ParseException e) {
			 return null;
		 }
		 
	 }
	 
	 
	
}
