	package kutokit.model.pmm;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {

	private static XPath xPath = XPathFactory.newInstance().newXPath();
	private static Document doc;
	public static Node rootFod = null;

	private static String prevRootFodExpression = "//FOD[@name='";

	private static String SDT_BASE_EXPRESSION = ".//SDT[@name='";
	private static String TTS_BASE_EXPRESSION = ".//TTS[@name='";
	private static String FSM_BASE_EXPRESSION = ".//FSM[@name='";
	public static final String FOD_EXPRESSION = "//FOD";
	public static final String CONDITION_EXPRESSION = "/condition";
	public static final String ACTION_EXPRESSION = "/action";
	public static final String CELL_EXPRESSION = "/cell[@col=0]"; 
	public static final String TRANSITIONS_EXPRESSION = ".//transitions";
	public static final String TRANSITION_EXPRESSION = ".//transition";
	public static final String TARGET_EXPRESSION = "/target[@refName='";
	public static final String SOURCE_EXPRESSION = "/source";
	public static final String ASSIGN_EXPRESSION = "/assignments";
	public static final String OUTPUT_EXPRESSION = ".//output";
	public static String firstLetter = "";
	
	public XmlReader(String filePath) {

		String rootFodExpression = FOD_EXPRESSION;
		try {
			
			doc = parseFile(filePath);
			rootFod = (Node) xPath.compile(rootFodExpression).evaluate(doc, XPathConstants.NODE);
			 System.out.println("root : " + rootFod.getAttributes().getNamedItem("name").getTextContent());

		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// Parse XML File
	public static Document parseFile(String filePath) {
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		
		try {
			builder = builderFactory.newDocumentBuilder();
			System.out.println("filePath: "+filePath);
			doc = builder.parse(new FileInputStream(filePath));
		} catch(Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	
	// Get SDT/TTS/FSM Node
	public static Node getNode(String nodeName) {
		
		String inputNode = nodeName.substring(0,1);
		Node findedNode = null;
		String expression = "";
		
		if( inputNode.equals("f")) { // SDT
			expression = SDT_BASE_EXPRESSION+nodeName+"']";
			firstLetter = "f";
		} else if(inputNode.equals("t")) { // TTS 
			expression = TTS_BASE_EXPRESSION+nodeName+"']";
			firstLetter = "t";
		} else if(inputNode.equals("h")) { // FSM
			expression = FSM_BASE_EXPRESSION+nodeName+"']";
			firstLetter = "h";
		} else if(inputNode.equals("g")) {
			expression = prevRootFodExpression+nodeName+"']";
			firstLetter = "g";
		}
		
		if(!expression.equals("")) {
			try {
				findedNode = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}	
		}
		return findedNode;
	}

	// Get SDT/TTS/FSM variables list
	public static NodeList getNodeList(Node node, String expression) {
	
		NodeList findedList = null;
		NodeList cellList = null;

		String prevNodeName = "";
			if(isEmpty(expression)) {
				try {
				String nodeName = node.getAttributes().getNamedItem("name").getTextContent();
					switch(firstLetter) {
						// SDT
						case "f": 
							prevNodeName = SDT_BASE_EXPRESSION+nodeName+"']";
							findedList = (NodeList) xPath.evaluate(prevNodeName, doc, XPathConstants.NODESET);
							cellList = ((NodeList) xPath.evaluate(prevNodeName+"/"+CELL_EXPRESSION, doc, XPathConstants.NODESET));
							findedList = cellList;	
							break;
						// TTS
						case "t":
							prevNodeName = TTS_BASE_EXPRESSION+nodeName+"']";
							findedList = (NodeList)xPath.evaluate(prevNodeName, doc, XPathConstants.NODESET);
							break;
						//FSM
						case "h":
							prevNodeName = FSM_BASE_EXPRESSION+nodeName+"']";
							findedList = (NodeList)xPath.evaluate(prevNodeName, doc, XPathConstants.NODESET);
							break;
						case "g":
							prevNodeName = prevRootFodExpression+nodeName+"']";
							findedList = (NodeList)xPath.evaluate(prevNodeName, doc, XPathConstants.NODESET);
							break;
					}
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					return findedList;
				}	
			}
			else { 
				try {
					prevNodeName = ".//"+node.getNodeName();
					if(expression.equals(FOD_EXPRESSION)) {
						findedList = (NodeList)xPath.evaluate(prevNodeName, doc, XPathConstants.NODESET);
					}else {
						findedList = (NodeList)xPath.evaluate(prevNodeName+expression, doc, XPathConstants.NODESET);
					}
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				} 
			}
			return findedList;
	}

	// Get transition Node list
	public static List getTransitionNodes(Node node) {
		
		List<String> sourceNodelist = new ArrayList();
		NodeList targetNodelist = null;
		List<String> condNodelist = null;
		
		// Way B : transitions
		try {
			String nodeName = node.getAttributes().getNamedItem("name").getTextContent();
			// 1. Get target node list with refName[nodeName] 
			targetNodelist = ((NodeList) xPath.evaluate(".//transitions/transition"+TARGET_EXPRESSION+nodeName+"']", doc, XPathConstants.NODESET));
		
			// 2. Get source node list in transition node list
			sourceNodelist = getSourceNodeList(targetNodelist, "source");
				
			// Way A : transitions in TTS 
			if( firstLetter.equals("t") || firstLetter.equals("h")) {
				System.out.println();
				String expression = nodeName+"']"+"/transitions/transition"+ASSIGN_EXPRESSION;
				try {
					// 1. Get assignments node list what contain [nodeName] contents
					if(firstLetter.equals("t")) 
						targetNodelist = ((NodeList) xPath.evaluate(TTS_BASE_EXPRESSION+expression, doc, XPathConstants.NODESET));
					else if(firstLetter.equals("h")) 
						targetNodelist = ((NodeList) xPath.evaluate(FSM_BASE_EXPRESSION+expression, doc, XPathConstants.NODESET));
	 
				} catch (XPathExpressionException e) { e.printStackTrace(); }
				
				// 2. Get conditions node list in transition node list
				condNodelist = getSourceNodeList(targetNodelist, "conditions");
				for(String cond: condNodelist) {
					sourceNodelist.add(cond);
				}
			}
		} catch (XPathExpressionException e) { e.printStackTrace(); }
		  catch (NullPointerException e) { return sourceNodelist; }
		
//		for(String str: sourceNodelist) {
//			System.out.println(str);
//		}
		
		return sourceNodelist;
	}
	// To help getTransitionNodes()
	public static List getSourceNodeList(NodeList targetlist, String attr) {
		
		List sourceNodelist = new ArrayList();
		Node parentNode = null;

		for(int i=0 ; i<targetlist.getLength(); i++) {
			parentNode = targetlist.item(i).getParentNode();
			for(int j=0; j<parentNode.getChildNodes().getLength(); j++) {
				if( parentNode.getChildNodes().item(j).getNodeName().equals(attr)) {
					if( attr.equals("conditions"))
						sourceNodelist.add(parentNode.getChildNodes().item(j).getTextContent());						
					else if ( attr.equals("source"))
						sourceNodelist.add(parentNode.getChildNodes().item(j).getAttributes().getNamedItem("refName").getTextContent());
				}
			}
		}
		return sourceNodelist;
	}
	
	public static void setRootFod(String fodName) {
		String rootFodExpression = prevRootFodExpression + fodName + "']";
		try {
			rootFod = (Node) xPath.compile(rootFodExpression).evaluate(doc, XPathConstants.NODE);

			System.out.println("rootFOD name : " + rootFod.getAttributes().getNamedItem("name").getTextContent());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	public static Node getRootFod() {
		return rootFod;
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}
	
	
	public static List<String> getOutputs() {
		NodeList outputNodes = null;
		List<String> outputList = new ArrayList<String>();
		try {
			String expression = prevRootFodExpression+rootFod.getAttributes().getNamedItem("name").getTextContent()+"']";
			outputNodes = ((NodeList) xPath.evaluate(expression+"/nodes/output", doc, XPathConstants.NODESET));
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		List<Node> outputs = new ArrayList<Node>();

		for(int i = 0; i < outputNodes.getLength(); i ++) {
//			            String name = outputNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
//			            System.out.println("output : " + name);
			outputs.add(outputNodes.item(i));

		}

		TreeSet<String> tree = new TreeSet<String>();
	    for(Node value: outputs) {
	    	tree.add(value.getAttributes().getNamedItem("name").getTextContent());
	    }      
	    
	    Iterator<String> it = tree.iterator();
        while ( it.hasNext() ) {
        	outputList.add((String) it.next());
        }
		return outputList;
	}
	
	public static List<Node> showValidFods() {

		NodeList fodNodes = XmlReader.getNodeList(XmlReader.getRootFod(), XmlReader.FOD_EXPRESSION);
		List<Node> validFods = new ArrayList<Node>();

		for(int i = 0; i < fodNodes.getLength(); i ++) {
			String name = fodNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
			if(name.equals("Root")) {
			} else {
				validFods.add(fodNodes.item(i));
			}
		}

		return validFods;
	}
	
	public static Node getNode(Node node, String nodeName) {

		Node findedNode = null;
		String nodeExpression = ".//" + nodeName;

		try {
			findedNode = (Node) xPath.compile(nodeExpression).evaluate(node, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return findedNode;
	}
//	
//	public static void main(String args[]) {
//		
//		XmlReader reader = new XmlReader("CVM_ver4_complete_nographic.xml");
////		XmlReader reader = new XmlReader("NuSCR_example.xml");
////		reader.getNodeList(getNode("f_display_makeable_coffee_1"), "");
////		reader.getTransitionNodes(getNode("f_display_makeable_coffee_1"));
//
//		// Show all FODs
//		for(Node fod: reader.showValidFods()) {
//			System.out.println(fod.getAttributes().getNamedItem("name") + ": root");
//		}
//		
//		// Select FODs
//		reader.setRootFod("g_Actuator");
//		
//		// Get output variables about Selected FODs
//		System.out.println(	reader.getOutputs());
//	}
}
