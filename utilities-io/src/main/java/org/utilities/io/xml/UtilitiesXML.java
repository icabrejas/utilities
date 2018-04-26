package org.utilities.io.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;
import org.utilities.core.lang.iterable.IterablePipeSequence;
import org.utilities.core.util.function.BiConsumerPlus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UtilitiesXML {

	public static Element read(File file) throws QuietException {
		try {
			return DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(file)
					.getDocumentElement();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new QuietException(e);
		}
	}

	public static Element read(InputStream stream) throws QuietException {
		try {
			return DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(stream)
					.getDocumentElement();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new QuietException(e);
		}
	}

	public static Element read(URL uri) throws QuietException {
		try {
			return DocumentBuilderFactory.newInstance()
					.newDocumentBuilder()
					.parse(uri.toString())
					.getDocumentElement();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new QuietException(e);
		}
	}

	public static void process(IterablePipe<Node> nodes, Consumer<Node> childConsumer) {
		nodes.forEach(childConsumer);
	}

	public static void process(IterablePipe<Node> nodes, BiConsumer<String, String> childConsumer) {
		nodes.forEach(BiConsumerPlus.parseConsumer(childConsumer, Node::getNodeName, Node::getTextContent));
	}

	public static IterablePipe<Node> getChildNodes(Node node) {
		NodeList children = node.getChildNodes();
		return IterablePipeSequence.from(0, children.getLength() - 1)
				.map(children::item);
	}

	public static void processChildNodes(Node node, BiConsumer<String, String> childConsumer) {
		process(UtilitiesXML.getChildNodes(node), childConsumer);
	}

	public static void processChildNodes(Node node, Consumer<Node> childConsumer) {
		process(UtilitiesXML.getChildNodes(node), childConsumer);
	}

	public static IterablePipe<Node> getAttributes(Node node) {
		NamedNodeMap attributes = node.getAttributes();
		return IterablePipeSequence.from(0, attributes.getLength() - 1)
				.map(attributes::item);
	}

	public static void processAttributes(Node node, BiConsumer<String, String> childConsumer) {
		process(UtilitiesXML.getAttributes(node), childConsumer);
	}

	public static void processAttributes(Node node, Consumer<Node> childConsumer) {
		process(UtilitiesXML.getAttributes(node), childConsumer);
	}

	public static String parseString(Node node) {
		StringWriter writer = new StringWriter();
		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(node), new StreamResult(writer));
			return writer.toString();
		} catch (TransformerFactoryConfigurationError | TransformerException e) {
			throw new QuietException(e);
		}
	}

	public static IterablePipe<Node> childs(NodeList node) {
		return IterablePipe.newInstance(node::item, node::getLength);
	}

	public static IterablePipe<Node> childs(Node node) {
		return childs(node.getChildNodes());
	}

	public static IterablePipe<Node> elementsByTagName(Document doc, String tagname) {
		return childs(doc.getElementsByTagName(tagname));
	}

}
