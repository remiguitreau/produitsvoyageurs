package fr.dwarfconan.produitsvoyageurs.dao.json;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.dwarfconan.produitsvoyageurs.Traveller;
import fr.dwarfconan.produitsvoyageurs.dao.TravellerRetrieveException;
import fr.dwarfconan.produitsvoyageurs.dao.TravellersDao;

public class JsonTravellersDao implements TravellersDao {

    private final File travellersDirectory;

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonTravellersDao(final File rootDirectory) {
        this.travellersDirectory = new File(rootDirectory, "travellers");
        if (!travellersDirectory.exists()) {
            travellersDirectory.mkdirs();
        }
    }

    @Override
    public List<Traveller> retrieveTravellers() {
        try {
            final List<Traveller> travellers = new ArrayList<Traveller>();
            for (final File travellerFile : travellersDirectory.listFiles()) {
                travellers.add(
                        mapper.readValue(FileUtils.readFileToByteArray(travellerFile), Traveller.class));
            }
            return travellers;
        } catch (final Exception ex) {
            throw new TravellerRetrieveException(ex);
        }
    }

    @Override
    public void saveTraveller(final Traveller traveller) {
        try {
            FileUtils.write(new File(travellersDirectory, traveller.getId() + ".json"),
                    mapper.writeValueAsString(traveller));
        } catch (final Exception ex) {
            throw new TravellerStoreException(ex);
        }
    }

}
