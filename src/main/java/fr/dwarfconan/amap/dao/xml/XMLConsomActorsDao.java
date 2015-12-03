/**
 * 
 */
package fr.dwarfconan.amap.dao.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import fr.dwarfconan.amap.ConsomActor;
import fr.dwarfconan.amap.dao.ConsomActorsDao;

/**
 * Dao XML des Consom'acteurs
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class XMLConsomActorsDao extends XMLHandler implements ConsomActorsDao {
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private Document document;
	private Element root;
	private File consomActorsFile;
	
	//---------------------------------------------------------
	// Constructeurs
	//---------------------------------------------------------

	public XMLConsomActorsDao(final File datasRepository) {
		super();
		
		this.consomActorsFile = new File(datasRepository, XMLConstants.CONSOM_ACTORS_FILE); ;
		parseConsomActorsFile();
	}

	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------

	private void parseConsomActorsFile() {
		try {
			document = loadFile(consomActorsFile);			
			root = getRoot(document, XMLConstants.CONSOM_ACTORS_ROOT);
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Element createConsomActorNode(final ConsomActor consomActor) {
		final Element node = document.createElement(XMLConstants.CONSOM_ACTOR_NODE);
		node.setAttribute(XMLConstants.ATTR_ID, consomActor.getId());
		node.setAttribute(XMLConstants.ATTR_NAME, consomActor.getName());
		node.setAttribute(XMLConstants.ATTR_FIRST_NAME, consomActor.getFirstName());
		node.setAttribute(XMLConstants.ATTR_ADDRESS, consomActor.getAddress());
		node.setAttribute(XMLConstants.ATTR_POST_CODE, consomActor.getPostCode());
		node.setAttribute(XMLConstants.ATTR_TOWN, consomActor.getTown());
		node.setAttribute(XMLConstants.ATTR_TELEPHONE, consomActor.getTelephone());
		node.setAttribute(XMLConstants.ATTR_EMAIL, consomActor.getEmail());
		return node;
	}
	
	private ConsomActor extractConsomActor(final Element node) {
		if(node!=null) {
			final ConsomActor consomActor = new ConsomActor(node.getAttribute(XMLConstants.ATTR_ID));
			consomActor.setName(node.getAttribute(XMLConstants.ATTR_NAME));
			consomActor.setFirstName(node.getAttribute(XMLConstants.ATTR_FIRST_NAME));
			consomActor.setAddress(node.getAttribute(XMLConstants.ATTR_ADDRESS));
			consomActor.setPostCode(node.getAttribute(XMLConstants.ATTR_POST_CODE));
			consomActor.setTown(node.getAttribute(XMLConstants.ATTR_TOWN));
			consomActor.setTelephone(node.getAttribute(XMLConstants.ATTR_TELEPHONE));
			consomActor.setEmail(node.getAttribute(XMLConstants.ATTR_EMAIL));
			return consomActor;
		}
		return null;
	}
	
	private void writeConsomActors() {
		try {
			write(document, consomActorsFile);
		}
		catch(Exception e) {
			System.out.println("Impossible d'écrire le fichier "+consomActorsFile+" -> "+e);
			e.printStackTrace();
		}
	}
	
	private Element getConsomActorElement(final String id) {
		return getFirstElementByAttr(document, XMLConstants.CONSOM_ACTOR_NODE, XMLConstants.ATTR_ID, id);
	}
	
	//---------------------------------------------------------
	// Implémentation de ConsomActorsDao
	//---------------------------------------------------------

	@Override
	public List<ConsomActor> retrieveConsomActors() {
		final List<ConsomActor> consomActors = new ArrayList<ConsomActor>();
		NodeList nodes = root.getElementsByTagName(XMLConstants.CONSOM_ACTOR_NODE);
		for(int i = 0, length=nodes.getLength() ; i<length ; ++i) {
			consomActors.add(extractConsomActor((Element)nodes.item(i)));
		}
		return consomActors;
	}

	@Override
	public ConsomActor retrieveConsomActor(final String id) {
		return extractConsomActor(getFirstElementByAttr(document, XMLConstants.CONSOM_ACTOR_NODE, XMLConstants.ATTR_ID, id));
	}
	
	@Override
	public void saveConsomActor(final ConsomActor consomActor) {
		//Si l'élément est déjà présent on le supprime avant de le réajouter.
		final Element consElement = getConsomActorElement(consomActor.getId());
		if(consElement != null) {
			root.removeChild(consElement);
		}
		root.appendChild(createConsomActorNode(consomActor));
		writeConsomActors();
	}

	@Override
	public void deleteConsomActor(final ConsomActor consomActor) {
		final Element consomElt = getConsomActorElement(consomActor.getId());
		if(consomElt != null) {
			root.removeChild(consomElt);
			writeConsomActors();
		}
	}
}
