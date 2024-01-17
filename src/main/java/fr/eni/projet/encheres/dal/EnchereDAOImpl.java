package fr.eni.projet.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Enchere;
import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	private static final String INSERT = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
	
	private static final String UPDATE = "UPDATE ENCHERES SET no_utilisateur = :no_utilisateur, no_article = :no_article, date_enchere = :date_enchere, montant_enchere = :montant_enchere";
	
	private static final String DELETE = "DELETE FROM ENCHERES WHERE (no_utilisateur = :no_utilisateur) AND (no_article = :no_article)";
	
	private static final String INFO_ENCHERISSEUR = "SELECT pseudo FROM UTILISATEURS u\r\n"
			+ "	JOIN ENCHERES e ON e.no_utilisateur = u.no_utilisateur\r\n"
			+ "	JOIN ARTICLES_VENDUS av ON e.no_article = av.no_article\r\n"
			+ "	WHERE (e.no_article = :no_article) AND (e.montant_enchere = :montant_enchere)";
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public void create(Enchere enchere) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		mapSqlParameterSource.addValue("no_utilisateur", enchere.getUtilisateurEncherit().getNo_utilisateur());
		mapSqlParameterSource.addValue("no_article",  enchere.getArticleVenduConcerne().getNoArticle());
		mapSqlParameterSource.addValue("date_enchere", enchere.getDateEnchere());
		mapSqlParameterSource.addValue("montant_enchere", enchere.getMontant_enchere());
		
		try {
			namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource);
		} catch (DataAccessException e) {
			try {
				namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
			} catch (DataAccessException e1) {
				namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
				namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource);
			}
		}
	}

	@Override
	public Utilisateur read(int no_article, int prixVente) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_article", no_article);
		mapSqlParameterSource.addValue("montant_enchere", prixVente);
		
		try {
			return namedParameterJdbcTemplate.queryForObject(INFO_ENCHERISSEUR, mapSqlParameterSource, new BeanPropertyRowMapper<Utilisateur>(Utilisateur.class));
		} catch (EmptyResultDataAccessException e) {
			
		}
		return null;
		
		
	}
	
	

}
