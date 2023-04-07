package com.dev.loja.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Permissao;
import com.dev.loja.repositorio.PermissaoRepositorio;
import com.dev.loja.repositorio.EstadoRepositorio;
import com.dev.loja.repositorio.FuncionarioRepositorio;
import com.dev.loja.repositorio.PapelRepositorio;

@Controller
public class PermissaoControle {
	
	@Autowired
	private PermissaoRepositorio permissaoRepositorio;
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@GetMapping("/administrativo/permissoes/cadastrar") //vai ser editado e ser repassado para a visão que vai ser rederizada, url de chamada
	public ModelAndView cadastrar(Permissao permissao) {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/cadastro"); //pagina que queremos buscar
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaPapeis", papelRepositorio.findAll());
		mv.addObject("permissao",permissao);
		return mv;
	}
	
	@GetMapping("/administrativo/permissoes/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/permissoes/lista");
		mv.addObject("listaPermissoes", permissaoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/permissoes/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepositorio.findById(id);
		return cadastrar(permissao.get());
	}
	
	@GetMapping("/administrativo/permissoes/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Permissao> permissao = permissaoRepositorio.findById(id); //evita casos de ter nulo
		permissaoRepositorio.delete(permissao.get());
		return listar();
		
	}
	
	@PostMapping("/administrativo/permissoes/salvar")
	public ModelAndView salvar(Permissao permissao, BindingResult result) { //vamos trabalhar com validações anotação @Valid  não pega
		
		//se tiver erro vai para a propria pagina de formulario
		if(result.hasErrors()) {
			return cadastrar(permissao);
		}
		
		permissaoRepositorio.saveAndFlush(permissao);
		return cadastrar(new Permissao());
	}
}
