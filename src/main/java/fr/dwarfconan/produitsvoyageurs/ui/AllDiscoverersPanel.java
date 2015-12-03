/**
 *
 */
package fr.dwarfconan.produitsvoyageurs.ui;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.dwarfconan.produitsvoyageurs.Discoverer;
import fr.dwarfconan.produitsvoyageurs.dao.DiscoverersDao;

public class AllDiscoverersPanel extends JPanel {

    private JList discoverersList;

    private final DiscoverersDao discoverersDao;

    private List<Discoverer> discoverers;

    private DiscovererPanel discovererPanel;

    private JButton saveButton;

    private JButton cancelButton;

    private JButton removeButton;

    private JButton modifiedButton;

    private final ListSelectionListener listSelectionListener;

    public AllDiscoverersPanel(final DiscoverersDao consomActorsDao) {
        this.discoverersDao = consomActorsDao;
        listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(final ListSelectionEvent event) {
                listSelectionChanged();
            }
        };

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        final JTextField tfSearch = new JTextField();
        add(tfSearch, new GridBagConstraints(0, 0, 3, 1, 0.3, 0.1, GridBagConstraints.NORTH,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        final JScrollPane scrollPane = new JScrollPane();
        discoverersList = new JList();
        add(scrollPane, new GridBagConstraints(0, 1, 3, 1, 0.3, 0.8, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        updateDiscoverersList();
        discoverersList.getSelectionModel().addListSelectionListener(listSelectionListener);
        discoverersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AutoCompleteDecorator.decorate(discoverersList, tfSearch);
        // consomActorsList.setPreferredSize(new Dimension(150, 400));
        scrollPane.setViewportView(discoverersList);

        final JButton addButton = new JButton("+");
        addButton.setToolTipText("Ajouter un Découvreur/Acheteur");
        add(addButton, new GridBagConstraints(0, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                addDiscoverer();
            }

        });

        removeButton = new JButton("-");
        removeButton.setToolTipText("Supprimer le Découvreur/Acheteur sélectionné");
        add(removeButton, new GridBagConstraints(1, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                removeDiscoverer();
            }

        });

        modifiedButton = new JButton("*");
        modifiedButton.setToolTipText("Modifier le Découvreur/Acheteur sélectionné");
        add(modifiedButton, new GridBagConstraints(2, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        modifiedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                modifyDiscoverer();
            }

        });

        discovererPanel = new DiscovererPanel();
        add(discovererPanel, new GridBagConstraints(3, 0, 2, 2, 0.7, 1.0, GridBagConstraints.NORTH,
                GridBagConstraints.VERTICAL, new Insets(5, 5, 5, 5), 0, 0));

        saveButton = new JButton("Sauver");
        add(saveButton, new GridBagConstraints(3, 2, 1, 1, 0.5, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                saveDiscoverer();
            }

        });
        saveButton.setVisible(false);

        cancelButton = new JButton("Annuler");
        add(cancelButton, new GridBagConstraints(4, 2, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                cancelDiscoverer();
            }
        });
        cancelButton.setVisible(false);
    }

    private Discoverer createDiscoverer() {
        return new Discoverer(String.valueOf(System.currentTimeMillis()));
    }

    private void editDiscoverer(final Discoverer consomActor) {
        discovererPanel.setDiscoverer(consomActor);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        discovererPanel.setEditable(true);
    }

    private void updateDiscoverersList() {
        discoverers = discoverersDao.retrieveDiscoverers();
        discoverersList.setListData(discoverers.toArray(new Discoverer[discoverers.size()]));
    }

    // ---------------------------------------------------------
    // Package
    // ---------------------------------------------------------
    void listSelectionChanged() {
        if (discovererPanel.isEditable()) {
            cancelDiscoverer();
        }
        discovererPanel.setDiscoverer((Discoverer) discoverersList.getSelectedValue());
        modifiedButton.setEnabled(discovererPanel.getDiscoverer() != null);
        removeButton.setEnabled(discovererPanel.getDiscoverer() != null);
    }

    void addDiscoverer() {
        discoverersList.clearSelection();
        editDiscoverer(createDiscoverer());
    }

    void removeDiscoverer() {
        discoverersDao.deleteDiscoverer((Discoverer) discoverersList.getSelectedValue());
        cancelDiscoverer();
        updateDiscoverersList();
    }

    void modifyDiscoverer() {
        editDiscoverer((Discoverer) discoverersList.getSelectedValue());
    }

    void saveDiscoverer() {
        final Discoverer discoverer = discovererPanel.getDiscoverer();
        discoverersDao.saveDiscoverer(discoverer);
        cancelDiscoverer();
        updateDiscoverersList();
        discoverersList.setSelectedValue(discoverer, true);
    }

    void cancelDiscoverer() {
        discovererPanel.setDiscoverer(null);
        discovererPanel.setEditable(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }

}
