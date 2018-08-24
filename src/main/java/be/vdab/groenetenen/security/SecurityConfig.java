package be.vdab.groenetenen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String MANAGER = "manager";
	private static final String HELPDESKMEDEWERKER = "helpdeskmedewerker";
	private static final String MAGAZIJNIER = "magazijnier";
	
	 @Bean   
	 InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		 return new InMemoryUserDetailsManager(
				 User.builder().username("joe").password("{noop}theboss").authorities(MANAGER).build(),
				 User.builder().username("averell").password("{noop}hungry").authorities(HELPDESKMEDEWERKER, MAGAZIJNIER).build());  
	 	}
	
	 @Override
	 public void configure(WebSecurity web) throws Exception {
		 web.ignoring()
		 	.mvcMatchers("/images/**")
		 	.mvcMatchers("/css/**")
		 	.mvcMatchers("/scripts/**");
	 }
	 
	 @Override
	 public void configure(HttpSecurity http) throws Exception {
		 http.formLogin()
		 	.and()
		 	.authorizeRequests()
		 	.mvcMatchers("/offertes/toevoegen").hasAuthority(MANAGER)
		 	.mvcMatchers("werknemers").hasAnyAuthority(MAGAZIJNIER,HELPDESKMEDEWERKER)
		 	.mvcMatchers("/", "/login").permitAll()
		 	.mvcMatchers("/**").authenticated();
	 }  
}