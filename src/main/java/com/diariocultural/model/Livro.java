// === Livro.java === "Model"

package com.diariocultural.model;

import java.util.Objects;

/**
 * Classe que representa um livro.
 * Herda da classe Midia e contém informações e regras de negócio
 * específicas sobre livros, como autor, editora e ISBN.
 */
public class Livro extends Midia {
    private String autor;
    private String editora;
    private String isbn;
    private boolean possuiExemplar;

    /**
     * Construtor para a classe Livro.
     * Valida todos os parâmetros recebidos através dos setters.
     *
     * @param titulo O título do livro.
     * @param autor O autor do livro.
     * @param editora A editora do livro.
     * @param isbn O ISBN do livro.
     * @param anoLancamento O ano de lançamento do livro.
     * @param genero O gênero do livro.
     * @param possuiExemplar Indica se o livro possui um exemplar físico.
     * @throws IllegalArgumentException se qualquer um dos parâmetros for inválido.
     */
    public Livro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        super(titulo, genero, anoLancamento);
        this.setAutor(autor);
        this.setEditora(editora);
        this.setIsbn(isbn);
        this.setPossuiExemplar(possuiExemplar);
    }

    /**
     * Retorna o autor do livro.
     * @return O autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Retorna a editora do livro.
     * @return A editora do livro.
     */
    public String getEditora() {
        return editora;
    }

    /**
     * Retorna o ISBN do livro.
     * @return O ISBN do livro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Verifica se o livro possui um exemplar físico.
     * @return true se o livro possui um exemplar físico, false caso contrário.
     */
    public boolean isPossuiExemplar() {
        return possuiExemplar;
    }

    /**
     * Define o autor do livro.
     * @param autor O nome do autor.
     * @throws IllegalArgumentException se o autor for nulo ou vazio.
     */
    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do autor não pode ser nulo ou vazio.");
        }
        this.autor = autor;
    }

    /**
     * Define a editora do livro.
     * @param editora O nome da editora.
     * @throws IllegalArgumentException se a editora for nula ou vazia.
     */
    public void setEditora(String editora) {
        if (editora == null || editora.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da editora não pode ser nulo ou vazio.");
        }
        this.editora = editora;
    }

    /**
     * Define o ISBN do livro.
     * @param isbn O código ISBN.
     * @throws IllegalArgumentException se o ISBN for nulo ou não tiver 10 ou 13 caracteres.
     */
    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("O ISBN não pode ser nulo ou vazio.");
        }
        String isbnLimpo = isbn.replace("-", "").trim();
        if (!(isbnLimpo.length() == 10 || isbnLimpo.length() == 13)) {
            throw new IllegalArgumentException("O ISBN deve conter 10 ou 13 dígitos.");
        }
        this.isbn = isbn;
    }

    /**
     * Define se o livro possui um exemplar físico.
     * @param possuiExemplar true se o livro possui um exemplar físico, false caso contrário.
     */
    public void setPossuiExemplar(boolean possuiExemplar) {
        this.possuiExemplar = possuiExemplar;
    }

    /**
     * Compara este livro com outro objeto para verificar se são iguais.
     * Dois livros são considerados iguais se tiverem o mesmo título, autor, ISBN e ano de lançamento.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return getAnoLancamento() == livro.getAnoLancamento() &&
                Objects.equals(getTitulo(), livro.getTitulo()) &&
                Objects.equals(getAutor(), livro.getAutor()) &&
                Objects.equals(getIsbn(), livro.getIsbn());
    }

    /**
     * Retorna um código hash para o livro, baseado no título, autor, ISBN e ano de lançamento.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getAutor(), getIsbn(), getAnoLancamento());
    }

    /**
     * Retorna uma representação em String do estado do objeto Livro.
     * Este método é destinado para uso em logs e depuração, não para a interface do usuário.
     *
     * @return Uma String representando os campos do objeto.
     */
    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + getTitulo() + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", anoLancamento=" + getAnoLancamento() +
                ", genero='" + getGenero() + '\'' +
                ", avaliacao=" + getAvaliacao() +
                ", consumido=" + isConsumido() +
                '}';
    }
}

/******************************************************************************************

 Autor: Walace de Jesus Venas
 Componente Curricular: EXA863 MI-PROGRAMAÇÃO
 Concluído em: 21/04/2025
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 *******************************************************************************************/