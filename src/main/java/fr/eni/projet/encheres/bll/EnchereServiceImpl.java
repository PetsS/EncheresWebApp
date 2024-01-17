package fr.eni.projet.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.ArticleVenduDAO;
import fr.eni.projet.encheres.dal.EnchereDAO;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class EnchereServiceImpl implements EnchereService {
	public EnchereDAO enchereDAO;
	public ArticleVenduDAO articleVenduDAO;

	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleVenduDAO articleVenduDAO) {
		this.enchereDAO = enchereDAO;
		this.articleVenduDAO = articleVenduDAO;
	}

	@Override
	public void creerEnchere(Enchere enchere) throws BusinessException {		
		ArticleVendu article = articleVenduDAO.read(enchere.getArticleVenduConcerne().getNoArticle());
		
		if (!(enchere.getMontant_enchere() <= article.getPrixVente())) {
			article.setPrixVente(enchere.getMontant_enchere());
			
			articleVenduDAO.update(article);
			enchereDAO.create(enchere);
		}

	}

	@Override
	public Utilisateur trouverEnchere(int no_article, int prixVente) {
		return enchereDAO.read(no_article, prixVente);
	}

}
