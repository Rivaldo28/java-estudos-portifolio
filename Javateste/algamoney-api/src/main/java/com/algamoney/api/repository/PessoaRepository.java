package com.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
//	
//	@Query("select * from Pessoa p order by p.codigo desc")
//	public List<Pessoa> findAllPessoa();

}
