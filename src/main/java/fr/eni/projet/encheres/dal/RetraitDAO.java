package fr.eni.projet.encheres.dal;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Retrait;

public interface RetraitDAO {
	void create(ArticleVendu articleVendu);
	Retrait read(int noArticle);
}
