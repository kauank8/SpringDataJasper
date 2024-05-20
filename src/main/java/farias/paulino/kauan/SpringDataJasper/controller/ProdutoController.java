package farias.paulino.kauan.SpringDataJasper.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import farias.paulino.kauan.SpringDataJasper.model.Produto;
import farias.paulino.kauan.SpringDataJasper.repository.IProdutoRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class ProdutoController {

	@Autowired
	IProdutoRepository pRep;

	@Autowired
	DataSource ds;

	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.GET)
	public ModelAndView produtoGet(ModelMap model) {
		return new ModelAndView("produto");
	}

	@RequestMapping(name = "produto", value = "/produto", method = RequestMethod.POST)
	public ModelAndView produtoPost(ModelMap model, @RequestParam Map<String, String> param) {
		String cmd = param.get("botao");
		String codigo = param.get("codigo");
		String nome = param.get("nome");
		String valor_unitario = param.get("valor_unitario");
		String qtd_estoque = param.get("qtd_estoque");

		// Retorno
		List<Produto> produtos = new ArrayList<>();
		Produto produto = new Produto();
		String erro = "";
		String saida = "";

		if (!cmd.contains("Listar") && !cmd.contains("Verificar") && !codigo.trim().isEmpty()) {
			produto.setCodigo(Integer.parseInt(codigo));
		}
		if (cmd.equalsIgnoreCase("Cadastrar") || cmd.equalsIgnoreCase("Alterar") && !nome.trim().isEmpty()
				&& !valor_unitario.trim().isEmpty() && !qtd_estoque.trim().isEmpty()) {
			produto.setNome(nome);
			produto.setValor_unitario(Double.parseDouble(valor_unitario));
			produto.setQtd_estoque(Integer.parseInt(qtd_estoque));
		}

		try {
			if (cmd.equalsIgnoreCase("Cadastrar")) {
				pRep.save(produto);
				produto = null;
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				pRep.save(produto);
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				pRep.deleteById(produto.getCodigo());
				produto = null;
			}
			if (cmd.equalsIgnoreCase("BUSCAR")) {
				produto = pRep.findById(produto.getCodigo()).orElse(new Produto());
			}
			if (cmd.equalsIgnoreCase("LISTAR")) {
				produtos = pRep.findAll();
			}
			if (cmd.equalsIgnoreCase("VERIFICAR")) {
				String valor = param.get("valor");
				saida = "Produtos abaixo no estoque: " + pRep.fn_abaixoEstoque(Integer.parseInt(valor));
			}

		} finally {
			model.addAttribute("erro", erro);
			model.addAttribute("saida", saida);
			model.addAttribute("produto", produto);
			model.addAttribute("produtos", produtos);
		}

		return new ModelAndView("produto");
	}

	@RequestMapping(name = "produtoRelatorio", value = "/produtoRelatorio", method = RequestMethod.POST)
	public ResponseEntity produtoRelatorioPost(@RequestParam Map<String, String> params) {
		String erro = "";
		String valor = params.get("valor");

		// Definindo os elementos que serão passas como parâmetros para o Jasper
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("valor", valor);

		byte[] bytes = null;

		// Inicializando elementos do response
		InputStreamResource resource = null;
		HttpStatus status = null;
		HttpHeaders header = new HttpHeaders();

		try {
			Connection conn = DataSourceUtils.getConnection(ds);
			File arquivo = ResourceUtils.getFile("classpath:reports/RelatorioProduto.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(arquivo.getAbsolutePath());
			bytes = JasperRunManager.runReportToPdf(report, param, conn);
		} catch (FileNotFoundException | JRException e) {
			e.printStackTrace();
			erro = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		} finally {
			if (erro.equals("")) {
				InputStream inputStream = new ByteArrayInputStream(bytes);
				resource = new InputStreamResource(inputStream);
				header.setContentLength(bytes.length);
				header.setContentType(MediaType.APPLICATION_PDF);
				status = HttpStatus.OK;
			}
		}

		return new ResponseEntity(resource, header, status);
	}

}
