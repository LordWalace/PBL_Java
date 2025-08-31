// === Midia.java === "Model"

package com.diariocultural.model;

import java.time.LocalDate;

/**
 * Classe abstrata que representa uma mídia.
 * Esta classe é a base para Livro, Filme e Série, contendo os atributos
 * e regras de negócio comuns a todas as mídias. Garante que os dados
 * de uma mídia sejam sempre consistentes através de validações internas.
 * O modelo é responsável por seus dados e regras, e comunica erros
 * através do lançamento de exceções.
 */
public abstract class Midia {
    protected String titulo;
    protected String genero;
    protected int anoLancamento;
    protected int avaliacao;
    protected boolean consumido;
    protected LocalDate dataConsumo;
    protected Review review;

    /**
     * Construtor da classe Midia.
     * Utiliza os setters para garantir que todos os dados sejam validados no momento da criação do objeto.
     *
     * @param titulo O título da mídia.
     * @param genero O gênero da mídia.
     * @param anoLancamento O ano de lançamento da mídia.
     * @throws IllegalArgumentException se qualquer um dos parâmetros for inválido.
     */
    public Midia(String titulo, String genero, int anoLancamento) {
        this.setTitulo(titulo);
        this.setGenero(genero);
        this.setAnoLancamento(anoLancamento);
        this.avaliacao = 0;
        this.consumido = false;
        this.review = null;
    }

    /**
     * Retorna o título da mídia.
     * @return O título da mídia.
     */
    public String getTitulo() { return titulo; }

    /**
     * Retorna o gênero da mídia.
     * @return O gênero da mídia.
     */
    public String getGenero() { return genero; }

    /**
     * Retorna o ano de lançamento da mídia.
     * @return O ano de lançamento da mídia.
     */
    public int getAnoLancamento() { return anoLancamento; }

    /**
     * Retorna a avaliação da mídia.
     * @return A avaliação da mídia (0 se não avaliada, 1-10 se avaliada).
     */
    public int getAvaliacao() { return avaliacao; }

    /**
     * Verifica se a mídia foi consumida.
     * @return true se a mídia foi consumida, false caso contrário.
     */
    public boolean isConsumido() { return consumido; }

    /**
     * Retorna a data de consumo da mídia.
     * @return A data de consumo da mídia.
     */
    public LocalDate getDataConsumo() { return dataConsumo; }

    /**
     * Retorna a review associada à mídia.
     * @return O objeto Review, ou null se não houver.
     */
    public Review getReview() { return review; }

    /**
     * Retorna o texto da review da mídia.
     * @return O texto da review, ou null se não houver review.
     */
    public String getReviewTexto() {
        return this.review != null ? this.review.getTexto() : null;
    }

    /**
     * Define o título da mídia.
     * @param titulo O título a ser definido.
     * @throws IllegalArgumentException se o título for nulo ou vazio.
     */
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode ser nulo ou vazio.");
        }
        this.titulo = titulo;
    }

    /**
     * Define o gênero da mídia.
     * @param genero O gênero a ser definido.
     * @throws IllegalArgumentException se o gênero for nulo ou vazio.
     */
    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero não pode ser nulo ou vazio.");
        }
        this.genero = genero;
    }

    /**
     * Define o ano de lançamento da mídia.
     * @param anoLancamento O ano de lançamento.
     * @throws IllegalArgumentException se o ano for inválido (menor que 1888 ou muito no futuro).
     */
    public void setAnoLancamento(int anoLancamento) {
        if (anoLancamento < 1888 || anoLancamento > LocalDate.now().getYear() + 2) {
            throw new IllegalArgumentException("Ano de lançamento inválido: " + anoLancamento);
        }
        this.anoLancamento = anoLancamento;
    }

    /**
     * Define a avaliação da mídia. A avaliação deve estar entre 1 e 10.
     * Regra de negócio: ao avaliar uma mídia, ela é automaticamente marcada como consumida.
     * @param avaliacao A nota de avaliação.
     * @throws IllegalArgumentException se a avaliação estiver fora do intervalo válido (1-5).
     */
    public void setAvaliacao(int avaliacao) {
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A nota de avaliação deve ser um valor entre 1 e 5.");
        }
        this.avaliacao = avaliacao;
        this.setConsumido(true);
    }

    /**
     * Define o status de consumo da mídia.
     * Regra de negócio: se uma mídia é marcada como "não consumida", sua data de consumo,
     * avaliação e review são removidos para manter a consistência do estado do objeto.
     * @param consumido O status de consumo (true se consumida, false caso contrário).
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
     * Define a data de consumo da mídia.
     * Regra de negócio: ao definir uma data de consumo, a mídia é marcada como consumida.
     * @param dataConsumo A data de consumo.
     * @throws IllegalArgumentException se a data de consumo for no futuro.
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
     * Define a review da mídia.
     * Regra de negócio: ao definir uma review, a mídia é automaticamente marcada como consumida.
     * @param review A review da mídia. A validação do conteúdo da review é delegada à própria classe Review.
     */
    public void setReview(Review review) {
        this.review = review;
        if (this.review != null) {
            this.setConsumido(true);
        }
    }

    /**
     * Define a review da mídia, criando um novo objeto Review.
     * @throws IllegalArgumentException se os dados da review forem inválidos (exceção lançada pelo construtor de Review).
     */
    public void setReviewTexto(String textoReview, int dia, int mes, int ano) {
        try {
            this.setReview(new Review(textoReview, dia, mes, ano));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Não foi possível criar a review: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna uma representação em String do estado do objeto Midia.
     * ESTE MÉTODO É DESTINADO APENAS PARA LOGS E DEPURAÇÃO PELO DESENVOLVEDOR.
     * @return Uma String representando os campos e valores do objeto.
     */
    @Override
    public String toString() {
        return "Midia{" +
                "tipo=" + this.getClass().getSimpleName() +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", anoLancamento=" + anoLancamento +
                ", avaliacao=" + avaliacao +
                ", consumido=" + consumido +
                ", dataConsumo=" + dataConsumo +
                ", review=" + (review != null ? "Presente" : "Ausente") +
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