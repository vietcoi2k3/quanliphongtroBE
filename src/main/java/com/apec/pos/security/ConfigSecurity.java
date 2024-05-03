package com.apec.pos.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.apec.pos.security.JwtFilterSecurity;
import com.apec.pos.service.JwtService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@Configuration
public class ConfigSecurity {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private JwtFilterSecurity jwtFilterSecurity;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())

				.authorizeHttpRequests(auth ->{auth.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
				auth.requestMatchers("/auth/**").permitAll();
				auth.requestMatchers("/swagger-ui/**",
						"/v3/api-docs/**").permitAll();
				auth.requestMatchers("/error").permitAll();
				auth.anyRequest().authenticated();
			});
		 http.sessionManagement(
	                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            );
	       http.addFilterBefore(jwtFilterSecurity, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.applyPermitDefaultValues();
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("PATCH");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedMethod("DELETE");
		corsConfig.addAllowedMethod("OPTIONS");
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedHeaders(Arrays.asList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return new CorsFilter(source);

	}
}