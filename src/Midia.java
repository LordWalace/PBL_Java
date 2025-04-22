// === Midia.java === "Model"

// Classe para os três tipos de mídia

public abstract class Midia {
    protected String titulo;
    protected String genero;
    protected int anoLancamento;
    protected int avaliacao;
    protected boolean consumido;
    protected String dataConsumo;
    protected Review review; // Adicionado o campo Review

    public Midia(String titulo, String genero, int anoLancamento) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.avaliacao = 0;
        this.consumido = false;
        this.dataConsumo = null;
        this.review = null; // Inicializa review como null
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
    }

    public boolean isConsumido() {
        return consumido;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public String getDataConsumo() {
        return dataConsumo;
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
        String reviewInfo = (review != null) ? " | Review: " + review.getTexto() : "";
        return String.format("Título: %s | Gênero: %s | Ano de Lançamento: %d | Avaliação: %d | Consumido: %s | Data: %s%s",
                titulo, genero, anoLancamento, avaliacao, consumido ? "Sim" : "Não", dataConsumo != null ? dataConsumo : "N/A", reviewInfo);
    }
}