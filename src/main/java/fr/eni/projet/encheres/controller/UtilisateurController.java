package fr.eni.projet.encheres.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/utilisateurs")
//@SessionAttributes({"utilisateurSession"})
public class UtilisateurController {

	private UtilisateurService utilisateurService;
	
	
	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping("/creer")
	public String creerProfil(Model model) {
		System.out.println("début creerProfil");
		
		model.addAttribute("utilisateur", new Utilisateur());
		
		return "view-inscription";
	}
	
	@PostMapping("/creer")
	public String creerProfil(
			@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
			BindingResult bindingResult) {
		
		if (utilisateur.getMot_de_passe().equals(utilisateur.getConfirm_mot_de_passe())) {
			
		
			if(bindingResult.hasErrors()) {
				return "view-inscription";
			} else {
				System.out.println("utilisateur = " + utilisateur);
				try {
					this.utilisateurService.add(utilisateur);
					
					return "redirect:/";
				} catch (BusinessException e) {
					e.getListeErreurs().forEach(
						erreur -> {
							ObjectError error = new ObjectError("globalError", erreur);
							bindingResult.addError(error);
						}
					);
					return "view-inscription";
				}
			}
		} else {
			System.out.println("error - matching password");
			bindingResult.rejectValue("confirm_mot_de_passe", "confirm_mot_de_passe.confirmation");
			return "view-inscription";
		}
	}
	
	@GetMapping("/profil")
	public String afficherProfil(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String pseudoUtilisateur = authentication.getName();
		
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
		// mise à jour du model avec l'instance d'utilisateur
		model.addAttribute("utilisateur", utilisateur);
		return "view-profil";
	}
	
	@GetMapping("/profil/modifier")
	public String afficherMettreAJourProfil(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String pseudoUtilisateur = authentication.getName();
		
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
		
		model.addAttribute("utilisateur", utilisateur);
		
		return "view-profil-modifier";
	}
	
	@PostMapping("/profil/modifier")
	public String mettreAJourProfil(
			@Valid @ModelAttribute(name = "utilisateur") Utilisateur utilisateur, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "view-profil-modifier";
		}else {
			
			try {
				
				this.utilisateurService.update(utilisateur);
				return "redirect:/utilisateurs/profil";
				
			} catch (BusinessException e) {
				e.getListeErreurs().forEach(
						erreur -> {
							ObjectError error = new ObjectError("globalError", erreur);
							bindingResult.addError(error);
						}
					);
					return "view-profil-modifier";
			}
			
		}
		
	}
	
	
	@PostMapping("/profil/supprimer")
	public String supprimerProfil(HttpServletRequest request, HttpServletResponse response) {
		
		//récuperer l'utilisateur connecté
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String utilisateur = authentication.getName();
		
		utilisateurService.delete(utilisateur);
						
		//déconnexion utilisateur
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, authentication);
		
		return "redirect:/encheres";

	}

}
