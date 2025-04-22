// === Serie.java === "Model"

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma série de TV.
 * Herda da classe Midia e contém informações específicas sobre séries, como elenco,
 * título original, onde assistir, lista de temporadas e número de temporadas.
 */
public class Serie extends Midia {
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;
    private List<Temporada> temporadasList;
    private int numeroTemporadas;

    /**
     * Construtor para a classe Serie.
     *
     * @param titulo O título da série.
     * @param genero O gênero da série.
     * @param anoLancamento O ano de lançamento da série.
     * @param elenco O elenco da série.
     * @param tituloOriginal O título original da série.
     * @param ondeAssistir Onde a série pode ser assistida.
     */
    public Serie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.temporadasList = new ArrayList<>();
        this.numeroTemporadas = 0;
    }

    /**
     * Retorna o elenco da série.
     *
     * @return O elenco da série.
     */
    public List<String> getElenco() {
        return elenco;
    }

    /**
     * Retorna o título original da série.
     *
     * @return O título original da série.
     */
    public String getTituloOriginal() {
        return tituloOriginal;
    }

    /**
     * Retorna onde a série pode ser assistida.
     *
     * @return Onde a série pode ser assistida.
     */
    public String getOndeAssistir() {
        return ondeAssistir;
    }

    /**
     * Retorna a lista de temporadas da série.
     *
     * @return A lista de temporadas da série.
     */
    public List<Temporada> getTemporadasList() {
        return temporadasList;
    }

    /**
     * Retorna o número de temporadas da série.
     *
     * @return O número de temporadas da série.
     */
    public int getNumeroTemporadas() {
        return numeroTemporadas;
    }

    /**
     * Define o número de temporadas da série.
     *
     * @param numeroTemporadas O número de temporadas da série.
     */
    public void setNumeroTemporadas(int numeroTemporadas) {
        this.numeroTemporadas = numeroTemporadas;
    }

    /**
     * Adiciona uma temporada à lista de temporadas da série.
     *
     * @param temporada A temporada a ser adicionada.
     */
    public void adicionarTemporada(Temporada temporada) {
        this.temporadasList.add(temporada);
        this.numeroTemporadas = this.temporadasList.size();
    }

    /**
     * Retorna uma temporada específica da série, dado o número da temporada.
     *
     * @param numeroTemporada O número da temporada a ser retornada.
     * @return A temporada correspondente ao número, ou null se não encontrada.
     */
    public Temporada getTemporada(int numeroTemporada) {
        for (Temporada temporada : temporadasList) {
            if (temporada.getNumero() == numeroTemporada) {
                return temporada;
            }
        }
        return null;
    }

    /**
     * Retorna o ano de encerramento da série, baseado no ano de encerramento da última temporada.
     *
     * @return O ano de encerramento da série, ou 0 se ainda estiver em andamento ou não houver temporadas.
     */
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

    /**
     * Retorna a avaliação da série, que é a média das avaliações de suas temporadas.
     *
     * @return A avaliação da série, ou 0 se não houver temporadas avaliadas.
     */
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

    /**
     * Retorna uma representação em String da série.
     * Inclui informações da classe mãe (Midia) e informações específicas da série,
     * como elenco, título original, onde assistir, número de temporadas e status.
     *
     * @return Uma String formatada representando a série.
     */
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

    /**
     * Retorna a review da série.
     *
     * @return A review da série.
     */
    public Review getReview() {
        return review;
    }

    /**
     * Define a review da série.
     *
     * @param review A review da série.
     */
    public void setReview(Review review) {
        this.review = review;
    }

    /**
     * Define o texto da review da série criando um novo objeto Review.
     *
     * @param textoReview O texto da review da série.
     */
    public void setReviewTexto(String textoReview) {
        this.review = new Review(textoReview);
    }

    /**
     * Retorna o texto da review da série.
     *
     * @return O texto da review da série.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }
}

/******************************************************************************************

 Autor: Walace de Jesus Venas
 Componente Curricular: EXA863 MI-PROGRAMAÇÃO
 Concluído em: 21/04/2025
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 *******************************************************************************************/