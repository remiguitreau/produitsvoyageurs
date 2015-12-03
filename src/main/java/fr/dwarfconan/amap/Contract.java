/**
 * 
 */
package fr.dwarfconan.amap;

import java.util.Date;

/**
 * Un contrat
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 23 avr. 08 : Création
 */
public interface Contract {

	ConsomActor getConsomActor();
	
	void setConsomActor(final ConsomActor consomActor);
	
	void setBeginDate(final Date beginDate);
	
	void setEndDate(final Date endDate);
}
