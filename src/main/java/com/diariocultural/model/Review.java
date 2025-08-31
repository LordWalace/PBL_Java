// === Review.java === "Model"

package com.diariocultural.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa uma review de uma mídia.
 * É um "Objeto de Valor" que garante a validade de seus próprios dados,
 * como o texto da review e sua data de criação.
 */
public class Review {
    private String texto;
    private LocalDate dataCriacao;

    /**
     * Construtor para a classe Review.
     * Valida o texto e a data fornecidos no momento da criação.
     *
     * @param texto O texto da review.
     * @param dia   O dia da criação da review (1-31).
     * @param mes   O mês da criação da review (1-12).
     * @param ano   O ano da criação da review.
     * @throws IllegalArgumentException se o texto for nulo/vazio ou a data for inválida/futura.
     */
    public Review(String texto, int dia, int mes, int ano) {
        this.setTexto(texto);
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            this.setDataCriacao(data); // Reutiliza a validação do setter
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Data de criação da review é inválida: " + dia + "/" + mes + "/" + ano, e);
        }
    }

    /**
     * Retorna o texto da review.
     * @return O texto da review.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Define o texto da review.
     * @param texto O texto da review.
     * @throws IllegalArgumentException se o texto for nulo ou vazio.
     */
    public void setTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto da review não pode ser nulo ou vazio.");
        }
        this.texto = texto;
    }

    /**
     * Retorna a data de criação da review.
     * @return A data de criação da review.
     */
    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Define a data de criação da review.
     * @param dataCriacao A data de criação da review.
     * @throws IllegalArgumentException se a data for nula ou uma data no futuro.
     */
    public void setDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
            throw new IllegalArgumentException("A data de criação não pode ser nula.");
        }
        if (dataCriacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de criação da review não pode ser no futuro.");
        }
        this.dataCriacao = dataCriacao;
    }

    /**
     * Compara esta review com outro objeto para verificar se são iguais.
     * Duas reviews são consideradas iguais se tiverem o mesmo texto e a mesma data de criação.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(texto, review.texto) &&
                Objects.equals(dataCriacao, review.dataCriacao);
    }

    /**
     * Retorna um código hash para a review, baseado no texto e na data de criação.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(texto, dataCriacao);
    }

    /**
     * Retorna uma representação em String do estado do objeto Review.
     * Destinado para uso em logs e depuração.
     *
     * @return Uma String representando os campos do objeto.
     */
    @Override
    public String toString() {
        return "Review{" +
                "texto='" + texto + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
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