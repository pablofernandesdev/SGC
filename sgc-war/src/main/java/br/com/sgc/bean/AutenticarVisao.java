package br.com.sgc.bean;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Usuario;
import br.com.sgc.service.UsuarioService;
import br.com.sgc.util.FacesUtils;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.autenticacao.CustomAuthenticationManager;

@Component("autenticarVisao")
@Scope("request")
public class AutenticarVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_LOGIN = "/login" + FacesUtils.PARAMETRO_JSF_REDIRECT;
	private static final String FW_LOGIN_SEM_REDIRECT = "/login";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	@Autowired		
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioSessao usuarioSessao;
	
	private Usuario usuario;
	
	public String autenticar(){
		
		limparSessao();
		
		if(validarUsuarioSenha()){
			Usuario usuario = usuarioService.findUsuario(getUsuario());
		
			if(usuario != null){
				//Logou
				autenticarSecurity();//colocando o usuário na sessão
				usuarioSessao.carregarDados(usuario);
				
				return FW_TELA_INICIAL;
			}else{
				
				//Nao logou
				Mensagens.addInfo("MSG_A013");
				
				return FW_LOGIN_SEM_REDIRECT;
			}
			
		}else{
			return FW_LOGIN_SEM_REDIRECT;
		}
		
	}
	
	//adiciona o usuário na sessão

	private void autenticarSecurity() {
		AuthenticationManager ax = new CustomAuthenticationManager();
		Authentication authentication = new UsernamePasswordAuthenticationToken(usuarioSessao, "default"); //adiciona o usuarioSessao no authentication
		Authentication retorno = ax.authenticate(authentication); //seta o authentication criando uma authentication
		SecurityContextHolder.getContext().setAuthentication(retorno); //seta no contexto da aplicação a autenticação criada
		
	}
	
	//limpar a sessão ao logar
	
	private void limparSessao(){
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
		request.getSession().invalidate();
		usuarioSessao = new UsuarioSessao();
	}
	
	public String sair(){
		limparSessao();
		
		return FW_LOGIN;
	}
	
	public UsuarioSessao getUsuarioSessao() {
		return usuarioSessao;
	}

	private boolean validarUsuarioSenha() {
		boolean retorno = true;
		
		if(getUsuario() == null){
			retorno = false;
			Mensagens.addError("MSG_A013");
		}else{
		
			if(getUsuario().getUsuario().trim().equals("")){
				retorno = false;
				Mensagens.addError("MSG_A010");
			}
			
			if(getUsuario().getSenha().trim().equals("")){
				retorno = false;
				Mensagens.addError("MSG_A011");
			}
		}
		return retorno;
	}

	public Usuario getUsuario() {
		if(usuario == null){
			usuario = new Usuario();
		}
		
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}