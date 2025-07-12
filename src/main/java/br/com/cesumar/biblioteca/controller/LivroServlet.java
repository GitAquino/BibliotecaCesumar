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
@WebServlet("/livros" )
public class LivroServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final LivroDAO livroDAO = new LivroDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        // Se não houver ação ou a ação for "listar", exibe a lista de livros.
        if (acao == null || acao.equals("listar")) {
            listarLivros(request, response);
        } else if (acao.equals("excluir")) {
            excluirLivro(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        if (acao != null && acao.equals("cadastrar")) {
            cadastrarLivro(request, response);
        } else {
            // Por padrão, redireciona para a listagem
            listarLivros(request, response);
        }
    }

    private void listarLivros(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Livro> listaDeLivros = livroDAO.listarTodos();
        request.setAttribute("listaDeLivros", listaDeLivros);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/jsp/listaLivros.jsp");
        dispatcher.forward(request, response);
    }

    private void cadastrarLivro(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String anoStr = request.getParameter("anoPublicacao");
        String isbn = request.getParameter("isbn");

        // Tratamento de erros: campos vazios
        if (titulo == null || titulo.trim().isEmpty() ||
                autor == null || autor.trim().isEmpty() ||
                anoStr == null || anoStr.trim().isEmpty() ||
                isbn == null || isbn.trim().isEmpty()) {

            request.setAttribute("erro", "Todos os campos são obrigatórios.");
            // Reencaminha para a página de cadastro (que será feita com JSF)
            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroLivro.xhtml");
            dispatcher.forward(request, response);
            return;
        }

        // Tratamento de erro: ISBN já existe
        if (livroDAO.buscarPorIsbn(isbn) != null) {
            request.setAttribute("erro", "Já existe um livro com este ISBN.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroLivro.xhtml");
            dispatcher.forward(request, response);
            return;
        }

        try {
            int anoPublicacao = Integer.parseInt(anoStr);
            Livro novoLivro = new Livro(titulo, autor, anoPublicacao, isbn);
            livroDAO.adicionar(novoLivro);

            // Redireciona para a lista de livros após o cadastro para evitar reenvio do formulário
            response.sendRedirect("livros?acao=listar");

        } catch (NumberFormatException e) {
            // Tratamento de erro: ano de publicação inválido
            request.setAttribute("erro", "O ano de publicação deve ser um número válido.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastroLivro.xhtml");
            dispatcher.forward(request, response);
        }
    }

    private void excluirLivro(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String isbn = request.getParameter("isbn");
        if (isbn != null && !isbn.isEmpty()) {
            livroDAO.removerPorIsbn(isbn);
        }
        // Redireciona de volta para a lista de livros
        response.sendRedirect("livros?acao=listar");
    }
}
