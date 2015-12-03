/**
 * 
 */
package fr.dwarfconan.amap.dao;

import java.util.List;

import fr.dwarfconan.amap.Season;

/**
 * Dao d'accès aux saisons.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public interface SeasonDao {
	List<String> retrieveProducts();
	
	List<Season> retrieveSeasons();
	
	void addProduct(final String product);
	
	void saveSeason(final Season season);	
}
