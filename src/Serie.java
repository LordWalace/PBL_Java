// === Serie.java === "Model"

import java.util.ArrayList;
import java.util.List;

public class Serie extends Midia {
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;
    private List<Temporada> temporadasList;
    private int numeroTemporadas;

    public Serie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.temporadasList = new ArrayList<>();
        this.numeroTemporadas = 0;
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

    public List<Temporada> getTemporadasList() {
        return temporadasList;
    }

    public int getNumeroTemporadas() {
        return numeroTemporadas;
    }

    public void setNumeroTemporadas(int numeroTemporadas) {
        this.numeroTemporadas = numeroTemporadas;
    }

    public void adicionarTemporada(Temporada temporada) {
        this.temporadasList.add(temporada);
        this.numeroTemporadas = this.temporadasList.size();
    }

    public Temporada getTemporada(int numeroTemporada) {
        for (Temporada temporada : temporadasList) {
            if (temporada.getNumero() == numeroTemporada) {
                return temporada;
            }
        }
        return null;
    }

    // Método para obter o ano de encerramento da série (baseado na última temporada)
    public int getAnoEncerramento() {
        if (temporadasList.isEmpty()) {
            return 0; // Ou outro valor que indique sem temporadas
        }
        int ultimoAnoEncerramento = 0;
        for (Temporada temporada : temporadasList) {
            if (temporada.getAnoEncerramento() > ultimoAnoEncerramento) {
                ultimoAnoEncerramento = temporada.getAnoEncerramento();
            }
        }
        return ultimoAnoEncerramento;
    }

    @Override
    public int getAvaliacao() {
        if (temporadasList.isEmpty()) {
            return 0;
        }
        int somaNotas = 0;
        int temporadasAvaliadas = 0;
        for (Temporada temporada : temporadasList) {
            if (temporada.getAvaliacao() > 0) {
                somaNotas += temporada.getAvaliacao();
                temporadasAvaliadas++;
            }
        }
        return temporadasAvaliadas > 0 ? somaNotas / temporadasAvaliadas : 0;
    }

    @Override
    public String toString() {
        String status = getAnoEncerramento() > 0 ? ", Ano de Encerramento: " + getAnoEncerramento() : ", Status: Em Andamento";
        String reviewInfo = (review != null) ? ", Review: " + review.getTexto() : ""; // Adicionado a review da série
        return super.toString() +
                ", Elenco: " + elenco +
                ", Título Original: " + tituloOriginal +
                ", Onde Assistir: " + ondeAssistir +
                ", Temporadas: " + numeroTemporadas +
                status +
                reviewInfo; // Inclui a informação da review
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
}