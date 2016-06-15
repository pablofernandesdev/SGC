package br.com.sgc.bean;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.sgc.domain.Categoria;
import br.com.sgc.exception.NegocioException;
import br.com.sgc.service.CategoriaService;
import br.com.sgc.util.Mensagens;

@Component("manterCategoriaVisao")
@Scope("request")
public class ManterCategoriaVisao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String FW_PESQUISAR_CATEGORIA = "/visao/manterCategoria/pesquisarCategoria";
	private static final String FW_CADASTRAR_CATEGORIA = "/visao/manterCategoria/cadastrarCategoria";
	private static final String FW_EDITAR_CATEGORIA = "/visao/manterCategoria/editarCategoria";
	private static final String FW_TELA_INICIAL = "/visao/principal";
	
	private Categoria categoria;
	private List<Categoria> listaCategorias;  
	
	@Autowired
	private CategoriaService categoriaService;
	
	public String irTelaPesquisarCategoria(){
		return FW_PESQUISAR_CATEGORIA;
	}
	
	public String irTelaCadastrarCategoria(){
		return FW_CADASTRAR_CATEGORIA;
	}
	
	public String irTelaEditarCategoria(Categoria categoria){
		setCategoria(categoria);
		return FW_EDITAR_CATEGORIA;
	}
	
	public void pesquisarCategoria() {

		setListaCategorias(categoriaService.findCategorias(getCategoria()));
		if (getListaCategorias().isEmpty()) {
			Mensagens.addError("MSG_E005");
		}
	}

	public String saveCategoria(){
		if(validarTelaCategoria()){
			categoriaService.saveCategoria(getCategoria());
			setCategoria(new Categoria());
			
			pesquisarCategoria();
			
			return FW_PESQUISAR_CATEGORIA;
		}else{
			return FW_CADASTRAR_CATEGORIA;
		}
	}
	
	public String voltarPesquisaCategoria(){
		return FW_PESQUISAR_CATEGORIA;
	}
	
	public String voltarTelaInicial(){
		return FW_TELA_INICIAL;
	}
	
	private boolean validarTelaCategoria(){
		boolean retorno = true;
		if(getCategoria().getNome() == null || getCategoria().getNome().trim().equals("")){
			Mensagens.addError("MSG_E001", Mensagens.getValueProperties("label.nome.categoria"));
			retorno = false;
		}
				
		return retorno;
	}
	
	public String updateCategoria(){
		
		
		if(validarTelaCategoria()){
			categoriaService.updateCategoria(getCategoria());
			
			setCategoria(new Categoria());
			
			pesquisarCategoria();
			
			return FW_PESQUISAR_CATEGORIA;
		}else{
			return FW_EDITAR_CATEGORIA;
		}
		
	}
	
	public void deleteCategoria(Long idCategoria){
		try{
			categoriaService.deleteCategoria(idCategoria);
			pesquisarCategoria();
		}catch(NegocioException ne){
			Mensagens.addError(ne.getMessage());
		}
	}

	public Categoria getCategoria() {
		if(categoria == null){
			categoria = new Categoria();
		}
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
		
}