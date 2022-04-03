package com.evaluacion.java.bcp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evaluacion.java.bcp.model.db.Usuario;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
}
