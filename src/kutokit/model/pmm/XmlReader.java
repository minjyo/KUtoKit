	package kutokit.model.pmm;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
	private static Node rootFod = null;

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
			// System.out.println("root : " + rootFod.getAttributes().getNamedItem("name").getTextContent());

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
		}
		
		try {
			findedNode = (Node) xPath.evaluate(expression, doc, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return findedNode;
	}

	// Get SDT/TTS/FSM variables list
	public static NodeList getNodeList(Node node, String expression) {
	
		NodeList findedList = null;
		NodeList cellList = null;

		String prevNodeName = "";
			if(isEmpty(expression)) {
				String nodeName = node.getAttributes().getNamedItem("name").getTextContent();
				try {
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
					}
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				} 	
			}
			else { 
				try {
					prevNodeName = ".//"+node.getNodeName();
					findedList = (NodeList)xPath.evaluate(prevNodeName+expression, doc, XPathConstants.NODESET);
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
		
		String nodeName = node.getAttributes().getNamedItem("name").getTextContent();

		// Way B : transitions
		try {
			// 1. Get target node list with refName[nodeName] 
			targetNodelist = ((NodeList) xPath.evaluate(".//transitions/transition"+TARGET_EXPRESSION+nodeName+"']", doc, XPathConstants.NODESET));
		} catch (XPathExpressionException e) { e.printStackTrace(); }
		// 2. Get source node list in transition node list
		sourceNodelist = getSourceNodeList(targetNodelist, "source");
				
		// Way A : transitions in TTS 
		if( firstLetter.equals("t")) {
			try {
				// 1. Get assignments node list what contain [nodeName] contents
				targetNodelist = ((NodeList) xPath.evaluate(TTS_BASE_EXPRESSION+nodeName+"']"+"/transitions/transition"+ASSIGN_EXPRESSION, doc, XPathConstants.NODESET));
			} catch (XPathExpressionException e) { e.printStackTrace(); }
			
			// 2. Get conditions node list in transition node list
			condNodelist = getSourceNodeList(targetNodelist, "conditions");
			for(String cond: condNodelist) {
				sourceNodelist.add(cond);
			}
		}
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
			outputNodes = (NodeList) xPath.compile(OUTPUT_EXPRESSION).evaluate(XmlReader.getRootFod(), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		List<Node> outputs = new ArrayList<Node>();

		for(int i = 0; i < outputNodes.getLength(); i ++) {
			            //String name = outputNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
			            //System.out.println("output : " + name);
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

			if(XmlReader.getNode(fodNodes.item(i), "FOD") == null) {
				validFods.add(fodNodes.item(i));
			} else {
			}

		}

		return validFods;
	}
	
	public static Node getNode(Node node, String nodeName) {

		Node findedNode = null;
		String nodeExpression = ".//" + nodeName;

		try {
			findedNode = (Node) xPath.compile(nodeExpression).evaluate(node, XPathConstants.NODE);
			//            //system.out.println("findedNode name : " + findedNode.getNodeName());
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return findedNode;
	}
	
	public static void main(String args[]) {
		XmlReader reader = new XmlReader("NuSCR_example.xml");
		List<String> list = reader.getOutputs();
		
		
	}
}
