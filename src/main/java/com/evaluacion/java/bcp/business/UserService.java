package com.evaluacion.java.bcp.business;

import com.evaluacion.java.bcp.model.db.Usuario;

public interface UserService {
	
	public Usuario findByUsername(String username);
	
}
