package fr.eni.projet.encheres.bll;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Retrait;
import fr.eni.projet.encheres.exception.BusinessException;

public interface RetraitService {
	
	void creerRetrait(ArticleVendu articleVendu) throws BusinessException;
	
	Retrait findByNoArticle(int noArticle);
}
