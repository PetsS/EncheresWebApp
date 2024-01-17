package fr.eni.projet.encheres.bll;

import java.util.List;

import fr.eni.projet.encheres.bo.Categorie;

public interface CategorieService {
	List<Categorie> getCategorie();
	
	List<Categorie> getCategories(int key);
	
	List<Categorie> findByPseudo(String pseudoUtilisateur);
}
