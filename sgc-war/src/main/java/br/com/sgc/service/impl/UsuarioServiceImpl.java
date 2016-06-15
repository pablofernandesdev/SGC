package br.com.sgc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sgc.dao.UsuarioDao;
import br.com.sgc.dao.VendaDao;
import br.com.sgc.domain.Usuario;
import br.com.sgc.domain.Venda;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.UsuarioService;

@Service("usuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private VendaDao vendaDao;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	public List<Usuario> findUsuarios(Usuario usuario) {
		return usuarioDao.findUsuarios(usuario);
	}

	@Override
	public void saveUsuario(Usuario usuario) {
		usuarioDao.saveUsuario(usuario);
	}

	@Override
	public void updateUsuario(Usuario usuario) {
		usuarioDao.updateUsuario(usuario);
	}
	
	@Override
	public void deleteUsuario(Long idUsuario) {
		List<Venda> listVenda = vendaDao.findVendaByUsuario(idUsuario);
		
		if(listVenda.size() > 0){
			throw new NegocioException("MSG_A006");
		}
		
		usuarioDao.deleteUsuario(idUsuario);
	}
	
	@Override
	public Usuario findUsuario(Usuario usuario) {
		return usuarioDao.findUsuario(usuario);
		
	}
	
	@Override
	public Usuario findUsuarioCpf(String cpf) {
		return usuarioDao.findUsuarioCpf(cpf);
		
	}
	
	@Override
	public Usuario findNomeUsuario(String usuario) {
		return usuarioDao.findNomeUsuario(usuario);
		
	}
	
}
