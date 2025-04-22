import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

public class ListaMidia {

    private Catalogo catalogo;

    public ListaMidia(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public List<Livro> listarLivros(String genero, Integer ano) {
        List<Livro> livrosFiltrados = catalogo.getLivros().stream()
                .filter(Midia::isConsumido)
                .collect(Collectors.toList());

        livrosFiltrados = ordenarLivros(livrosFiltrados, false);

        if (genero != null && !genero.isEmpty()) {
            livrosFiltrados = livrosFiltrados.stream()
                    .filter(livro -> livro.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            livrosFiltrados = livrosFiltrados.stream()
                    .filter(livro -> livro.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }
        return livrosFiltrados;
    }

    public List<Filme> listarFilmes(String genero, Integer ano) {
        List<Filme> filmesFiltrados = catalogo.getFilmes().stream()
                .filter(Midia::isConsumido)
                .collect(Collectors.toList());

        filmesFiltrados = ordenarFilmes(filmesFiltrados, false);

        if (genero != null && !genero.isEmpty()) {
            filmesFiltrados = filmesFiltrados.stream()
                    .filter(filme -> filme.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            filmesFiltrados = filmesFiltrados.stream()
                    .filter(filme -> filme.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }
        return filmesFiltrados;
    }

    public List<Serie> listarSeries(String genero, Integer ano) {
        List<Serie> seriesFiltradas = catalogo.getSeries().stream()
                .filter(Midia::isConsumido)
                .collect(Collectors.toList());

        seriesFiltradas = ordenarSeries(seriesFiltradas, false);

        if (genero != null && !genero.isEmpty()) {
            seriesFiltradas = seriesFiltradas.stream()
                    .filter(serie -> serie.getGenero().equalsIgnoreCase(genero))
                    .collect(Collectors.toList());
        }

        if (ano != null) {
            seriesFiltradas = seriesFiltradas.stream()
                    .filter(serie -> serie.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        }
        return seriesFiltradas;
    }

    private List<Livro> ordenarLivros(List<Livro> livros, boolean ascendente) {
        livros.sort((l1, l2) -> {
            int avaliacaoComparison = Integer.compare(l2.getAvaliacao(), l1.getAvaliacao());
            if (avaliacaoComparison != 0) {
                return avaliacaoComparison;
            }
            return 0;
        });
        if (ascendente) {
            java.util.Collections.reverse(livros);
        }
        return livros;
    }

    private List<Filme> ordenarFilmes(List<Filme> filmes, boolean ascendente) {
        filmes.sort((f1, f2) -> {
            int avaliacaoComparison = Integer.compare(f2.getAvaliacao(), f1.getAvaliacao());
            if (avaliacaoComparison != 0) {
                return avaliacaoComparison;
            }
            return 0;
        });
        if (ascendente) {
            java.util.Collections.reverse(filmes);
        }
        return filmes;
    }

    private List<Serie> ordenarSeries(List<Serie> series, boolean ascendente) {
        series.sort((s1, s2) -> {
            int avaliacaoComparison = Double.compare(calcularAvaliacaoMediaSerie(s2), calcularAvaliacaoMediaSerie(s1));
            if (avaliacaoComparison != 0) {
                return avaliacaoComparison;
            }
            return 0;
        });
        if (ascendente) {
            java.util.Collections.reverse(series);
        }
        return series;
    }

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