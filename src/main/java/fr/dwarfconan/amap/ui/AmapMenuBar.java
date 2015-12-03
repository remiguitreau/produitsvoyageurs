/**
 * 
 * Copyright (C) 2007 Rémi "DwarfConan" Guitreau
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 *
 */
package fr.dwarfconan.amap.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.dwarfconan.amap.AmapConstants;
import fr.dwarfconan.amap.Season;
import fr.dwarfconan.amap.dao.ConsomActorsDao;
import fr.dwarfconan.amap.dao.SeasonDao;
import fr.dwarfconan.amap.export.SeasonExporter;
import fr.dwarfconan.update.Updater;

/**
 * Menu bar for amap.
 *
 * @author Remi "DwarfConan" Guitreau
 * @since 18 avr. 2008
 * 
 */
public class AmapMenuBar extends JMenuBar implements UIConstants {

	//---------------------------------------------------------
	// Attributes
	//---------------------------------------------------------
	private final Updater updater;
	private final ConsomActorsDao consomActorsDao;
	private final SeasonDao seasonDao;
	private final SeasonExporter seasonExporter;
	
	//---------------------------------------------------------
	// Constructor
	//---------------------------------------------------------
	public AmapMenuBar(final Updater updater, final ConsomActorsDao consomActorsDao, final SeasonDao seasonDao, final SeasonExporter seasonExporter) {
		super();
		
		this.updater = updater;
		this.consomActorsDao = consomActorsDao;
		this.seasonDao = seasonDao;
		this.seasonExporter = seasonExporter;
		
		initMenu();
	}
	
	//---------------------------------------------------------
	// Private
	//---------------------------------------------------------
	/**
	 * Initialize the graphics components.
	 */
	private void initMenu() {
		final JMenuBar menuBar = new JMenuBar();
		
		initSeasonsMenu(menuBar);
		initConsomActorsMenu(menuBar);
		initInterrogationMenu(menuBar);
		
		add(menuBar);
	}
	
	/**
	 * Initialize the Season menu.
	 * @param menuBar
	 */
	private void initSeasonsMenu(final JMenuBar menuBar) {
		final JMenu consomActorsMenu = new JMenu("Saisons");
		final JMenuItem loadSeasonMenu = new JMenuItem("Charger une saison");
		
		loadSeasonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				loadSeason();
			}
		});
		consomActorsMenu.add(loadSeasonMenu);
		
		final JMenuItem newSeasonMenu = new JMenuItem("Nouvelle saison");
		newSeasonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				newSeason();
			}
		});
		consomActorsMenu.add(newSeasonMenu);
		
		menuBar.add(consomActorsMenu);
	}
	
	/**
	 * Initialize the Consom'Acteurs menu.
	 * @param menuBar
	 */
	private void initConsomActorsMenu(final JMenuBar menuBar) {
		final JMenu consomActorsMenu = new JMenu("Consom'Acteurs");
		final JMenuItem viewMenu = new JMenuItem("Visualiser");
		
		viewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				viewConsomActors();
			}
		});
		consomActorsMenu.add(viewMenu);
		
		menuBar.add(consomActorsMenu);
	}
	
	
	/**
	 * Initialize the ? menu.
	 * @param menuBar
	 */
	private void initInterrogationMenu(final JMenuBar menuBar) {
		final JMenu interrogationMenu = new JMenu("?");
		final JMenuItem updateMenu = new JMenuItem("Vérification des mises à jour...");
		
		updateMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				updateActionPerform(event);
			}
		});
		interrogationMenu.add(updateMenu);
		
		final JMenuItem aboutMenu = new JMenuItem("A propos...");
		
		aboutMenu.addActionListener(new ActionListener() {
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
	
	//---------------------------------------------------------
	// Graphical actions
	//---------------------------------------------------------
	
	/**
	 * Action on the menu item "update".
	 */
	void updateActionPerform(final ActionEvent event) {
		if(updater.checkForUpdate(AmapConstants.APPLICATION_NAME, AmapConstants.VERSION)) {
			if(JOptionPane.showConfirmDialog(this, "Une mise à jour est disponible voulez-vous l'installer ?", "Mise à jour...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, UIConstants.DWARFCONAN_64x64) == JOptionPane.YES_OPTION) {
				try {
					updater.processUpdate();
					JOptionPane.showMessageDialog(this, "Mise à jour effectuée ! Vous devez redémarrer l'application.");
				}
				catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, "Impossible de récupérer la mise à jour.", "Mise à jour...", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Pas de mise à jour disponible.");
		}
	}
	
	/**
	 * Action on the menu item "about...".
	 */
	void aboutActionPerform(final ActionEvent event) {
		JOptionPane.showMessageDialog(this, UIConstants.ABOUT_TEXT, "A propos...", JOptionPane.INFORMATION_MESSAGE, UIConstants.DWARFCONAN_64x64);
	}

	/**
	 * Action on the menu item "Visualiser".
	 */
	void viewConsomActors() {
		displayFrame("Consom'Acteurs", new AllConsomActorsPanel(consomActorsDao));
	}
	
	void loadSeason() {
		final Season season = (Season)JOptionPane.showInputDialog(this, "Choix de la saison", "Chargement d'une saison", JOptionPane.PLAIN_MESSAGE, UIConstants.DWARFCONAN_16x16, seasonDao.retrieveSeasons().toArray(new Season[0]), null);
		if(season != null) {
			displayFrame(season.getName(), new SeasonPanel(seasonDao, consomActorsDao, seasonExporter, season));
		}		
	}
	
	void newSeason() {
		displayFrame("Nouvelle Saison", new SeasonPanel(seasonDao, consomActorsDao, seasonExporter, new Season()));
	}
}
