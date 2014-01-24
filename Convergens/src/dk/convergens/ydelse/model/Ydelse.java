package dk.convergens.ydelse.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ydelse {
	
	@Id
	private int id;
	private String cpr;
	private int kr;
	private String dato;
	private String type;
	
	public Ydelse() {}
	
	public Ydelse(int id, String cpr, int kr, String dato, String type) {
		this.id = id;
		this.cpr = cpr;
		this.kr = kr;
		this.dato = dato;
		this.type = type;
		System.out.println("Entity created");
	}
	
	public Ydelse(String cpr, int kr, String dato, String type) {
		this.cpr = cpr;
		this.kr = kr;
		this.dato = dato;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
		return "Ydelse [id=" + id + ", cpr=" + cpr + ", kr=" + kr + ", dato="
				+ dato + ", type=" + type + "]";
	}
	
}
