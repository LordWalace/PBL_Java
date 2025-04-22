// === Filme.java === "Model"

import java.util.List;

/**
 * Classe que representa um filme.
 * Herda da classe Midia e contém informações específicas sobre filmes,
 * como direção, roteiro, elenco, título original e onde assistir.
 */
public class Filme extends Midia {
    private String direcao;
    private String roteiro;
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;

    /**
     * Construtor para a classe Filme.
     *
     * @param titulo O título do filme.
     * @param genero O gênero do filme.
     * @param anoLancamento O ano de lançamento do filme.
     * @param direcao O diretor do filme.
     * @param roteiro O roteirista do filme.
     * @param elenco O elenco do filme.
     * @param tituloOriginal O título original do filme.
     * @param ondeAssistir Onde o filme pode ser assistido.
     */
    public Filme(String titulo, String genero, int anoLancamento, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Retorna o diretor do filme.
     *
     * @return O diretor do filme.
     */
    public String getDirecao() {
        return direcao;
    }

    /**
     * Retorna o roteirista do filme.
     *
     * @return O roteirista do filme.
     */
    public String getRoteiro() {
        return roteiro;
    }

    /**
     * Retorna o elenco do filme.
     *
     * @return O elenco do filme.
     */
    public List<String> getElenco() {
        return elenco;
    }

    /**
     * Retorna o título original do filme.
     *
     * @return O título original do filme.
     */
    public String getTituloOriginal() {
        return tituloOriginal;
    }

    /**
     * Retorna onde o filme pode ser assistido.
     *
     * @return Onde o filme pode ser assistido.
     */
    public String getOndeAssistir() {
        return ondeAssistir;
    }

    /**
     * Define o diretor do filme.
     *
     * @param direcao O diretor do filme.
     */
    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    /**
     * Define o roteirista do filme.
     *
     * @param roteiro O roteirista do filme.
     */
    public void setRoteiro(String roteiro) {
        this.roteiro = roteiro;
    }

    /**
     * Define o elenco do filme.
     *
     * @param elenco O elenco do filme.
     */
    public void setElenco(List<String> elenco) {
        this.elenco = elenco;
    }

    /**
     * Define o título original do filme.
     *
     * @param tituloOriginal O título original do filme.
     */
    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    /**
     * Define onde o filme pode ser assistido.
     *
     * @param ondeAssistir Onde o filme pode ser assistido.
     */
    public void setOndeAssistir(String ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Retorna a review do filme.
     *
     * @return A review do filme.
     */
    public Review getReview() {
        return review;
    }

    /**
     * Define a review do filme.
     *
     * @param review A review do filme.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Define o texto da review do filme criando um novo objeto Review.
     *
     * @param textoReview O texto da review do filme.
     */
    public void setReviewTexto(String textoReview) {
        this.review = new Review(textoReview);
    }

    /**
     * Retorna o texto da review do filme.
     *
     * @return O texto da review do filme.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    /**
     * Retorna uma representação em String do filme.
     * Inclui informações da classe mãe (Midia) e informações específicas do filme,
     * como direção, roteiro, elenco, título original e onde assistir.
     *
     * @return Uma String formatada representando o filme.
     */
    @Override
    public String toString() {
        String reviewInfo = (review != null) ? " | Review: " + review.getTexto() : "";
        return super.toString() + String.format(" | Direção: %s | Roteiro: %s | Elenco: %s | Título Original: %s | Onde Assistir: %s%s",
                direcao, roteiro, elenco, tituloOriginal, ondeAssistir, reviewInfo);
    }
}