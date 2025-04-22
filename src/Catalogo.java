// === Catalogo.java === "Model"

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private final List<Livro> livros;
    private final List<Filme> filmes;
    private final List<Serie> series;
    private final BuscarMidia buscarMidia;

    public Catalogo() {
        this.livros = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.series = new ArrayList<>();
        this.buscarMidia = new BuscarMidia(); // Cria uma instância de BuscarMidia
    }

    public boolean adicionarLivro(Livro livro) {
        if (buscarMidia.buscarLivrosPorTitulo(livros, livro.getTitulo()).isEmpty()) {
            this.livros.add(livro);
            return true;
        }
        return false;
    }

    public boolean adicionarFilme(Filme filme) {
        if (buscarMidia.buscarFilmesPorTitulo(filmes, filme.getTitulo()).isEmpty()) {
            this.filmes.add(filme);
            return true;
        }
        return false;
    }

    public boolean adicionarSerie(Serie serie) {
        if (buscarMidia.buscarSeriesPorTitulo(series, serie.getTitulo()).isEmpty()) {
            this.series.add(serie);
            return true;
        }
        return false;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public boolean removerLivro(String titulo) {
        return livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
    }

    public boolean removerFilme(String titulo) {
        return filmes.removeIf(f -> f.getTitulo().equalsIgnoreCase(titulo));
    }

    public boolean removerSerie(String titulo) {
        return series.removeIf(s -> s.getTitulo().equalsIgnoreCase(titulo));
    }
}