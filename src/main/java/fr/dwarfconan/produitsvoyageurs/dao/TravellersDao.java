/**
 *
 */
package fr.dwarfconan.produitsvoyageurs.dao;

import java.util.List;

import fr.dwarfconan.produitsvoyageurs.Traveller;

public interface TravellersDao {

    List<Traveller> retrieveTravellers();

    void saveTraveller(final Traveller season);
}
