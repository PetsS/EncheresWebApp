package fr.eni.projet.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.ArticleVendu;
import fr.eni.projet.encheres.bo.Retrait;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	private static final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (:no_article, :rue, :code_postal, :ville)";
	
	private final String FIND_BY_NO_ARTICLE = "SELECT no_article, rue, code_postal, ville FROM RETRAITS WHERE no_article = :no_article";
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public void create(ArticleVendu articleVendu) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		
		mapSqlParameterSource.addValue("no_article", articleVendu.getNoArticle());
		mapSqlParameterSource.addValue("rue", articleVendu.getLieuRetrait().getRue());
		mapSqlParameterSource.addValue("code_postal", articleVendu.getLieuRetrait().getCode_postal());
		mapSqlParameterSource.addValue("ville", articleVendu.getLieuRetrait().getVille());
 
		namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource);
	}

	@Override
	public Retrait read(int noArticle) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_article", noArticle);
		
		return namedParameterJdbcTemplate.queryForObject(FIND_BY_NO_ARTICLE, mapSqlParameterSource, new BeanPropertyRowMapper<Retrait>(Retrait.class));
	
	}

}
