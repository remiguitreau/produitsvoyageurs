/**
 * 
 */
package fr.dwarfconan.amap;

/**
 * Article distribué pendant une saison :
 * 	- Panier
 * 	- 1/2 panier
 * 	- 6 oeufs
 * 	- 12 oeufs
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class Article {

	private String name;
	private float unitPrice;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------

	public Article() {
		super();
	}

	//---------------------------------------------------------
	// Public
	//---------------------------------------------------------

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the unitPrice
	 */
	public float getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(final float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	@Override
	public String toString() {
		return name+" ("+String.valueOf(unitPrice)+")";
	}
	
	@Override
	public boolean equals(final Object obj) {
		return obj instanceof Article && name.equals(((Article)obj).name) && unitPrice==((Article)obj).unitPrice; 
	}

}
