package com.dev.loja.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.loja.modelos.ItemEntrada;

public interface ItemEntradaRepositorio extends JpaRepository<ItemEntrada, Long>{
	
	
}
