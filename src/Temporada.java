// === Temporada.java === "Model"

public class Temporada {
    private int numero;
    private int anoLancamento;
    private int anoEncerramento;
    private int numeroEpisodios;
    private int avaliacao;
    private boolean consumido;
    private String dataConsumo;
    private String reviewTexto;

    public Temporada(int numero, int anoLancamento, int anoEncerramento, int numeroEpisodios) {
        this.numero = numero;
        this.anoLancamento = anoLancamento;
        this.anoEncerramento = anoEncerramento;
        this.numeroEpisodios = numeroEpisodios;
        this.avaliacao = 0;
        this.consumido = false;
        this.dataConsumo = null;
        this.reviewTexto = null;
    }

    public int getNumero() {
        return numero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public int getAnoEncerramento() {
        return anoEncerramento;
    }

    public void setAnoEncerramento(int anoEncerramento) {
        this.anoEncerramento = anoEncerramento;
    }

    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    public boolean isConsumido() {
        return consumido;
    }

    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
    }

    public String getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public String getReviewTexto() {
        return reviewTexto;
    }

    public void setReviewTexto(String reviewTexto) {
        this.reviewTexto = reviewTexto;
    }

    @Override
    public String toString() {
        return "Temporada " + numero +
                ": Lançamento=" + anoLancamento +
                ", Encerramento=" + (anoEncerramento > 0 ? String.valueOf(anoEncerramento) : "Em Lançamento") +
                ", Episódios=" + numeroEpisodios +
                ", Avaliação=" + avaliacao +
                ", Consumido=" + (consumido ? "Sim" : "Não") +
                (dataConsumo != null ? ", Data de Consumo=" + dataConsumo : "") +
                (reviewTexto != null ? ", Review=" + reviewTexto : "");
    }
}