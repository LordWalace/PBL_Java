// === ListaMidia.java === "Model"

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por listar e ordenar mídias (livros, filmes e séries).
 * Esta classe utiliza um Catálogo para obter as mídias e fornece métodos para listá-las
 * filtradas por gênero e ano, e separadas em avaliadas (5 a 1) e não avaliadas (0).
 */
public class ListaMidia {

    private Catalogo catalogo;

    /**
     * Construtor da classe ListaMidia.
     *
     * @param catalogo O catálogo que contém as listas de livros, filmes e séries.
     */
    public ListaMidia(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * Lista todos os livros, com opção de filtrar por gênero e ano, separando em avaliados (5 a 1) e não avaliados (0).
     *
     * @param genero O gênero dos livros a serem listados (pode ser null ou vazio para não filtrar).
     * @param ano    O ano de lançamento dos livros a serem listados (pode ser null para não filtrar).
     * @return Um array de duas listas de livros. A primeira lista contém os livros avaliados (5 a 1) e a segunda os não avaliados (0).
     */
    public List<Livro>[] listarLivros(String genero, Integer ano) {
        List<Livro> livrosFiltrados = catalogo.getLivros();

        List<Livro> livrosAvaliados = livrosFiltrados.stream()
                .filter(livro -> livro.getAvaliacao() > 0)
                .collect(Collectors.toList());
        List<Livro> livrosNaoAvaliados = livrosFiltrados.stream()
                .filter(livro -> livro.getAvaliacao() == 0)
                .collect(Collectors.toList());

        livrosAvaliados = ordenarLivros(livrosAvaliados, false);

        if (genero != null && !genero.isEmpty()) {
            livrosAvaliados = livrosAvaliados.stream()
                    .filter(livro -> livro.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
            livrosNaoAvaliados = livrosNaoAvaliados.stream()
                    .filter(livro -> livro.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            livrosAvaliados = livrosAvaliados.stream()
                    .filter(livro -> livro.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
            livrosNaoAvaliados = livrosNaoAvaliados.stream()
                    .filter(livro -> livro.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }

        return new List[]{livrosAvaliados, livrosNaoAvaliados};
    }

    /**
     * Lista todos os filmes, com opção de filtrar por gênero e ano, separando em avaliados (5 a 1) e não avaliados (0).
     *
     * @param genero O gênero dos filmes a serem listados (pode ser null ou vazio para não filtrar).
     * @param ano    O ano de lançamento dos filmes a serem listados (pode ser null para não filtrar).
     * @return Um array de duas listas de filmes. A primeira lista contém os filmes avaliados (5 a 1) e a segunda os não avaliados (0).
     */
    public List<Filme>[] listarFilmes(String genero, Integer ano) {
        List<Filme> filmesFiltrados = catalogo.getFilmes();

        List<Filme> filmesAvaliados = filmesFiltrados.stream()
                .filter(filme -> filme.getAvaliacao() > 0)
                .collect(Collectors.toList());
        List<Filme> filmesNaoAvaliados = filmesFiltrados.stream()
                .filter(filme -> filme.getAvaliacao() == 0)
                .collect(Collectors.toList());

        filmesAvaliados = ordenarFilmes(filmesAvaliados, false);

        if (genero != null && !genero.isEmpty()) {
            filmesAvaliados = filmesAvaliados.stream()
                    .filter(filme -> filme.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
            filmesNaoAvaliados = filmesNaoAvaliados.stream()
                    .filter(filme -> filme.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            filmesAvaliados = filmesAvaliados.stream()
                    .filter(filme -> filme.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
            filmesNaoAvaliados = filmesNaoAvaliados.stream()
                    .filter(filme -> filme.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }

        return new List[]{filmesAvaliados, filmesNaoAvaliados};
    }

    /**
     * Lista todas as séries, com opção de filtrar por gênero e ano, separando em avaliadas (5 a 1) e não avaliadas (0).
     * A avaliação de uma série é a média das avaliações de suas temporadas.
     *
     * @param genero O gênero das séries a serem listadas (pode ser null ou vazio para não filtrar).
     * @param ano    O ano de lançamento das séries a serem listadas (pode ser null para não filtrar).
     * @return Um array de duas listas de séries. A primeira lista contém as séries avaliadas (5 a 1) e a segunda as não avaliadas (0).
     */
    public List<Serie>[] listarSeries(String genero, Integer ano) {
        List<Serie> seriesFiltradas = catalogo.getSeries();

        List<Serie> seriesAvaliadas = seriesFiltradas.stream()
                .filter(serie -> calcularAvaliacaoMediaSerie(serie) > 0)
                .collect(Collectors.toList());
        List<Serie> seriesNaoAvaliadas = seriesFiltradas.stream()
                .filter(serie -> calcularAvaliacaoMediaSerie(serie) == 0)
                .collect(Collectors.toList());

        seriesAvaliadas = ordenarSeries(seriesAvaliadas, false);

        if (genero != null && !genero.isEmpty()) {
            seriesAvaliadas = seriesAvaliadas.stream()
                    .filter(serie -> serie.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
            seriesNaoAvaliadas = seriesNaoAvaliadas.stream()
                    .filter(serie -> serie.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            seriesAvaliadas = seriesAvaliadas.stream()
                    .filter(serie -> serie.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
            seriesNaoAvaliadas = seriesNaoAvaliadas.stream()
                    .filter(serie -> serie.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }
        return new List[]{seriesAvaliadas, seriesNaoAvaliadas};
    }

    /**
     * Ordena uma lista de livros por avaliação, de 5 estrelas a 1.
     *
     * @param livros     A lista de livros a serem ordenados.
     * @param ascendente Se true, a ordenação será ascendente (de 1 a 5). Se false, descendente (de 5 a 1).
     * @return A lista de livros ordenada por avaliação.
     */
    private List<Livro> ordenarLivros(List<Livro> livros, boolean ascendente) {
        livros.sort((l1, l2) -> {
            return Integer.compare(l2.getAvaliacao(), l1.getAvaliacao());
        });
        if (ascendente) {
            java.util.Collections.reverse(livros);
        }
        return livros;
    }

    /**
     * Ordena uma lista de filmes por avaliação, de 5 estrelas a 1.
     *
     * @param filmes     A lista de filmes a serem ordenados.
     * @param ascendente Se true, a ordenação será ascendente (de 1 a 5). Se false, descendente (de 5 a 1).
     * @return A lista de filmes ordenada por avaliação.
     */
    private List<Filme> ordenarFilmes(List<Filme> filmes, boolean ascendente) {
        filmes.sort((f1, f2) -> {
            return Integer.compare(f2.getAvaliacao(), f1.getAvaliacao());
        });
        if (ascendente) {
            java.util.Collections.reverse(filmes);
        }
        return filmes;
    }

    /**
     * Ordena uma lista de séries por avaliação média, de 5 estrelas a 1.
     * A avaliação de uma série é a média das avaliações de suas temporadas.
     *
     * @param series     A lista de séries a serem ordenadas.
     * @param ascendente Se true, a ordenação será ascendente (de 1 a 5). Se false, descendente (de 5 a 1).
     * @return A lista de séries ordenada por avaliação média.
     */
    private List<Serie> ordenarSeries(List<Serie> series, boolean ascendente) {
        series.sort((s1, s2) -> {
            return Double.compare(calcularAvaliacaoMediaSerie(s2), calcularAvaliacaoMediaSerie(s1));
        });
        if (ascendente) {
            java.util.Collections.reverse(series);
        }
        return series;
    }

    /**
     * Calcula a avaliação média de uma série, com base nas avaliações de suas temporadas.
     *
     * @param serie A série para a qual calcular a avaliação média.
     * @return A avaliação média da série. Se a série não tiver temporadas, ou nenhuma temporada avaliada, retorna 0.0.
     */
    private double calcularAvaliacaoMediaSerie(Serie serie) {
        if (serie.getTemporadasList().isEmpty()) {
            return 0.0;
        }
        double somaAvaliacoes = 0;
        int temporadasAvaliadas = 0;
        for (Temporada temporada : serie.getTemporadasList()) {
            if (temporada.getAvaliacao() != 0) {
                somaAvaliacoes += temporada.getAvaliacao();
                temporadasAvaliadas++;
            }
        }
        return temporadasAvaliadas > 0 ? somaAvaliacoes / temporadasAvaliadas : 0.0;
    }
}