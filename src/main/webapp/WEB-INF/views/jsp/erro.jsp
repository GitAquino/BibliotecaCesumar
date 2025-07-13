<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Erro no Sistema</title>
  <style>
    body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
    .error-container { border: 2px solid #dc3545; background-color: #f8d7da; padding: 20px; display: inline-block; }
    h1 { color: #dc3545; }
  </style>
</head>
<body>
<div class="error-container">
  <h1>Ocorreu um Erro Inesperado</h1>
  <p>Desculpe, o sistema encontrou um problema que não pôde resolver.</p>
  <p><strong>Detalhe do erro:</strong> ${erroFatal}</p>
  <p><a href="${pageContext.request.contextPath}/livros">Voltar para a página inicial</a></p>
</div>
</body>
</html>
