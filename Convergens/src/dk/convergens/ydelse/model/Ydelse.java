package dk.convergens.ydelse.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity class.
 */
@Entity
// TODO Why queries are placed here and not in the rest service or in orm xml file?
// TODO What is YdelseDS-ds.xml?
// TODO Documentation for java doc!
// TODO Create J-Unit test!
// TODO Create logging if something goes wrong!
@NamedQueries({
		@NamedQuery(name = "Ydelse.findAll", query = "SELECT c FROM Ydelse c"),
		@NamedQuery(name = "Ydelse.findByCPR", query = "SELECT c FROM Ydelse c WHERE c.cpr = :cpr"),
		@NamedQuery(name = "Ydelse.findByTypeAndCPR", query = "SELECT c FROM Ydelse c WHERE c.type = :type AND c.cpr = :cpr") })
public class Ydelse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String cpr;
	private int kr;
	private String dato;
	private String type;

	/**
	 * Default Constructor.
	 */
	public Ydelse() {}
	
	/**
	 * 
	 * Constructor to set all class variables.
	 * 
	 * @param id ydelse Identification.
	 * @param cpr CPR number of customer.
	 * @param kr Price of ydelse.
	 * @param dato "dd-MM-yyyy".
	 * @param type Type of ydelse.
	 */
	public Ydelse(Long id, String cpr, int kr, String dato, String type) {
		this.id = id;
		this.cpr = cpr;
		this.kr = kr;
		this.dato = dato;
		this.type = type;
	}
	
	/**
	 * 
	 * Returns the id.
	 * 
	 * @return Id.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 
	 * Returns the CPR number of the person, who purchased the ydelse.
	 * 
	 * @return String Returns the CPR number.
	 */
	public String getCpr() {
		return cpr;
	}

	/**
	 * 
	 * Sets the CPR number of person who purchased the ydelse.
	 * 
	 * @param cpr The new CPR number.
	 */
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	/**
	 * Returns the price of the ydelse.
	 * 
	 * @return int Returns the price.
	 */
	public int getKr() {
		return kr;
	}
	
	/**
	 * Sets the price of the ydelse.
	 * 
	 * @param kr Replace new price with old.
	 */
	public void setKr(int kr) {
		this.kr = kr;
	}

	/**
	 * Returns the date when the ydelse was purchased. ("dd-MM-yyyy")
	 * 
	 * @return String Returns the date of the ydelse.
	 */
	public String getDato() {
		return dato;
	}
	
	/**
	 * 
	 * Sets the date of when the ydelse was purchased. ("dd-MM-yyyy")
	 * 
	 * @param dato Replace new date with old date.
	 */
	public void setDato(String dato) {
		this.dato = dato;
	}

	/**
	 * Returns the type of ydelse.
	 * 
	 * @return String Return the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of ydelse
	 * 
	 * @param type Replace new type with old type.
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * toString method to print ydelse info.
	 */
	@Override
	public String toString() {
		return "Ydelse [id=" + id + ", cpr=" + cpr + ", kr=" + kr + ", dato=" + dato + ", type=" + type + "]";
	}

}
