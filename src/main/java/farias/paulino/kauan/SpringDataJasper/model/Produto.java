package farias.paulino.kauan.SpringDataJasper.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Produto")
@NamedNativeQuery(
		name = "Produto.fn_abaixoEstoque",
		query = "Select dbo.fn_abaixoEstoque(?1)",
		resultClass = Integer.class
		)
public class Produto {

	@Id
	@Column(name = "codigo", nullable = false)
	private int codigo;
	
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;
	
	@Column(name = "valor_unitario", nullable = false)
	private double valor_unitario;
	
	@Column(name = "qtd_estoque", nullable = false)
	private int qtd_estoque;
}
