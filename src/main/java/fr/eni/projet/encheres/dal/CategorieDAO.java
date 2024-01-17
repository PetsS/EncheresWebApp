package fr.eni.projet.encheres.dal;

import java.util.List;

import fr.eni.projet.encheres.bo.Categorie;

public interface CategorieDAO {
	List<Categorie> findAll();
	
	List<Categorie> read(String pseudoUtilisateur);
	
	Categorie read(int noCategorie);

	List<Categorie> search(int key);
	
}
