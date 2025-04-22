// === Temporada.java === "Model"

import java.time.LocalDate;

/**
 * Classe que representa uma temporada de uma série.
 * Contém informações sobre o número da temporada, anos de lançamento e encerramento,
 * número de episódios, avaliação, status de consumo, data de consumo e review.
 */
public class Temporada {
    private int numero;
    private int anoLancamento;
    private int anoEncerramento;
    private int numeroEpisodios;
    private int avaliacao;
    private boolean consumido;
    private String dataConsumo;
    private String reviewTexto;

    /**
     * Construtor para a classe Temporada.
     *
     * @param numero O número da temporada.
     * @param anoLancamento O ano de lançamento da temporada.
     * @param anoEncerramento O ano de encerramento da temporada (0 se ainda em lançamento).
     * @param numeroEpisodios O número de episódios da temporada.
     */
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

    /**
     * Retorna o número da temporada.
     *
     * @return O número da temporada.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retorna o ano de lançamento da temporada.
     *
     * @return O ano de lançamento da temporada.
     */
    public int getAnoLancamento() {
        return anoLancamento;
    }

    /**
     * Define o ano de lançamento da temporada.
     *
     * @param anoLancamento O ano de lançamento da temporada.
     */
    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    /**
     * Retorna o ano de encerramento da temporada.
     *
     * @return O ano de encerramento da temporada.
     */
    public int getAnoEncerramento() {
        return anoEncerramento;
    }

    /**
     * Define o ano de encerramento da temporada.
     *
     * @param anoEncerramento O ano de encerramento da temporada.
     */
    public void setAnoEncerramento(int anoEncerramento) {
        this.anoEncerramento = anoEncerramento;
    }

    /**
     * Retorna o número de episódios da temporada.
     *
     * @return O número de episódios da temporada.
     */
    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    /**
     * Retorna a avaliação da temporada.
     *
     * @return A avaliação da temporada.
     */
    public int getAvaliacao() {
        return avaliacao;
    }

    /**
     * Define a avaliação da temporada.
     *
     * @param avaliacao A avaliação da temporada.
     */
    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }

    /**
     * Verifica se a temporada foi consumida.
     *
     * @return true se a temporada foi consumida, false caso contrário.
     */
    public boolean isConsumido() {
        return consumido;
    }

    /**
     * Define o status de consumo da temporada.
     *
     * @param consumido O status de consumo da temporada.
     */
    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
    }

    /**
     * Retorna a data de consumo da temporada.
     *
     * @return A data de consumo da temporada.
     */
    public String getDataConsumo() {
        return dataConsumo;
    }

    /**
     * Define a data de consumo da temporada.
     *
     * @param dataConsumo A data de consumo da temporada.
     */
    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    /**
     * Retorna o texto da review da temporada.
     *
     * @return O texto da review da temporada.
     */
    public String getReviewTexto() {
        return reviewTexto;
    }

    /**
     * Define o texto da review da temporada.
     *
     * @param reviewTexto O texto da review da temporada.
     */
    public void setReviewTexto(String reviewTexto) {
        this.reviewTexto = reviewTexto;
    }

    /**
     * Retorna uma representação em String da temporada.
     * Inclui número da temporada, anos de lançamento e encerramento, número de episódios,
     * avaliação, status de consumo, data de consumo e review.
     *
     * @return Uma String formatada representando a temporada.
     */
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