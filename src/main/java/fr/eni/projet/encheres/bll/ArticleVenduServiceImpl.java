package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.ArticleVenduDAO;
import fr.eni.projet.encheres.dal.CategorieDAO;
import fr.eni.projet.encheres.dal.RetraitDAO;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	private ArticleVenduDAO articleVenduDAO;
	private CategorieDAO categorieDAO;
	private RetraitDAO retraitDAO;

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO, RetraitDAO retraitDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
		this.retraitDAO = retraitDAO;
	}

	@Override
	public ArticleVendu getArticleVendus(int no_utilisateur) {
		return articleVenduDAO.findEncheres(no_utilisateur);
	}

	@Override
	public List<ArticleVendu> consulterArticleVendus(String keyword, int key) {
		if ((keyword != null) && (key != 0)) {
            return articleVenduDAO.search(keyword, key);
        } else if ((keyword != null) && (key == 0)) {
        	// TODO creer un autre méthode qui cherche seulement le 'keyword' en ignorant le 'key' (categories)
//        	return articleVenduDAO.searchByKeyword(keyword);
        }
		return this.articleVenduDAO.findAll();
	}

	@Override
	public void creerArticleVendu(ArticleVendu articleVendu) throws BusinessException {
		articleVenduDAO.create(articleVendu);
		retraitDAO.create(articleVendu);
	}

	@Override
	public ArticleVendu consulterArticleVenduParNoArticle(int noArticle) {
		ArticleVendu av = articleVenduDAO.read(noArticle);
		
		Categorie categorie = this.categorieDAO.read(av.getCategorieArticle().getNoCategorie());
		av.setCategorieArticle(categorie);
		
		return av;
	}

}
