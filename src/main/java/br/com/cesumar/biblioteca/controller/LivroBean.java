package br.com.cesumar.biblioteca.controller;

import br.com.cesumar.biblioteca.dao.LivroDAO;
import br.com.cesumar.biblioteca.model.Livro;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.IOException;

/**
 * Managed Bean para a interface de cadastro de livros com JSF.
 */
@Named
@RequestScoped
public class LivroBean {

    private Livro livro = new Livro();
    private final LivroDAO livroDAO = new LivroDAO();

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void cadastrar() throws IOException { // <-- Mude o retorno para void e adicione throws IOException
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();

        // ... (todo o código de validação continua exatamente o mesmo) ...
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty() ||
                livro.getAutor() == null || livro.getAutor().trim().isEmpty() ||
                livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "Os campos Título, Autor e ISBN são obrigatórios."));
            return; // Apenas retorna, sem valor
        }
        if (livro.getAnoPublicacao() == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "O campo Ano de Publicação é obrigatório e não pode ser zero."));
            return; // Apenas retorna, sem valor
        }
        if (livroDAO.buscarPorIsbn(livro.getIsbn()) != null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de Validação", "Já existe um livro cadastrado com este ISBN."));
            return; // Apenas retorna, sem valor
        }

        // Se todas as validações passaram, adicione o livro
        livroDAO.adicionar(livro);

        // Adiciona uma mensagem de sucesso que será exibida na próxima página
        ec.getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Livro cadastrado com sucesso!"));

        // --- A GRANDE MUDANÇA ESTÁ AQUI ---
        // Redireciona manualmente para o Servlet de listagem
        ec.redirect(ec.getRequestContextPath() + "/livros?acao=listar");
    }
}
