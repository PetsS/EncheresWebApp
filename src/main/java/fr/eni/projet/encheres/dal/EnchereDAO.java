package fr.eni.projet.encheres.dal;

import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Utilisateur;

public interface EnchereDAO {
	
	void create(Enchere enchere);
	
	public Utilisateur read(int no_article, int prixVente);
}
