//jsf tem um ciclo com 6 fases, quando ele passa por elas, a ultima coisa que ele faz e carregar alguma na classe.e chamado pelo @PostConstruct

package br.com.sgc.util.spring;

import java.io.Serializable;

import org.springframework.stereotype.Component;


@Component("postInit") 
public class PostInit implements Serializable {

	private static final long serialVersionUID = -9034170868469193555L;


	public void init() {

	}

}
