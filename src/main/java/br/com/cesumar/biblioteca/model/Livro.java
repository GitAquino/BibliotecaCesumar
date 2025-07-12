package br.com.cesumar.biblioteca.model;

import java.io.Serializable;

/**
 * Classe que representa um Livro (Model).
 * Implementa Serializable para ser compatível com escopos do JSF.
 */
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String isbn;

    // Construtor padrão é necessário para o JSF Managed Bean
    public Livro() {
    }

    // Construtor com parâmetros para facilitar a criação de objetos
    public Livro(String titulo, String autor, int anoPublicacao, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbn;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Sobrescrever o metodo equals e hashCode é uma boa prática,
    // especialmente ao trabalhar com coleções, para comparar objetos com base no ISBN.
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        return true;
    }
}
