package fr.eni.projet.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.dal.CategorieDAO;

@Service
public class CategorieServiceImpl implements CategorieService {
	private CategorieDAO categorieDAO;	

	public CategorieServiceImpl(CategorieDAO categorieDAO) {
		this.categorieDAO = categorieDAO;
	}

	@Override
	public List<Categorie> getCategories(int key) {
		if (key != 0) {
            return categorieDAO.search(key);
        }
		return categorieDAO.findAll();
	}

	@Override
	public List<Categorie> findByPseudo(String pseudoUtilisateur) {
		return categorieDAO.read(pseudoUtilisateur);
	}

	@Override
	public List<Categorie> getCategorie() {
		return categorieDAO.findAll();
	}

}
