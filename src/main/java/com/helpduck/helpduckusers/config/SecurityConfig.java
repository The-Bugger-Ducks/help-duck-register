package com.helpduck.helpduckusers.config;

import java.util.Arrays;

import com.helpduck.helpduckusers.security.JWTAuthenticateFilter;
import com.helpduck.helpduckusers.security.JWTUtil;
import com.helpduck.helpduckusers.security.JWTValitadeFilter;
import com.helpduck.helpduckusers.service.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Autowired
	JWTUtil jwtUtil;

	private static final String[] publicRoutes = { "/auth/authentication" };

	private BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}

		http.cors().and().csrf().disable();

		http.authorizeRequests().antMatchers(publicRoutes).permitAll().anyRequest().authenticated();
		http.addFilter(jwtAuthorizationFilter());
		http.addFilter(new JWTValitadeFilter(authenticationManager()));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	public JWTAuthenticateFilter jwtAuthorizationFilter() throws Exception {
		JWTAuthenticateFilter jwtAuthenticationFilter = new JWTAuthenticateFilter(authenticationManager(), jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/auth/authentication");
		return jwtAuthenticationFilter;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
