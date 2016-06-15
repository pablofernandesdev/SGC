package br.com.sgc.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Cliente;
import br.com.sgc.domain.Empresa;
import br.com.sgc.domain.Produto;
import br.com.sgc.domain.SaidaProduto;
import br.com.sgc.domain.Usuario;
import br.com.sgc.domain.Venda;
import br.com.sgc.service.ClienteService;
import br.com.sgc.service.EmpresaService;
import br.com.sgc.service.ProdutoService;
import br.com.sgc.service.UsuarioService;
import br.com.sgc.service.VendaService;
import br.com.sgc.util.Mensagens;
import br.com.sgc.util.MensagensModal;
import br.com.sgc.util.SgcUtil;

@Component("manterVendaVisao")
@Scope("request")
public class ManterVendaVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_VENDA = "/visao/manterVenda/pesquisarVenda";
	private static final String FW_REGISTRAR_VENDA = "/visao/manterVenda/registrarVenda";
	private static final String FW_EDITAR_VENDA = "/visao/manterVenda/editarVenda";
	private static final String FW_DETALHAR_VENDA = "/visao/manterVenda/detalharVenda";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Venda venda;
	private List<Venda> listaVendas;  
	
	private List<SaidaProduto> listaSaidaProduto;
	private List<Produto> listaProdutosModal;
	private List<Produto> listaProdutos;
	
	private List<Cliente> listaClientes;
	private List<Cliente> listaClientesModal;
	
	private List<Usuario> listaUsuarios;
	private List<Usuario> listaUsuariosModal;
	
	private List<Empresa> listaEmpresas;
	private List<Empresa> listaEmpresasModal;

	private boolean abrirModalPesquisarProdutos;
	private boolean abrirModalPesquisarEmpresas;
	private boolean abrirModalPesquisarClientes;
	private boolean abrirModalPesquisarUsuarios;
	private boolean abrirModalAlertaQtd;
	private boolean abrirModalConfirmacao;
	
	private Double totalGeral;

	private Produto produtoConsultaModal;
	private Empresa empresaConsultaModal;
	private Cliente clienteConsultaModal;
	private Usuario usuarioConsultaModal;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioSessao usuarioSessao;
	
	@Transient
	private String totalGeralComMascara;
	
	public String irTelaPesquisarVenda(){
		return FW_PESQUISAR_VENDA;
	}
	
	public String irTelaRegistrarVenda(){
		
		getVenda().setTotal(0D);
		setListaUsuarios(null);
		setTotalGeral(0D);
		getVenda().setDataVenda(new Date());
		
		//adiciona o usuário que está logado na lista de usuário do registro de venda
		getListaUsuarios().add(usuarioSessao.getInstance().getUsuario()); 
		
		//adiciona o nome da empresa que esta cadastrado no sistema
		setListaEmpresas(empresaService.findEmpresas(null));
		
		return FW_REGISTRAR_VENDA;
	}
	
	public String irTelaEditarVenda(Venda venda){
		setVenda(venda);
		return FW_EDITAR_VENDA;
	}
	
	public void pesquisarVenda(){
		setListaVendas(vendaService.findVendas(getVenda()));
		if(getListaVendas().isEmpty()){
			Mensagens.addError("MSG_E005");
		}
	}
	
	public String saveVenda(){
		if(validarTelaVenda()){

			getVenda().setTotal(getTotalGeral());
			getVenda().setUsuario(getListaUsuarios().get(0));
			getVenda().setCliente(getListaClientes().get(0));
			getVenda().setEmpresa(getListaEmpresas().get(0));
		
			vendaService.saveVenda(getVenda(), getListaSaidaProduto());
			setVenda(new Venda());
			pesquisarVenda();
			
			return FW_PESQUISAR_VENDA;
		}else{
			return FW_REGISTRAR_VENDA;
		}
	}
	
	public String voltarPesquisaVenda(){
		return FW_PESQUISAR_VENDA;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	private boolean validarTelaVenda(){
		boolean retorno = true;
		
		if(getVenda().getDataVenda() == null){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.data"));
			retorno = false;
		}
		
		if(getListaClientes() == null || getListaClientes().isEmpty()){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.cli"));
			retorno = false;
		}
		
		if(getListaSaidaProduto() == null || getListaSaidaProduto().isEmpty()){
			Mensagens.addError("MSG_E007");
			retorno = false;
		}
		
		/*Lista da tela de registrar venda*/
		List<Boolean> listResultFor = new ArrayList<Boolean>();
		for(SaidaProduto saidaProduto: getListaSaidaProduto()){
			listResultFor.add(validarQtdProduto(saidaProduto));
		}
		
		if(!listResultFor.isEmpty()){
			if(listResultFor.contains(Boolean.FALSE)){
				retorno = false;
			}
		}
		
		return retorno;
	}
	
	public String updateVenda(){  
		if(validarTelaVenda()){
			vendaService.updateVenda(getVenda());
			
			setVenda(new Venda());
			
			pesquisarVenda();
			
			return FW_PESQUISAR_VENDA;
		}else{
			return FW_REGISTRAR_VENDA;
		}
		
	}
	
	public void deleteVenda(Long idVenda){
		vendaService.deleteVenda(idVenda);
		pesquisarVenda();
	}
	
	//Produto
	
	public void removerProduto(SaidaProduto saidaProduto){
		getListaSaidaProduto().remove(saidaProduto);
		
		calcular();
	}
	
	public void adicionarProduto(Produto produto){
		
		if((!isPossui(produto)) && (validarInclusaoProduto(produto))){
			SaidaProduto saidaProduto = new SaidaProduto();
			saidaProduto.setProduto(produto);	
			saidaProduto.setPrecoVendaSaida(produto.getPrecoVenda()); //está setando o preço de venda atual e salvando no preço de saída
			getListaSaidaProduto().add(saidaProduto);
		}
		
		calcular();
		
	}
	
	private boolean isPossui(Produto produto){
		
		for (SaidaProduto produtoLista : getListaSaidaProduto()) {
			if (produtoLista.getProduto().getIdProduto()== produto.getIdProduto()){
				MensagensModal.addError("MSG_A003");
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean validarInclusaoCliente (Cliente cliente){
		if (getListaClientesModal().isEmpty()) {
			MensagensModal.addError("MSG_A018");
			return false;
		}
		return true;
	}
	
	public boolean validarInclusaoProduto(Produto produto) {

		
			if (produto.getQuantidade() == 0) {
				MensagensModal.addError("MSG_A007");
				return false;
			}
		
		return true;

	}
	
	public void abrirModalPesquisarProduto (){
		if (getListaClientesModal().isEmpty()) {
			Mensagens.addError("MSG_A018");
			setAbrirModalPesquisarProdutos(Boolean.FALSE);
		}else{
			setAbrirModalPesquisarProdutos(Boolean.TRUE);
		}
	}
	
	public void fecharModalPesquisarProduto (){
		setAbrirModalPesquisarProdutos(Boolean.FALSE);
	}
	
	public void abrirModalAlertaQtd (){
		setAbrirModalAlertaQtd(Boolean.TRUE);
	}
	
	public void fecharModalAlertaQtd (){
		setAbrirModalAlertaQtd(Boolean.FALSE);
	}
	
	public void abrirModalConfirmacao (){
		setAbrirModalConfirmacao(Boolean.TRUE);
	}
	
	public void fecharModalConfirmacao (){
		setAbrirModalConfirmacao(Boolean.FALSE);
	}
	
	public void pesquisarProduto(){
		setListaProdutosModal(produtoService.findProdutos(getProdutoConsultaModal()));
		if (getListaProdutosModal().isEmpty()) {
			MensagensModal.addError("MSG_A019", MensagensModal.getValueProperties("label.prod"));
		}
	}

	//Cliente
	
	public void removerCliente(Cliente cliente){
		getListaClientes().remove(cliente);
	}
	
	public void adicionarCliente(Cliente cliente){
		getListaClientes().add(cliente);
		fecharModalPesquisarCliente();
		
	}
	
	public void abrirModalPesquisarCliente (){
		setAbrirModalPesquisarClientes(Boolean.TRUE);
	}
	
	public void fecharModalPesquisarCliente (){
		setAbrirModalPesquisarClientes(Boolean.FALSE);
	}

	public void pesquisarCliente(){
		setListaClientesModal(clienteService.findClientes(getClienteConsultaModal()));
		if (getListaClientesModal().isEmpty()){
			MensagensModal.addError("MSG_A017");
		}
		
	}

	// Usuario

	public void removerUsuario(Usuario usuario) {
		getListaUsuarios().remove(usuario);
	}

	public void adicionarUsuario(Usuario usuario) {
		getListaUsuarios().add(usuario);

	}

	public void abrirModalPesquisarUsuario() {
		setAbrirModalPesquisarUsuarios(Boolean.TRUE);
	}

	public void fecharModalPesquisarUsuario() {
		setAbrirModalPesquisarUsuarios(Boolean.FALSE);
	}

	public void pesquisarUsuario() {
		setListaUsuariosModal(usuarioService
				.findUsuarios(getUsuarioConsultaModal()));
	}

	// Empresa

	public void removerEmpresa(Empresa empresa) {
		getListaEmpresas().remove(empresa);
	}

	public void adicionarEmpresa(Empresa empresa) {
		getListaEmpresas().add(empresa);

	}

	public void abrirModalPesquisarEmpresa() {
		setAbrirModalPesquisarEmpresas(Boolean.TRUE);
	}

	public void fecharModalPesquisarEmpresa() {
		setAbrirModalPesquisarEmpresas(Boolean.FALSE);
	}

	public void pesquisarEmpresa() {
		setListaEmpresasModal(empresaService
				.findEmpresas(getEmpresaConsultaModal()));
	}

	//
	
	public void multiplicarPrecoQtd(SaidaProduto saidaProduto){
		
		if(validarQtdProduto(saidaProduto)){
			multiplicar(saidaProduto);
		}
		
		calcular();
	}
	
	private void multiplicar(SaidaProduto saidaProduto){
		
		Double total = new Double(saidaProduto.getQuantidadeSaida()) * new Double(saidaProduto.getPrecoVendaSaida());
		saidaProduto.setTotalSomado(total);
		
		calcular();
	}
	
	private void calcular(){
		Double total = 0d;
		for(SaidaProduto saida : getListaSaidaProduto()){
			total = total + saida.getTotalSomado();
		}
		setTotalGeral(total);
	}
	
	private boolean validarQtdProduto(SaidaProduto saidaProduto){
		boolean retorno = true;
		if (saidaProduto.getQuantidadeSaida() == null || saidaProduto.getQuantidadeSaida() <=0){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.quantidade"));
			return false;
		}else
		
		if(saidaProduto.getQuantidadeSaida() > saidaProduto.getProduto().getQuantidade()){
			Mensagens.addError("MSG_A001");
			return false;
		}
			
		return retorno;
	}
	
	public String detalharVenda (Long idVenda){
		
		setListaSaidaProduto(vendaService.findSaidaProdutoByIdVenda(idVenda));
		multiplicaPrecoQtd();
		
		return FW_DETALHAR_VENDA;
	}
	
	private void  multiplicaPrecoQtd(){
		for(SaidaProduto saida: getListaSaidaProduto()){
			multiplicar(saida);
		}
	}
	
	public Venda getVenda() {
		if(venda == null){
			venda = new Venda();
		}
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	public List<Venda> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Venda> listaVendas) {
		this.listaVendas = listaVendas;
	}
	
	//Produtos

	public List<SaidaProduto> getListaSaidaProduto() {
		if(listaSaidaProduto == null){
			listaSaidaProduto = new ArrayList<SaidaProduto>(); //lazy sen mapeamento
		}
		return listaSaidaProduto;
	}

	public void setListaSaidaProduto(List<SaidaProduto> listaSaidaProduto) {
		this.listaSaidaProduto = listaSaidaProduto;
	}
	//
	
	public List<Produto> getListaProdutos() {
		if(listaProdutos == null){
			listaProdutos = new ArrayList<Produto>(); //lazy sen mapeamento
		}
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	//

	public List<Produto> getListaProdutosModal() {
		if(listaProdutosModal == null){
			listaProdutosModal = new ArrayList<Produto>();
		}
		return listaProdutosModal;
	}

	public void setListaProdutosModal(List<Produto> listaProdutosModal) {
		this.listaProdutosModal = listaProdutosModal;
	}

	public boolean isAbrirModalPesquisarProdutos() {
		return abrirModalPesquisarProdutos;
	}

	public void setAbrirModalPesquisarProdutos(boolean abrirModalPesquisarProdutos) {
		this.abrirModalPesquisarProdutos = abrirModalPesquisarProdutos;
	}
	
	public boolean isAbrirModalAlertaQtd() {
		return abrirModalAlertaQtd;
	}

	public void setAbrirModalAlertaQtd(boolean abrirModalAlertaQtd) {
		this.abrirModalAlertaQtd = abrirModalAlertaQtd;
	}
	
	public boolean isAbrirModalConfirmacao() {
		return abrirModalConfirmacao;
	}

	public void setAbrirModalConfirmacao(boolean abrirModalConfirmacao) {
		this.abrirModalConfirmacao = abrirModalConfirmacao;
	}
	
	public Produto getProdutoConsultaModal() {
		if(produtoConsultaModal == null){
			produtoConsultaModal = new Produto();
		}
		return produtoConsultaModal;
	}

	public void setProdutoConsultaModal(Produto produtoConsultaModal) {
		this.produtoConsultaModal = produtoConsultaModal;
	}

	//Cliente
	
	public List<Cliente> getListaClientes() {
		if(listaClientes == null){
			listaClientes = new ArrayList<Cliente>(); //lazy sen mapeamento
		}
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Cliente> getListaClientesModal() {
		if(listaClientesModal == null){
			listaClientesModal = new ArrayList<Cliente>();
		}
		return listaClientesModal;
	}

	public void setListaClientesModal(List<Cliente> listaClientesModal) {
		this.listaClientesModal = listaClientesModal;
	}

	public boolean isAbrirModalPesquisarClientes() {
		return abrirModalPesquisarClientes;
	}

	public void setAbrirModalPesquisarClientes(boolean abrirModalPesquisarClientes) {
		this.abrirModalPesquisarClientes = abrirModalPesquisarClientes;
	}

	public Cliente getClienteConsultaModal() {
		if(clienteConsultaModal == null){
			clienteConsultaModal = new Cliente();
		}
		return clienteConsultaModal;
	}

	public void setClienteConsultaModal(Cliente clienteConsultaModal) {
		this.clienteConsultaModal = clienteConsultaModal;
	}
	
	//Usuario
	
		public List<Usuario> getListaUsuarios() {
			if(listaUsuarios == null){
				listaUsuarios = new ArrayList<Usuario>(); //lazy sen mapeamento
			}
			return listaUsuarios;
		}

		public void setListaUsuarios(List<Usuario> listaUsuarios) {
			this.listaUsuarios = listaUsuarios;
		}

		public List<Usuario> getListaUsuariosModal() {
			if(listaUsuariosModal == null){
				listaUsuariosModal = new ArrayList<Usuario>();
			}
			return listaUsuariosModal;
		}

		public void setListaUsuariosModal(List<Usuario> listaUsuariosModal) {
			this.listaUsuariosModal = listaUsuariosModal;
		}

		public boolean isAbrirModalPesquisarUsuarios() {
			return abrirModalPesquisarUsuarios;
		}

		public void setAbrirModalPesquisarUsuarios(boolean abrirModalPesquisarUsuarios) {
			this.abrirModalPesquisarUsuarios = abrirModalPesquisarUsuarios;
		}

		public Usuario getUsuarioConsultaModal() {
			if(usuarioConsultaModal == null){
				usuarioConsultaModal = new Usuario();
			}
			return usuarioConsultaModal;
		}

		public void setUsuarioConsultaModal(Usuario usuarioConsultaModal) {
			this.usuarioConsultaModal = usuarioConsultaModal;
		}
		
		// Empresa
	
		public List<Empresa> getListaEmpresas() {
			if (listaEmpresas == null) {
				listaEmpresas = new ArrayList<Empresa>(); // lazy sen mapeamento
			}
			return listaEmpresas;
		}
	
		public void setListaEmpresas(List<Empresa> listaEmpresas) {
			this.listaEmpresas = listaEmpresas;
		}
	
		public List<Empresa> getListaEmpresasModal() {
			if (listaEmpresasModal == null) {
				listaEmpresasModal = new ArrayList<Empresa>();
			}
			return listaEmpresasModal;
		}
	
		public void setListaEmpresasModal(List<Empresa> listaEmpresasModal) {
			this.listaEmpresasModal = listaEmpresasModal;
		}
	
		public boolean isAbrirModalPesquisarEmpresas() {
			return abrirModalPesquisarEmpresas;
		}
	
		public void setAbrirModalPesquisarEmpresas(
				boolean abrirModalPesquisarEmpresas) {
			this.abrirModalPesquisarEmpresas = abrirModalPesquisarEmpresas;
		}
	
		public Empresa getEmpresaConsultaModal() {
			if (empresaConsultaModal == null) {
				empresaConsultaModal = new Empresa();
			}
			return empresaConsultaModal;
		}
	
		public void setEmpresaConsultaModal(Empresa empresaConsultaModal) {
			this.empresaConsultaModal = empresaConsultaModal;
		}
	
		//

		public Double getTotalGeral() {
			return totalGeral;
		}

		public void setTotalGeral(Double totalGeral) {
			this.totalGeral = totalGeral;
		}
		
		public String getTotalGeralComMascara() {
			return SgcUtil.formataMoeda(getTotalGeral());
		}

		public void setTotalGeralComMascara(String totalGeralComMascara) {
			this.totalGeralComMascara = totalGeralComMascara;
		}
	
}
