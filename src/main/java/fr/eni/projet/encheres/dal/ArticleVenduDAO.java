package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface ArticleVenduDAO{
	ArticleVendu findEncheres(int no_utilisateur);

	List<ArticleVendu> findAll();
	
	void create(ArticleVendu articleVendu);
	
	ArticleVendu read(int noArticle);

	List<ArticleVendu> search(String keyword, int key);
	
	void update(ArticleVendu articleVendu);

}
