package com.dev.loja.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Entrada;
import com.dev.loja.modelos.ItemEntrada;
import com.dev.loja.modelos.Produto;
import com.dev.loja.modelos.ItemEntrada;
import com.dev.loja.repositorio.ItemEntradaRepositorio;
import com.dev.loja.repositorio.ProdutoRepositorio;

import javassist.expr.NewArray;

import com.dev.loja.repositorio.EntradaRepositorio;
import com.dev.loja.repositorio.FuncionarioRepositorio;

@Controller
public class EntradaProdutoControle {
	
	@Autowired
	private EntradaRepositorio entradaRepositorio;
	@Autowired
	private ItemEntradaRepositorio itemEntradaRepositorio;
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	private List<ItemEntrada> listaItemEntrada = new ArrayList<ItemEntrada>();
	
	@GetMapping("/administrativo/entrada/cadastrar") 
	public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada) {
		ModelAndView mv = new ModelAndView("administrativo/entrada/cadastro");
		mv.addObject("entrada",entrada);
		mv.addObject("listaItemEntrada", this.listaItemEntrada);
		mv.addObject("itemEntrada", itemEntrada);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}
	
//	@GetMapping("/administrativo/estados/listar")
//	public ModelAndView listar() {
//		ModelAndView mv = new ModelAndView("administrativo/estados/lista");
//		mv.addObject("listaEstados", estadoRepositorio.findAll());
//		return mv;
//	}
//	
//	@GetMapping("/administrativo/estados/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") Long id) {
//		Optional<Estado> estado = estadoRepositorio.findById(id);
//		return cadastrar(estado.get());
//	}
//	
//	@GetMapping("/administrativo/estados/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") Long id) {
//		Optional<Estado> estado = estadoRepositorio.findById(id);
//		estadoRepositorio.delete(estado.get());
//		return listar();
//		
//	}
	
	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada) { 
		
		if(acao.equals("itens")) {
			this.listaItemEntrada.add(itemEntrada);
		}else if(acao.equals("salvar")){
			entradaRepositorio.saveAndFlush(entrada);
			
			for(ItemEntrada it : listaItemEntrada) {
				it.setEntrada(entrada);
				itemEntradaRepositorio.saveAndFlush(it);
				
				Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				produtoRepositorio.saveAndFlush(produto);
				
				this.listaItemEntrada = new ArrayList<>();
			}
			return cadastrar(new Entrada(), new ItemEntrada());
			
		}
		
		System.out.println(listaItemEntrada.size());
		return cadastrar(entrada, new ItemEntrada());
	}

	public List<ItemEntrada> getListaItemEntrada() {
		return listaItemEntrada;
	}

	public void setListaItemEntrada(List<ItemEntrada> listaItemEntrada) {
		this.listaItemEntrada = listaItemEntrada;
	}


	
}
