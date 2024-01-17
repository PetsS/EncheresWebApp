package fr.eni.projet.encheres.dal;

import fr.eni.projet.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	void create(Utilisateur utilisateur);
	
	Utilisateur read(String pseudoUtilisateur);

	void update(Utilisateur utilisateur);
	
	void delete(String pseudoUtilisateur);
	
	int countPseudo(String pseudo);
	
	int countEmail(String email);
	
	
}
