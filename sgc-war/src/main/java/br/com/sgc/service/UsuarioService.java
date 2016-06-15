package br.com.sgc.service;

import java.io.Serializable;
import java.util.List;

import br.com.sgc.domain.Usuario;

public interface UsuarioService extends Serializable {

	public List<Usuario> findUsuarios(Usuario usuario);

	public void saveUsuario(Usuario usuario);

	public void updateUsuario(Usuario usuario);

	public void deleteUsuario(Long idUsuario);

	public Usuario findUsuario(Usuario usuario);

	public Usuario findUsuarioCpf(String cpf);
	
	public Usuario findNomeUsuario(String usuario);
	
}