/**
 * 
 */
package fr.dwarfconan.amap;

import java.util.Date;

/**
 * Un contrat simple d'amapien.
 * Un seul article.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class SimpleContract implements Contract {

	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private ConsomActor consomActor;
	private int nbArticles;
	private Article article;
	private Date beginDate;
	private Date endDate;
	private int paiement;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------
	public SimpleContract() {
		super();
	}
	
	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------
	
	/**
	 * @return the consomActor
	 */
	public ConsomActor getConsomActor() {
		return consomActor;
	}

	/**
	 * @param consomActor the consomActor to set
	 */
	public void setConsomActor(final ConsomActor consomActor) {
		this.consomActor = consomActor;
	}

	/**
	 * @return the article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article the article to set
	 */
	public void setArticle(final Article article) {
		this.article = article;
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
	 * @return the paiement
	 */
	public int getPaiement() {
		return paiement;
	}

	/**
	 * @param paiement the paiement to set
	 */
	public void setPaiement(final int paiement) {
		this.paiement = paiement;
	}

	/**
	 * @return the nbArticle
	 */
	public int getNbArticles() {
		return nbArticles;
	}

	/**
	 * @param nbArticles the nbArticle to set
	 */
	public void setNbArticles(final int nbArticles) {
		this.nbArticles = nbArticles;
	}

}
