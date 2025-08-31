// === Serie.java === "Model"

package com.diariocultural.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Classe que representa uma série de TV.
 * Herda da classe Midia e gerencia uma coleção de objetos Temporada,
 * contendo regras de negócio específicas para séries.
 */
public class Serie extends Midia {
    private String tituloOriginal;
    private List<String> elenco;
    private String ondeAssistir;
    private final List<Temporada> temporadasList;

    /**
     * Construtor para a classe Serie.
     * Valida todos os parâmetros recebidos e inicializa a lista de temporadas.
     *
     * @param titulo O título da série.
     * @param genero O gênero da série.
     * @param anoLancamento O ano de lançamento da série.
     * @param elenco O elenco principal da série.
     * @param tituloOriginal O título original da série.
     * @param ondeAssistir O serviço de streaming ou local onde a série pode ser assistida.
     * @throws IllegalArgumentException se qualquer um dos parâmetros obrigatórios for inválido.
     */
    public Serie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        super(titulo, genero, anoLancamento);
        this.setElenco(elenco);
        this.setTituloOriginal(tituloOriginal);
        this.setOndeAssistir(ondeAssistir);
        this.temporadasList = new ArrayList<>();
    }

    /**
     * Retorna o título original da série.
     * @return O título original.
     */
    public String getTituloOriginal() {
        return tituloOriginal;
    }

    /**
     * Retorna o elenco da série.
     * @return Uma lista com o elenco principal.
     */
    public List<String> getElenco() {
        return elenco;
    }

    /**
     * Retorna onde a série pode ser assistida.
     * @return O serviço ou local onde a série está disponível.
     */
    public String getOndeAssistir() {
        return ondeAssistir;
    }

    /**
     * Retorna uma cópia não modificável da lista de temporadas da série.
     * @return A lista de temporadas.
     */
    public List<Temporada> getTemporadasList() {
        return Collections.unmodifiableList(temporadasList);
    }

    /**
     * Retorna o número de temporadas da série.
     * Este valor é derivado dinamicamente do tamanho da lista de temporadas.
     * @return O número total de temporadas.
     */
    public int getNumeroTemporadas() {
        return this.temporadasList.size();
    }

    /**
     * Define o elenco principal da série.
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
     * Define o título original da série.
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
     * Define onde a série pode ser assistida.
     * @param ondeAssistir O serviço de streaming ou mídia.
     */
    public void setOndeAssistir(String ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
    }

    /**
     * Adiciona uma temporada à série, se o número da temporada ainda não existir.
     * @param temporada A temporada a ser adicionada.
     * @throws IllegalArgumentException se a temporada for nula ou se uma temporada com o mesmo número já existir.
     */
    public void adicionarTemporada(Temporada temporada) {
        if (temporada == null) {
            throw new IllegalArgumentException("Não é possível adicionar uma temporada nula.");
        }
        boolean jaExiste = this.temporadasList.stream()
                .anyMatch(t -> t.getNumero() == temporada.getNumero());

        if (jaExiste) {
            throw new IllegalArgumentException("A temporada de número " + temporada.getNumero() + " já existe para esta série.");
        }
        this.temporadasList.add(temporada);
    }

    /**
     * Remove uma temporada da lista com base no seu número.
     * @param numeroTemporada O número da temporada a ser removida.
     * @return true se a temporada foi encontrada e removida, false caso contrário.
     */
    public boolean removerTemporada(int numeroTemporada) {
        return this.temporadasList.removeIf(t -> t.getNumero() == numeroTemporada);
    }

    /**
     * Verifica o estado de todas as temporadas e atualiza o status de consumo da série.
     * A série só é considerada consumida se todas as suas temporadas estiverem consumidas.
     */
    public void atualizarStatusConsumo() {
        if (temporadasList.isEmpty()) {
            this.setConsumido(false);
            return;
        }

        boolean todasConsumidas = temporadasList.stream().allMatch(Temporada::isConsumido);

        this.setConsumido(todasConsumidas);

        if (todasConsumidas) {
            temporadasList.stream()
                    .map(Temporada::getDataConsumo)
                    .filter(Objects::nonNull)
                    .max(LocalDate::compareTo)
                    .ifPresent(this::setDataConsumo);
        } else {
            this.setDataConsumo(null);
        }
    }

    /**
     * Busca uma temporada pelo seu número.
     * @param numeroTemporada O número da temporada a ser buscada.
     * @return um Optional contendo a Temporada se encontrada, ou um Optional vazio caso contrário.
     */
    public Optional<Temporada> getTemporada(int numeroTemporada) {
        return this.temporadasList.stream()
                .filter(t -> t.getNumero() == numeroTemporada)
                .findFirst();
    }

    /**
     * Calcula e retorna a avaliação geral da série, que é a média arredondada
     * das avaliações de todas as suas temporadas avaliadas.
     * Esta é uma sobrescrita necessária, pois a lógica de avaliação de uma série é única.
     *
     * @return A avaliação média da série (1-5), ou 0 se nenhuma temporada foi avaliada.
     */
    @Override
    public int getAvaliacao() {
        double media = temporadasList.stream()
                .filter(t -> t.getAvaliacao() > 0)
                .mapToInt(Temporada::getAvaliacao)
                .average()
                .orElse(0.0);

        return (int) Math.round(media);
    }

    /**
     * Compara esta série com outro objeto para verificar se são iguais.
     * Duas séries são consideradas iguais se tiverem o mesmo título e ano de lançamento.
     *
     * @param o O objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Serie serie = (Serie) o;
        return getAnoLancamento() == serie.getAnoLancamento() &&
                Objects.equals(getTitulo(), serie.getTitulo());
    }

    /**
     * Retorna um código hash para a série, baseado no título e ano de lançamento.
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getAnoLancamento());
    }

    /**
     * Retorna uma representação em String do estado do objeto Serie.
     * Este método é destinado para uso em logs e depuração.
     *
     * @return Uma String representando os campos do objeto.
     */
    @Override
    public String toString() {
        return "Serie{" +
                "titulo='" + getTitulo() + '\'' +
                ", anoLancamento=" + getAnoLancamento() +
                ", genero='" + getGenero() + '\'' +
                ", temporadas=" + getNumeroTemporadas() +
                ", avaliacaoGeral=" + getAvaliacao() +
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