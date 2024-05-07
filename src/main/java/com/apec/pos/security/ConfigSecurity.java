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
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Vô hiệu hóa bảo vệ CSRF cho tất cả các yêu cầu
		http.csrf(csrf -> csrf.disable())
				// Cho phép CORS từ bất kỳ nguồn gốc nào
				.cors(Customizer.withDefaults())

				// Cấu hình quyền truy cập cho các yêu cầu HTTP
				.authorizeHttpRequests(auth -> {
					// Cho phép tất cả các yêu cầu OPTIONS từ bất kỳ nguồn gốc nào
					auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
					// Cho phép truy cập không xác thực cho các đường dẫn /auth/**
					auth.requestMatchers("/auth/**").permitAll();
					// Cho phép truy cập không xác thực cho Swagger UI và tài liệu API
					auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
					// Cho phép truy cập không xác thực cho đường dẫn /error
					auth.requestMatchers("/error").permitAll();
					// Yêu cầu xác thực cho tất cả các yêu cầu khác
					auth.anyRequest().authenticated();
				});
		// Cấu hình chính sách tạo phiên làm việc là STATELESS (không lưu trữ phiên làm việc trên máy chủ)
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		);
		// Thêm bộ lọc JWT (jwtFilterSecurity) trước bộ lọc xác thực người dùng và mật khẩu
		http.addFilterBefore(jwtFilterSecurity, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		// Tạo cấu hình CORS mới
		CorsConfiguration corsConfig = new CorsConfiguration();
		// Cho phép tất cả các nguồn gốc và phương thức HTTP mặc định
		corsConfig.applyPermitDefaultValues();
		// Thêm các phương thức HTTP được cho phép
		corsConfig.addAllowedMethod("GET");
		corsConfig.addAllowedMethod("PATCH");
		corsConfig.addAllowedMethod("POST");
		corsConfig.addAllowedMethod("PUT");
		corsConfig.addAllowedMethod("DELETE");
		corsConfig.addAllowedMethod("OPTIONS");
		// Cho phép tất cả các nguồn gốc
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		// Cho phép tất cả các tiêu đề
		corsConfig.setAllowedHeaders(Arrays.asList("*"));

		// Tạo nguồn cấu hình CORS dựa trên URL
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Đăng ký cấu hình CORS cho tất cả các đường dẫn
		source.registerCorsConfiguration("/**", corsConfig);
		// Tạo bộ lọc CORS với nguồn cấu hình đã đăng ký
		return new CorsFilter(source);
	}
}