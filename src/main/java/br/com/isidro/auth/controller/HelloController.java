package br.com.isidro.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isidro.auth.model.Usuario;
import br.com.isidro.auth.security.JWTToken;
import br.com.isidro.auth.security.TokenUtil;

@RestController
public class HelloController {
	
	@GetMapping("/free")
	public String sayHello() {
		return "Hello World without authentication";
	}
	
	@GetMapping("/auth")
	public String sayAuth() {
		return "Hello Authenticated endpoint";
	}
	
	@PostMapping("/login")
	public JWTToken fazerLogin(@RequestBody Usuario user) {

		if (user.getLogin().equals("isidro") && user.getSenha().equals("1234")) {
			return TokenUtil.encode(user);
		}
		return null;
	}

}
