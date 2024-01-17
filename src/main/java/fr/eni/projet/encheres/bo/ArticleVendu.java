package fr.eni.projet.encheres.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArticleVendu implements Serializable {

	private static final long serialVersionUID = 1L;

	private int noArticle;
	
	@NotBlank
	private String nomArticle;
	
	@NotBlank
	private String description;

	private Date dateDebutEncheres;
	
	@NotNull
	private Date dateFinEncheres;
	
	@Min(0)
	private int miseAPrix;
	
	private int prixVente;
	
	private String etatVente;

	@NotNull
	@Valid
	private Retrait lieuRetrait;
	
	private Categorie categorieArticle;
	private Utilisateur utilisateurAchete;
	private Utilisateur utilisateurVend;

	private List<Categorie> categorieList;

	public ArticleVendu() {
		categorieList = new ArrayList<>();
	}

	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres, int miseAPrix, int prixVente) {
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		categorieList = new ArrayList<>();
	}

	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int miseAPrix, int prixVente, String etatVente, Retrait lieuRetrait,
			Categorie categorieArticle, Utilisateur utilisateurAchete, Utilisateur utilisateurVend) {

		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
		this.utilisateurAchete = utilisateurAchete;
		this.utilisateurVend = utilisateurVend;
		categorieList = new ArrayList<>();
	}

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public String getEtatVente() {
		return etatVente;
	}

	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}

	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}

	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public Utilisateur getUtilisateurAchete() {
		return utilisateurAchete;
	}

	public void setUtilisateurAchete(Utilisateur utilisateurAchete) {
		this.utilisateurAchete = utilisateurAchete;
	}

	public Utilisateur getUtilisateurVend() {
		return utilisateurVend;
	}

	public void setUtilisateurVend(Utilisateur utilisateurVend) {
		this.utilisateurVend = utilisateurVend;
	}

	public List<Categorie> getCategorieList() {
		return categorieList;
	}

	public void setCategorieList(List<Categorie> categorieList) {
		this.categorieList = categorieList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleVendu [noArticle=");
		builder.append(noArticle);
		builder.append(", nomArticle=");
		builder.append(nomArticle);
		builder.append(", description=");
		builder.append(description);
		builder.append(", dateDebutEncheres=");
		builder.append(dateDebutEncheres);
		builder.append(", dateFinEncheres=");
		builder.append(dateFinEncheres);
		builder.append(", miseAPrix=");
		builder.append(miseAPrix);
		builder.append(", prixVente=");
		builder.append(prixVente);
		builder.append(", etatVente=");
		builder.append(etatVente);
		builder.append(", lieuRetrait=");
		builder.append(lieuRetrait);
		builder.append(", categorieArticle=");
		builder.append(categorieArticle);
		builder.append(", utilisateurAchete=");
		builder.append(utilisateurAchete);
		builder.append(", utilisateurVend=");
		builder.append(utilisateurVend);
		builder.append("]");
		return builder.toString();
	}

}
