package br.com.cesumar.biblioteca.controller;

import br.com.cesumar.biblioteca.dao.LivroDAO;
import br.com.cesumar.biblioteca.model.Livro;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Servlet que atua como Controller para as operações de Livros.
 * Mapeado para o URL /livros
 */
@WebServlet("/livros")
public class LivroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LivroDAO livroDAO = new LivroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listar"; // Ação padrão
        }

        try {
            switch (acao) {
                case "excluir":
                    excluirLivro(request, response);
                    break;
                case "listar":
                default: // Se a ação for desconhecida, apenas lista os livros
                    listarLivros(request, response);
                    break;
            }
        } catch (Exception e) {
            // Tratamento genérico de erro para o Servlet
            e.printStackTrace(); // Loga o erro
            request.setAttribute("erroFatal", "Ocorreu um erro inesperado no servidor: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/jsp/erro.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void listarLivros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Livro> listaDeLivros = livroDAO.listarTodos();
        request.setAttribute("listaDeLivros", listaDeLivros);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/jsp/listaLivros.jsp");
        dispatcher.forward(request, response);
    }

    private void excluirLivro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String isbn = request.getParameter("isbn");

        // Validação: verifica se o ISBN foi fornecido
        if (isbn == null || isbn.trim().isEmpty()) {
            request.getSession().setAttribute("erroExclusao", "ISBN não fornecido para exclusão.");
            response.sendRedirect("livros?acao=listar");
            return;
        }

        // Validação: verifica se o livro realmente existe antes de tentar remover
        if (livroDAO.buscarPorIsbn(isbn) == null) {
            request.getSession().setAttribute("erroExclusao", "Livro com o ISBN informado não foi encontrado para exclusão.");
            response.sendRedirect("livros?acao=listar");
            return;
        }

        livroDAO.removerPorIsbn(isbn);
        request.getSession().setAttribute("sucessoExclusao", "Livro removido com sucesso!");
        response.sendRedirect("livros?acao=listar");
    }

    // O método doPost não é mais usado para cadastro, pois o JSF cuida disso.
    // Pode ser removido ou mantido para futuras funcionalidades.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}