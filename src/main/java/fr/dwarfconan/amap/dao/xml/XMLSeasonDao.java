/**
 * 
 */
package fr.dwarfconan.amap.dao.xml;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.dwarfconan.amap.Season;
import fr.dwarfconan.amap.dao.ConsomActorsDao;
import fr.dwarfconan.amap.dao.SeasonDao;

/**
 * Dao de récupération des saisons
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class XMLSeasonDao implements SeasonDao {

	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private final File seasonsRep;
	
	private final XMLSeasonHandler seasonHandler;
	
	//---------------------------------------------------------
	// Constructeurs
	//---------------------------------------------------------

	public XMLSeasonDao(final File datasRep, final ConsomActorsDao consomActorsDao) {
		super();
		
		seasonsRep = new File(datasRep, XMLConstants.SEASONS_REP);
		if(!seasonsRep.exists()) {
			seasonsRep.mkdirs();
		}
		seasonHandler = new XMLSeasonHandler(seasonsRep, consomActorsDao);
	}

	//---------------------------------------------------------
	// Implémentation de SeasonDao
	//---------------------------------------------------------
	
	@Override
	public void addProduct(final String product) {
		final File productRep = new File(seasonsRep, product);
		if(!productRep.exists()) {
			productRep.mkdirs();
		}
	}

	@Override
	public List<String> retrieveProducts() {
		final List<String> products = new ArrayList<String>();
		for(final File file : seasonsRep.listFiles()) {
			if(file.isDirectory()) {
				products.add(file.getName());
			}
		}
		return products;
	}

	@Override
	public List<Season> retrieveSeasons() {
		final List<Season> seasons = new ArrayList<Season>();
		for(final File product : seasonsRep.listFiles()) {
			for(final File seasonFile : product.listFiles()) {
				seasons.add(seasonHandler.loadSeason(seasonFile));
			}
		}
		return seasons;
	}

	@Override
	public void saveSeason(final Season season) {
		seasonHandler.writeSeason(season);
	}

}
