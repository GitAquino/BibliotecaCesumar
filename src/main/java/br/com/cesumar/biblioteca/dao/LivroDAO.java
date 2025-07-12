package br.com.cesumar.biblioteca.dao;

import br.com.cesumar.biblioteca.model.Livro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * DAO (Data Access Object) para simular a persistência de dados dos livros.
 * Utiliza uma lista estática para manter os dados em memória durante a execução da aplicação.
 */
public class LivroDAO {

    // Usamos CopyOnWriteArrayList para evitar ConcurrentModificationException em ambiente web.
    private static final List<Livro> acervo = new CopyOnWriteArrayList<>();

    // Bloco estático para popular a lista com alguns dados iniciais.
    static {
        acervo.add(new Livro("Java para Iniciantes", "Herbert Schildt", 2022, "978-85-8260-502-9"));
        acervo.add(new Livro("Engenharia de Software", "Ian Sommerville", 2019, "978-85-430-0497-4"));
        acervo.add(new Livro("Código Limpo", "Robert C. Martin", 2009, "978-85-7608-267-5"));
    }

    /**
     * Retorna todos os livros cadastrados.
     * @return uma cópia da lista de livros.
     */
    public List<Livro> listarTodos() {
        return new ArrayList<>(acervo);
    }

    /**
     * Adiciona um novo livro ao acervo.
     * @param livro O livro a ser adicionado.
     */
    public void adicionar(Livro livro) {
        acervo.add(livro);
    }

    /**
     * Remove um livro do acervo com base no ISBN.
     * @param isbn O ISBN do livro a ser removido.
     */
    public void removerPorIsbn(String isbn) {
        acervo.removeIf(livro -> livro.getIsbn().equals(isbn));
    }

    /**
     * Busca um livro pelo seu ISBN.
     * @param isbn O ISBN a ser buscado.
     * @return O livro encontrado ou null se não existir.
     */
    public Livro buscarPorIsbn(String isbn) {
        for (Livro livro : acervo) {
            if (livro.getIsbn().equals(isbn)) {
                return livro;
            }
        }
        return null;
    }
}
