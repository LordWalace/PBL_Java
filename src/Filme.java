// === Filme.java === "Model"

import java.util.List;

public class Filme extends Midia {
    private String direcao;
    private String roteiro;
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;

    public Filme(String titulo, String genero, int anoLancamento, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
    }

    public String getDirecao() {
        return direcao;
    }

    public String getRoteiro() {
        return roteiro;
    }

    public List<String> getElenco() {
        return elenco;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public String getOndeAssistir() {
        return ondeAssistir;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setRoteiro(String roteiro) {
        this.roteiro = roteiro;
    }

    public void setElenco(List<String> elenco) {
        this.elenco = elenco;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public void setOndeAssistir(String ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
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
        return super.toString() + String.format(" | Direção: %s | Roteiro: %s | Elenco: %s | Título Original: %s | Onde Assistir: %s%s",
                direcao, roteiro, elenco, tituloOriginal, ondeAssistir, reviewInfo);
    }
}