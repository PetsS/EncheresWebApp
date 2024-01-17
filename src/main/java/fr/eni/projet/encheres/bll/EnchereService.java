package fr.eni.projet.encheres.bll;

import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;

public interface EnchereService {
	void creerEnchere(Enchere enchere) throws BusinessException;

	Utilisateur trouverEnchere(int no_article, int prixVente);
}
