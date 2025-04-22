// === Livro.java === "Model"

/**
 * Classe que representa um livro.
 * Herda da classe Midia e contém informações específicas sobre livros,
 * como autor, editora, ISBN e se possui exemplar.
 */
public class Livro extends Midia {
    private String autor;
    private String editora;
    private String isbn;
    private boolean possuiExemplar;

    /**
     * Construtor para a classe Livro.
     *
     * @param titulo O título do livro.
     * @param autor O autor do livro.
     * @param editora A editora do livro.
     * @param isbn O ISBN do livro.
     * @param anoLancamento O ano de lançamento do livro.
     * @param genero O gênero do livro.
     * @param possuiExemplar Indica se o livro possui um exemplar físico.
     */
    public Livro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        super(titulo, genero, anoLancamento);
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.possuiExemplar = possuiExemplar;
    }

    /**
     * Retorna o autor do livro.
     *
     * @return O autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Retorna a editora do livro.
     *
     * @return A editora do livro.
     */
    public String getEditora() {
        return editora;
    }

    /**
     * Retorna o ISBN do livro.
     *
     * @return O ISBN do livro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Verifica se o livro possui um exemplar físico.
     *
     * @return true se o livro possui um exemplar físico, false caso contrário.
     */
    public boolean isPossuiExemplar() {
        return possuiExemplar;
    }

    /**
     * Define se o livro possui um exemplar físico.
     *
     * @param possuiExemplar true se o livro possui um exemplar físico, false caso contrário.
     */
    public void setPossuiExemplar(boolean possuiExemplar) {
        this.possuiExemplar = possuiExemplar;
    }

    /**
     * Retorna a review do livro.
     *
     * @return A review do livro.
     */
    public Review getReview() {
        return review;
    }

    /**
     * Define a review do livro.
     *
     * @param review A review do livro.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Define o texto da review do livro criando um novo objeto Review.
     *
     * @param textoReview O texto da review do livro.
     */
    public void setReviewTexto(String textoReview) {
        this.review = new Review(textoReview);
    }

    /**
     * Retorna o texto da review do livro.
     *
     * @return O texto da review do livro.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    /**
     * Retorna uma representação em String do livro.
     * Inclui informações da classe mãe (Midia) e informações específicas do livro,
     * como autor, editora, ISBN e se possui exemplar.
     *
     * @return Uma String formatada representando o livro.
     */
    @Override
    public String toString() {
        String reviewInfo = (review != null) ? " | Review: " + review.getTexto() : " | Review: N/A";
        return super.toString() + String.format(" | Autor: %s | Editora: %s | ISBN: %s%s | Possui Exemplar: %s",
                autor, editora, isbn, reviewInfo, possuiExemplar ? "Sim" : "Não");
    }
}

