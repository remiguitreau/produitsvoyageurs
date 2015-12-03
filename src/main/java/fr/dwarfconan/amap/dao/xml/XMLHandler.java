/**
 * 
 */
package fr.dwarfconan.amap.dao.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Gestionnaire XML. 
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class XMLHandler {

	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------
	public XMLHandler() {
		super();
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------
	public void write(final Document document, final File destinationFile) throws TransformerException {
		//Vérification que l'on peut écrire dans le fichier.
		checkFile(destinationFile);
		Source source = new DOMSource(document);
		Result result = new StreamResult(destinationFile);
	    // Configuration du transformer
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	    // Ecriture dans le xml
	    transformer.transform(source, result);
	}
	
	public Element getFirstElementByAttr(final Document document, final String tagName, final String attrName, final String attrValue) {
		final NodeList list = document.getElementsByTagName(tagName);
		for(int i=0 ; i<list.getLength() ; i++) {
			final Element elt  = (Element)list.item(i);
			if(attrValue.equals(elt.getAttribute(attrName))) {
				return elt;
			}
		}
		return null;
	}
	
	public Document loadFile(final File file) throws ParserConfigurationException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		Document document;
		try {
			document = builder.parse(file);
		}
		catch(final Exception ex) {
			document = builder.newDocument();
		}
		return document;
	}
	
	public Element getRoot(final Document document , final String rootName) {
		final NodeList list = document.getElementsByTagName(rootName);
		Element root = (Element)((list==null || list.getLength()==0) ? null : list.item(0));
		return root == null ? createRoot(document, rootName) : root;
	}
	
	public Element createRoot(final Document document, final String rootName) {
		final Element rootElt = (Element)document.createElement(rootName); 
		document.appendChild(rootElt);
		return rootElt;		
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------
	private void checkFile(final File file) {
		//Création du répertoire parent s'il n'existe pas.
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}
}
