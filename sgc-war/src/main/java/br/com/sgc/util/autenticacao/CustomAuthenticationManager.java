package br.com.sgc.util.autenticacao;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication usuario) throws AuthenticationException {
		return new UsernamePasswordAuthenticationToken(usuario.getPrincipal(), usuario.getCredentials(), usuario
				.getAuthorities());
	}

}