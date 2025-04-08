public class Livro extends Midia {
    private String autor;
    private String editora;
    private String isbn;
    private String review;
    private boolean possuiExemplar;

    public Livro(String titulo, String autor, String editora, String isbn, int ano, String genero, boolean possuiExemplar) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.ano = ano;
        this.genero = genero;
        this.possuiExemplar = possuiExemplar;
        this.avaliacao = 0;
        this.consumido = false;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Autor: %s | Editora: %s | ISBN: %s | Review: %s | Possui Exemplar: %s",
                autor, editora, isbn, review != null ? review : "N/A", possuiExemplar ? "Sim" : "Não");
    }
}