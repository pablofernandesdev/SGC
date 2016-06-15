package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.EstadosBrasilEnum;
import br.com.sgc.domain.SexoEnum;
import br.com.sgc.domain.StatusEnum;
import br.com.sgc.domain.Usuario;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.UsuarioService;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.SgcUtil;
import br.com.sgc.validators.Validacoes;



@Component("manterUsuarioVisao")
@Scope("request")
public class ManterUsuarioVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_USUARIO = "/visao/manterUsuario/pesquisarUsuario";
	private static final String FW_CADASTRAR_USUARIO = "/visao/manterUsuario/cadastrarUsuario";
	private static final String FW_EDITAR_USUARIO = "/visao/manterUsuario/editarUsuario";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	@SuppressWarnings("unused")
	private static final String String = null;
	
	private Usuario usuario;
	private List<Usuario> listaUsuarios;
	private List<StatusEnum> statuss;
	private List<EstadosBrasilEnum> estados;
	private List<SexoEnum> sexos;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioSessao usuarioSessao;
	
	public String irTelaPesquisarUsuario(){
		return FW_PESQUISAR_USUARIO;
	}
	
	public String voltarPesquisaUsuario(){
		return FW_PESQUISAR_USUARIO;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	public String irTelaCadastrarUsuario(){
		
		carregarListaEstados();
		carregarSexos();
		carregarStatuss();
		return FW_CADASTRAR_USUARIO;
	}
	
	private void carregarListaEstados() {
		for (EstadosBrasilEnum estado : EstadosBrasilEnum.values()) {
			getEstados().add(estado);
		}
	}

	private void carregarSexos(){
		setSexos(null); //limpando antes de adicionar os ja existentes
		for(SexoEnum sexo:  SexoEnum.values()){
			getSexos().add(sexo);
		}
	}

	private void carregarStatuss() {
		setStatuss(null);
		for (StatusEnum status : StatusEnum.values()) {
			getStatuss().add(status);
		}
	}
	
	public String irTelaEditarUsuario(Usuario usuario){
		carregarListaEstados();
		carregarSexos();
		carregarStatuss();
		setUsuario(usuario);
		return FW_EDITAR_USUARIO;
	}
	
	public void pesquisarUsuario() {
		setListaUsuarios(usuarioService.findUsuarios(getUsuario()));
		if(getListaUsuarios().isEmpty()){
			Mensagens.addError("MSG_E005");
		}
			
	}
	
	public String saveUsuario() {
		if (validarTelaUsuario(true)) {
			
			removerMascaras();
			
			usuarioService.saveUsuario(getUsuario());
			setUsuario(new Usuario());

			pesquisarUsuario();
			
			return FW_PESQUISAR_USUARIO;
		} else {
			return FW_CADASTRAR_USUARIO;
		}
	}
	
	private boolean validarTelaUsuario(boolean isNovoCadastro){
		boolean retorno = true;
		if(getUsuario().getBairro() == null || getUsuario().getBairro().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.bairro"));
			retorno = false;
		}
		
		if(getUsuario().getCep() == null || getUsuario().getCep().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cep"));
			retorno = false;
		}
		
		if(getUsuario().getCidade() == null || getUsuario().getCidade().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if(getUsuario().getComplemento() == null || getUsuario().getComplemento().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.complemento"));
			retorno = false;
		}
		
		if(getUsuario().getCpf() == null || getUsuario().getCpf().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cpf"));
			retorno = false;
		}
		
		if(getUsuario().getEmail() == null || getUsuario().getEmail().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if(getUsuario().getLogradouro() == null || getUsuario().getLogradouro().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.logradouro"));
			retorno = false;
		}
		
		if(getUsuario().getNascimento() == null){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.data.nascimento"));
			retorno = false;
		}
		
		if(getUsuario().getNome() == null || getUsuario().getNome().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.nome"));
			
		}
		
		if(getUsuario().getSenha() == null || getUsuario().getSenha().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.senha"));
			retorno = false;
		}
		
		if(getUsuario().getSenha().length() < 6){
			Mensagens.addError("MSG_A014");
		}
		
		if(getUsuario().getSexo() == null || getUsuario().getSexo().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.sexo"));
			retorno = false;
		}
		
		if(getUsuario().getStatus() == null || getUsuario().getStatus().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.status"));
			retorno = false;
		}
		
		if(getUsuario().getTelCel() == null || getUsuario().getTelCel().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if(getUsuario().getTelFixo() == null || getUsuario().getTelFixo().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if(getUsuario().getUf() == null || getUsuario().getUf().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.uf"));
			retorno = false;
		}
		
		if(getUsuario().getUsuario() == null || getUsuario().getUsuario().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.nome.usuario"));
			retorno = false;
		}
		
		if((getUsuario().getCpf().length()>0) && (Validacoes.validarCPF(getUsuario().getCpf()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cpf"));
			retorno = false;
			
		}
		
		if((getUsuario().getEmail().length()>0) && (Validacoes.validEmail(getUsuario().getEmail()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.email"));
		}
		
		if((getUsuario().getNome().length()>0) && (Validacoes.validarNome(getUsuario().getNome()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.nome"));
			retorno = false;
		}
		
		if((getUsuario().getCep().length()>0) && (Validacoes.validarCep(getUsuario().getCep()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cep"));
			retorno = false;
		}
		
		if((getUsuario().getTelCel().length()>0) && (Validacoes.validarTelefone(getUsuario().getTelCel()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if((getUsuario().getTelFixo().length()>0) && (Validacoes.validarTelefone(getUsuario().getTelFixo()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if(isNovoCadastro){
			//verifica se o cpf informado já se encontra cadastrado no banco
			if(!getUsuario().getCpf().trim().equals("")){
				
				String cpf = SgcUtil.removerMascara(getUsuario().getCpf());
				
				Usuario usuario = usuarioService.findUsuarioCpf(cpf);
				
				if(usuario != null){
					Mensagens.addError("MSG_A012", Mensagens.getValueProperties("label.cpf"));
					retorno = false;
				}
				
			}
			
			//verifica se o nome de usuário informado já se encontra cadastrado no banco
			if(!getUsuario().getUsuario().trim().equals("")){
				Usuario usuario = usuarioService.findNomeUsuario(getUsuario().getUsuario());
				
				if(usuario != null){
					Mensagens.addError("MSG_A012", Mensagens.getValueProperties("label.nome.usuario"));
					retorno = false;
				}
			}
		}
		
		return retorno;
		
	}
	
	private void removerMascaras(){
		getUsuario().setCpf(SgcUtil.removerMascara(getUsuario().getCpf()));
		getUsuario().setTelCel(SgcUtil.removerMascara(getUsuario().getTelCel()));
		getUsuario().setTelFixo(SgcUtil.removerMascara(getUsuario().getTelFixo()));
		getUsuario().setCep(SgcUtil.removerMascara(getUsuario().getCep()));
	}
	
	public String updateUsuario() {
		if (validarTelaUsuario(false)) {
			removerMascaras();
			
			usuarioService.updateUsuario(getUsuario());

			setUsuario(new Usuario());

			pesquisarUsuario();
			
			return FW_PESQUISAR_USUARIO;
		} 
		 
		else {
			return FW_EDITAR_USUARIO;
		}
		
	}
	
	public void deleteUsuario(Long idUsuario){
		try{
			usuarioService.deleteUsuario(idUsuario);
			pesquisarUsuario();
		}catch(NegocioException ne){
			Mensagens.addError(ne.getMessage());
		}
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

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	 
	public List<EstadosBrasilEnum> getEstados() {
		if (estados == null) {
			estados = new ArrayList<EstadosBrasilEnum>();
		}
		
		return estados;
	}

	public void setEstados(List<EstadosBrasilEnum> estados) {
		this.estados = estados;
	}

	public List<SexoEnum> getSexos() {
		if (sexos == null) {
			sexos = new ArrayList<SexoEnum>();
		}
		
		return sexos;
	}

	public void setSexos(List<SexoEnum> sexos) {
		this.sexos = sexos;
	}

	public List<StatusEnum> getStatuss() {
		
		if (statuss == null) {
			statuss = new ArrayList<StatusEnum>();
		}
		
		return statuss;
	}

	public void setStatuss(List<StatusEnum> statuss) {
		this.statuss = statuss;
	}
		
}