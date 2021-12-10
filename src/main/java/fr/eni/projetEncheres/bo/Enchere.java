package fr.eni.projetEncheres.bo;

import java.time.LocalDate;

/**
 * @author Daphné 
 */
public class Enchere {
	// Attributs :
	private int no_enchere; //@Julian
	Utilisateur utilisateur;
    private int no_article;
    private LocalDate date_enchere;
    private int montant_enchere;
    
    // Constructeur :
    public Enchere() {
    }
    
	public Enchere(Utilisateur utilisateur, int no_article, LocalDate date_enchere, int montant_enchere) {
		super();
		this.utilisateur = utilisateur;
		this.no_article = no_article;
		this.date_enchere = date_enchere;
		this.montant_enchere = montant_enchere;
	}
	
	// Getter / Setter :	
	
	public int getNo_enchere() {
		return no_enchere;
	}

	public void setNo_enchere(int no_enchere) {
		this.no_enchere = no_enchere;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public int getNo_article() {
		return no_article;
	}

	public void setNo_article(int no_article) {
		this.no_article = no_article;
	}

	public LocalDate getDate_enchere() {
		return date_enchere;
	}

	public void setDate_enchere(LocalDate date_enchere) {
		this.date_enchere = date_enchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}
	
    // Overrive toString :
	@Override
	public String toString() {
		return "Enchere [utilisateur=" + utilisateur + ", no_article=" + no_article + ", date_enchere="
				+ date_enchere + ", montant_enchere=" + montant_enchere + "]";
	}
   
}



