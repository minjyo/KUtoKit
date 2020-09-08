package kutokit.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class xmlTest {

	private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder builder = null;
	private static XPath xPath = XPathFactory.newInstance().newXPath();
	private static Document doc;
	private static Node rootFod = null;

	// 임의로 지정
	private static String SDT_NAME_EXPRESSION = ".//SDT[@name='";
	public static final String SDT_EXPRESSION = "//SDT";
	public static final String FOD_EXPRESSION = "//FOD";
	public static final String CONDITION_EXPRESSION = "/condition";
	public static final String ACTION_EXPRESSION = "/action";
	public static final String CELL_EXPRESSION = "/cell[@col=0]"; 
	
	public xmlTest(String filePath) {

		String rootFodExpression = FOD_EXPRESSION;
		try {

			builder = builderFactory.newDocumentBuilder();
			doc = builder.parse(new FileInputStream(filePath));
			// Current root FOD
			rootFod = (Node) xPath.compile(rootFodExpression).evaluate(doc, XPathConstants.NODE);
			// System.out.println("root : " + rootFod.getAttributes().getNamedItem("name").getTextContent());

		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Get SDT[@name='nodeName'] Node
	public static Node getNode(String nodeName) {
		
		Node findedNode = null;
		String expression = SDT_NAME_EXPRESSION+nodeName+"']";
		try {
			findedNode = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
			System.out.println(findedNode.getAttributes().getNamedItem("name").getTextContent());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return findedNode;
	}

	// Get all SDT( TTS or FSM ) NodeList
	public static NodeList showValidFods() {

		NodeList fodNodes = xmlTest.getNodeList(xmlTest.getrootFod(), "//FOD");	// '//SDT, //FSM'	
		
		for(int i = 0; i < fodNodes.getLength(); i ++) {
			String name = fodNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
			System.out.println(name);
		}
		return fodNodes;
	}
	
	
	// Get SDT[@name='nodeName']'s variables list
	public static NodeList getNodeList(Node node, String expression) {
	
		NodeList findedNode = null;
		String nodeName = node.getAttributes().getNamedItem("name").getTextContent();
		String prevNodeName = SDT_NAME_EXPRESSION+nodeName+"']";
				
		if(expression.equals(CONDITION_EXPRESSION)) { // 'condition' variables
			try {
				findedNode = (NodeList)xPath.evaluate(prevNodeName+CONDITION_EXPRESSION+CELL_EXPRESSION, doc, XPathConstants.NODESET);
				System.out.println(" 1. condition variable's length : "+findedNode.getLength());
				for( int idx=0; idx<findedNode.getLength(); idx++ ){
					System.out.println(findedNode.item(idx).getAttributes().getNamedItem("value").getTextContent());
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
			System.out.println();
			
		} else if (expression.equals(ACTION_EXPRESSION)) { // 'action' variables
			try {
				findedNode = (NodeList)xPath.evaluate(prevNodeName+ACTION_EXPRESSION+CELL_EXPRESSION, doc, XPathConstants.NODESET);
				System.out.println(" 2. action variable's length : "+findedNode.getLength());
				for( int idx=0; idx<findedNode.getLength(); idx++ ){
					System.out.println(findedNode.item(idx).getAttributes().getNamedItem("value").getTextContent());
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		} else {
			// Except Searching SDT..
			try {
				findedNode = (NodeList)xPath.evaluate(expression, doc, XPathConstants.NODESET);
				for( int idx=0; idx<findedNode.getLength(); idx++ ){
					System.out.println(findedNode.item(idx).getAttributes().getNamedItem("name").getTextContent());
				}
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		return findedNode;
	}

	public static Node getrootFod() {
		return rootFod;
	}

}
