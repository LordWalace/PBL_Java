// === Livro.java === "Model"

public class Livro extends Midia {
    private String autor;
    private String editora;
    private String isbn;
    private boolean possuiExemplar;

    public Livro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        super(titulo, genero, anoLancamento);
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.possuiExemplar = possuiExemplar;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isPossuiExemplar() {
        return possuiExemplar;
    }

    public void setPossuiExemplar(boolean possuiExemplar) {
        this.possuiExemplar = possuiExemplar;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public void setReviewTexto(String textoReview) {
        this.review = new Review(textoReview);
    }

    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    @Override
    public String toString() {
        String reviewInfo = (review != null) ? " | Review: " + review.getTexto() : " | Review: N/A";
        return super.toString() + String.format(" | Autor: %s | Editora: %s | ISBN: %s%s | Possui Exemplar: %s",
                autor, editora, isbn, reviewInfo, possuiExemplar ? "Sim" : "Não");
    }
}