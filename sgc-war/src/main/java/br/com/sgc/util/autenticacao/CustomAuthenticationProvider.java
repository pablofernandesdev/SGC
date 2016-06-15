package br.com.sgc.util.autenticacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new GrantedAuthorityImpl("USER"));
		Authentication logado = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication
				.getCredentials(), authorities);
		logado.setAuthenticated(true);
		return logado;
	}

	public boolean supports(Class<? extends Object> authentication) {
		return false;
	}

}
