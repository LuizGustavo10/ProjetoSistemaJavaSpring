package com.dev.loja.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.Entrada;

public interface EntradaRepositorio extends JpaRepository<Entrada, Long>{
	
	
}
