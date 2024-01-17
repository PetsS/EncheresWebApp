package fr.eni.projet.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

	private final String FIND_ALL = "SELECT no_categorie, libelle FROM CATEGORIES";
	private final String SELECT_CATEGORIES_FILTRE = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie LIKE :keyword";
	private final String FIND_BY_PSEUDO = "SELECT c.no_categorie, libelle FROM CATEGORIES c INNER JOIN ARTICLES_VENDUS av ON c.no_categorie = av.no_categorie INNER JOIN UTILISATEURS u ON u.no_utilisateur = av.no_utilisateur WHERE pseudo = :pseudo";
	private final String SELECT_BY_NO_CATEGORIE = "SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :no_categorie";
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Categorie> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	}
	
	@Override
	public List<Categorie> search(int key) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("key", key);
		
		return namedParameterJdbcTemplate.query(SELECT_CATEGORIES_FILTRE, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	}

	@Override
	public List<Categorie> read(String pseudoUtilisateur) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", pseudoUtilisateur);
		
		return namedParameterJdbcTemplate.query(FIND_BY_PSEUDO, mapSqlParameterSource, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	}

	@Override
	public Categorie read(int noCategorie) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_categorie", noCategorie);
		
		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_NO_CATEGORIE, mapSqlParameterSource, new BeanPropertyRowMapper<Categorie>(Categorie.class));
	
	}
	
}
