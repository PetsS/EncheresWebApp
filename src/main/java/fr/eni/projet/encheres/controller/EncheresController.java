package fr.eni.projet.encheres.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.projet.encheres.bll.ArticleVenduService;
import fr.eni.projet.encheres.bll.CategorieService;
import fr.eni.projet.encheres.bll.EnchereService;
import fr.eni.projet.encheres.bll.RetraitService;
import fr.eni.projet.encheres.bll.UtilisateurService;
import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Retrait;
import fr.eni.projet.encheres.bo.Utilisateur;
import fr.eni.projet.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
//@RequestMapping("/encheres")
//@SessionAttributes({ "utilisateurSession", "categorieSession" })
public class EncheresController {

	private ArticleVenduService articleVenduService;
	private CategorieService categorieService;
	private UtilisateurService utilisateurService;
	private RetraitService retraitService;
	private EnchereService enchereService;

	public EncheresController(ArticleVenduService articleVenduService, CategorieService categorieService,
			UtilisateurService utilisateurService, RetraitService retraitService, EnchereService enchereService) {
		this.articleVenduService = articleVenduService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
		this.enchereService = enchereService;
	}

	@GetMapping
	public String afficherListEncheres(Model model, @RequestParam(value="keyword", required = false) String keyword,
													@RequestParam(value="key", required = false, defaultValue = "0") Integer key) {
		List<ArticleVendu> articleVendus = articleVenduService.consulterArticleVendus(keyword, key);
		List<Categorie> listeCategories = categorieService.getCategorie();
		
		model.addAttribute("listeCategories", listeCategories);
		model.addAttribute("articleVendus", articleVendus);
		model.addAttribute("keyword", keyword);

		Categorie categorie = new Categorie();
		
		categorie.setNoCategorie(key);
		model.addAttribute("categorie", categorie);
		
		return "index";
	}
	
	@GetMapping("/encheres")
	public String afficherListEncheresConnecte(Model model, @RequestParam(value="keyword", required = false) String keyword,
															@RequestParam(value="key", required = false, defaultValue = "0") Integer key) {
		
		List<ArticleVendu> articleVendus = articleVenduService.consulterArticleVendus(keyword, key);
		List<Categorie> listeCategories = categorieService.getCategorie();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String pseudoUtilisateur = authentication.getName();
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
		
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("listeCategories", listeCategories);
		model.addAttribute("articleVendus", articleVendus);
		model.addAttribute("keyword", keyword);

		Categorie categorie = new Categorie();
		
		categorie.setNoCategorie(key);
		model.addAttribute("categorie", categorie);
		
		return "index";
	}

	@GetMapping("/encheres/creer")
	public String creerArticleVendu(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String pseudoUtilisateur = authentication.getName();
		
		// Ajout utilisateur authentifié dans le modèle pour récuperer adresse de l'utilisateur
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
		model.addAttribute("utilisateur", utilisateur);
		
		// Ajout liste des categories dans le modèle
		List<Categorie> listeCategories = categorieService.getCategorie();
		model.addAttribute("listeCategories", listeCategories);

		// Utilisation de "DateTimeFormatter" pour formater la date et l'heure actuelles
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    String dateTimeFormatted = LocalDateTime.now().format(formatter);
	    
	    //ajout au model de création d'article
	    model.addAttribute("dateTimeFormatted", dateTimeFormatted);
		
		// Ajout de l'instance dans le modèle
		ArticleVendu article = new ArticleVendu();
		Retrait retrait = new Retrait();
		retrait.setRue(utilisateur.getRue());
		retrait.setCode_postal(utilisateur.getCode_postal());
		retrait.setVille(utilisateur.getVille());
		article.setLieuRetrait(retrait);
		
		model.addAttribute("articleVendu", article);
		return "view-encheres-creer";
		
	}

	@PostMapping("/encheres/creer")
	public String creerArticleVendu(
			@Valid @ModelAttribute("articleVendu") ArticleVendu articleVendu,
			BindingResult bindingResult,
			Model model) {
		System.out.println(articleVendu);
		if (bindingResult.hasErrors()) {
			
			// En cas d'erreur reajout liste des categories dans le modèle et garde selection
			List<Categorie> listeCategories = categorieService.getCategorie();
			model.addAttribute("listeCategories", listeCategories);
			
			// Reajut date en cas d'erreur
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		    String dateTimeFormatted = LocalDateTime.now().format(formatter);
		    model.addAttribute("dateTimeFormatted", dateTimeFormatted);
		    
			return "view-encheres-creer";
		} else {

			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String pseudoUtilisateur = authentication.getName();
				Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
				
				articleVendu.setUtilisateurVend(utilisateur);

				List<Categorie> categorie = categorieService.findByPseudo(pseudoUtilisateur);
				articleVendu.setCategorieList(categorie);	
				
				articleVenduService.creerArticleVendu(articleVendu);

				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.getListeErreurs().forEach(erreur -> {
					ObjectError error = new ObjectError("globalError", erreur);
					bindingResult.addError(error);
				});
				return "view-encheres-creer";
			}
		}
	}

	@GetMapping("/encheres/detail")
	public String afficherUnArticleVendu(@RequestParam(value="no_article", required=false, defaultValue = "1") Integer no_article, Model model) {
		ArticleVendu article = articleVenduService.consulterArticleVenduParNoArticle(no_article);
		model.addAttribute("no_article", article);
		Retrait retrait = retraitService.findByNoArticle(no_article);
		model.addAttribute("retrait", retrait);
		
		Enchere enchere = new Enchere();
		if (article.getPrixVente() == 0) {
			enchere.setMontant_enchere(article.getMiseAPrix());
			article.setUtilisateurAchete(article.getUtilisateurVend());
		} else {
			enchere.setMontant_enchere(article.getPrixVente() + 1);
			article.setUtilisateurAchete(enchereService.trouverEnchere(no_article, article.getPrixVente()));
		}
		enchere.setArticleVenduConcerne(article);
		
		model.addAttribute("enchere", enchere);
		
		return "view-encheres-detail";
	}
	
	@PostMapping("/encheres/detail")
	public String creerPropositionEnchere(
			@Valid @ModelAttribute("enchere") Enchere enchere,
			BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "view-encheres-detail";
		} else {

			try {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				String pseudoUtilisateur = authentication.getName();
				Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);
				
				enchere.setUtilisateurEncherit(utilisateur);

				Date date = new Date();
				enchere.setDateEnchere(date);
				
				enchereService.creerEnchere(enchere);

				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.getListeErreurs().forEach(erreur -> {
					ObjectError error = new ObjectError("globalError", erreur);
					bindingResult.addError(error);
				});
				return "view-encheres-detail";
			}
		}
	}

	// Conversion String à Date - convert value of type java.lang.String to required
	// type java.util.Date
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

//	@ModelAttribute("utilisateurSession")
//	public Utilisateur addUtilisateurSession() {
//		return new Utilisateur();
//	}
//	
//	@ModelAttribute("categorieSession")
//	public List<Categorie> addCategorieSession(){
//		System.out.println("Chargement de la liste des categories en session");
//		return this.categorieService.getCategorie();
//	}

}
