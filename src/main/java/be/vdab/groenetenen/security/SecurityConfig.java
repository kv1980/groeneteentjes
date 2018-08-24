package be.vdab.groenetenen.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true) //om ook services beveiligd te maken
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String MANAGER = "manager";
	private static final String HELPDESKMEDEWERKER = "helpdeskmedewerker";
	private static final String MAGAZIJNIER = "magazijnier";
	private static final String USERS_BY_USERNAME =
			"select naam as username, paswoord as password, actief as enabled "+
			"from gebruikers where naam=?";
	private static final String AUTHORITIES_BY_USERNAME =
			"select gebruikers.naam as username, rollen.naam as authorities "+
			"from gebruikers "+
			"inner join gebruikersrollen on gebruikers.id=gebruikersrollen.gebruikerid "+
			"inner join rollen on gebruikersrollen.rolid = rollen.id "+
			"where gebruikers.naam=?";

/*	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				User.builder().username("joe").password("{noop}theboss").authorities(MANAGER).build(),
				User.builder().username("averell").password("{noop}hungry").authorities(HELPDESKMEDEWERKER, MAGAZIJNIER)
						.build());
	}*/

	@Bean
	JdbcDaoImpl jdbcDaoImpl(DataSource dataSource) {
		JdbcDaoImpl impl = new JdbcDaoImpl();
		impl.setDataSource(dataSource);
		//hieronder enkel methods om bestaande tabellen naar de vereiste tabellen om te zetten
		impl.setUsersByUsernameQuery(USERS_BY_USERNAME);
		impl.setAuthoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME);
		return impl;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers("/images/**").mvcMatchers("/css/**").mvcMatchers("/scripts/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/").and().authorizeRequests()
				.mvcMatchers("/offertes/toevoegen").hasAuthority(MANAGER).mvcMatchers("/werknemers")
				.hasAnyAuthority(MAGAZIJNIER, HELPDESKMEDEWERKER).mvcMatchers("/", "/login").permitAll()
				.mvcMatchers("/**").authenticated();
		http.httpBasic(); // om ook niet-browser REST-clients basis authentication mogelijk te maken
	}
}