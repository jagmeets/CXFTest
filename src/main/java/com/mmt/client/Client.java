package com.mmt.client;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.springframework.core.io.ClassPathResource;

public final class Client {

	private Client() {
	}

	public static void main(String args[]) throws Exception {

		String host = "http://localhost:8080";
		String context = "/CXFTest-0.0.1-SNAPSHOT";
		// Sent HTTP GET request to query customer info
		//defaults to JSON
		System.out.println("Sent HTTP GET request to query customer info");
		URL url = new URL(host + context + "/customerservice/customers/123");
		InputStream in = url.openStream();
		System.out.println(getStringFromInputStream(in));

		// Sent HTTP GET request to query customer info, expect XML
		System.out.println("\n");
		System.out.println("Sent HTTP GET request to query customer info, expect XML");
		GetMethod get = new GetMethod(host + context + "/customerservice/customers/123");
		get.addRequestHeader("Accept" , "application/xml");
		HttpClient httpclient = new HttpClient();

		try {
			int result = httpclient.executeMethod(get);
			System.out.println("Response status code: " + result);
			System.out.println("Response body: ");
			System.out.println(get.getResponseBodyAsString());
		} finally {
			get.releaseConnection();
		}

		// Sent HTTP GET request to query sub resource product info
		System.out.println("\n");
		System.out.println("Sent HTTP GET request to query sub resource product info");
		url = new URL(host + context + "/customerservice/orders/223/products/323");
		in = url.openStream();
		System.out.println(getStringFromInputStream(in));

		// Sent HTTP PUT request to update customer info
		System.out.println("\n");
		System.out.println("Sent HTTP PUT request to update customer info");
		File input = new ClassPathResource("update_customer.xml").getFile();
		PutMethod put = new PutMethod(host + context + "/customerservice/customers");
		RequestEntity entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
		put.setRequestEntity(entity);
		httpclient = new HttpClient();

		try {
			int result = httpclient.executeMethod(put);
			System.out.println("Response status code: " + result);
			System.out.println("Response body: ");
			System.out.println(put.getResponseBodyAsString());
		} finally {
			// Release current connection to the connection pool once you are
			// done
			put.releaseConnection();
		}

		// Sent HTTP POST request to add customer
		System.out.println("\n");
		System.out.println("Sent HTTP POST request to add customer");
		input = new ClassPathResource("add_customer.xml").getFile();
		PostMethod post = new PostMethod(host + context + "/customerservice/customers");
		post.addRequestHeader("Accept" , "text/xml");
		entity = new FileRequestEntity(input, "text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		httpclient = new HttpClient();

		try {
			int result = httpclient.executeMethod(post);
			System.out.println("Response status code: " + result);
			System.out.println("Response body: ");
			System.out.println(post.getResponseBodyAsString());
		} finally {
			// Release current connection to the connection pool once you are
			// done
			post.releaseConnection();
		}

		System.out.println("\n");
		System.exit(0);
	}

	private static String getStringFromInputStream(InputStream in) throws Exception {
		CachedOutputStream bos = new CachedOutputStream();
		IOUtils.copy(in, bos);
		in.close();
		bos.close();
		return bos.getOut().toString();
	}

}
