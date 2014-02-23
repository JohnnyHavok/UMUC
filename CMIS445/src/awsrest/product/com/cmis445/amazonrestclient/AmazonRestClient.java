/*
 * File: AmazonRestClient.java
 * 
 * This file contains a client class that accesses the Amazon web services interface
 * via rest calls.  It makes a rest query to retrieve books with the keyword of dog.
 * The response is an xml document.  The are 3 output methods supported with this 
 * sample.  One is to print the raw bytes that were returned.  Another takes the
 * response and does some xml parsing.  The last one is to use XSLT to reformat
 * the response.
 * 
 * Note: You need to Sign up with Amazon and receive an awsAccessKeyId and awsSecretKey
 *       The web page to do this is located at: http://aws.amazon.com/
 *       Select sign up now.
 */
package com.cmis445.amazonrestclient;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cmis445.xslttransform.XSLTTransform;

/**
 * This class handles amazon client rest requests
 */
public class AmazonRestClient {
	
	private static int BUFFER_SIZE = 1000;
	
	// The XSLT style sheet to use for the transform
	private static String XSLTFILENAMEWITHALLFIELDS =
               "../resource/searchitems.xsl";
	
	// The ACCESSKEY that amazon provides you when you sign up for their web
	// service - you must change this to your own
	private static final String ACCESSKEY = "";
	
	// Your generated secret key - change it to your own.
	private static final String SECRETKEY = "";
	
	// amazon service URL
	private static final String ENDPOINT = "webservices.amazon.com"; 
	
	/**
	 * main - this method is the main method for the class.  It creates a Map 
	 *        of the parameters for an Amazon ItemSearch call, calls a
	 *        helper class to sign the call and create the REST string,
	 *        send the request and calls 3 methods to format and output
	 *        result 3 different ways.
	 * @param xmlInput - command line args
	 */
	public static void main(String[] args) {
		
		String request = "";
		
		InputStream inputStream = null;
		
		// create a hash map of parameters for the Amazon ItemSearch call
		HashMap<String, String> parameterMap = new HashMap<String, String>(); 
		
		parameterMap.put("Service", "AWSECommerceService");
		parameterMap.put("Operation", "ItemSearch");
		parameterMap.put("MerchantId", "All");
		parameterMap.put("Condition","All");
		parameterMap.put("Availability","Available");
		parameterMap.put("SearchIndex", "Books");
		parameterMap.put("Keywords", "dog");
		
		// Call a helper class passing the parameters and keys that will create the request and sign it. 
		try {
			//Amazon now requires the request to be signed.  Call a helper class to sign the request
			SignedRequestsHelper requestsHelper = new SignedRequestsHelper(ACCESSKEY, SECRETKEY, ENDPOINT);
		    request = requestsHelper.sign(parameterMap);
		} catch (Exception e) {
			 System.out.println("Exception thrown");
		}
		
		// make the call and print out the results 3 different ways.
		try {
				
			URL url = new URL(request);
									
			URLConnection connection = url.openConnection();

	        connection.connect();

	        inputStream = connection.getInputStream();
	       
	        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(inputStream.available());
	       
	        copyInputStreamToOutputStream(inputStream, byteOutputStream);
	        	        	        
	        printResponseAsBytes(new ByteArrayInputStream(byteOutputStream.toByteArray()));
	        	        	                 
	        printXMLResponseAfterParsingTheXML(new ByteArrayInputStream(byteOutputStream.toByteArray()));
	        	        
	        printXMLResponseUsingXSLTTransform(new ByteArrayInputStream(byteOutputStream.toByteArray()));	    	        
		} catch (Exception e) {
			System.err.println("Exception:" + e.toString());
		} finally {			
			try {
			   inputStream.close();
			}   
			catch (Exception e) {
			   System.err.println("Exception closing the input stream:" + e.toString());
		    }
		}
	} // end main

	/*
	 * this method uses an XSLT transform to print the ASIN, author, and
	 * title of the books returned in the search.
	 */
	private static void printXMLResponseUsingXSLTTransform(InputStream xmlInputStream) {
				 	 
	   System.out.println("starting to print the response after doing an XSLT transform");	
		
	   String outbuf = new XSLTTransform().transformString(xmlInputStream, XSLTFILENAMEWITHALLFIELDS);		
	
	   System.out.println("XSLT Transform output:" + outbuf);
	   
	   System.out.println("finshed printing the response after doing an XSLT transform");			
	} // end parseXML
	
	/*
	 * this method parses the XML using JAXP and prints the ASIN, author, and 
	 * title of the books returned in the search.
	 */
	private static void printXMLResponseAfterParsingTheXML(InputStream xmlInputStream) {
	 	
		System.out.println("starting to print the response after parsing the XML");
		
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);

	    DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(xmlInputStream);	
			
			NodeList rootNode = doc.getChildNodes();
			Node curNode = rootNode.item(0);
			Node itemsNode = curNode.getChildNodes().item(1);
			NodeList itemList = itemsNode.getChildNodes();			
			
			for(int i = 3; i < itemList.getLength() ; i++) {
				Node curItem = itemList.item(i);
				
				NodeList itemChildren = curItem.getChildNodes();
							
				Node asin = itemChildren.item(0);
				System.out.println("ASIN:" + asin.getTextContent());
				
				Node itemAttributes = itemChildren.item(2);
				NodeList attributeList = itemAttributes.getChildNodes();
				
				System.out.println("Author:" + (attributeList.item(0).getTextContent()));
				System.out.println("Title:" + (attributeList.item(3).getTextContent()));
				System.out.println("");
			}
		} catch (Exception e) {
			System.err.println("Exception:" + e.toString());
		}
		
		System.out.println("finished printing the response after parsing the XML");
	} // end parseDocument

	/*
	 * this method prints the raw bytes returned from the call.
	 */
	private static void printResponseAsBytes(InputStream in) throws IOException{
	   
	   System.out.println("starting to print the response as bytes");
		
	   byte[] buf = new byte[1024];

	   int nread = 0;
       while ((nread = in.read(buf)) > 0) {
	      System.out.write(buf, 0, nread);
	   }
             
	   System.out.flush();	   
	   
	   System.out.println("\nfinished printing the response as bytes");
	} // end printBytes
	
	/*
	 * Copy the input stream to an output stream
	 */
	public static int copyInputStreamToOutputStream(InputStream input, OutputStream output) throws IOException {        
		byte[] buffer = new byte[BUFFER_SIZE];
        int numberBytes = 0;
        int numberRead = 0;
        while ((numberRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, numberRead);
            numberBytes += numberRead;
        }
        return numberBytes;
	}
} // end AmazonRestClient
