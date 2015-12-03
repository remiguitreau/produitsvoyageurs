/**
 * 
 */
package fr.dwarfconan.amap;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import fr.dwarfconan.amap.dao.ConsomActorsDao;
import fr.dwarfconan.amap.dao.SeasonDao;
import fr.dwarfconan.amap.dao.xml.XMLConsomActorsDao;
import fr.dwarfconan.amap.dao.xml.XMLSeasonDao;
import fr.dwarfconan.amap.export.ExcelSeasonExporter;
import fr.dwarfconan.amap.export.SeasonExporter;
import fr.dwarfconan.amap.ui.AmapMenuBar;
import fr.dwarfconan.ui.UIConstants;
import fr.dwarfconan.update.DwarfConanUpdater;
import fr.dwarfconan.update.PackageDownloader;
import fr.dwarfconan.update.Updater;

/**
 * Classe de lancement
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class Amap {


	public static void main(final String[] args) {
		//---------------------------------------------------------
		// Amap
		//---------------------------------------------------------
		//Initialisation du répertoire de données.
		final File datasRep = new File("datas");
		if(! datasRep.exists()) {
			datasRep.mkdirs();
		}
		
		final ConsomActorsDao consomActorsDao = new XMLConsomActorsDao(datasRep);
		
		final SeasonDao seasonDao = new XMLSeasonDao(datasRep, consomActorsDao);
		
		final SeasonExporter seasonExporter = new ExcelSeasonExporter();
		
		//---------------------------------------------------------
		// Other
		//---------------------------------------------------------
		final Updater updater = new DwarfConanUpdater(new PackageDownloader());
		
		System.out.println("DwarfConan "+AmapConstants.APPLICATION_NAME+" v."+getVersion()+" launching ...");
		
		final JFrame mainFrame = new JFrame();
		mainFrame.setTitle(AmapConstants.APPLICATION_NAME+" v."+getVersion());
		mainFrame.setJMenuBar(new AmapMenuBar(updater, consomActorsDao, seasonDao, seasonExporter));
		mainFrame.pack();
		mainFrame.setSize(300, mainFrame.getPreferredSize().height);
		mainFrame.setPreferredSize(new Dimension(300, mainFrame.getSize().height));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setIconImage(UIConstants.DWARFCONAN_16x16.getImage());
		mainFrame.setVisible(true);
	}

	private static String getVersion() {
		return AmapConstants.VERSION;
	}

}
