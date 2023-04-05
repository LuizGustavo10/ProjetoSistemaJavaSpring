package com.dev.loja.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Cidade;
import com.dev.loja.repositorio.CidadeRepositorio;
import com.dev.loja.repositorio.EstadoRepositorio;

@Controller
public class CidadeControle {
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	@GetMapping("/administrativo/cidades/cadastrar") //vai ser editado e ser repassado para a visão que vai ser rederizada, url de chamada
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro"); //pagina que queremos buscar
		mv.addObject("listaEstados", estadoRepositorio.findAll());
		mv.addObject("cidade",cidade);
		return mv;
	}
	
	@GetMapping("/administrativo/cidades/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/cidades/lista");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/cidades/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepositorio.findById(id);
		return cadastrar(cidade.get());
	}
	
	@GetMapping("/administrativo/cidades/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepositorio.findById(id); //evita casos de ter nulo
		cidadeRepositorio.delete(cidade.get());
		return listar();
		
	}
	
	@PostMapping("/administrativo/cidades/salvar")
	public ModelAndView salvar(Cidade cidade, BindingResult result) { //vamos trabalhar com validações anotação @Valid  não pega
		
		//se tiver erro vai para a propria pagina de formulario
		if(result.hasErrors()) {
			return cadastrar(cidade);
		}
		
		cidadeRepositorio.saveAndFlush(cidade);
		return cadastrar(new Cidade());
	}
}
