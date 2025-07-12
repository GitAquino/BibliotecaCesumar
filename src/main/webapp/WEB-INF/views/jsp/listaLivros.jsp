<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Acervo da Biblioteca Cesumar</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1, h2 { color: #0056b3; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        tr:nth-child(even ) { background-color: #f9f9f9; }
        a { text-decoration: none; color: #007bff; }
        a.delete { color: #dc3545; }
        .container { max-width: 900px; margin: auto; }
        .add-link { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #28a745; color: white; border-radius: 5px; }
    </style>
</head>
<body>
<div class="container">
    <h1>Biblioteca da Universidade Cesumar</h1>
    <h2>Acervo de Livros</h2>

    <table border="1">
        <thead>
        <tr>
            <th>Título</th>
            <th>Autor</th>
            <th>Ano de Publicação</th>
            <th>ISBN</th>
            <th>Ação</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="livro" items="${listaDeLivros}">
            <tr>
                <td><c:out value="${livro.titulo}" /></td>
                <td><c:out value="${livro.autor}" /></td>
                <td><c:out value="${livro.anoPublicacao}" /></td>
                <td><c:out value="${livro.isbn}" /></td>
                <td>
                    <a class="delete" href="livros?acao=excluir&isbn=${livro.isbn}"
                       onclick="return confirm('Tem certeza que deseja excluir este livro?');">
                        Excluir
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/cadastroLivro.xhtml" class="add-link">Cadastrar Novo Livro</a>
</div>
</body>
</html>
