/**
 * 
 */
package fr.dwarfconan.amap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Une saison correspond à la saison d'un produit.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class Season {

	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private String product;
	private String name;
	private Date beginDate;
	private Date endDate;
	private final List<Date> interruptions;
	private final List<Article> articlesAvailable;
	private final List<Contract> contracts;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------
	public Season() {
		super();
		
		interruptions = new ArrayList<Date>();
		articlesAvailable = new ArrayList<Article>();
		contracts = new ArrayList<Contract>();
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(final String product) {
		this.product = product;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(final Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the articlesAvailable
	 */
	public List<Article> getArticlesAvailable() {
		return articlesAvailable;
	}
		

	public void addArticle(final Article article) {
		articlesAvailable.add(article);
	}

	public void removeArticle(final Article article) {
		articlesAvailable.remove(article);
	}
	
	/**
	 * @return the interruptions
	 */
	public List<Date> getInterruptions() {
		return interruptions;
	}
	
	public void addInterruption(final Date interruption) {
		interruptions.add(interruption);
	}

	/**
	 * @return the contracts
	 */
	public List<Contract> getContracts() {
		return contracts;
	}
	
	public void addContract(final Contract contract) {
		contracts.add(contract);
	}
	
	public void removeContract(final Contract contract) {
		contracts.remove(contract);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
