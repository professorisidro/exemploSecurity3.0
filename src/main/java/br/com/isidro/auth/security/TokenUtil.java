package br.com.isidro.auth.security;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.isidro.auth.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenUtil {

	private static final int SEGUNDOS = 1000;
	private static final int MINUTOS = 60 * SEGUNDOS;
	private static final int HORAS = 60 * MINUTOS;
	private static final int DIAS = 24 * HORAS;
	private static final int EXPIRATION = 3 * DIAS;
	private static final String SECRET_KEY = "1234567890123456789012345678901234";

	private static final String PREFIX = "Bearer ";

	public static JWTToken encode(Usuario usuario) {
		Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
		String jws = Jwts.builder().setSubject(usuario.getLogin()).setIssuer("isidro")
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(key, SignatureAlgorithm.HS256).compact();
		return new JWTToken(PREFIX + jws);
	}

	public static Authentication decodeToken(HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization");
			token = token.replace(PREFIX, "");

			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build()
					.parseClaimsJws(token);
			String subject = claims.getBody().getSubject();
			String issuer = claims.getBody().getIssuer();
			Date exp = claims.getBody().getExpiration();

			if (isValid(subject, issuer, exp)) {
				System.out.println("Valid token!!");
				return new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	private static boolean isValid(String subject, String issuer, Date exp) {
		// TODO Auto-generated method stub
		System.out.println("DEBUG = " + subject + "/" + issuer + "/" + exp);
		return subject != null && issuer.equals("isidro") && exp.after(new Date(System.currentTimeMillis()));
	}
}
