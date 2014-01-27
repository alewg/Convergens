package dk.convergens.ydelse.model;

import java.io.Serializable;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
		@NamedQuery(name = "Ydelse.findAll", query = "SELECT c FROM Ydelse c"),
		@NamedQuery(name = "Ydelse.findByCPR", query = "SELECT c FROM Ydelse c WHERE c.cpr = :cpr"),
		@NamedQuery(name = "Ydelse.findByTypeAndCPR", query = "SELECT c FROM Ydelse c WHERE c.type = :type AND c.cpr = :cpr") })
public class Ydelse implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String cpr;
	private int kr;
	private String dato;
	private String type;

	public Ydelse() {
	}

	public Ydelse(Long id, String cpr, int kr, String dato, String type) {
		this.id = id;
		this.cpr = cpr;
		this.kr = kr;
		this.dato = dato;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpr() {
		return cpr;
	}

	public void setCpr(String cpr) {
		this.cpr = cpr;
	}

	public int getKr() {
		return kr;
	}

	public void setKr(int kr) {
		this.kr = kr;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Ydelse [id=" + id + ", cpr=" + cpr + ", kr=" + kr + ", dato=" + dato + ", type=" + type + "]";
	}

}
