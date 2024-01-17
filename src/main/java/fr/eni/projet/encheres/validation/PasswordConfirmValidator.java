package fr.eni.projet.encheres.validation;

import fr.eni.projet.encheres.bo.Utilisateur;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements

	ConstraintValidator<PasswordValidator, String> {
	
	@Override
	public void initialize(PasswordValidator passwordValidator) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		Utilisateur util = new Utilisateur(); 
		return contactField != util.getMot_de_passe();
		// TODO
	}

}
