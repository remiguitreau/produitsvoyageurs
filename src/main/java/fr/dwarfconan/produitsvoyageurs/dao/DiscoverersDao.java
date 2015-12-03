/**
 * 
 */
package fr.dwarfconan.produitsvoyageurs.dao;

import java.util.List;

import fr.dwarfconan.produitsvoyageurs.Discoverer;

/**
 * Dao des consom'acteurs.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public interface DiscoverersDao {
	
	/**
	 * @return La liste des consomactors.
	 */
	List<Discoverer> retrieveDiscoverers();
	
	Discoverer retrieveDiscoverer(final String id);
	
	void saveDiscoverer(final Discoverer consomActor);
	
	void deleteDiscoverer(final Discoverer consomActor);
}
