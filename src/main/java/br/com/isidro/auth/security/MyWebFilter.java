package br.com.isidro.auth.security;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MyWebFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getHeader("Authorization") != null) {
			Authentication auth = TokenUtil.decodeToken(request);
			if (auth != null)
				SecurityContextHolder.getContext().setAuthentication(auth);
			else {
				System.out.println("Erro de token");
				ErroDTO erro = new ErroDTO(401,"Token Inválido");
				response.setStatus(erro.getStatus());
				response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				response.getWriter().print(mapper.writeValueAsString(erro));
				response.getWriter().flush();
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

}
