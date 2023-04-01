package com.dev.loja.controle;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FuncionarioControle {
	
	@GetMapping("/administrativo/funcionarios/cadastrar")
	public String cadastrar() {
		return "administrativo/funcionarios/cadastro";
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public String listar() {
		return "administrativo/funcionarios/lista";
	}
}
