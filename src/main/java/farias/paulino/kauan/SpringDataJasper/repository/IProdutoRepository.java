package farias.paulino.kauan.SpringDataJasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import farias.paulino.kauan.SpringDataJasper.model.Produto;

public interface IProdutoRepository extends JpaRepository<Produto, Integer> {
	Integer fn_abaixoEstoque(int valor);
}
