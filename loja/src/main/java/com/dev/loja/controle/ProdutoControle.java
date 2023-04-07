package com.dev.loja.controle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dev.loja.modelos.Produto;
import com.dev.loja.repositorio.CidadeRepositorio;
import com.dev.loja.repositorio.EstadoRepositorio;
import com.dev.loja.repositorio.ProdutoRepositorio;

@Controller
public class ProdutoControle {
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	public static String caminhoImagens = "C:\\Users\\Luiz Gustavo\\Documents\\sub\\";

	
	@GetMapping("/administrativo/produtos/cadastrar") //vai ser editado e ser repassado para a visão que vai ser rederizada, url de chamada
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("administrativo/produtos/cadastro"); //pagina que queremos buscar
		mv.addObject("produto",produto);
		return mv;
	}
	
	@GetMapping("/administrativo/produtos/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		return cadastrar(produto.get());
	}
	
	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id); //evita casos de ter nulo
		produtoRepositorio.delete(produto.get());
		return listar();
		
	}
	
	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(Produto produto, BindingResult result, @RequestParam("file") MultipartFile arquivo) { //vamos trabalhar com validações anotação @Valid  não pega
		
		//se tiver erro vai para a propria pagina de formulario
		if(result.hasErrors()) {
			return cadastrar(produto);
		}
		produtoRepositorio.saveAndFlush(produto);
		
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes(); //pega po conteudo no array de bats
				Path caminho = Paths.get( caminhoImagens+String.valueOf(produto.getId()) + arquivo.getOriginalFilename()); //caminho_idProduto+nomedaimagem
				Files.write(caminho, bytes);

				produto.setNomeImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename()); //salva no banco
				
				produtoRepositorio.saveAndFlush(produto);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return cadastrar(new Produto());
	}
	
	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
		File imagemArquivo = new File(caminhoImagens+imagem);
		
		if(imagem != null || imagem.trim().length() > 0) {

				return Files.readAllBytes(imagemArquivo.toPath());
	
		}else {
			return null;
		}
	}
	
	
	
	
}
