// === Catalogo.java === "Model"

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa o catálogo de mídias (livros, filmes e séries) da aplicação.
 * Esta classe é responsável por armazenar e gerenciar as listas de diferentes tipos de mídia,
 * bem como fornecer métodos para adicionar e remover mídias do catálogo, separando-as
 * em avaliadas e não avaliadas.
 */
public class Catalogo {
    private final List<Livro> livrosAvaliados;
    private final List<Livro> livrosNaoAvaliados;
    private final List<Filme> filmesAvaliados;
    private final List<Filme> filmesNaoAvaliados;
    private final List<Serie> seriesAvaliadas;
    private final List<Serie> seriesNaoAvaliadas;
    private final BuscarMidia buscarMidia;

    /**
     * Construtor da classe Catalogo.
     * Inicializa as listas de livros, filmes e séries avaliadas e não avaliadas,
     * e a instância de BuscarMidia.
     */
    public Catalogo() {
        this.livrosAvaliados = new ArrayList<>();
        this.livrosNaoAvaliados = new ArrayList<>();
        this.filmesAvaliados = new ArrayList<>();
        this.filmesNaoAvaliados = new ArrayList<>();
        this.seriesAvaliadas = new ArrayList<>();
        this.seriesNaoAvaliadas = new ArrayList<>();
        this.buscarMidia = new BuscarMidia();
    }

    /**
     * Obtém a lista de livros do catálogo.
     * @return A lista combinada de livros avaliados e não avaliados.
     */
    public List<Livro> getLivros() {
        List<Livro> todosLivros = new ArrayList<>();
        todosLivros.addAll(this.livrosAvaliados);
        todosLivros.addAll(this.livrosNaoAvaliados);
        return todosLivros;
    }

    /**
     * Obtém a lista de filmes do catálogo.
     * @return A lista combinada de filmes avaliados e não avaliados.
     */
    public List<Filme> getFilmes() {
        List<Filme> todosFilmes = new ArrayList<>();
        todosFilmes.addAll(this.filmesAvaliados);
        todosFilmes.addAll(this.filmesNaoAvaliados);
        return todosFilmes;
    }

    /**
     * Obtém a lista de séries do catálogo.
     * @return A lista combinada de séries avaliadas e não avaliados.
     */
    public List<Serie> getSeries() {
        List<Serie> todasSeries = new ArrayList<>();
        todasSeries.addAll(this.seriesAvaliadas);
        todasSeries.addAll(this.seriesNaoAvaliadas);
        return todasSeries;
    }

    /**
     * Adiciona um livro ao catálogo, separando-o em avaliado ou não avaliado.
     *
     * @param livro O livro a ser adicionado.
     * @return true se o livro foi adicionado com sucesso, false se já existir um livro com o mesmo título.
     */
    public boolean adicionarLivro(Livro livro) {
        if (buscarMidia.buscarLivrosPorTitulo(getLivros(), livro.getTitulo()).isEmpty()) {
            if (livro.getAvaliacao() > 0) {
                this.livrosAvaliados.add(livro);
            } else {
                this.livrosNaoAvaliados.add(livro);
            }
            return true;
        }
        return false;
    }

    /**
     * Adiciona um filme ao catálogo, separando-o em avaliado ou não avaliado.
     *
     * @param filme O filme a ser adicionado.
     * @return true se o filme foi adicionado com sucesso, false se já existir um filme com o mesmo título.
     */
    public boolean adicionarFilme(Filme filme) {
        if (buscarMidia.buscarFilmesPorTitulo(getFilmes(), filme.getTitulo()).isEmpty()) {
            if (filme.getAvaliacao() > 0) {
                this.filmesAvaliados.add(filme);
            } else {
                this.filmesNaoAvaliados.add(filme);
            }
            return true;
        }
        return false;
    }

    /**
     * Adiciona uma série ao catálogo, separando-a em avaliada ou não avaliada.
     *
     * @param serie A série a ser adicionada.
     * @return true se a série foi adicionada com sucesso, false se já existir uma série com o mesmo título.
     */
    public boolean adicionarSerie(Serie serie) {
        if (buscarMidia.buscarSeriesPorTitulo(getSeries(), serie.getTitulo()).isEmpty()) {
            if (calcularAvaliacaoMediaSerie(serie) > 0) {
                this.seriesAvaliadas.add(serie);
            } else {
                this.seriesNaoAvaliadas.add(serie);
            }
            return true;
        }
        return false;
    }

    /**
     * Calcula a avaliação média de uma série, com base nas avaliações de suas temporadas.
     *
     * @param serie A série para a qual calcular a avaliação média.
     * @return A avaliação média da série. Se a série não tiver temporadas, ou nenhuma temporada avaliada, retorna 0.0.
     */
    private double calcularAvaliacaoMediaSerie(Serie serie) {
        if (serie.getTemporadasList() == null || serie.getTemporadasList().isEmpty()) {
            return 0.0;
        }
        double somaAvaliacoes = 0;
        int temporadasAvaliadas = 0;
        for (Temporada temporada : serie.getTemporadasList()) {
            if (temporada.getAvaliacao() > 0) { // Alterado para > 0
                somaAvaliacoes += temporada.getAvaliacao();
                temporadasAvaliadas++;
            }
        }
        return temporadasAvaliadas > 0 ? somaAvaliacoes / temporadasAvaliadas : 0.0;
    }

    /**
     * Remove um livro do catálogo pelo título.
     *
     * @param titulo O título do livro a ser removido.
     * @return true se o livro foi removido com sucesso, false se não foi encontrado.
     */
    public boolean removerLivro(String titulo) {
        for (Livro livro : livrosAvaliados) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livrosAvaliados.remove(livro);
                return true;
            }
        }
        for (Livro livro : livrosNaoAvaliados) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livrosNaoAvaliados.remove(livro);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove um filme do catálogo pelo título.
     *
     * @param titulo O título do filme a ser removido.
     * @return true se o filme foi removido com sucesso, false se não foi encontrado.
     */
    public boolean removerFilme(String titulo) {
        for (Filme filme : filmesAvaliados) {
            if (filme.getTitulo().equalsIgnoreCase(titulo)) {
                filmesAvaliados.remove(filme);
                return true;
            }
        }
        for (Filme filme : filmesNaoAvaliados) {
            if (filme.getTitulo().equalsIgnoreCase(titulo)) {
                filmesNaoAvaliados.remove(filme);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove uma série do catálogo pelo título.
     *
     * @param titulo O título da série a ser removida.
     * @return true se a série foi removida com sucesso, false se não foi encontrada.
     */
    public boolean removerSerie(String titulo) {
        for (Serie serie : seriesAvaliadas) {
            if (serie.getTitulo().equalsIgnoreCase(titulo)) {
                seriesAvaliadas.remove(serie);
                return true;
            }
        }
        for (Serie serie : seriesNaoAvaliadas) {
            if (serie.getTitulo().equalsIgnoreCase(titulo)) {
                seriesNaoAvaliadas.remove(serie);
                return true;
            }
        }
        return false;
    }

    // Métodos para obter as listas de avaliados e não avaliados

    public List<Livro> getLivrosAvaliados() {
        return livrosAvaliados;
    }

    public List<Livro> getLivrosNaoAvaliados() {
        return livrosNaoAvaliados;
    }

    public List<Filme> getFilmesAvaliados() {
        return filmesAvaliados;
    }

    public List<Filme> getFilmesNaoAvaliados() {
        return filmesNaoAvaliados;
    }

    public List<Serie> getSeriesAvaliadas() {
        return seriesAvaliadas;
    }

    public List<Serie> getSeriesNaoAvaliados() {
        return seriesNaoAvaliadas;
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