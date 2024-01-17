package fr.eni.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.eni.projet.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	// insertion d'un utilisateur
	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (:pseudo,:nom,:prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe, :credit, :administrateur)";
	
	// recherche d'un utilisateur par son pseudo
	private static final String FIND_BY_PSEUDO = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville FROM UTILISATEURS WHERE pseudo = :pseudo";

	// mise à jour d'un utilisateur
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = :pseudo, prenom = :prenom, email = :email, nom = :nom, telephone = :telephone, rue = :rue, code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe WHERE pseudo = :pseudo";
	
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";
	
	private static final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String COUNT_EMAIL = "SELECT COUNT(email) FROM UTILISATEURS WHERE email = :email";
	
	
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Override
	public void create(Utilisateur utilisateur) {
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", utilisateur.getPseudo());
		mapSqlParameterSource.addValue("prenom", utilisateur.getPrenom());
		mapSqlParameterSource.addValue("nom", utilisateur.getNom());
		mapSqlParameterSource.addValue("email", utilisateur.getEmail());
		mapSqlParameterSource.addValue("telephone", utilisateur.getTelephone());
		mapSqlParameterSource.addValue("rue", utilisateur.getRue());
		mapSqlParameterSource.addValue("code_postal", utilisateur.getCode_postal());
		mapSqlParameterSource.addValue("ville", utilisateur.getVille());
		mapSqlParameterSource.addValue("mot_de_passe", passwordEncoder.encode(utilisateur.getMot_de_passe()));
		mapSqlParameterSource.addValue("credit", 0);
		mapSqlParameterSource.addValue("administrateur", 0);
 
		namedParameterJdbcTemplate.update(INSERT, mapSqlParameterSource);
		System.out.println("L'utilisateur à été créé");
	}

	@Override
	public Utilisateur read(String pseudoUtilisateur) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", pseudoUtilisateur);
		try {
			return namedParameterJdbcTemplate.queryForObject(FIND_BY_PSEUDO, mapSqlParameterSource, new UtilisateurRowMapper());
		} catch (EmptyResultDataAccessException e) {
	        return null;
	    }
		
	}

	@Override
	public void update(Utilisateur utilisateur) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", utilisateur.getPseudo());
		mapSqlParameterSource.addValue("prenom", utilisateur.getPrenom());
		mapSqlParameterSource.addValue("nom", utilisateur.getNom());
		mapSqlParameterSource.addValue("email", utilisateur.getEmail());
		mapSqlParameterSource.addValue("telephone", utilisateur.getTelephone());
		mapSqlParameterSource.addValue("rue", utilisateur.getRue());
		mapSqlParameterSource.addValue("code_postal", utilisateur.getCode_postal());
		mapSqlParameterSource.addValue("ville", utilisateur.getVille());
		mapSqlParameterSource.addValue("mot_de_passe", passwordEncoder.encode(utilisateur.getMot_de_passe()));

 
		namedParameterJdbcTemplate.update(UPDATE, mapSqlParameterSource);
		System.out.println("L'utilisateur à été modifié");
	}
	
	@Override
	public void delete(String pseudoUtilisateur) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", pseudoUtilisateur);
		
		namedParameterJdbcTemplate.update(DELETE, mapSqlParameterSource);
		System.out.println("L'utilisateur à été supprimé");
	}
	
	@Override
	public int countPseudo(String pseudo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("pseudo", pseudo);
		
		return namedParameterJdbcTemplate.queryForObject(COUNT_PSEUDO, mapSqlParameterSource, Integer.class);
	}

	@Override
	public int countEmail(String email) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("email", email);
		
		return namedParameterJdbcTemplate.queryForObject(COUNT_EMAIL, mapSqlParameterSource, Integer.class);
	}

	class UtilisateurRowMapper implements RowMapper<Utilisateur>{

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur u = new Utilisateur();
			u.setNo_utilisateur(rs.getInt("no_utilisateur"));
			u.setPseudo(rs.getString("pseudo"));
			u.setPrenom(rs.getString("prenom"));
			u.setNom(rs.getString("nom"));
			u.setEmail(rs.getString("email"));
			u.setTelephone(rs.getString("telephone"));
			u.setRue(rs.getString("rue"));
			u.setCode_postal(rs.getString("code_postal"));
			u.setVille(rs.getString("ville"));
			
			return u;
		}
	}



}
