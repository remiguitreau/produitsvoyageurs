/**
 * 
 */
package fr.dwarfconan.amap.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import fr.dwarfconan.amap.Article;
import fr.dwarfconan.amap.ConsomActor;
import fr.dwarfconan.amap.Contract;
import fr.dwarfconan.amap.MultipleContract;
import fr.dwarfconan.amap.Season;
import fr.dwarfconan.amap.SimpleContract;
import fr.dwarfconan.amap.dao.ConsomActorsDao;
import fr.dwarfconan.amap.dao.SeasonDao;
import fr.dwarfconan.amap.export.SeasonExporter;

/**
 * Panneau de gestion d'une saison
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class SeasonPanel extends JPanel {
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private final SeasonDao seasonDao;
	private final ConsomActorsDao consomActorsDao;
	private final SeasonExporter seasonExporter;
	
	private Season season;
	
	private JList articlesList;
	
	private JComboBox cbProduct;
	
	private JTable contractsTable;
	
	//---------------------------------------------------------
	// Constructeurs
	//---------------------------------------------------------

	public SeasonPanel(final SeasonDao seasonDao, final ConsomActorsDao consomActorsDao, final SeasonExporter seasonExporter, final Season season) {
		super();
		
		this.seasonDao = seasonDao;
		this.consomActorsDao = consomActorsDao;
		this.seasonExporter = seasonExporter;
		this.season = season;
		
		initComponents();
	}

	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------
	private void initComponents() {
		setLayout(new GridBagLayout());
		
		int y=0;
		
		//Produit
		final JLabel productLabel = new JLabel("Produit :");
		add(productLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		cbProduct = new JComboBox();
		add(cbProduct, new GridBagConstraints(1, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		cbProduct.setPreferredSize(new Dimension(150, 20));
		updateProductList();
		cbProduct.setSelectedItem(season.getProduct());
		cbProduct.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(final ItemEvent evt) {
				season.setProduct((String)cbProduct.getSelectedItem());
			}
		});
		
		final JButton butAddProduct = new JButton("+");
		add(butAddProduct, new GridBagConstraints(2, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		butAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				addProduct();
			}
		});
		
		y++;
		
		//Nom de la saison
		final JLabel nameLabel = new JLabel("Nom :");
		add(nameLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		final JTextField tfName = new JTextField();
		add(tfName, new GridBagConstraints(1, y, 2, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		tfName.setPreferredSize(new Dimension(150, 20));
		tfName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent evt) {
				season.setName(tfName.getText());
			}
		});
		tfName.setText(season.getName());
		y++;
		
		//Date de début
		final JLabel beginLabel = new JLabel("Début :");
		add(beginLabel, new GridBagConstraints(0, y, 1, 1, 0.1, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		final JXDatePicker dateBegin = new JXDatePicker();
		add(dateBegin, new GridBagConstraints(1, y, 2, 1, 0.9, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		dateBegin.setDate(season.getBeginDate());
		dateBegin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent evt) {
				season.setBeginDate(dateBegin.getDate());
			}
		});
		
		y++;
		
		//Date de fin
		final JLabel endLabel = new JLabel("End :");
		add(endLabel, new GridBagConstraints(0, y, 1, 1, 0.1, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		final JXDatePicker dateEnd = new JXDatePicker();
		add(dateEnd, new GridBagConstraints(1, y, 2, 1, 0.9, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		dateEnd.setDate(season.getEndDate());
		dateEnd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(final ActionEvent evt) {
				season.setEndDate(dateEnd.getDate());
			}
		});
		
		y++;
		
		//Liste des articles
		final JLabel articlesLabel = new JLabel("Articles disponibles :");
		add(articlesLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		y++;
		
		final JScrollPane articlesScrollPane = new JScrollPane();
		articlesList = new JList();
		add(articlesScrollPane, new GridBagConstraints(0, y, 2, 2, 0.3, 0.2, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		articlesList.setPreferredSize(new Dimension(100, 100));
		articlesScrollPane.setViewportView(articlesList);
		articlesScrollPane.setPreferredSize(new Dimension(100, 100));
		updateArticleList();
		
		final JButton addArticleButton = new JButton("+");
		add(addArticleButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		addArticleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				addArticle();
			}
			
		});
		y++;
		final JButton removeArticleButton = new JButton("-");
		add(removeArticleButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		removeArticleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				removeArticle();
			}
		});
		
		y++;
		
		//Liste des Contrats
		final JScrollPane contractsScrollPane = new JScrollPane();
		contractsTable = new JTable();
		add(contractsScrollPane, new GridBagConstraints(0, y, 2, 2, 0.3, 0.8, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		contractsScrollPane.setPreferredSize(new Dimension(400, 400));
		contractsScrollPane.setViewportView(contractsTable);
		updateContractsTable();
		
		final JButton addContractButton = new JButton("+");
		add(addContractButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		addContractButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				addContract();
			}
			
		});
		y++;
		final JButton removeContractButton = new JButton("-");
		add(removeContractButton, new GridBagConstraints(2, y, 1, 1, 0.1, 0.1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		removeContractButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				removeContract();
			}
			
		});
		
		y++;
		
		final JButton saveButton = new JButton("Sauver");
		add(saveButton, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				saveSeason();
			}
			
		});
		
		final JButton cancelButton = new JButton("Annuler");
		add(cancelButton, new GridBagConstraints(1, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				cancel();
			}			
		});
		
		final JButton exportButton = new JButton("Exporter");
		add(exportButton, new GridBagConstraints(2, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				exportSeason();
			}			
		});
		
		setPreferredSize(new Dimension(650, 700));
	}

	/**
	 * @return the season
	 */
	public Season getSeason() {
		return season;
	}

	/**
	 * @param season the season to set
	 */
	public void setSeason(Season season) {
		this.season = season;
	}
	
	private void updateArticleList() {
		articlesList.setListData(season.getArticlesAvailable().toArray(new Article[season.getArticlesAvailable().size()]));
	}
	
	private void updateProductList() {
		final List<String> products = seasonDao.retrieveProducts();
		cbProduct.removeAllItems();
		for(final String product : products) {
			cbProduct.addItem(product);
		}
	}
	
	private void updateContractsTable() {
		final List<Object[]> datas = new ArrayList<Object[]>();
		for(final Contract contract : season.getContracts()) {
			if(contract instanceof SimpleContract) {
				completeTableDatas((SimpleContract)contract, datas);
			}
			else if(contract instanceof MultipleContract && ((MultipleContract)contract).getContracts() != null) {
				completeTableDatas((MultipleContract)contract, datas);				
			}
			
		}
		((DefaultTableModel)contractsTable.getModel()).setDataVector(datas.toArray(new Object[datas.size()][]), new String[]{"Consom'Acteur", "Article", "Règlement"});
	}
	
	private void completeTableDatas(final SimpleContract contract, final List<Object[]> datas) {
		datas.add(new Object[]{contract.getConsomActor(), contract.getArticle(), Integer.valueOf(contract.getPaiement())});
	}
	
	private void completeTableDatas(final MultipleContract contract, final List<Object[]> datas) {
		final StringBuffer buf = new StringBuffer();
		int paiement = 1;
		if(contract.getContracts() != null) {
			for(final SimpleContract subContract : contract.getContracts()) {
				buf.append(subContract.getArticle().toString());
				if(subContract.getNbArticles() > 1) {
					buf.append("(x").append(subContract.getNbArticles()).append(")");
				}
				buf.append(';');
				if(subContract.getPaiement() != paiement) {
					paiement = subContract.getPaiement();
				}
			}
		}
		datas.add(new Object[]{contract.getConsomActor(), buf.toString(), Integer.valueOf(paiement)});
	}
	
	private boolean isExistedContract(final Contract contract) {
		for(final Contract existedContract : season.getContracts()) {
			/** 23 avr. 08 (DwarfConan) : On peut avoir une amapien souhaitant plusieurs produits. */
			if (existedContract.getConsomActor().equals(contract.getConsomActor())) {
				return true;
			}
		}
		return false;
	}
	
	//---------------------------------------------------------
	// Package
	//---------------------------------------------------------
	void addProduct() {
		final String product = JOptionPane.showInputDialog("Nom du produit :");
		if(product != null) {
			seasonDao.addProduct(product);
			updateProductList();
		}
	}
	
	void addArticle() {
		final Article article = new Article();
		final String name = JOptionPane.showInputDialog("Nom de l'article :");
		if(name != null) {
			article.setName(name);
			try {
				final float price = Float.parseFloat(JOptionPane.showInputDialog("Prix unitaire de l'article :"));
				article.setUnitPrice(price);
				season.addArticle(article);
				updateArticleList();
			}
			catch(final Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	void removeArticle() {
		final Article selectedArticle = (Article)articlesList.getSelectedValue();
		if(selectedArticle != null) {
			season.getArticlesAvailable().remove(selectedArticle);
			updateArticleList();
		}
	}
	
	void addContract() {
		final ContractPanel panel = new ContractPanel(consomActorsDao.retrieveConsomActors(), season.getArticlesAvailable());
		if(JOptionPane.showConfirmDialog(this, panel, "Ajout d'un contrat", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
			final Contract contract = panel.getContract();
			if(contract != null) {
				contract.setBeginDate(season.getBeginDate());
				contract.setEndDate(season.getEndDate());
				if(isExistedContract(contract)) {
					JOptionPane.showMessageDialog(this, "Un contrat avec "+contract.getConsomActor()+" existe déjà pour cette saison.", "Contrat existant", JOptionPane.ERROR_MESSAGE);
				}
				else {
					season.addContract(contract);
					updateContractsTable();
				}
			}
		}
	}
	
	void removeContract() {
		final ConsomActor consomActor = (ConsomActor)contractsTable.getValueAt(contractsTable.getSelectedRow(), 0);		
		for(int i=0 ; i<season.getContracts().size() ; i++) {
			if(season.getContracts().get(i).getConsomActor().equals(consomActor)) {
				season.getContracts().remove(i);
				updateContractsTable();
				break;
			}
		}
	}
	
	void saveSeason() {
		if(season != null) {
			seasonDao.saveSeason(season);
		}
	}
	
	void cancel() {
		SwingUtilities.windowForComponent(this).setVisible(false);
	}
	
	void exportSeason() {
		seasonExporter.exportSeason(season);
	}
}
