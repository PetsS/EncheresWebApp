package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.exception.BusinessException;

public interface ArticleVenduService {
	ArticleVendu getArticleVendus(int no_utilisateur);

	List<ArticleVendu> consulterArticleVendus(String keyword, int key);
	
	ArticleVendu consulterArticleVenduParNoArticle(int noArticle);

	void creerArticleVendu(ArticleVendu articleVendu) throws BusinessException;

}
