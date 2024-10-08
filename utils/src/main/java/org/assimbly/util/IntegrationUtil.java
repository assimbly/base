package org.assimbly.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.*;


public final class IntegrationUtil {

	protected final static Logger log = LoggerFactory.getLogger("org.assimbly.util.IntegrationUtil");

	public static boolean isValidUri(String name) throws Exception {
		try {
			URI uri = new URI(name);

			if(uri.getScheme() == null){
				return false;
			}else{
				return true;
			}

		} catch (URISyntaxException e) {
			return false;
		}

	}

	public static boolean isYaml(String yaml){
		try {
			final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			mapper.readTree(yaml);
			return true;
		 } catch (IOException e) {
			return false;
		 }
	}

	public static boolean isJson(String json){
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.readTree(json);
			return true;
		 } catch (IOException e) {
			return false;
		 }
	}

	public static boolean isXML(String xml) {
		if(xml.startsWith("<")){
			return true;
		}else{
			return false;
		}
	}
	
	public static String isValidXML(URL schemaFile, String xml) {

		String result = null;

		Source xmlFile = new StreamSource(new StringReader(xml));
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = schemaFactory.newSchema(schemaFile);
			Validator validator = schema.newValidator();
			validator.validate(xmlFile);
			result = "xml is valid";
		} catch (SAXException e) {
			result = "xml is NOT valid. Reason:" + e;
		} catch (IOException e) {}

		return result;

	}

	public static Resource setResource(String route){

		String uuid = UUID.randomUUID().toString();

		if(IntegrationUtil.isXML(route)){
			return ResourceHelper.fromString("route_" + uuid + ".xml", route);
		}else if(IntegrationUtil.isYaml(route)){
			return ResourceHelper.fromString("route_" + uuid + ".yaml", route);
		}else{
			log.warn("unknown route format");
			return ResourceHelper.fromString("route_" + uuid + ".xml", route);
		}		
	}

	@SuppressWarnings("resource")
	public static String testConnection(String host, int port, int timeOut) {

		SocketAddress socketAddress = new InetSocketAddress(host, port);
		Socket socket = new Socket();
		timeOut = timeOut * 1000;

		try {
			socket.connect(socketAddress, timeOut);
		} catch (SocketTimeoutException stex) {
			return "Connection error: Timed out";
		} catch (IOException ioException) {
			return "Connection error: IOException";
		} finally {
			try {
				socket.close();
			} catch (IOException ioException2) {
				return "Connection error: Can't close connection." + ioException2.getMessage();
			}
		}

		return "Connection successful";
	}


	public static List<String> getXMLParameters(XMLConfiguration conf, String prefix) throws ConfigurationException {

		Iterator<String> keys;

		if(prefix == null || prefix.isEmpty()){
			keys = conf.getKeys();
		}else{
			keys = conf.getKeys(prefix);
		}

		List<String> keyList = new ArrayList<String>();

		while(keys.hasNext()){
			keyList.add(keys.next());
		}

		return keyList;
	}

	public static Node getNode(XMLConfiguration xmlConfiguration, String xpath) throws XPathExpressionException {

		Document doc = xmlConfiguration.getDocument();

		XPath xpathFactory = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpathFactory.compile(xpath);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);

		return node;

	}


	public static NodeList getNodeList(String xml, String nodeName) throws IOException, SAXException, ParserConfigurationException {

		InputStream isr = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(isr);

		NodeList nodeList = doc.getElementsByTagName(nodeName);

		return nodeList;
	}

	public static Iterable<Node> iterable(final NodeList nodeList) {
		return () -> new Iterator<Node>() {

			private int index = 0;

			@Override
			public boolean hasNext() {
				return index < nodeList.getLength();
			}

			@Override
			public Node next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return nodeList.item(index++);
			}
		};
	}

	public static void printTreemap(TreeMap<String, String> treeMap) throws Exception {

		System.out.println("print treemap: \n");
		for (Map.Entry<String,String> entry : treeMap.entrySet()) {
			System.out.println("key: " + entry.getKey() + "; value: " + entry.getValue());
		}

	}

	public static void printConfiguration(TreeMap<String, String> treeMap) throws Exception {

		List<String> items = Arrays.asList( "id", "flow", "source", "action", "sink", "response", "error", "header", "connection", "route", "routeConfiguration", "routeTemplate");

		String configuration = convertTreemapToString(treeMap, items);

		System.out.println(configuration);

	}

	public static String convertTreemapToString(TreeMap<String, String> treeMap, List<String> items) throws Exception {

		StringBuilder string = new StringBuilder();

		string.append("\n");
		string.append("Flow Configuration\n");
		string.append("-----------------------------------------------------------------\n");

		Map<String, String> subMap = null;

		for (String item : items) {

			subMap = treeMap.entrySet()
					.stream()
					.filter(map -> map.getKey().startsWith(item))
					.collect(Collectors.toMap(map -> map.getKey(), map -> Optional.ofNullable(map.getValue()).orElse("")));

			if(subMap.size() > 0) {

				string.append("\n" + item.toUpperCase() + "\n");

				for(Map.Entry<String,String> entry : subMap.entrySet()) {

					String key = entry.getKey();
					String value = entry.getValue();

					if (key.contains("password")){
						value = "***********";
					}

					string.append(key + ":" + value + "\n");
				}
			}

		}

		string.append("\n");

		return string.toString();
	}

}
