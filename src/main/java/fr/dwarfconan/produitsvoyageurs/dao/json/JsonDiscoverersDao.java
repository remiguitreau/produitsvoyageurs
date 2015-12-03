/**
 *
 */
package fr.dwarfconan.produitsvoyageurs.dao.json;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import fr.dwarfconan.produitsvoyageurs.Discoverer;
import fr.dwarfconan.produitsvoyageurs.dao.DiscoverersDao;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonDiscoverersDao implements DiscoverersDao {

    private final File discoverersFile;

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<Discoverer> discoverers = new LinkedList<>();

    public JsonDiscoverersDao(final File datasRepository) {
        this.discoverersFile = new File(datasRepository, "discoverers.json");
        if (!datasRepository.exists()) {
            datasRepository.mkdirs();
        }
        loadDiscoverers();
    }

    private void loadDiscoverers() {
        discoverers.clear();
        try {
            discoverers.addAll(Arrays.asList(
                    mapper.readValue(FileUtils.readFileToByteArray(discoverersFile), Discoverer[].class)));
        } catch (final Exception ex) {
            log.warn("Unable to load discoverers...", ex);
        }
    }

    @Override
    public List<Discoverer> retrieveDiscoverers() {
        return discoverers;
    }

    @Override
    public Discoverer retrieveDiscoverer(final String id) {
        for (final Discoverer discoverer : discoverers) {
            if (discoverer.getId().equals(id)) {
                return discoverer;
            }
        }
        return null;
    }

    @Override
    public void saveDiscoverer(final Discoverer discoverer) {
        final Discoverer existingDiscoverer = retrieveDiscoverer(discoverer.getId());
        if (existingDiscoverer != null) {
            copyDiscoverer(discoverer, existingDiscoverer);
        } else {
            discoverers.add(discoverer);
        }
        saveDiscoverers();
    }

    private void saveDiscoverers() {
        try {
            FileUtils.writeByteArrayToFile(discoverersFile, mapper.writeValueAsBytes(discoverers));
        } catch (final Exception ex) {
            throw new DiscoverersStoreException(ex);
        }
    }

    private void copyDiscoverer(final Discoverer discoverer, final Discoverer existingDiscoverer) {
        existingDiscoverer.setAddress(discoverer.getAddress());
        existingDiscoverer.setEmail(discoverer.getEmail());
        existingDiscoverer.setFirstName(discoverer.getFirstName());
        existingDiscoverer.setName(discoverer.getName());
        existingDiscoverer.setPostCode(discoverer.getPostCode());
        existingDiscoverer.setTelephone(discoverer.getTelephone());
        existingDiscoverer.setTown(discoverer.getTown());
    }

    @Override
    public void deleteDiscoverer(final Discoverer discoverer) {
        final Discoverer existingDiscoverer = retrieveDiscoverer(discoverer.getId());
        if (existingDiscoverer != null) {
            discoverers.remove(existingDiscoverer);
        }
        saveDiscoverers();
    }
}
