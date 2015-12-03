/**
 * 
 */
package fr.dwarfconan.amap.dao.xml;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.dwarfconan.amap.Article;
import fr.dwarfconan.amap.Contract;
import fr.dwarfconan.amap.MultipleContract;
import fr.dwarfconan.amap.Season;
import fr.dwarfconan.amap.SimpleContract;
import fr.dwarfconan.amap.dao.ConsomActorsDao;

/**
 * Gestionnaire XML des saisons.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class XMLSeasonHandler extends XMLHandler {
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private File seasonsRep;
	
	private final DateFormat dateFormat;
	
	private final ConsomActorsDao consomActorsDao;
	
	//---------------------------------------------------------
	// Constructeurs
	//---------------------------------------------------------

	public XMLSeasonHandler(final File seasonsRep, final ConsomActorsDao consomActorsDao) {
		super();
		
		this.seasonsRep = seasonsRep;
		this.consomActorsDao = consomActorsDao;
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------
	public void writeSeason(final Season season) {
		//Chargement du fichier saison existant
		final File seasonFile = getSeasonFile(season);
		final Document document = loadSeasonFile(seasonFile);
		//Remplissage du document avec la saison.
		fillSeasonDocument(document, season);
		//Ecriture du fichier XML
		try {
			write(document, seasonFile);
		}
		catch(final Exception ex) {
			System.out.println("Erreur lors de l'écriture de "+seasonFile);
			ex.printStackTrace();
		}
	}
	
	public Season loadSeason(final File seasonFile) {
		final Document document = loadSeasonFile(seasonFile);
		return extractSeason(document, getRoot(document, XMLConstants.SEASON_ROOT), seasonFile.getParentFile().getName());
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------
	private File getSeasonFile(final Season season) {
		return new File(new File(seasonsRep, season.getProduct()), season.getName()+".xml");
	}
	
	private Document loadSeasonFile(final File seasonFile) {
		try {
			return loadFile(seasonFile);
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private void fillSeasonDocument(final Document document, final Season season) {
		final Element root = getRoot(document, XMLConstants.SEASON_ROOT);
		root.setAttribute(XMLConstants.ATTR_SEASON_NAME, season.getName());
		root.setAttribute(XMLConstants.ATTR_SEASON_BEGIN, dateFormat.format(season.getBeginDate()));
		root.setAttribute(XMLConstants.ATTR_SEASON_END, dateFormat.format(season.getEndDate()));
		removeOldNodes(root, XMLConstants.INTERRUPTIONS_NODE);
		root.appendChild(createInterruptionsElement(document, season.getInterruptions()));
		removeOldNodes(root, XMLConstants.ARTICLES_NODE);
		root.appendChild(createArticlesElement(document, season.getArticlesAvailable()));
		removeOldNodes(root, XMLConstants.CONTRACTS_NODE);
		root.appendChild(createContractsElement(document, season.getContracts()));
	}
	
	private void removeOldNodes(final Element parentElt, final String tagName) {
		final NodeList list = parentElt.getElementsByTagName(tagName);
		if(list != null && list.getLength() > 0) {
			for(int i=list.getLength()-1 ; i>=0 ; i--) {
				parentElt.removeChild(list.item(i));
			}
		}
	}
	
	private Element createInterruptionsElement(final Document document, final List<Date> interruptions) {		
		final Element interruptionsElt = document.createElement(XMLConstants.INTERRUPTIONS_NODE);
		if(interruptions != null && !interruptions.isEmpty()) {			
			for(final Date interruption : interruptions) {
				final Element interruptionElt = document.createElement(XMLConstants.INTERRUPTION_NODE);
				interruptionElt.setNodeValue(dateFormat.format(interruption));
				interruptionsElt.appendChild(interruptionElt);
			}			
		}
		return interruptionsElt;
	}
	
	private Element createArticlesElement(final Document document, final List<Article> articles) {
		final Element articlesElt = document.createElement(XMLConstants.ARTICLES_NODE);
		if(articles != null && !articles.isEmpty()) {			
			for(final Article article : articles) {
				final Element articleElt = document.createElement(XMLConstants.ARTICLE_NODE);
				articleElt.setAttribute(XMLConstants.ATTR_ARTICLE_NAME, article.getName());
				articleElt.setAttribute(XMLConstants.ATTR_ARTICLE_PRICE, String.valueOf(article.getUnitPrice()));
				articlesElt.appendChild(articleElt);
			}			
		}
		return articlesElt;
	}
	
	private Element createContractsElement(final Document document, final List<Contract> contracts) {
		final Element contractsElt = document.createElement(XMLConstants.CONTRACTS_NODE);
		if(contracts != null && !contracts.isEmpty()) {			
			for(final Contract contract : contracts) {
				if(contract instanceof SimpleContract) {
					contractsElt.appendChild(createSimpleContractElement(document, ((SimpleContract)contract), XMLConstants.CONTRACT_NODE));
				}
				else if(contract instanceof MultipleContract) {
					contractsElt.appendChild(createMultipleContractElement(document, ((MultipleContract)contract)));
				}
			}			
		}
		return contractsElt;
	}
	
	private Element createMultipleContractElement(final Document document, final MultipleContract contract) {
		final Element contractElt = document.createElement(XMLConstants.MULTI_CONTRACT_NODE);
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_CONSOMACTOR_ID, contract.getConsomActor().getId());
		if(contract.getContracts() != null) {
			for(final SimpleContract subContract : contract.getContracts()) {
				contractElt.appendChild(createSimpleContractElement(document, subContract, XMLConstants.SUB_CONTRACT_NODE));
			}
		}
		return contractElt;
	}
	
	private Element createSimpleContractElement(final Document document, final SimpleContract contract, final String nodeName) {
		final Element contractElt = document.createElement(nodeName);
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_CONSOMACTOR_ID, contract.getConsomActor().getId());
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_ARTICLE_NAME, contract.getArticle().getName());
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_NB_ARTICLES, String.valueOf(contract.getNbArticles()));
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_BEGIN, dateFormat.format(contract.getBeginDate()));
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_END, dateFormat.format(contract.getEndDate()));
		contractElt.setAttribute(XMLConstants.ATTR_CONTRACT_PAIEMENT, String.valueOf(contract.getPaiement()));
		return contractElt;
	}
	
	private Season extractSeason(final Document document, final Element node, final String product) {
		try {
			final Season season = new Season();
			season.setName(node.getAttribute(XMLConstants.ATTR_SEASON_NAME));
			season.setProduct(product);
			season.setBeginDate(dateFormat.parse(node.getAttribute(XMLConstants.ATTR_SEASON_BEGIN)));
			season.setEndDate(dateFormat.parse(node.getAttribute(XMLConstants.ATTR_SEASON_END)));
			fillWithInterruptions(node.getElementsByTagName(XMLConstants.INTERRUPTION_NODE), season);
			fillWithArticles(node.getElementsByTagName(XMLConstants.ARTICLE_NODE), season);
			fillWithSimpleContracts(document, node.getElementsByTagName(XMLConstants.CONTRACT_NODE), season);
			fillWithMultipleContracts(document, node.getElementsByTagName(XMLConstants.MULTI_CONTRACT_NODE), season);
			return season;
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private void fillWithInterruptions(final NodeList interruptionNodes, final Season season) {
		for(int i=0 ; i<interruptionNodes.getLength() ; i++) {
			season.addInterruption(extractInterruption(interruptionNodes.item(i)));
		}
	}
	
	private Date extractInterruption(final Node node) {
		if(node!=null) {
			try {
				return dateFormat.parse(node.getNodeValue());
			}			
			catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	private void fillWithArticles(final NodeList articlesNodes, final Season season) {
		for(int i=0 ; i<articlesNodes.getLength() ; i++) {
			season.addArticle(extractArticle((Element)articlesNodes.item(i)));
		}
	}
	
	private Article extractArticle(final Element node) {
		if(node!=null) {
			try {
				final Article article = new Article();
				article.setName(node.getAttribute(XMLConstants.ATTR_ARTICLE_NAME));
				article.setUnitPrice(Float.parseFloat(node.getAttribute(XMLConstants.ATTR_ARTICLE_PRICE)));
				return article;
			}			
			catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	private void fillWithSimpleContracts(final Document document, final NodeList contractsNodes, final Season season) {
		for(int i=0 ; i<contractsNodes.getLength() ; i++) {
			season.addContract(extractSimpleContract(document, (Element)contractsNodes.item(i), true));
		}
	}
	
	private SimpleContract extractSimpleContract(final Document document, final Element node, final boolean extractConsomActor) {
		if(node!=null) {
			try {
				final SimpleContract contract = new SimpleContract();
				if(extractConsomActor) {
					contract.setConsomActor(consomActorsDao.retrieveConsomActor(node.getAttribute(XMLConstants.ATTR_CONTRACT_CONSOMACTOR_ID)));
				}
				contract.setArticle(extractArticle(getFirstElementByAttr(document, XMLConstants.ARTICLE_NODE, XMLConstants.ATTR_ARTICLE_NAME, node.getAttribute(XMLConstants.ATTR_CONTRACT_ARTICLE_NAME))));				
				contract.setBeginDate(dateFormat.parse(node.getAttribute(XMLConstants.ATTR_CONTRACT_BEGIN)));
				contract.setEndDate(dateFormat.parse(node.getAttribute(XMLConstants.ATTR_CONTRACT_END)));
				try {
					contract.setPaiement(Integer.parseInt(node.getAttribute(XMLConstants.ATTR_CONTRACT_PAIEMENT)));
				}
				catch(final Exception ex) {
					contract.setPaiement(1);
				}
				try {
					contract.setNbArticles(Integer.parseInt(node.getAttribute(XMLConstants.ATTR_CONTRACT_NB_ARTICLES)));
				}
				catch(final Exception ex) {
					contract.setNbArticles(1);
				}
				return contract;
			}			
			catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	private MultipleContract extractMultipleContract(final Document document, final Element node) {
		if(node!=null) {
			try {
				final MultipleContract contract = new MultipleContract();
				contract.setConsomActor(consomActorsDao.retrieveConsomActor(node.getAttribute(XMLConstants.ATTR_CONTRACT_CONSOMACTOR_ID)));
				final NodeList list = node.getElementsByTagName(XMLConstants.SUB_CONTRACT_NODE);
				if(list != null && list.getLength() > 0) {
					final List<SimpleContract> contracts = new ArrayList<SimpleContract>();
					for(int i=0 ; i<list.getLength() ; i++) {						
						contracts.add(extractSimpleContract(document, (Element)list.item(i), false));
					}
					contract.setContracts(contracts);
				}
				return contract;
			}			
			catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	private void fillWithMultipleContracts(final Document document, final NodeList contractsNodes, final Season season) {
		for(int i=0 ; i<contractsNodes.getLength() ; i++) {
			season.addContract(extractMultipleContract(document, (Element)contractsNodes.item(i)));
		}
	}
}
