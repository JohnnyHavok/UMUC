/**
 * File: SignedRequestsHelper.java
 * 
 * This file contains a class that supports creating a signature for an Amazon REST
 * Request and building the final REST request string
 * 
 * Source: Amazon Sample - See 
 *    http://docs.amazonwebservices.com/AWSECommerceService/latest/DG/index.html?RequestAuthenticationArticle.html
 *    for more information
 *
 * Modified: Mike Tarquinio
 *
 */

package com.cmis445.amazonrestclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * SignedRequestsHelper
 * 
 * This class supports creating a signature for a Amazon REST request and
 * building the final request string.
 * 
 */
public class SignedRequestsHelper {
	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String REQUEST_URI = "/onca/xml";
	private static final String REQUEST_METHOD = "GET";

	private String endPoint = null;
	private String awsAccessKeyId = null;

	private SecretKeySpec secretKeySpec = null;
	private Mac mac = null;

	/**
	 * SignedRequestsHelper - Constructor
	 * 
	 * @param awsAccessKeyId -
	 *            public access key that Amazon assigns
	 * @param awsSecretKey -
	 *            secret access key that Amazon assigns
	 * @param endPoint -
	 *            Amazon REST service endpoint
	 */
	public SignedRequestsHelper(String awsAccessKeyId, String awsSecretKey,
			String endPoint) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException {

		this.awsAccessKeyId = awsAccessKeyId;
		this.endPoint = endPoint;

		byte[] secretyKeyBytes = awsSecretKey.getBytes(UTF8_CHARSET);
		secretKeySpec = new SecretKeySpec(secretyKeyBytes,
				HMAC_SHA256_ALGORITHM);
		mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
		mac.init(secretKeySpec);
	}

	/**
	 * sign - signs and builds the Amazon REST request
	 * 
	 * @param Map -
	 *            Map containing string pairs of parameter, value pairs for a
	 *            Amazon REST call
	 * @return - Sign REST call
	 */
	public String sign(Map<String, String> params) {
		params.put("AWSAccessKeyId", awsAccessKeyId);
		params.put("Timestamp", getTime());

		SortedMap<String, String> sortedParamMap = new TreeMap<String, String>(
				params);
		String canonicalQS = canonicalize(sortedParamMap);
		String toSign = REQUEST_METHOD + "\n" + endPoint + "\n" + REQUEST_URI
				+ "\n" + canonicalQS;

		String hmac = hmac(toSign);
		String sig = percentEncodeRfc3986(hmac);

		// strip out the trailing carriage return and line feed
		sig = sig.replace("%0D%0A", "");

		String url = "http://" + endPoint + REQUEST_URI + "?" + canonicalQS
				+ "&Signature=" + sig;

		return url;
	}

	protected String getTime() {
		return timestamp();
	}

	private String hmac(String stringToSign) {
		String signature = null;
		byte[] data;
		byte[] rawHmac;
		try {
			data = stringToSign.getBytes(UTF8_CHARSET);
			rawHmac = mac.doFinal(data);
			Base64 encoder = new Base64();
			signature = new String(encoder.encode(rawHmac));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(UTF8_CHARSET + " is unsupported!", e);
		}
		return signature;
	}

	private String timestamp() {
		String timestamp = null;
		Calendar cal = Calendar.getInstance();
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dfm.setTimeZone(TimeZone.getTimeZone("GMT"));
		timestamp = dfm.format(cal.getTime());
		return timestamp;
	}

	private String canonicalize(SortedMap<String, String> sortedParamMap) {
		if (sortedParamMap.isEmpty()) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		Iterator<Map.Entry<String, String>> iter = sortedParamMap.entrySet()
				.iterator();

		while (iter.hasNext()) {
			Map.Entry<String, String> kvpair = iter.next();
			buffer.append(percentEncodeRfc3986(kvpair.getKey()));
			buffer.append("=");
			buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			if (iter.hasNext()) {
				buffer.append("&");
			}
		}
		String cannoical = buffer.toString();
		return cannoical;
	}

	private String percentEncodeRfc3986(String s) {
		String out;
		try {
			out = URLEncoder.encode(s, UTF8_CHARSET).replace("+", "%20")
					.replace("*", "%2A").replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			out = s;
		}
		return out;
	}
}
