// === Review.java === "Model"

import java.time.LocalDate;

/**
 * Classe que representa uma review de uma mídia.
 * Contém o texto da review e a data de criação.
 */
public class Review {
    private String texto;
    private LocalDate dataCriacao;

    /**
     * Construtor para a classe Review.
     *
     * @param texto O texto da review.
     */
    public Review(String texto) {
        this.texto = texto;
        this.dataCriacao = LocalDate.now();
    }

    /**
     * Retorna o texto da review.
     *
     * @return O texto da review.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define o texto da review.
     *
     * @param texto O texto da review.
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Retorna a data de criação da review.
     *
     * @return A data de criação da review.
     */
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}
