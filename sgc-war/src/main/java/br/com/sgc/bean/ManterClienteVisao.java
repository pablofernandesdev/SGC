package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Cliente;
import br.com.sgc.domain.EstadosBrasilEnum;
import br.com.sgc.domain.SexoEnum;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.ClienteService;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.SgcUtil;
import br.com.sgc.validators.Validacoes;

@Component("manterClienteVisao")
@Scope("request")
public class ManterClienteVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_CLIENTE = "/visao/manterCliente/pesquisarCliente";
	private static final String FW_CADASTRAR_CLIENTE = "/visao/manterCliente/cadastrarCliente";
	private static final String FW_EDITAR_CLIENTE = "/visao/manterCliente/editarCliente";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Cliente cliente;
	private List<Cliente> listaClientes;
	private List<EstadosBrasilEnum> estados;
	private List<SexoEnum> sexos;
	
	@Autowired
	private ClienteService clienteService;
	
	public String irTelaPesquisarCliente(){
		return FW_PESQUISAR_CLIENTE;
	}
	
	public String irTelaCadastrarCliente(){
		
		carregarListaEstados();
		carregarSexos();
		return FW_CADASTRAR_CLIENTE;
	}
	
	private void carregarListaEstados(){
		for(EstadosBrasilEnum estado:  EstadosBrasilEnum.values()){
			getEstados().add(estado);
		}
	}
	
	private void carregarSexos(){
		setSexos(null); //limpando antes de adicionar os ja existentes
		for(SexoEnum sexo:  SexoEnum.values()){
			getSexos().add(sexo);
		}
	}
	
	public String irTelaEditarCliente(Cliente cliente){
		carregarListaEstados();
		carregarSexos();
		setCliente(cliente);
		return FW_EDITAR_CLIENTE;
	}
	
	public void pesquisarCliente() {

		setListaClientes(clienteService.findClientes(getCliente()));
		if (getListaClientes().isEmpty()) {
			Mensagens.addError("MSG_E005");
		}

	}
	
	public String saveCliente(){
		if(validarTelaCliente(true)){
			removerMascaras();
			clienteService.saveCliente(getCliente());
			setCliente(new Cliente());
			
			pesquisarCliente();
			
			return FW_PESQUISAR_CLIENTE;
		}else{
			return FW_CADASTRAR_CLIENTE;
		}
	}
	
	private boolean validarTelaCliente(boolean isNovoCadastro){
		boolean retorno = true;
		if(getCliente().getNome() == null || getCliente().getNome().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.nome"));
			retorno = false;
		}
		
		if(getCliente().getBairro() == null || getCliente().getBairro().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.bairro"));
			retorno = false;
		}
		
		if(getCliente().getCep() == null || getCliente().getCep().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cep"));
			retorno = false;
		}
		
		if(getCliente().getCidade() == null || getCliente().getCidade().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cidade"));
			retorno = false;
		}
		
		if(getCliente().getComplemento() == null || getCliente().getComplemento().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.complemento"));
			retorno = false;
		}
		
		if(getCliente().getCpf() == null || getCliente().getCpf().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cpf"));
			retorno = false;
		}
		
		if(getCliente().getEmail() == null || getCliente().getEmail().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if(getCliente().getLogradouro() == null || getCliente().getLogradouro().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.logradouro"));
			retorno = false;
		}
		
		if(getCliente().getNascimento() == null){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.data.nascimento"));
			retorno = false;
		}
		
		if(getCliente().getSexo() == null || getCliente().getSexo().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.sexo"));
			retorno = false;
		}
		
		if(getCliente().getTelCel() == null || getCliente().getTelCel().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if(getCliente().getTelFixo() == null || getCliente().getTelFixo().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if(getCliente().getUf() == null || getCliente().getUf().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.uf"));
			retorno = false;
		}
		
		if((getCliente().getCpf().length()>0) && (Validacoes.validarCPF(getCliente().getCpf()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cpf"));
			retorno = false;
		}
		
		if ((getCliente().getNome().length()>0) && (Validacoes.validarNome(getCliente().getNome()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.nome"));
			retorno = false;
		}
		
		if ((getCliente().getEmail().length()>0) && (Validacoes.validEmail(getCliente().getEmail()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.email"));
			retorno = false;
		}
		
		if ((getCliente().getCep().length()>0) && (Validacoes.validarCep(getCliente().getCep()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.cep"));
			retorno = false;
		}
		
		if ((getCliente().getTelCel().length()>0) && (Validacoes.validarTelefone(getCliente().getTelCel()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telCel"));
			retorno = false;
		}
		
		if ((getCliente().getTelFixo().length()>0) && (Validacoes.validarTelefone(getCliente().getTelFixo()) == false)){
			Mensagens.addError("MSG_E002", Mensagens.getValueProperties("label.telFixo"));
			retorno = false;
		}
		
		if(isNovoCadastro){
			//verifica se o cpf informado já se encontra cadastrado no banco
			if(!getCliente().getCpf().trim().equals("")){
				
				String cpf = SgcUtil.removerMascara(getCliente().getCpf());
				
				Cliente cliente = clienteService.findClienteCpf(cpf);
				
				if(cliente != null){
					Mensagens.addError("MSG_A012", Mensagens.getValueProperties("label.cpf"));
					retorno = false;
				}
				
			}
			
		}
		
		return retorno;
	}
	
	private void removerMascaras(){
		getCliente().setCpf(SgcUtil.removerMascara(getCliente().getCpf()));
		getCliente().setTelCel(SgcUtil.removerMascara(getCliente().getTelCel()));
		getCliente().setTelFixo(SgcUtil.removerMascara(getCliente().getTelFixo()));
		getCliente().setCep(SgcUtil.removerMascara(getCliente().getCep()));
	}
	
	public String updateCliente(){
		if(validarTelaCliente(false)){
			removerMascaras();
			clienteService.updateCliente(getCliente());
			
			setCliente(new Cliente());
			
			pesquisarCliente();
			
			return FW_PESQUISAR_CLIENTE;
		}else{
			return FW_EDITAR_CLIENTE;
		}
		
	}
	
	// Um bloco “try” é chamado de bloco “protegido” porque, caso ocorra algum
	// problema com os comandos dentro do bloco, a execução desviará para os
	// blocos “catch” correspondentes.
	
	public void deleteCliente(Long idCliente){ 
		try{ // código a ser executado
			clienteService.deleteCliente(idCliente);
			pesquisarCliente();
		}catch(NegocioException ne){// tratamento da exceção
			Mensagens.addError(ne.getMessage());
		}
	}
	
	public String voltarPesquisaCliente(){
		return FW_PESQUISAR_CLIENTE;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}

	public Cliente getCliente() {
		if(cliente == null){
			cliente = new Cliente();
		}
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<EstadosBrasilEnum> getEstados() {
		if(estados == null){
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
	
}