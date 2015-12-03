/**
 * 
 */
package fr.dwarfconan.amap.export;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import fr.dwarfconan.amap.Article;
import fr.dwarfconan.amap.Contract;
import fr.dwarfconan.amap.MultipleContract;
import fr.dwarfconan.amap.Season;
import fr.dwarfconan.amap.SimpleContract;

/**
 * Export Excel
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class ExcelSeasonExporter implements SeasonExporter {

	//---------------------------------------------------------
	// Constantes
	//---------------------------------------------------------
	private final static WritableFont HEADER_FONT = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
	private final static WritableCellFormat HEADER_FORMAT = new WritableCellFormat(HEADER_FONT);	
	private final static WritableFont NORMAL_FONT = new WritableFont(WritableFont.ARIAL, 10);
	private final static WritableCellFormat NORMAL_FORMAT = new WritableCellFormat(NORMAL_FONT);
	
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private Map<String, Integer> articles;
	private int paiementCol = 0;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public ExcelSeasonExporter() {
		super();
	}

	//---------------------------------------------------------
	// Implémentation de SeasonExporter
	//---------------------------------------------------------
	@Override
	public void exportSeason(final Season season) {
		try {  
			WritableWorkbook workbook = Workbook.createWorkbook(new File(season.getName()+".xls"));
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			writeHeaders(season, sheet);
			int currentContract = 1;
			for(final Contract contract : season.getContracts()) {
				addContract(currentContract, contract, sheet);
				currentContract++;
			}
			writeTotalByArticle(currentContract, sheet);
			
			workbook.write();
			workbook.close();
		}
		catch(final Exception ex) {
			ex.printStackTrace();
		}
	}
	
	//---------------------------------------------------------
	// Privées
	//---------------------------------------------------------
	private void writeHeaders(final Season season, final WritableSheet sheet) throws Exception {
		int col=1;
		sheet.addCell(new Label(col++, 0, "NOM", HEADER_FORMAT));
		articles = new HashMap<String, Integer>();
		for(final Article article : season.getArticlesAvailable()) {
			articles.put(article.getName(), Integer.valueOf(col));
			sheet.addCell(new Label(col++, 0, article.getName(), HEADER_FORMAT));
		}
		paiementCol = col;
		sheet.addCell(new Label(col++, 0, "Règlement", HEADER_FORMAT));
	}
	
	private void addContract(final int currentContract, final Contract contract, final WritableSheet sheet) throws Exception {
		if(contract instanceof SimpleContract) {
			addSimpleContract(currentContract, ((SimpleContract)contract), sheet);
		}
		else if(contract instanceof MultipleContract) {
			addMultipleContract(currentContract, ((MultipleContract)contract), sheet);
		}
	}
	
	private void addSimpleContract(final int currentContract, final SimpleContract contract, final WritableSheet sheet) throws Exception {
		sheet.addCell(new Number(0, currentContract, currentContract, NORMAL_FORMAT));
		sheet.addCell(new Label(1, currentContract, contract.getConsomActor().toString(), NORMAL_FORMAT));
		sheet.addCell(new Number(getArticleColumn(contract.getArticle().getName()), currentContract, contract.getNbArticles(), NORMAL_FORMAT));		
		sheet.addCell(new Number(paiementCol, currentContract, contract.getPaiement(), NORMAL_FORMAT));
	}
	
	private void addMultipleContract(final int currentContract, final MultipleContract contract, final WritableSheet sheet) throws Exception {
		int paiement = 1;
		sheet.addCell(new Number(0, currentContract, currentContract, NORMAL_FORMAT));
		sheet.addCell(new Label(1, currentContract, contract.getConsomActor().toString(), NORMAL_FORMAT));
		for(final SimpleContract subContract : contract.getContracts()) {
			sheet.addCell(new Number(getArticleColumn(subContract.getArticle().getName()), currentContract, subContract.getNbArticles(), NORMAL_FORMAT));
			if(subContract.getPaiement() != paiement) {
				paiement = subContract.getPaiement();
			}
		}
				
		sheet.addCell(new Number(paiementCol, currentContract, paiement, NORMAL_FORMAT));
	}
	
	private int getArticleColumn(final String name) {
		return articles.get(name).intValue();
	}
	
	private void writeTotalByArticle(final int currentContract, final WritableSheet sheet) throws Exception {
		int article = 2;
		for(final Entry<String, Integer> entry : articles.entrySet()) {
			sheet.addCell(new Label(1, (currentContract+article), "Total "+entry.getKey(), HEADER_FORMAT));
			final char articleColChar = ALPHABET.charAt(entry.getValue().intValue());
			sheet.addCell(new Formula(2, (currentContract+article), "SOMME("+articleColChar+"2:"+articleColChar+""+currentContract+")", HEADER_FORMAT));
			article++;
		}
	}
}
