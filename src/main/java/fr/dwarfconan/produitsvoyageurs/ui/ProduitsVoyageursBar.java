
package fr.dwarfconan.produitsvoyageurs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.dwarfconan.produitsvoyageurs.ProduitsVoyageursConstants;
import fr.dwarfconan.produitsvoyageurs.Traveller;
import fr.dwarfconan.produitsvoyageurs.dao.DiscoverersDao;
import fr.dwarfconan.produitsvoyageurs.dao.TravellersDao;
import fr.dwarfconan.produitsvoyageurs.export.TravellerContractsExporter;
import fr.dwarfconan.update.Updater;

public class ProduitsVoyageursBar extends JMenuBar implements UIConstants {

    private final Updater updater;

    private final DiscoverersDao discoverersDao;

    private final TravellersDao travellersDao;

    private final TravellerContractsExporter seasonExporter;

    public ProduitsVoyageursBar(final Updater updater, final DiscoverersDao consomActorsDao,
            final TravellersDao seasonDao, final TravellerContractsExporter seasonExporter) {
        this.updater = updater;
        this.discoverersDao = consomActorsDao;
        this.travellersDao = seasonDao;
        this.seasonExporter = seasonExporter;

        initMenu();
    }

    private void initMenu() {
        final JMenuBar menuBar = new JMenuBar();

        initTravellersMenu(menuBar);
        initConsomActorsMenu(menuBar);
        initInterrogationMenu(menuBar);

        add(menuBar);
    }

    private void initTravellersMenu(final JMenuBar menuBar) {
        final JMenu travellersMenu = new JMenu("Voyageurs");
        final JMenuItem loadTravellerMenu = new JMenuItem("Charger un voyageur");

        loadTravellerMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                loadTraveller();
            }
        });
        travellersMenu.add(loadTravellerMenu);

        final JMenuItem newTravellerMenu = new JMenuItem("Nouveau voyageur");
        newTravellerMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                newTraveller();
            }
        });
        travellersMenu.add(newTravellerMenu);

        menuBar.add(travellersMenu);
    }

    private void initConsomActorsMenu(final JMenuBar menuBar) {
        final JMenu consomActorsMenu = new JMenu("Découvreurs/Acheteurs");
        final JMenuItem viewMenu = new JMenuItem("Visualiser");

        viewMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                viewDiscoverers();
            }
        });
        consomActorsMenu.add(viewMenu);

        menuBar.add(consomActorsMenu);
    }

    private void initInterrogationMenu(final JMenuBar menuBar) {
        final JMenu interrogationMenu = new JMenu("?");
        final JMenuItem updateMenu = new JMenuItem("Vérification des mises à jour...");

        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                updateActionPerform(event);
            }
        });
        interrogationMenu.add(updateMenu);

        final JMenuItem aboutMenu = new JMenuItem("A propos...");

        aboutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                aboutActionPerform(event);
            }
        });
        interrogationMenu.add(aboutMenu);

        menuBar.add(interrogationMenu);
    }

    private void displayFrame(final String title, final JPanel contentPane) {
        final JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setContentPane(contentPane);
        frame.setIconImage(UIConstants.DWARFCONAN_16x16.getImage());
        frame.pack();
        frame.validate();
        frame.setVisible(true);
    }

    void updateActionPerform(final ActionEvent event) {
        if (updater.checkForUpdate(ProduitsVoyageursConstants.APPLICATION_NAME,
                ProduitsVoyageursConstants.VERSION)) {
            if (JOptionPane.showConfirmDialog(this,
                    "Une mise à jour est disponible voulez-vous l'installer ?", "Mise à jour...",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    UIConstants.DWARFCONAN_64x64) == JOptionPane.YES_OPTION) {
                try {
                    updater.processUpdate();
                    JOptionPane.showMessageDialog(this,
                            "Mise à jour effectuée ! Vous devez redémarrer l'application.");
                } catch (final Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Impossible de récupérer la mise à jour.",
                            "Mise à jour...", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pas de mise à jour disponible.");
        }
    }

    /**
     * Action on the menu item "about...".
     */
    void aboutActionPerform(final ActionEvent event) {
        JOptionPane.showMessageDialog(this, UIConstants.ABOUT_TEXT, "A propos...",
                JOptionPane.INFORMATION_MESSAGE, UIConstants.DWARFCONAN_64x64);
    }

    void viewDiscoverers() {
        displayFrame("Découvreurs/Acheteurs", new AllDiscoverersPanel(discoverersDao));
    }

    void loadTraveller() {
        final Traveller traveller = (Traveller) JOptionPane.showInputDialog(this, "Choix du voyageur",
                "Chargement d'un voyageur", JOptionPane.PLAIN_MESSAGE, UIConstants.DWARFCONAN_16x16,
                travellersDao.retrieveTravellers().toArray(new Traveller[0]), null);
        if (traveller != null) {
            displayFrame(traveller.getName(),
                    new TravellerPanel(travellersDao, discoverersDao, seasonExporter, traveller));
        }
    }

    void newTraveller() {
        displayFrame("Nouveau voyageur", new TravellerPanel(travellersDao, discoverersDao, seasonExporter,
                new Traveller(String.valueOf(System.currentTimeMillis()))));
    }
}
