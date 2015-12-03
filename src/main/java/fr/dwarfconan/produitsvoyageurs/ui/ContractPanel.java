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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.dwarfconan.produitsvoyageurs.Article;
import fr.dwarfconan.produitsvoyageurs.Discoverer;
import fr.dwarfconan.produitsvoyageurs.MultipleContract;
import fr.dwarfconan.produitsvoyageurs.SimpleContract;

/**
 * Panneau de visualisation des consom'acteurs
 * @author Rémi "DwarfConan" Guitreau
 * @since 18 avr. 08 : Création
 */
public class ContractPanel extends JPanel {

    // ---------------------------------------------------------
    // Attributs
    // ---------------------------------------------------------
    private JComboBox cbConsomActors;

    private final List<ArticleContractPanel> articleContractPanels;

    // ---------------------------------------------------------
    // Constructeur
    // ---------------------------------------------------------

    public ContractPanel(final List<Discoverer> consomActors, final List<Article> articles) {
        super();

        articleContractPanels = new ArrayList<ArticleContractPanel>();
        initComponents(consomActors, articles);
    }

    // ---------------------------------------------------------
    // Public
    // ---------------------------------------------------------
    public MultipleContract getContract() {
        MultipleContract contract = null;
        final List<SimpleContract> contracts = new ArrayList<SimpleContract>();
        for (final ArticleContractPanel panel : articleContractPanels) {
            final SimpleContract articleContract = panel.getContract();
            if (articleContract != null) {
                contracts.add(articleContract);
            }
        }
        if (!contracts.isEmpty()) {
            contract = new MultipleContract();
            contract.setContracts(contracts);
        }
        if (contract != null && cbConsomActors.getSelectedItem() != null) {
            contract.setDiscoverer((Discoverer) cbConsomActors.getSelectedItem());
            return contract;
        }
        return null;
    }

    // ---------------------------------------------------------
    // Privées
    // ---------------------------------------------------------

    private void initComponents(final List<Discoverer> consomActors, final List<Article> articles) {
        setLayout(new GridBagLayout());

        int y = 0;

        // Consom'Acteur
        final JLabel consomActorLabel = new JLabel("Consom'Acteur :");
        add(consomActorLabel, new GridBagConstraints(0, y, 1, 1, 0.1, 0.5, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        cbConsomActors = new JComboBox();
        add(cbConsomActors, new GridBagConstraints(1, y, 1, 1, 0.8, 0.5, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        for (final Discoverer consomActor : consomActors) {
            cbConsomActors.addItem(consomActor);
        }
        AutoCompleteDecorator.decorate(cbConsomActors);

        final JButton detailsButton = new JButton("Détails");
        add(detailsButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.5, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                viewConsomActor((Discoverer) cbConsomActors.getSelectedItem());
            }
        });
        y++;

        for (final Article article : articles) {
            final ArticleContractPanel articleContractPanel = new ArticleContractPanel(article);
            articleContractPanels.add(articleContractPanel);
            add(articleContractPanel, new GridBagConstraints(0, y++, 3, 1, 0.1, 0.5, GridBagConstraints.WEST,
                    GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        }
    }

    // ---------------------------------------------------------
    // Package
    // ---------------------------------------------------------
    void viewConsomActor(final Discoverer consomActor) {
        final DiscovererPanel panel = new DiscovererPanel();
        panel.setDiscoverer(consomActor);
        JOptionPane.showMessageDialog(this, panel, "Détails", JOptionPane.PLAIN_MESSAGE);
    }
}
