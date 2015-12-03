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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import fr.dwarfconan.amap.Article;
import fr.dwarfconan.amap.ConsomActor;
import fr.dwarfconan.amap.SimpleContract;

/**
 * Panneau de visualisation d'un article de contrat.
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class ArticleContractPanel extends JPanel {
	
	
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private JCheckBox chkArticle;
	private JTextField tfNbArticles;
	private JComboBox cbPaiement;
	private final Article article;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public ArticleContractPanel(final Article article) {
		super();
		
		this.article = article;
		initComponents();
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------
	public SimpleContract getContract() {
		if(chkArticle.isSelected()) {
			final SimpleContract contract = new SimpleContract();
			contract.setArticle(article);
			try {
				contract.setNbArticles(Integer.parseInt(tfNbArticles.getText()));
			}
			catch(final Exception ex) {
				ex.printStackTrace();
				contract.setNbArticles(1);
			}
			contract.setPaiement(((Integer)cbPaiement.getSelectedItem()).intValue());
			return contract;
		}
		return null;
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------

	private void initComponents() {
		setLayout(new GridBagLayout());
		
		int x=0;
		
		//Case à cocher
		chkArticle = new JCheckBox();
		chkArticle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				tfNbArticles.setEnabled(chkArticle.isSelected());
				cbPaiement.setEnabled(chkArticle.isSelected());
				if(chkArticle.isSelected()) {
					tfNbArticles.setText("1");
				}
			}
		});
		add(chkArticle, new GridBagConstraints(x++, 0, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		
		//Champ du nombre d'article.
		tfNbArticles = new JTextField();
		tfNbArticles.setPreferredSize(new Dimension(30, 20));
		add(tfNbArticles, new GridBagConstraints(x++, 0, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		tfNbArticles.setEnabled(false);
		add(new JLabel(article.getName()), new GridBagConstraints(x++, 0, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

		//Paiement
		add(new JLabel("Paiement :"), new GridBagConstraints(x++, 0, 1, 1, 0.1, 0.5, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		
		cbPaiement = new JComboBox();
		add(cbPaiement, new GridBagConstraints(x++, 0, 1, 1, 0.8, 0.5, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		cbPaiement.addItem(Integer.valueOf(1));
		cbPaiement.addItem(Integer.valueOf(3));
		cbPaiement.setEnabled(false);
	}
}
