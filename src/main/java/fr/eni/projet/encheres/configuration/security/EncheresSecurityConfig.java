package fr.eni.projet.encheres.configuration.security;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class EncheresSecurityConfig {

	
	
	@Bean
	UserDetailsManager userDetailManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM UTILISATEURS INNER JOIN roles ON UTILISATEURS.administrateur = roles.is_admin WHERE pseudo = ?");
		
		return jdbcUserDetailsManager;
	}
		
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/encheres/creer").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "/encheres/creer").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.GET, "/encheres/detail").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "/encheres/detail").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.GET, "/utilisateurs/profil").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.GET, "/utilisateurs/profil/modifier").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				.requestMatchers(HttpMethod.POST, "/utilisateurs/profil/modifier").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				
				.requestMatchers(HttpMethod.POST, "/utilisateurs/profil/supprimer").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
				
				.requestMatchers(HttpMethod.GET, "/utilisateurs/creer").permitAll()
				.requestMatchers(HttpMethod.POST, "/utilisateurs/creer").permitAll()
				.requestMatchers(HttpMethod.GET, "/encheres").permitAll()
				
				.requestMatchers(HttpMethod.GET, "/").permitAll()
				.requestMatchers(HttpMethod.GET, "/css/*").permitAll()
				.requestMatchers(HttpMethod.GET, "/images/*").permitAll()
				.anyRequest().denyAll()
		);

		http.formLogin(form -> 
			form.loginPage("/login").permitAll()
				.defaultSuccessUrl("/encheres"));
		
		http.logout(logout -> 
			logout.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID") // suppression du cookie session
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // definition l'url permettant la deconnecion
				.logoutSuccessUrl("/login?logout") // url appelée suite à la déconnexion
				.permitAll()
		);
				
		return http.build();
	}
	
}
