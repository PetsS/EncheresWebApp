package fr.eni.projet.encheres.bo;

import java.io.Serializable;
import java.util.Date;

public class Enchere implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date dateEnchere;
	
	private int montant_enchere;
	
	private ArticleVendu articleVenduConcerne;
	private Utilisateur utilisateurEncherit;
	
	public Enchere() {
	}
	
	public Enchere(Date dateEnchere, int montant_enchere, ArticleVendu articleVenduConcerne,
			Utilisateur utilisateurEncherit) {
		this.dateEnchere = dateEnchere;
		this.montant_enchere = montant_enchere;
		this.articleVenduConcerne = articleVenduConcerne;
		this.utilisateurEncherit = utilisateurEncherit;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	public ArticleVendu getArticleVenduConcerne() {
		return articleVenduConcerne;
	}

	public void setArticleVenduConcerne(ArticleVendu articleVenduConcerne) {
		this.articleVenduConcerne = articleVenduConcerne;
	}

	public Utilisateur getUtilisateurEncherit() {
		return utilisateurEncherit;
	}

	public void setUtilisateurEncherit(Utilisateur utilisateurEncherit) {
		this.utilisateurEncherit = utilisateurEncherit;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [dateEnchere=");
		builder.append(dateEnchere);
		builder.append(", montant_enchere=");
		builder.append(montant_enchere);
		builder.append(", articleVenduConcerne=");
		builder.append(articleVenduConcerne);
		builder.append(", utilisateurEncherit=");
		builder.append(utilisateurEncherit);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
