/**
 * 
 */
package fr.dwarfconan.amap.dao;

import java.util.List;

import fr.dwarfconan.amap.ConsomActor;

/**
 * Dao des consom'acteurs.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public interface ConsomActorsDao {
	
	/**
	 * @return La liste des consomactors.
	 */
	List<ConsomActor> retrieveConsomActors();
	
	ConsomActor retrieveConsomActor(final String id);
	
	void saveConsomActor(final ConsomActor consomActor);
	
	void deleteConsomActor(final ConsomActor consomActor);
}
