package com.rivaldosouza.services;

import com.rivaldosouza.entidades.Entidade;

public class EntidadeServiceImpl implements EntidadeService {
	
	@Override
	public void altaEntidade(Entidade entidade) {
		System.out.println("Entidade dada alta ");
	}
	
	private void searchEntidade() {
		System.out.println("Entidade encontrado");
	}
	
	private void updateEntidade() {
		System.out.println("Entidade modificado");
	}
	
	private void deleteEntidade() {
		System.out.println("Entidade eliminado");
	}
}
