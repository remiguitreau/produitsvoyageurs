/**
 * 
 */
package fr.dwarfconan.amap.ui;

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

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import fr.dwarfconan.amap.ConsomActor;
import fr.dwarfconan.amap.dao.ConsomActorsDao;

/**
 * Panneau de visualisation des consom'acteurs
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class AllConsomActorsPanel extends JPanel {
	
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private JList consomActorsList;
	private final ConsomActorsDao consomActorsDao;
	private List<ConsomActor> consomActors;
	
	private ConsomActorPanel consomActorPanel;
	
	private JButton saveButton;
	private JButton cancelButton;
	private JButton removeButton;
	private JButton modifiedButton;
	
	private ListSelectionListener listSelectionListener;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public AllConsomActorsPanel(final ConsomActorsDao consomActorsDao) {
		super();
		
		this.consomActorsDao = consomActorsDao;		
		
		listSelectionListener = new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent event) {
				listSelectionChanged();
			}			
		};
		
		initComponents();
	}

	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------

	private void initComponents() {
		setLayout(new GridBagLayout());
		
		final JTextField tfSearch = new JTextField();
		add(tfSearch, new GridBagConstraints(0, 0, 3, 1, 0.3, 0.1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		
		final JScrollPane scrollPane = new JScrollPane();
		consomActorsList = new JList();
		add(scrollPane, new GridBagConstraints(0, 1, 3, 1, 0.3, 0.8, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		updateConsomActorsList();
		consomActorsList.getSelectionModel().addListSelectionListener(listSelectionListener);
		consomActorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		AutoCompleteDecorator.decorate(consomActorsList, tfSearch);
//		consomActorsList.setPreferredSize(new Dimension(150, 400));
		scrollPane.setViewportView(consomActorsList);
		
		final JButton addButton = new JButton("+");
		addButton.setToolTipText("Ajouter un Consom'Acteur");
		add(addButton, new GridBagConstraints(0, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				addConsomActor();
			}
			
		});
		
		removeButton = new JButton("-");
		removeButton.setToolTipText("Supprimer le Consom'Acteur sélectionné");
		add(removeButton, new GridBagConstraints(1, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				removeConsomActor();
			}
			
		});
		
		modifiedButton = new JButton("*");
		modifiedButton.setToolTipText("Modifier le Consom'Acteur sélectionné");
		add(modifiedButton, new GridBagConstraints(2, 2, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		modifiedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				modifyConsomActor();
			}
			
		});
		
		consomActorPanel = new ConsomActorPanel();
		add(consomActorPanel, new GridBagConstraints(3, 0, 2, 2, 0.7, 1.0, GridBagConstraints.NORTH, GridBagConstraints.VERTICAL, new Insets(5, 5, 5, 5), 0, 0));

		saveButton = new JButton("Sauver");
		add(saveButton, new GridBagConstraints(3, 2, 1, 1, 0.5, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				saveConsomActor();
			}
			
		});
		saveButton.setVisible(false);
		
		cancelButton = new JButton("Annuler");
		add(cancelButton, new GridBagConstraints(4, 2, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				cancelConsomActor();
			}			
		});
		cancelButton.setVisible(false);
	}
	
	private ConsomActor createNewConsomActor() {
		return new ConsomActor(String.valueOf(System.currentTimeMillis()));
	}
	
	private void editConsomActor(final ConsomActor consomActor) {
		consomActorPanel.setConsomActor(consomActor);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);
		consomActorPanel.setEditable(true);
	}
	
	private void updateConsomActorsList() {
		consomActors = consomActorsDao.retrieveConsomActors();
		consomActorsList.setListData(consomActors.toArray(new ConsomActor[consomActors.size()]));
	}
	
	//---------------------------------------------------------
	// Package
	//---------------------------------------------------------
	void listSelectionChanged() {
		if(consomActorPanel.isEditable()) {
			cancelConsomActor();
		}
		consomActorPanel.setConsomActor((ConsomActor)consomActorsList.getSelectedValue());
		modifiedButton.setEnabled(consomActorPanel.getConsomActor() != null);
		removeButton.setEnabled(consomActorPanel.getConsomActor() != null);
	}
	
	void addConsomActor() {
		consomActorsList.clearSelection();
		editConsomActor(createNewConsomActor());
	}
	
	void removeConsomActor() {
		consomActorsDao.deleteConsomActor((ConsomActor)consomActorsList.getSelectedValue());
		cancelConsomActor();
		updateConsomActorsList();		
	}
	
	void modifyConsomActor() {
		editConsomActor((ConsomActor)consomActorsList.getSelectedValue());
	}
	
	void saveConsomActor() {
		final ConsomActor actor = consomActorPanel.getConsomActor();
		consomActorsDao.saveConsomActor(actor);
		cancelConsomActor();
		updateConsomActorsList();
		consomActorsList.setSelectedValue(actor, true);
	}
	
	void cancelConsomActor() {
		consomActorPanel.setConsomActor(null);
		consomActorPanel.setEditable(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
	}
	
}
