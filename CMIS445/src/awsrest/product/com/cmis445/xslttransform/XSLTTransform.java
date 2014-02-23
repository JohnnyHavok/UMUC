/**
 * File: XSLTransform.java
 * 
 * This file contains a class that supports doing an XSLT transform on
 * an XML document
 */

package com.cmis445.xslttransform;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * XSLTransform
 * 
 * This class supports doing an XSLT transform against and XML document
 *
 */
public class XSLTTransform {
	
	/**
	 * transformString - this method takes an xslt file name as well
	 *                   as a name of an XML file and performs a XSLT
	 *                   transform
	 * @param xmlInput - name of an xml stream source
	 * @param xslFname - name of an xslt file
	 * @return
	 */
	public String transformString(InputStream xmlInput, String xslFname) {
		
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);

		try {

			Transformer trans = loadXSLTTemplate(xslFname);			
			trans.transform(new StreamSource(xmlInput), result); 
			return writer.getBuffer().toString();
		} catch (Exception e1) {
			System.out.println("Cannot Transform files." + e1.toString());
		}
		
		return null;
	}

	/*
	 * loadXSLTTemplate - load the xslt template
	 */
	private Transformer loadXSLTTemplate(String xslFname) throws TransformerFactoryConfigurationError, TransformerConfigurationException {
		Transformer trans;
		StreamSource src = new StreamSource(new File(xslFname));
		TransformerFactory tfac = TransformerFactory.newInstance();
		trans = tfac.newTemplates(src).newTransformer();
		return trans;
	}
}
/*
 * $Log$
 */