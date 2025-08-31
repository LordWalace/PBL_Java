// === Filme.java === "Model"

package com.diariocultural.model;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * Classe que representa um filme.
 * Herda da classe Midia e contém informações e regras de negócio
 * específicas sobre filmes, como tempo de duração, direção, roteiro e elenco.
 */
public class Filme extends Midia {
    private Duration tempoDeDuracao;
    private String direcao;
    private String roteiro;
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;

    /**
     * Construtor para a classe Filme.
     * Valida todos os parâmetros recebidos através dos setters.
     *
     * @param titulo O título do filme.
     * @param genero O gênero do filme.
     * @param anoLancamento O ano de lançamento do filme.
     * @param tempoDeDuracao O tempo de duração do filme em minutos.
     * @param direcao O(s) diretor(es) do filme.
     * @param roteiro O(s) roteirista(s) do filme.
     * @param elenco A lista com o elenco principal do filme.
     * @param tituloOriginal O título original do filme.
     * @param ondeAssistir O serviço onde o filme pode ser assistido.
     * @throws IllegalArgumentException se qualquer um dos parâmetros obrigatórios for inválido.
     */
    public Filme(String titulo, String genero, int anoLancamento, int tempoDeDuracao, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.setTempoDeDuracao(Duration.ofMinutes(tempoDeDuracao));
        this.setDirecao(direcao);
        this.setRoteiro(roteiro);
        this.setElenco(elenco);
        this.setTituloOriginal(tituloOriginal);
        this.setOndeAssistir(ondeAssistir);
    }

    /**
     * Retorna o tempo de duração do filme.
     * @return um objeto Duration representando o tempo de duração.
     */
    public Duration getTempoDeDuracao() {
        return tempoDeDuracao;
    }

    /**
     * Retorna o(s) diretor(es) do filme.
     * @return O(s) diretor(es) do filme.
     */
    public String getDirecao() { return direcao; }

    /**
     * Retorna o(s) roteirista(s) do filme.
     * @return O(s) roteirista(s) do filme.
     */
    public String getRoteiro() { return roteiro; }

    /**
     * Retorna o elenco do filme.
     * @return Uma lista com o elenco principal.
     */
    public List<String> getElenco() { return elenco; }

    /**
     * Retorna o título original do filme.
     * @return O título original do filme.
     */
    public String getTituloOriginal() { return tituloOriginal; }

    /**
     * Retorna onde o filme pode ser assistido.
     * @return O serviço ou local onde o filme está disponível.
     */
    public String getOndeAssistir() { return ondeAssistir; }

    /**
     * Define o tempo de duração do filme.
     * @param tempoDeDuracao A duração como um objeto Duration.
     * @throws IllegalArgumentException se a duração for nula, negativa ou zero.
     */
    public void setTempoDeDuracao(Duration tempoDeDuracao) {
        if (tempoDeDuracao == null || tempoDeDuracao.isNegative() || tempoDeDuracao.isZero()) {
            throw new IllegalArgumentException("O tempo de duração deve ser um valor positivo.");
        }
        this.tempoDeDuracao = tempoDeDuracao;
    }

    /**
     * Define o(s) diretor(es) do filme.
     * @param direcao O nome do(s) diretor(es).
     * @throws IllegalArgumentException se o campo direção for nulo ou vazio.
     */
    public void setDirecao(String direcao) {
        if (direcao == null || direcao.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'direção' não pode ser nulo ou vazio.");
        }
        this.direcao = direcao;
    }

    /**
     * Define o(s) roteirista(s) do filme.
     * @param roteiro O nome do(s) roteirista(s).
     * @throws IllegalArgumentException se o campo roteiro for nulo ou vazio.
     */
    public void setRoteiro(String roteiro) {
        if (roteiro == null || roteiro.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo 'roteiro' não pode ser nulo ou vazio.");
        }
        this.roteiro = roteiro;
    }

    /**
     * Define o elenco principal do filme.
     * @param elenco Uma lista com os nomes dos atores/atrizes.
     * @throws IllegalArgumentException se a lista do elenco for nula ou vazia.
     */
    public void setElenco(List<String> elenco) {
        if (elenco == null || elenco.isEmpty()) {
            throw new IllegalArgumentException("A lista do elenco não pode ser nula ou vazia.");
        }
        this.elenco = elenco;
    }

    /**
     * Define o título original do filme.
     * @param tituloOriginal O título original.
     * @throws IllegalArgumentException se o título original for nulo ou vazio.
     */
    public void setTituloOriginal(String tituloOriginal) {
        if (tituloOriginal == null || tituloOriginal.trim().isEmpty()) {
            throw new IllegalArgumentException("O título original não pode ser nulo ou vazio.");
        }
        this.tituloOriginal = tituloOriginal;
    }

    /**
     * Define onde o filme pode ser assistido.
     * @param ondeAssistir O serviço de streaming ou mídia.
     */
    public void setOndeAssistir(String ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Compara este filme com outro objeto para verificar se são iguais.
     * Dois filmes são considerados iguais se tiverem o mesmo título, ano de lançamento e direção.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return getAnoLancamento() == filme.getAnoLancamento() &&
                Objects.equals(getTitulo(), filme.getTitulo()) &&
                Objects.equals(getDirecao(), filme.getDirecao());
    }

    /**
     * Retorna um código hash para o filme, baseado no título, ano de lançamento e direção.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getAnoLancamento(), getDirecao());
    }

    /**
     * Retorna uma representação em String do estado do objeto Filme.
     * Este método é destinado para uso em logs e depuração, não para a interface do usuário.
     *
     * @return Uma String representando os campos do objeto.
     */
    @Override
    public String toString() {
        return "Filme{" +
                "titulo='" + getTitulo() + '\'' +
                ", direcao='" + direcao + '\'' +
                ", anoLancamento=" + getAnoLancamento() +
                ", duracao=" + (tempoDeDuracao != null ? tempoDeDuracao.toMinutes() : 0) + " min" +
                ", genero='" + getGenero() + '\'' +
                ", avaliacao=" + getAvaliacao() +
                ", consumido=" + isConsumido() +
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