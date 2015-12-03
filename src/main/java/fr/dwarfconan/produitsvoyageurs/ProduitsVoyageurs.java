/**
 *
 */
package fr.dwarfconan.produitsvoyageurs;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import fr.dwarfconan.produitsvoyageurs.dao.DiscoverersDao;
import fr.dwarfconan.produitsvoyageurs.dao.TravellersDao;
import fr.dwarfconan.produitsvoyageurs.dao.json.JsonDiscoverersDao;
import fr.dwarfconan.produitsvoyageurs.dao.json.JsonTravellersDao;
import fr.dwarfconan.produitsvoyageurs.export.ExcelTravellerContractsExporter;
import fr.dwarfconan.produitsvoyageurs.export.TravellerContractsExporter;
import fr.dwarfconan.produitsvoyageurs.ui.ProduitsVoyageursBar;
import fr.dwarfconan.ui.UIConstants;
import fr.dwarfconan.update.DwarfConanUpdater;
import fr.dwarfconan.update.PackageDownloader;
import fr.dwarfconan.update.Updater;

public class ProduitsVoyageurs {

    public static void main(final String[] args) {
        // Initialisation du répertoire de données.
        final File datasRep = new File("datas");
        if (!datasRep.exists()) {
            datasRep.mkdirs();
        }

        final DiscoverersDao consomActorsDao = new JsonDiscoverersDao(datasRep);

        final TravellersDao seasonDao = new JsonTravellersDao(datasRep);

        final TravellerContractsExporter seasonExporter = new ExcelTravellerContractsExporter();

        // ---------------------------------------------------------
        // Other
        // ---------------------------------------------------------
        final Updater updater = new DwarfConanUpdater(new PackageDownloader());

        System.out.println("DwarfConan " + ProduitsVoyageursConstants.APPLICATION_NAME + " v." + getVersion()
                + " launching ...");

        final JFrame mainFrame = new JFrame();
        mainFrame.setTitle(ProduitsVoyageursConstants.APPLICATION_NAME + " v." + getVersion());
        mainFrame.setJMenuBar(new ProduitsVoyageursBar(updater, consomActorsDao, seasonDao, seasonExporter));
        mainFrame.pack();
        mainFrame.setSize(300, mainFrame.getPreferredSize().height);
        mainFrame.setPreferredSize(new Dimension(300, mainFrame.getSize().height));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setIconImage(UIConstants.DWARFCONAN_16x16.getImage());
        mainFrame.setVisible(true);
    }

    private static String getVersion() {
        return ProduitsVoyageursConstants.VERSION;
    }

}
