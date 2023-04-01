package com.dev.loja.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Funcionario;
import com.dev.loja.repositorio.FuncionarioRepositorio;

@Controller
public class FuncionarioControle {
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@GetMapping("/administrativo/funcionarios/cadastrar") //vai ser editado e ser repassado para a visão que vai ser rederizada
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("funcionario",funcionario);
		return mv;
	}
	
	@GetMapping("/administrativo/funcionarios/listar")
	public String listar() {
		return "administrativo/funcionarios/lista";
	}
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView salvar(Funcionario funcionario, BindingResult result) { //vamos trabalhar com validações anotação @Valid  não pega
		
		//se tiver erro vai para a propria pagina de formulario
		if(result.hasErrors()) {
			return cadastrar(funcionario);
		}
		
		funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastrar(new Funcionario());
	}
}
