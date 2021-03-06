package fr.eni.projetEncheres.bo;

import java.io.Serializable;

/**
 * @author Daphné 
 */
public class Categorie implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// Attributs :
	private int no_categorie;
	private String libelle;
	
	// Constructeurs : 
	public Categorie() {
    }
	
	public Categorie(int no_categorie, String libelle) {
		super();
		this.no_categorie = no_categorie;
		this.libelle = libelle;
	}

	// Getter / Setter : 
	public int getNo_categorie() {
		return no_categorie;
	}

	public void setNo_categorie(int no_categorie) {
		this.no_categorie = no_categorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	// Override toString :
	@Override
	public String toString() {
		return "Categorie [no_categorie=" + no_categorie + ", libelle=" + libelle + "]";
	}

}

