package fr.eni.projet.encheres.bll;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;

public interface UtilisateurService {
	void add(Utilisateur utilisateur) throws BusinessException;
	void update(Utilisateur utilisateur) throws BusinessException;
	void delete(String pseudoUtilisateur);
	Utilisateur findByPseudo(String pseudoUtilisateur);
	
	
	
}
