// Controller
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiarioCultural {
    private List<Livro> livros = new ArrayList<>();
    private List<Filme> filmes = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    public void cadastrarLivro(String titulo, String autor, String editora, String isbn, int ano, String genero, boolean possuiExemplar) {
        livros.add(new Livro(titulo, autor, editora, isbn, ano, genero, possuiExemplar));
    }

    public void cadastrarFilme(String titulo, String genero, int ano, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        filmes.add(new Filme(titulo, genero, ano, direcao, roteiro, elenco, tituloOriginal, ondeAssistir));
    }

    public void cadastrarSerie(String titulo, String genero, int ano, int temporadas, Map<Integer, Integer> episodiosPorTemporada, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        series.add(new Serie(titulo, genero, ano, temporadas, episodiosPorTemporada, elenco, tituloOriginal, ondeAssistir));
    }

    public boolean avaliarLivro(String titulo, int nota, String review, String dataConsumo) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equalsIgnoreCase(titulo)) {
                livro.setConsumido(true);
                livro.setAvaliacao(nota);
                livro.setReview(review);
                livro.setDataConsumo(dataConsumo);
                return true;
            }
        }
        return false;
    }

    public boolean avaliarFilme(String titulo, int nota, String dataConsumo) {
        for (Filme filme : filmes) {
            if (filme.getTitulo().equalsIgnoreCase(titulo)) {
                filme.setConsumido(true);
                filme.setAvaliacao(nota);
                filme.setDataConsumo(dataConsumo);
                return true;
            }
        }
        return false;
    }

    public boolean avaliarSerie(String titulo, Map<Integer, Integer> avaliacoesPorTemporada, String dataConsumo) {
        for (Serie serie : series) {
            if (serie.getTitulo().equalsIgnoreCase(titulo)) {
                serie.setConsumido(true);
                serie.avaliarTemporadas(avaliacoesPorTemporada);
                serie.setDataConsumo(dataConsumo);
                return true;
            }
        }
        return false;
    }

    public void listarLivros() {
        livros.forEach(System.out::println);
    }

    public void listarFilmes() {
        filmes.forEach(System.out::println);
    }

    public void listarSeries() {
        series.forEach(System.out::println);
    }

    public Livro buscarLivroPorTitulo(String titulo) {
        return livros.stream().filter(l -> l.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }

    public Filme buscarFilmePorTitulo(String titulo) {
        return filmes.stream().filter(f -> f.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }

    public Serie buscarSeriePorTitulo(String titulo) {
        return series.stream().filter(s -> s.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }
}