package br.com.isidro.auth.security;

public class JWTToken {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JWTToken(String token) {
		super();
		this.token = token;
	}

	public JWTToken() {
		super();
	}

	@Override
	public String toString() {
		return "JWTToken [token=" + token + "]";
	}
	
	
}	
