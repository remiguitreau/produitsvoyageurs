/**
 * 
 */
package fr.dwarfconan.amap.dao.xml;

import java.io.File;

/**
 * 
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public interface XMLConstants {
	
	//Consom'Acteurs
	final static String CONSOM_ACTORS_FILE = "consomactors.xml";	
	final static String CONSOM_ACTORS_ROOT = "consomactors";
	final static String CONSOM_ACTOR_NODE = "consomactor";
	final static String ATTR_ID = "id";
	final static String ATTR_NAME = "name";
	final static String ATTR_FIRST_NAME = "firstname";
	final static String ATTR_ADDRESS = "address";
	final static String ATTR_POST_CODE = "postcode";
	final static String ATTR_TOWN = "town";
	final static String ATTR_TELEPHONE = "telephone";
	final static String ATTR_EMAIL = "email";

	//Saisons
	final static String SEASONS_REP = "seasons";
	final static String SEASON_ROOT = "season";
	final static String ATTR_SEASON_NAME = "name";
	final static String ATTR_SEASON_BEGIN = "begin";
	final static String ATTR_SEASON_END = "end";
	final static String INTERRUPTIONS_NODE = "interruptions";
	final static String INTERRUPTION_NODE = "interruption";
	final static String ARTICLES_NODE = "articles";
	final static String ARTICLE_NODE = "article";
	final static String ATTR_ARTICLE_NAME = "name";
	final static String ATTR_ARTICLE_PRICE = "price";
	final static String CONTRACTS_NODE = "contracts";
	final static String CONTRACT_NODE = "contract";
	final static String MULTI_CONTRACT_NODE = "multicontract";
	final static String SUB_CONTRACT_NODE = "subcontract";
	final static String ATTR_CONTRACT_ARTICLE_NAME = "article.name";
	final static String ATTR_CONTRACT_NB_ARTICLES = "nb.articles";
	final static String ATTR_CONTRACT_CONSOMACTOR_ID = "consomactor.id";
	final static String ATTR_CONTRACT_BEGIN = "begin";
	final static String ATTR_CONTRACT_END = "end";
	final static String ATTR_CONTRACT_PAIEMENT = "paiement";
}
