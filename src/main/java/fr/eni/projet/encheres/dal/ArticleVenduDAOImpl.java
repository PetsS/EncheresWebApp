package fr.eni.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Categorie;
import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {

	private static final String INFO_ENCHERES = "SELECT nom_article, prix_initial, date_fin_encheres, pseudo FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur WHERE u.no_utilisateur = :no_utilisateur";

	private static final String SELECT_ENCHERES = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, av.no_utilisateur, no_categorie, pseudo FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur";

	private static final String SELECT_ENCHERES_FILTRE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, av.no_utilisateur, no_categorie, pseudo FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur WHERE (nom_article LIKE :keyword) AND (no_categorie = :key)";
	
	private static final String SELECT_BY_NO_ARTICLE = "SELECT no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, av.no_utilisateur, no_categorie, pseudo FROM ARTICLES_VENDUS av INNER JOIN UTILISATEURS u ON av.no_utilisateur = u.no_utilisateur WHERE no_article = :no_article";

	private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie)\r\n"
			+ "	VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";

	private static final String UPDATE = "UPDATE ARTICLES_VENDUS SET prix_vente = :prix_vente WHERE no_article = :no_article";
	
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	// recherche une article vendu par no_utilisateur
	@Override
	public ArticleVendu findEncheres(int no_utilisateur) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_utilisateur", no_utilisateur);
		
		return namedParameterJdbcTemplate.queryForObject(INFO_ENCHERES, mapSqlParameterSource, new ArticleVenduRowMapper());
	}

	// recherche toutes les articles vendus par pseudo - join des tables Articles_Vendus et Utilisateurs par no_utilisateur
	@Override
	public List<ArticleVendu> findAll() {
		return this.namedParameterJdbcTemplate.query(SELECT_ENCHERES, new ArticleVenduRowMapper());
	}
	
	@Override
	public List<ArticleVendu> search(String keyword, int key) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("keyword", "%" + keyword + "%");
		mapSqlParameterSource.addValue("key", key);
		
		return namedParameterJdbcTemplate.query(SELECT_ENCHERES_FILTRE, mapSqlParameterSource, new ArticleVenduRowMapper());
	}

	@Override
	public void create(ArticleVendu articleVendu) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		mapSqlParameterSource.addValue("nom_article", articleVendu.getNomArticle());
		mapSqlParameterSource.addValue("description",  articleVendu.getDescription());
		mapSqlParameterSource.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
		mapSqlParameterSource.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
		mapSqlParameterSource.addValue("prix_initial", articleVendu.getMiseAPrix());
		mapSqlParameterSource.addValue("prix_vente", articleVendu.getPrixVente());
		mapSqlParameterSource.addValue("no_utilisateur", articleVendu.getUtilisateurVend().getNo_utilisateur());
		mapSqlParameterSource.addValue("no_categorie", articleVendu.getCategorieArticle().getNoCategorie());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			// Mise à jour de l'identifiant d'article vendu auto-généré par la base - IDENTITY
			articleVendu.setNoArticle(keyHolder.getKey().intValue());
		}
	}

	@Override
	public ArticleVendu read(int noArticle) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_article", noArticle);

		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_NO_ARTICLE, mapSqlParameterSource, new ArticleVenduRowMapper());
	}

	@Override
	public void update(ArticleVendu articleVendu) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		mapSqlParameterSource.addValue("no_article", articleVendu.getNoArticle());
		mapSqlParameterSource.addValue("prix_vente", articleVendu.getPrixVente());
	 
		namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
	}
}

class ArticleVenduRowMapper implements RowMapper<ArticleVendu>{

	@Override
	public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ArticleVendu article = new ArticleVendu(
				rs.getInt("no_article"),
				rs.getString("nom_article"),
				rs.getString("description"),
				rs.getDate("date_debut_encheres"),
				rs.getDate("date_fin_encheres"),
				rs.getInt("prix_initial"),
				rs.getInt("prix_vente")
				);
				
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(rs.getString("pseudo"));
		utilisateur.setNo_utilisateur(rs.getInt("no_utilisateur"));
		article.setUtilisateurVend(utilisateur);
		
		Categorie categorie = new Categorie();
		categorie.setNoCategorie(rs.getInt("no_categorie"));
		article.setCategorieArticle(categorie);
		
		return article;
	}
}

