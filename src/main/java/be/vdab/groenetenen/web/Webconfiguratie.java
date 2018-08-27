package be.vdab.groenetenen.web;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
class Webconfiguratie implements WebMvcConfigurer {			

//	INDIEN ZELF VASTGELEGD
//	@Bean
//	FixedLocaleResolver localeResolver()	{
//		return new FixedLocaleResolver(new Locale("fr","BE"));	
//	}

//	INDIEN GEVRAAGD EN VASTGELEGD IN SESSION	
//	@Bean
//	SessionLocaleResolver localeResolver() {
//		return new SessionLocaleResolver();
//	}

//	INDIEN GEVRAAGD EN VASTGELEGD IN COOKIE	
	@Bean
	CookieLocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieMaxAge(604_800);
		return resolver;
	}

//	NODIG OM localeResolve OP TE ROEPEN (zowel session als cookie)
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
}