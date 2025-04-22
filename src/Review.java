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