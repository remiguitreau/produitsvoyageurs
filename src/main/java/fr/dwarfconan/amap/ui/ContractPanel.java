/**
 * 
 */
package fr.dwarfconan.amap.ui;

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

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import fr.dwarfconan.amap.Article;
import fr.dwarfconan.amap.ConsomActor;
import fr.dwarfconan.amap.Contract;
import fr.dwarfconan.amap.MultipleContract;
import fr.dwarfconan.amap.SimpleContract;

/**
 * Panneau de visualisation des consom'acteurs
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class ContractPanel extends JPanel {
	
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private JComboBox cbConsomActors;
	private final List<ArticleContractPanel> articleContractPanels;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public ContractPanel(final List<ConsomActor> consomActors, final List<Article> articles) {
		super();
		
		articleContractPanels = new ArrayList<ArticleContractPanel>();
		initComponents(consomActors, articles);
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------
	public Contract getContract() {
		Contract contract = null;
		final List<SimpleContract> contracts = new ArrayList<SimpleContract>();
		for(final ArticleContractPanel panel : articleContractPanels) {
			final SimpleContract articleContract = panel.getContract();
			if(articleContract != null) {
				contracts.add(articleContract);
			}
		}
		if(!contracts.isEmpty()) {
			if(contracts.size() == 1) {
				contract = contracts.get(0);
			}
			else {
				contract = new MultipleContract();
				((MultipleContract)contract).setContracts(contracts);
			}
		}
		if(contract != null && cbConsomActors.getSelectedItem() != null) {
			contract.setConsomActor((ConsomActor)cbConsomActors.getSelectedItem());
			return contract;
		}
		return null;
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------

	private void initComponents(final List<ConsomActor> consomActors, final List<Article> articles) {
		setLayout(new GridBagLayout());
		
		int y=0;
		
		//Consom'Acteur
		final JLabel consomActorLabel = new JLabel("Consom'Acteur :");
		add(consomActorLabel, new GridBagConstraints(0, y, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		cbConsomActors = new JComboBox();
		add(cbConsomActors, new GridBagConstraints(1, y, 1, 1, 0.8, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		for(final ConsomActor consomActor : consomActors) {
			cbConsomActors.addItem(consomActor);
		}
		AutoCompleteDecorator.decorate(cbConsomActors);
		
		final JButton detailsButton = new JButton("Détails");
		add(detailsButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		detailsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				viewConsomActor((ConsomActor)cbConsomActors.getSelectedItem());
			}
		});
		y++;
		
		for(final Article article : articles) {
			final ArticleContractPanel articleContractPanel = new ArticleContractPanel(article);
			articleContractPanels.add(articleContractPanel);
			add(articleContractPanel, new GridBagConstraints(0, y++, 3, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));			
		}		
	}
	
	//---------------------------------------------------------
	// Package
	//---------------------------------------------------------
	void viewConsomActor(final ConsomActor consomActor) {
		final ConsomActorPanel panel = new ConsomActorPanel();
		panel.setConsomActor(consomActor);
		JOptionPane.showMessageDialog(this, panel, "Détails", JOptionPane.PLAIN_MESSAGE);
	}
}
