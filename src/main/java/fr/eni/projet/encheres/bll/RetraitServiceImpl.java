package fr.eni.projet.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Retrait;
import fr.eni.projet.encheres.dal.RetraitDAO;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class RetraitServiceImpl implements RetraitService {
	private RetraitDAO retraitDAO;

	public RetraitServiceImpl(RetraitDAO retraitDAO) {
		this.retraitDAO = retraitDAO;
	}

	@Override
	public void creerRetrait(ArticleVendu articleVendu) throws BusinessException {
		retraitDAO.create(articleVendu);
	}

	@Override
	public Retrait findByNoArticle(int noArticle) {
		return retraitDAO.read(noArticle);
	}

}
