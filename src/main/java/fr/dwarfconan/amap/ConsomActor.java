/**
 * 
 */
package fr.dwarfconan.amap;

/**
 * Un consom'acteur
 *
 * @author Rémi "DwarfConan" Guitreau 
 * @since 18 avr. 08 : Création
 */
public class ConsomActor {

	//---------------------------------------------------------
	// Attributs
	//---------------------------------------------------------
	private final String id;
	private String name;
	private String firstName;
	private String address;
	private String postCode;
	private String town;
	private String telephone;
	private String email;
	
	//---------------------------------------------------------
	// Constructeur
	//---------------------------------------------------------
	public ConsomActor(final String id) {
		super();
		
		this.id = id;
	}

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(final String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(final String town) {
		this.town = town;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return name+" "+firstName;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof ConsomActor && id.equals(((ConsomActor)obj).id);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
