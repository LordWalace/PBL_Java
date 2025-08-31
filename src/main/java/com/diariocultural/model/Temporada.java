// === Temporada.java === "Model"

package com.diariocultural.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe que representa uma temporada de uma série.
 * É um objeto de valor que contém informações e regras de negócio próprias,
 * como número da temporada, anos, episódios e avaliações.
 */
public class Temporada {
    private int numero;
    private Integer anoLancamento;
    private Integer anoEncerramento;
    private int numeroEpisodios;
    private int avaliacao;
    private boolean consumido;
    private LocalDate dataConsumo;
    private Review review;

    /**
     * Construtor para a classe Temporada.
     * Valida todos os parâmetros recebidos através de seus setters.
     *
     * @param numero O número da temporada (deve ser maior que zero).
     * @param anoLancamento O ano de lançamento da temporada.
     * @param anoEncerramento O ano de encerramento da temporada (pode ser nulo).
     * @param numeroEpisodios O número de episódios (deve ser maior que zero).
     * @throws IllegalArgumentException se qualquer um dos parâmetros for inválido.
     */
    public Temporada(int numero, Integer anoLancamento, Integer anoEncerramento, int numeroEpisodios) {
        this.setNumero(numero);
        this.setAnoLancamento(anoLancamento);
        this.setAnoEncerramento(anoEncerramento);
        this.setNumeroEpisodios(numeroEpisodios);
        this.avaliacao = 0;
        this.consumido = false;
        this.dataConsumo = null;
        this.review = null;
    }

    /**
     * Retorna o número da temporada.
     * @return O número da temporada.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Retorna o ano de lançamento da temporada.
     * @return O ano de lançamento, ou null se não definido.
     */
    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    /**
     * Retorna o ano de encerramento da temporada.
     * @return O ano de encerramento, ou null se não definido ou em andamento.
     */
    public Integer getAnoEncerramento() {
        return anoEncerramento;
    }

    /**
     * Retorna o número de episódios da temporada.
     * @return O número de episódios.
     */
    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }

    /**
     * Retorna a avaliação da temporada.
     * @return A avaliação (1-10), ou 0 se não avaliada.
     */
    public int getAvaliacao() {
        return avaliacao;
    }

    /**
     * Verifica se a temporada foi consumida.
     * @return true se foi consumida, false caso contrário.
     */
    public boolean isConsumido() {
        return consumido;
    }

    /**
     * Retorna a data de consumo da temporada.
     * @return A data de consumo.
     */
    public LocalDate getDataConsumo() {
        return dataConsumo;
    }

    /**
     * Retorna a review da temporada.
     * @return O objeto Review, ou null se não houver.
     */
    public Review getReview() {
        return review;
    }

    /**
     * Retorna o texto da review da temporada.
     * @return O texto da review, ou null se não houver.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    /**
     * Define o número da temporada.
     * @param numero O número da temporada.
     * @throws IllegalArgumentException se o número for menor ou igual a zero.
     */
    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da temporada deve ser positivo.");
        }
        this.numero = numero;
    }

    /**
     * Define o ano de lançamento da temporada.
     * @param anoLancamento O ano de lançamento da temporada.
     */
    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    /**
     * Define o ano de encerramento da temporada.
     * @param anoEncerramento O ano de encerramento da temporada.
     * @throws IllegalArgumentException se o ano de encerramento for anterior ao de lançamento.
     */
    public void setAnoEncerramento(Integer anoEncerramento) {
        if (this.anoLancamento != null && anoEncerramento != null && anoEncerramento < this.anoLancamento) {
            throw new IllegalArgumentException("O ano de encerramento não pode ser anterior ao ano de lançamento.");
        }
        this.anoEncerramento = anoEncerramento;
    }

    /**
     * Define o número de episódios da temporada.
     * @param numeroEpisodios O número de episódios.
     * @throws IllegalArgumentException se o número de episódios for menor ou igual a zero.
     */
    public void setNumeroEpisodios(int numeroEpisodios) {
        if (numeroEpisodios <= 0) {
            throw new IllegalArgumentException("O número de episódios deve ser positivo.");
        }
        this.numeroEpisodios = numeroEpisodios;
    }

    /**
     * Define a avaliação da temporada (1 a 5).
     * Regra de negócio: ao avaliar, a temporada é marcada como consumida.
     * @param avaliacao A nota de avaliação.
     * @throws IllegalArgumentException se a avaliação estiver fora do intervalo válido.
     */
    public void setAvaliacao(int avaliacao) {
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A nota de avaliação deve ser um valor entre 1 e 5.");
        }
        this.avaliacao = avaliacao;
        this.setConsumido(true);
    }

    /**
     * Define o status de consumo da temporada.
     * Regra de negócio: se marcada como não consumida, reseta os dados de avaliação e review.
     * @param consumido O status de consumo.
     */
    public void setConsumido(boolean consumido) {
        this.consumido = consumido;
        if (!consumido) {
            this.dataConsumo = null;
            this.avaliacao = 0;
            this.review = null;
        }
    }

    /**
     * Define a data de consumo da temporada.
     * @param ano O ano de consumo.
     * @param mes O mês de consumo.
     * @param dia O dia de consumo.
     * @throws IllegalArgumentException se a data for inválida ou futura.
     */
    public void setDataConsumo(int ano, int mes, int dia) {
        try {
            LocalDate data = LocalDate.of(ano, mes, dia);
            this.setDataConsumo(data);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Data de consumo inválida: " + dia + "/" + mes + "/" + ano, e);
        }
    }

    /**
     * Define a data de consumo da temporada.
     * Regra de negócio: ao definir data, a temporada é marcada como consumida.
     * @param dataConsumo A data de consumo.
     * @throws IllegalArgumentException se a data for futura.
     */
    public void setDataConsumo(LocalDate dataConsumo) {
        if (dataConsumo != null && dataConsumo.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de consumo não pode ser uma data no futuro.");
        }
        this.dataConsumo = dataConsumo;
        if (this.dataConsumo != null) {
            this.setConsumido(true);
        }
    }

    /**
     * Define a review da temporada.
     * Regra de negócio: ao definir review, a temporada é marcada como consumida.
     * @param review O objeto Review.
     */
    public void setReview(Review review) {
        this.review = review;
        if (this.review != null) {
            this.setConsumido(true);
        }
    }

    /**
     * Define a review da temporada, criando um novo objeto Review.
     * @throws IllegalArgumentException se os dados da review forem inválidos.
     */
    public void setReviewTexto(String textoReview, int dia, int mes, int ano) {
        try {
            this.setReview(new Review(textoReview, dia, mes, ano));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Não foi possível criar a review: " + e.getMessage(), e);
        }
    }

    /**
     * Compara esta temporada com outro objeto para verificar se são iguais.
     * Duas temporadas são consideradas iguais se tiverem o mesmo número.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temporada temporada = (Temporada) o;
        return numero == temporada.numero;
    }

    /**
     * Retorna um código hash para a temporada, baseado em seu número.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    /**
     * Retorna uma representação em String do estado do objeto Temporada.
     * Este método é destinado para uso em logs e depuração.
     *
     * @return Uma String representando os campos do objeto.
     */
    @Override
    public String toString() {
        return "Temporada{" +
                "numero=" + numero +
                ", anoLancamento=" + anoLancamento +
                ", anoEncerramento=" + anoEncerramento +
                ", numeroEpisodios=" + numeroEpisodios +
                ", avaliacao=" + avaliacao +
                ", consumido=" + consumido +
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