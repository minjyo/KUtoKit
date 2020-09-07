package kutokit.model;

import java.io.FileInputStream;
import java.io.IOException;

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

	public static final String FOD_EXPRESSION = "//FOD";

	public xmlTest(String filePath) {

		String rootFodExpression = FOD_EXPRESSION;

		try {

			builder = builderFactory.newDocumentBuilder();
			doc = builder.parse(new FileInputStream(filePath));
			
			// 현재 rootFod
			rootFod = (Node) xPath.compile(rootFodExpression).evaluate(doc, XPathConstants.NODE);
			System.out.println("root : " + rootFod.getAttributes().getNamedItem("name").getTextContent());

			// xpath 생성
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			// ex) id = 103 인 SDT
			String expression = ".//SDT[@id='103']";
			
			Node sdt1 = (Node)xpath.evaluate(expression, doc, XPathConstants.NODE);
		        System.out.println(sdt1.getAttributes().getNamedItem("name").getTextContent());
		    
		    // col = 0 인 cell nodelist 생성
		    NodeList condition_value = (NodeList)xpath.evaluate(expression+"/condition/cell[@col=0]", doc, XPathConstants.NODESET);
			    System.out.println(" 1. condition variable's length : "+condition_value.getLength());
			    for( int idx=0; idx<condition_value.getLength(); idx++ ){
		            System.out.println(condition_value.item(idx).getAttributes().getNamedItem("value").getTextContent());
		        }
			    System.out.println();
		    
		    NodeList action_value = (NodeList)xpath.evaluate(expression+"/action/cell[@col=0]", doc, XPathConstants.NODESET);
			    System.out.println(" 2. action variable's length : "+action_value.getLength());
			    for( int idx=0; idx<action_value.getLength(); idx++ ){
		            System.out.println(action_value.item(idx).getAttributes().getNamedItem("value").getTextContent());
		        }
		    
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		xmlTest test = new xmlTest("NuSCR_example.xml");
	}
}
