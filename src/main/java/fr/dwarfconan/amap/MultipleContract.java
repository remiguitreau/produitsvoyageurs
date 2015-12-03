/**
 * 
 */
package fr.dwarfconan.amap;

import java.util.Date;
import java.util.List;

/**
 * Contrat multiple = plusieurs articles.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 23 avr. 08 : Création
 */
public class MultipleContract implements Contract {

	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private ConsomActor consomActor;
	private List<SimpleContract> contracts;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public MultipleContract() {
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
		updateConsomActorForSubContracts();
	}

	/**
	 * @return the contracts
	 */
	public List<SimpleContract> getContracts() {
		return contracts;
	}
	
	public void setContracts(final List<SimpleContract> contracts) {
		this.contracts = contracts;
		updateConsomActorForSubContracts();
	}

	@Override
	public void setBeginDate(final Date beginDate) {
		if(contracts != null) {
			for(final SimpleContract contract : contracts) {
				contract.setBeginDate(beginDate);
			}
		}
	}

	@Override
	public void setEndDate(final Date endDate) {
		if(contracts != null) {
			for(final SimpleContract contract : contracts) {
				contract.setEndDate(endDate);
			}
		}
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------
	private void updateConsomActorForSubContracts() {
		if(contracts != null) {
			for(final SimpleContract contract : contracts) {
				contract.setConsomActor(consomActor);
			}
		}
	}
}
