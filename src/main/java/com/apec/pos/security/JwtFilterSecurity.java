package com.apec.pos.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.apec.pos.repository.AccountRepository;
import com.apec.pos.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilterSecurity extends OncePerRequestFilter{
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AccountRepository accountRepository;
	

	//bộ lọc token
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Lấy giá trị của header "Authorization" từ request
		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		// Kiểm tra nếu header "Authorization" tồn tại và bắt đầu bằng "Bearer "
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			// Lấy phần token JWT từ header
			jwtToken = requestTokenHeader.substring(7);
			try {
				// Lấy tên người dùng từ token JWT
				username = jwtService.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		// Nếu tên người dùng không null và chưa được xác thực
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			// Lấy thông tin người dùng từ cơ sở dữ liệu
			UserDetails userDetails = this.accountRepository.findByUsername(username);
			// Kiểm tra tính hợp lệ của token JWT
			if (jwtService.validateToken(jwtToken, userDetails)) {
				// Tạo đối tượng xác thực
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				// Đặt thông tin chi tiết của request vào đối tượng xác thực
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// Đặt đối tượng xác thực vào trong SecurityContext
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		// Chuyển tiếp request và response cho filter tiếp theo trong chuỗi filter
		filterChain.doFilter(request, response);
	}

	
}