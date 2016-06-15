package br.com.sgc.bean;

import java.io.Serializable;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Usuario;

@SuppressWarnings("serial")
@Component("usuarioSessao")
public class UsuarioSessao implements Serializable {
	
	private Usuario usuario;
	
	public void carregarDados(Usuario usuario){
		getInstance().setUsuario(usuario);
	}

	public UsuarioSessao getInstance(){
		if(SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null || !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UsuarioSessao)){
			return new UsuarioSessao();
		}
		
		return (UsuarioSessao) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public boolean isAutenticated(){
		return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}