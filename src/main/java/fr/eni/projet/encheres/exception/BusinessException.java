package fr.eni.projet.encheres.exception;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> listeErreurs;
		
	public BusinessException() {
		super();
		this.listeErreurs = new ArrayList<String>();
	}
	
	public void add(String erreur) {
		listeErreurs.add(erreur);
	}

	public List<String> getListeErreurs() {
		return listeErreurs;
	}
	
	public boolean isValid() {
		return listeErreurs==null || listeErreurs.isEmpty();
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
