// === Midia.java === "Model"

// Classe para os três tipos de mídia

/**
 * Classe abstrata que representa uma mídia, como livro, filme ou série.
 * Contém informações básicas sobre a mídia, como título, gênero, ano de lançamento,
 * avaliação, status de consumo, data de consumo e uma review associada.
 */
public abstract class Midia {
    protected String titulo;
    protected String genero;
    protected int anoLancamento;
    protected int avaliacao;
    protected boolean consumido;
    protected String dataConsumo;
    protected Review review; // Adicionado o campo Review

    /**
     * Construtor para a classe Midia.
     *
     * @param titulo O título da mídia.
     * @param genero O gênero da mídia.
     * @param anoLancamento O ano de lançamento da mídia.
     */
    public Midia(String titulo, String genero, int anoLancamento) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.avaliacao = 0;
        this.consumido = false;
        this.dataConsumo = null;
        this.review = null; // Inicializa review como null
    }

    /**
     * Retorna o título da mídia.
     *
     * @return O título da mídia.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna a avaliação da mídia.
     *
     * @return A avaliação da mídia.
     */
    public int getAvaliacao() {
        return avaliacao;
    }

    /**
     * Define a avaliação da mídia.
     *
     * @param avaliacao A avaliação da mídia.
     */
    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * Define o status de consumo da mídia.
     *
     * @param consumido O status de consumo da mídia (true se consumida, false caso contrário).
     */
    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
    }

    /**
     * Verifica se a mídia foi consumida.
     *
     * @return true se a mídia foi consumida, false caso contrário.
     */
    public boolean isConsumido() {
        return consumido;
    }

    /**
     * Retorna o gênero da mídia.
     *
     * @return O gênero da mídia.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Retorna o ano de lançamento da mídia.
     *
     * @return O ano de lançamento da mídia.
     */
    public int getAnoLancamento() {
        return anoLancamento;
    }

    /**
     * Define a data de consumo da mídia.
     *
     * @param dataConsumo A data de consumo da mídia.
     */
    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    /**
     * Retorna a data de consumo da mídia.
     *
     * @return A data de consumo da mídia.
     */
    public String getDataConsumo() {
        return dataConsumo;
    }

    /**
     * Retorna a review da mídia.
     *
     * @return A review da mídia.
     */
    public Review getReview() {
        return review;
    }

    /**
     * Define a review da mídia.
     *
     * @param review A review da mídia.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Define o texto da review da mídia criando um novo objeto Review.
     *
     * @param textoReview O texto da review da mídia.
     */
    public void setReviewTexto(String textoReview) {
        this.review = new Review(textoReview);
    }

    /**
     * Retorna o texto da review da mídia.
     *
     * @return O texto da review da mídia, ou null se não houver review.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    /**
     * Retorna uma representação em String da mídia.
     * Inclui título, gênero, ano de lançamento, avaliação, status de consumo,
     * data de consumo e a review, se existir.
     *
     * @return Uma String formatada representando a mídia.
     */
    @Override
    public String toString() {
        String reviewInfo = (review != null) ? " | Review: " + review.getTexto() : "";
        return String.format("Título: %s | Gênero: %s | Ano de Lançamento: %d | Avaliação: %d | Consumido: %s | Data: %s%s",
                titulo, genero, anoLancamento, avaliacao, consumido ? "Sim" : "Não", dataConsumo != null ? dataConsumo : "N/A", reviewInfo);
    }
}