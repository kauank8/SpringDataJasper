<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href='<c:url value = "./resources/style1.css"/>'>
<title>Produto</title>
</head>
<body>
<div align="center" class="container">

		<form action="produto" method="post">
			<p class="title">
				<b>Produto</b>
			</p>
			<table>
				<tr>
					<td colspan="3">
						<input class="id_input_data" type="number" min="0" step="1" id="codigo" name="codigo" placeholder="Codigo"
						value='<c:out value="${produto.codigo }"></c:out>'>
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text"  id="nome" name="nome" placeholder="Nome"
						value='<c:out value="${produto.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text"  id="valor_unitario" name="valor_unitario" placeholder="valorUnitario"
						pattern="[0-9.,]*" value='<c:out value="${produto.valor_unitario }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="id_input_data" type="number" min="0" step="1" id="qtd_estoque" name="qtd_estoque" 
						placeholder="Quantidade Estoque"
						value='<c:out value="${produto.qtd_estoque }"></c:out>'>
					</td>
				</tr>
				
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Cadastrar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Alterar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Excluir">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Listar">
					</td>	
				</tr>
			</table>
		</form>
	</div>
	
	<br />
	<br />
	<br />
	<div align="center" class="container" >

		<form action="produto" method="post">
			<p class="title">
				<b>Consultar Quantidade de Produtos em Estoque Abaixo do Valor Selecionado</b>
			</p>
			<table>
				<tr>
					<td>
						<input class="id_input_data" type="number" min="0" step="1" id="valor" name="valor" placeholder="Valor"
						>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Verificar">
					</td>	
				</tr>
			</table>
		</form>
	</div>
	
	<br />
	<br />
	<br />
	<div align="center" class="container">
		<form action="produtoRelatorio" method="post" target="_blank">
			<p class="title">
				<b>Consultar Produtos em Estoque Abaixo do Valor Escolhido</b>
			</p>
			<table>
				<tr>
					<td>
						<input class="id_input_data" type="number" min="0" step="1" id="valor" name="valor" placeholder="Valor"
						>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Gerar Pdf">
					</td>	
				</tr>
			</table>
		</form>
	</div>
	
	<div align="center">
		<c:if test="${not empty erro }">
			<h2><b> <c:out value="${erro }" /> </b></h2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<h3><b> <c:out value="${saida }" /> </b></h3>	
		</c:if>
	</div>
	<br />
	<br />
	<br />
	<div align="center" >
		<c:if test="${not empty produtos }">
			<table class="table_round">
				<thead>
					<tr>
						<th class="lista">Codigo</th>
						<th class="lista">Nome</th>
						<th class="lista">Valor Unitario</th>
						<th class="lista_ultimoelemento">Qtd Estoque</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="p" items="${produtos }">
						<tr>
							<td class="lista"><c:out value="${p.codigo } " /></td>
							<td class="lista"><c:out value="${p.nome } " /></td>
							<td class="lista"><c:out value="${p.valor_unitario } " /></td>
							<td class="lista_ultimoelemento"><c:out value="${p.qtd_estoque } " /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>	

</body>
</html>