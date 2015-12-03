/**
 *
 */
package fr.dwarfconan.produitsvoyageurs.ui;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import fr.dwarfconan.produitsvoyageurs.Article;
import fr.dwarfconan.produitsvoyageurs.Contract;
import fr.dwarfconan.produitsvoyageurs.Discoverer;
import fr.dwarfconan.produitsvoyageurs.MultipleContract;
import fr.dwarfconan.produitsvoyageurs.SimpleContract;
import fr.dwarfconan.produitsvoyageurs.Traveller;
import fr.dwarfconan.produitsvoyageurs.dao.DiscoverersDao;
import fr.dwarfconan.produitsvoyageurs.dao.TravellersDao;
import fr.dwarfconan.produitsvoyageurs.export.TravellerContractsExporter;
import lombok.Getter;
import lombok.Setter;

public class TravellerPanel extends JPanel {

    private final TravellersDao travellerDao;

    private final DiscoverersDao discoverersDao;

    private final TravellerContractsExporter travellerExporter;

    @Getter
    @Setter
    private Traveller traveller;

    private JList articlesList;

    private JTable contractsTable;

    public TravellerPanel(final TravellersDao seasonDao, final DiscoverersDao consomActorsDao,
            final TravellerContractsExporter seasonExporter, final Traveller season) {
        this.travellerDao = seasonDao;
        this.discoverersDao = consomActorsDao;
        this.travellerExporter = seasonExporter;
        this.traveller = season;

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        int y = 0;

        // Nom du voyageur
        final JLabel nameLabel = new JLabel("Nom :");
        add(nameLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JTextField tfName = new JTextField();
        add(tfName, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        tfName.setPreferredSize(new Dimension(150, 20));
        tfName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                traveller.setName(tfName.getText());
                saveTraveller();
            }
        });
        tfName.setText(traveller.getName());
        y++;

        // Prénom du voyageur
        final JLabel firstnameLabel = new JLabel("Prénom :");
        add(firstnameLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JTextField tfFirstname = new JTextField();
        add(tfFirstname, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        tfFirstname.setPreferredSize(new Dimension(150, 20));
        tfFirstname.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                traveller.setFirstName(tfFirstname.getText());
                saveTraveller();
            }
        });
        tfFirstname.setText(traveller.getFirstName());
        y++;

        // Email
        final JLabel emailLabel = new JLabel("Email :");
        add(emailLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JTextField tfEmail = new JTextField();
        add(tfEmail, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        tfEmail.setPreferredSize(new Dimension(150, 20));
        tfEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                traveller.setEmail(tfEmail.getText());
                saveTraveller();
            }
        });
        tfEmail.setText(traveller.getEmail());
        y++;

        // Phone
        final JLabel phoneLabel = new JLabel("Tel. :");
        add(phoneLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JTextField tfPhone = new JTextField();
        add(tfPhone, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        tfPhone.setPreferredSize(new Dimension(150, 20));
        tfPhone.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                traveller.setPhone(tfPhone.getText());
                saveTraveller();
            }
        });
        tfPhone.setText(traveller.getPhone());
        y++;

        // Date de livraison
        final JLabel deliveryLabel = new JLabel("Date de livraison :");
        add(deliveryLabel, new GridBagConstraints(0, y, 1, 1, 0.1, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JXDatePicker deliveryBegin = new JXDatePicker();
        add(deliveryBegin, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        deliveryBegin.setDate(traveller.getDeliveryDate());
        deliveryBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                traveller.setDeliveryDate(deliveryBegin.getDate());
                saveTraveller();
            }
        });
        y++;

        // Lieux
        final JLabel locationsLabel = new JLabel("Lieux de livraison (séparé par des ,) :");
        add(locationsLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        final JTextField tfLocations = new JTextField();
        add(tfLocations, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        tfLocations.setPreferredSize(new Dimension(150, 20));
        tfLocations.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent evt) {
                traveller.setDeliveryLocations(toLocations(tfLocations.getText()));
                saveTraveller();
            }

            private List<String> toLocations(final String string) {
                if (string == null || string.isEmpty()) {
                    return Collections.emptyList();
                }
                final List<String> locations = new LinkedList<>();
                for (final String str : string.split(",")) {
                    locations.add(str.trim());
                }
                return locations;
            }
        });
        tfLocations.setText(locationsAsString(traveller.getDeliveryLocations()));
        y++;

        // Liste des articles
        final JLabel articlesLabel = new JLabel("Articles disponibles :");
        add(articlesLabel, new GridBagConstraints(0, y, 3, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        y++;

        final JScrollPane articlesScrollPane = new JScrollPane();
        articlesList = new JList();
        add(articlesScrollPane, new GridBagConstraints(0, y, 3, 2, 0.5, 0.2, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        articlesList.setPreferredSize(new Dimension(100, 100));
        articlesScrollPane.setViewportView(articlesList);
        articlesScrollPane.setPreferredSize(new Dimension(100, 100));
        updateArticleList();

        final JButton addArticleButton = new JButton("+");
        add(addArticleButton, new GridBagConstraints(3, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        addArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                addArticle();
            }

        });
        y++;
        final JButton removeArticleButton = new JButton("-");
        add(removeArticleButton, new GridBagConstraints(3, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        removeArticleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                removeArticle();
            }
        });

        y++;

        // Liste des Contrats
        final JScrollPane contractsScrollPane = new JScrollPane();
        contractsTable = new JTable();
        add(contractsScrollPane, new GridBagConstraints(0, y, 3, 2, 0.3, 0.8, GridBagConstraints.NORTH,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        contractsScrollPane.setPreferredSize(new Dimension(400, 400));
        contractsScrollPane.setViewportView(contractsTable);
        updateContractsTable();

        final JButton addContractButton = new JButton("+");
        add(addContractButton, new GridBagConstraints(3, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        addContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                addContract();
            }

        });
        y++;
        final JButton removeContractButton = new JButton("-");
        add(removeContractButton, new GridBagConstraints(3, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        removeContractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                removeContract();
            }

        });

        y++;

        final JButton exportButton = new JButton("Exporter");
        add(exportButton, new GridBagConstraints(0, y, 3, 1, 0.5, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                exportTravellerContracts();
            }
        });

        setPreferredSize(new Dimension(650, 700));
    }

    private String locationsAsString(final List<String> deliveryLocations) {
        if (deliveryLocations == null || deliveryLocations.isEmpty()) {
            return "";
        }
        if (deliveryLocations.size() == 1) {
            return deliveryLocations.get(0);
        }
        final StringBuilder buf = new StringBuilder(deliveryLocations.get(0));
        for (int i = 1; i < deliveryLocations.size(); i++) {
            buf.append(", ").append(deliveryLocations.get(i));
        }
        return buf.toString();
    }

    private void updateArticleList() {
        articlesList.setListData(traveller.getArticlesAvailable().toArray(
                new Article[traveller.getArticlesAvailable().size()]));
    }

    private void updateContractsTable() {
        final List<Object[]> datas = new ArrayList<Object[]>();
        for (final Contract contract : traveller.getContracts()) {
            if (contract instanceof SimpleContract) {
                completeTableDatas((SimpleContract) contract, datas);
            } else if (contract instanceof MultipleContract
                    && ((MultipleContract) contract).getContracts() != null) {
                completeTableDatas((MultipleContract) contract, datas);
            }

        }
        ((DefaultTableModel) contractsTable.getModel()).setDataVector(
                datas.toArray(new Object[datas.size()][]),
                new String[] { "Découvreur/Acheteur", "Article", "Règlement" });
    }

    private void completeTableDatas(final SimpleContract contract, final List<Object[]> datas) {
        datas.add(new Object[] { contract.getDiscoverer(), contract.getArticle(),
                Integer.valueOf(contract.getPaiement()) });
    }

    private void completeTableDatas(final MultipleContract contract, final List<Object[]> datas) {
        final StringBuffer buf = new StringBuffer();
        int paiement = 1;
        if (contract.getContracts() != null) {
            for (final SimpleContract subContract : contract.getContracts()) {
                buf.append(subContract.getArticle().toString());
                if (subContract.getNbArticles() > 1) {
                    buf.append("(x").append(subContract.getNbArticles()).append(")");
                }
                buf.append(';');
                if (subContract.getPaiement() != paiement) {
                    paiement = subContract.getPaiement();
                }
            }
        }
        datas.add(new Object[] { contract.getDiscoverer(), buf.toString(), Integer.valueOf(paiement) });
    }

    private boolean isExistedContract(final Contract contract) {
        for (final Contract existedContract : traveller.getContracts()) {
            /**
             * 23 avr. 08 (DwarfConan) : On peut avoir une amapien souhaitant
             * plusieurs produits.
             */
            if (existedContract.getDiscoverer().equals(contract.getDiscoverer())) {
                return true;
            }
        }
        return false;
    }

    void addArticle() {
        final Article article = new Article();
        final String name = JOptionPane.showInputDialog("Nom de l'article :");
        if (name != null) {
            article.setName(name);
            try {
                final String unitPriceAsString = JOptionPane.showInputDialog("Prix unitaire de l'article :");
                if (unitPriceAsString != null && !unitPriceAsString.isEmpty()) {
                    article.setUnitPrice(Float.parseFloat(unitPriceAsString));
                }
                traveller.addArticle(article);
                saveTraveller();
                updateArticleList();
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    void removeArticle() {
        final Article selectedArticle = (Article) articlesList.getSelectedValue();
        if (selectedArticle != null) {
            traveller.getArticlesAvailable().remove(selectedArticle);
            saveTraveller();
            updateArticleList();
        }
    }

    void addContract() {
        final ContractPanel panel = new ContractPanel(discoverersDao.retrieveDiscoverers(),
                traveller.getArticlesAvailable());
        if (JOptionPane.showConfirmDialog(this, panel, "Ajout d'un contrat", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            final MultipleContract contract = panel.getContract();
            if (contract != null) {
                if (isExistedContract(contract)) {
                    JOptionPane.showMessageDialog(this,
                            "Un contrat avec " + contract.getDiscoverer() + " existe déjà pour ce voyageur.",
                            "Contrat existant", JOptionPane.ERROR_MESSAGE);
                } else {
                    traveller.addContract(contract);
                    saveTraveller();
                    updateContractsTable();
                }
            }
        }
    }

    void removeContract() {
        final Discoverer consomActor = (Discoverer) contractsTable.getValueAt(contractsTable.getSelectedRow(),
                0);
        for (int i = 0; i < traveller.getContracts().size(); i++) {
            if (traveller.getContracts().get(i).getDiscoverer().equals(consomActor)) {
                traveller.getContracts().remove(i);
                saveTraveller();
                updateContractsTable();
                break;
            }
        }
    }

    void saveTraveller() {
        if (traveller != null) {
            travellerDao.saveTraveller(traveller);
        }
    }

    void cancel() {
        SwingUtilities.windowForComponent(this).setVisible(false);
    }

    void exportTravellerContracts() {
        travellerExporter.exportTravellerContracts(traveller);
    }
}
