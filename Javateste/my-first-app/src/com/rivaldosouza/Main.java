package com.rivaldosouza;

import com.rivaldosouza.entidades.Docente;
import com.rivaldosouza.entidades.Entidade;
import com.rivaldosouza.services.EntidadeService;
import com.rivaldosouza.services.EntidadeServiceImpl;

public class Main {

	private static String mensagem;
	
	public static void main(String[] args) {
		//code
		EntidadeService service = 	new EntidadeServiceImpl();
		//service.altaEntidade(new Entidade());
		Entidade entidade	= new Entidade();
		service.altaEntidade(entidade);
		
		extracted(mensagem= "Olá, como está?");
		
		Docente docente = null;
	}
	private static void extracted(String mensagem) {
		System.out.println(mensagem);
//		System.out.println(mensagem);
	}
	
}
