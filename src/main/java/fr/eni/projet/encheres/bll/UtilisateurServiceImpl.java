package fr.eni.projet.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.dal.UtilisateurDAO;
import fr.eni.projet.encheres.exception.BusinessException;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}


	@Override
	public void add(Utilisateur utilisateur) throws BusinessException {
		BusinessException be = new BusinessException();
		boolean validPseudo = validerUniquePseudo(utilisateur.getPseudo(), be);
		boolean validEmail = validerUniqueEmail(utilisateur.getEmail(), be);
		
		if(validPseudo && validEmail) {
			utilisateurDAO.create(utilisateur);
			
		}else {
			throw be;
		}

	}
	
	@Override
	public void update(Utilisateur utilisateur) throws BusinessException {
		utilisateurDAO.update(utilisateur);
	}
	
	@Override
	public void delete(String pseudoUtilisateur) {
		utilisateurDAO.delete(pseudoUtilisateur);
	}
	
	private boolean validerUniquePseudo(String pseudo, BusinessException be) {
		int nbPseudo = utilisateurDAO.countPseudo(pseudo);
		if(nbPseudo == 1) {
			be.add("Le pseudo existe déjà");
			return false;
		}
		return nbPseudo == 0;
	}
	
	private boolean validerUniqueEmail(String email, BusinessException be) {
		int nbEmail = utilisateurDAO.countEmail(email);
		if(nbEmail == 1) {
			be.add("L'email existe déjà");
			return false;
		}
		return nbEmail == 0;
	}


	@Override
	public Utilisateur findByPseudo(String pseudoUtilisateur) {
		return utilisateurDAO.read(pseudoUtilisateur);

	}

	
	
}
