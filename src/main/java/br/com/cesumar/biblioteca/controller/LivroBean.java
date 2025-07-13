package br.com.cesumar.biblioteca.controller;

import br.com.cesumar.biblioteca.dao.LivroDAO;
import br.com.cesumar.biblioteca.model.Livro;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.time.Year;

@Named
@RequestScoped
public class LivroBean {

    private Livro livro = new Livro();
    private final LivroDAO livroDAO = new LivroDAO();

    // Getters e Setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    /**
     * Retorna o ano atual para ser usado na validação da View (JSF).
     * @return O número do ano atual.
     */
    public int getAnoAtual() {
        return Year.now().getValue();
    }

    public void cadastrar() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();

        try {
            // A validação de formato e obrigatoriedade é feita pelo JSF na View.
            // Aqui focamos na regra de negócio.

            // Validação de ISBN duplicado
            if (livroDAO.buscarPorIsbn(livro.getIsbn()) != null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN Inválido", "Já existe um livro cadastrado com este ISBN."));
                return; // Permanece na página para correção
            }

            // Se todas as validações passaram, adiciona o livro
            livroDAO.adicionar(livro);

            // Adiciona uma mensagem de sucesso para a próxima página
            ec.getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Livro cadastrado com sucesso."));

            // Redireciona para a lista de livros
            ec.redirect(ec.getRequestContextPath() + "/livros?acao=listar");

        } catch (IOException e) {
            // Erro de redirecionamento (raro, mas possível)
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro Crítico", "Não foi possível redirecionar a página. Contate o suporte."));
            e.printStackTrace(); // Loga o erro no console do servidor
        } catch (Exception e) {
            // Captura qualquer outra exceção inesperada durante o processo
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro Inesperado", "Ocorreu um erro inesperado ao tentar cadastrar o livro."));
            e.printStackTrace(); // Loga o erro no console do servidor
        }
    }
}
