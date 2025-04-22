// === BuscarMidia.java === "Model"

import java.util.List;
import java.util.stream.Collectors;

public class BuscarMidia {

    public List<Livro> buscarLivrosPorTitulo(List<Livro> livros, String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAutor(List<Livro> livros, String autor) {
        return livros.stream()
                .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorGenero(List<Livro> livros, String genero) {
        return livros.stream()
                .filter(l -> l.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAno(List<Livro> livros, int ano) {
        return livros.stream()
                .filter(l -> l.getAnoLancamento() == ano)
                .collect(Collectors.toList());
    }

    public Livro buscarLivroPorISBN(List<Livro> livros, String isbn) {
        return livros.stream()
                .filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Filme> buscarFilmesPorTitulo(List<Filme> filmes, String titulo) {
        return filmes.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorDiretor(List<Filme> filmes, String diretor) {
        return filmes.stream()
                .filter(f -> f.getDirecao().equalsIgnoreCase(diretor))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorAtor(List<Filme> filmes, String ator) {
        return filmes.stream()
                .filter(f -> f.getElenco().stream().anyMatch(a -> a.equalsIgnoreCase(ator)))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorGenero(List<Filme> filmes, String genero) {
        return filmes.stream()
                .filter(f -> f.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorAno(List<Filme> filmes, int ano) {
        return filmes.stream()
                .filter(f -> f.getAnoLancamento() == ano)
                .collect(Collectors.toList());
    }

    public List<Serie> buscarSeriesPorTitulo(List<Serie> series, String titulo) {
        return series.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    public Serie buscarSeriePorTitulo(List<Serie> series, String titulo) {
        return series.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }
}