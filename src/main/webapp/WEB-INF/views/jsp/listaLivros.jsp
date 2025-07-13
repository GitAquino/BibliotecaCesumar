<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

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
        tr:nth-child(even) { background-color: #f9f9f9; }
        a { text-decoration: none; color: #007bff; }
        a.delete { color: #dc3545; font-weight: bold; }
        a.delete:hover { color: #721c24; }
        .container { max-width: 900px; margin: auto; }
        .add-link { display: inline-block; margin-top: 20px; padding: 10px 15px; background-color: #28a745; color: white; border-radius: 5px; }
        .add-link:hover { background-color: #218838; }

        /* Estilos para as mensagens de feedback */
        .message {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
            font-size: 1em;
        }
        .message.info, .message.success { /* JSF usa INFO, nós usamos success */
            color: #0f5132;
            background-color: #d1e7dd;
            border-color: #badbcc;
        }
        .message.error, .message.fatal { /* JSF usa ERROR/FATAL */
            color: #842029;
            background-color: #f8d7da;
            border-color: #f5c2c7;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Biblioteca da Universidade Cesumar</h1>
    <h2>Acervo de Livros</h2>

    <%-- Bloco para exibir mensagens de feedback da Sessão (vindo do Servlet de exclusão) --%>
    <c:if test="${not empty sessionScope.sucessoExclusao}">
        <div class="message success">${sessionScope.sucessoExclusao}</div>
        <%-- Remove o atributo da sessão para não exibi-lo novamente --%>
        <% session.removeAttribute("sucessoExclusao"); %>
    </c:if>
    <c:if test="${not empty sessionScope.erroExclusao}">
        <div class="message error">${sessionScope.erroExclusao}</div>
        <%-- Remove o atributo da sessão para não exibi-lo novamente --%>
        <% session.removeAttribute("erroExclusao"); %>
    </c:if>

    <%-- Bloco para exibir mensagens de feedback do Flash Scope (vindo do cadastro JSF) --%>
    <%-- O JSF armazena as mensagens no FacesContext, que é acessível via EL --%>
    <c:if test="${not empty facesContext.messageList}">
        <c:forEach items="${facesContext.messageList}" var="message">
            <%-- Usa a severidade da mensagem (INFO, ERROR) para definir a classe CSS --%>
            <div class="message ${message.severity.toString().toLowerCase()}">
                <strong>${message.summary}</strong> ${message.detail}
            </div>
        </c:forEach>
    </c:if>

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
                    <a class="delete" href="${pageContext.request.contextPath}/livros?acao=excluir&isbn=${livro.isbn}"
                       onclick="return confirm('Tem certeza que deseja excluir o livro \'${livro.titulo}\'?');">
                        Excluir
                    </a>
                </td>
            </tr>
        </c:forEach>
        <%-- Mensagem para caso a lista de livros esteja vazia --%>
        <c:if test="${empty listaDeLivros}">
            <tr>
                <td colspan="5" style="text-align:center;">Nenhum livro cadastrado no momento.</td>
            </tr>
        </c:if>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/cadastroLivro.xhtml" class="add-link">Cadastrar Novo Livro</a>
</div>
</body>
</html>
