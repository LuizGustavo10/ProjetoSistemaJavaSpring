package com.dev.loja.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long>{
	
	
}
