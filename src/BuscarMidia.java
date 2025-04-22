// === BuscarMidia.java === "Model"

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe responsável por implementar a lógica de busca de mídias (livros, filmes e séries).
 * Esta classe fornece métodos para buscar mídias por diferentes critérios, como título, autor, gênero, etc.
 */
public class BuscarMidia {

    /**
     * Busca livros por título.
     *
     * @param livros A lista de livros na qual realizar a busca.
     * @param titulo O título do livro a ser buscado.
     * @return Uma lista de livros que correspondem ao título fornecido.
     */
    public List<Livro> buscarLivrosPorTitulo(List<Livro> livros, String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por autor.
     *
     * @param livros A lista de livros na qual realizar a busca.
     * @param autor O autor dos livros a serem buscados.
     * @return Uma lista de livros que correspondem ao autor fornecido.
     */
    public List<Livro> buscarLivrosPorAutor(List<Livro> livros, String autor) {
        return livros.stream()
                .filter(l -> l.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por gênero.
     *
     * @param livros A lista de livros na qual realizar a busca.
     * @param genero O gênero dos livros a serem buscados.
     * @return Uma lista de livros que correspondem ao gênero fornecido.
     */
    public List<Livro> buscarLivrosPorGenero(List<Livro> livros, String genero) {
        return livros.stream()
                .filter(l -> l.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por ano de lançamento.
     *
     * @param livros A lista de livros na qual realizar a busca.
     * @param ano O ano de lançamento dos livros a serem buscados.
     * @return Uma lista de livros que correspondem ao ano de lançamento fornecido.
     */
    public List<Livro> buscarLivrosPorAno(List<Livro> livros, int ano) {
        return livros.stream()
                .filter(l -> l.getAnoLancamento() == ano)
                .collect(Collectors.toList());
    }

    /**
     * Busca um livro por ISBN.
     *
     * @param livros A lista de livros na qual realizar a busca.
     * @param isbn O ISBN do livro a ser buscado.
     * @return O livro correspondente ao ISBN fornecido, ou null se não for encontrado.
     */
    public Livro buscarLivroPorISBN(List<Livro> livros, String isbn) {
        return livros.stream()
                .filter(l -> l.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca filmes por título.
     *
     * @param filmes A lista de filmes na qual realizar a busca.
     * @param titulo O título do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao título fornecido.
     */
    public List<Filme> buscarFilmesPorTitulo(List<Filme> filmes, String titulo) {
        return filmes.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por diretor.
     *
     * @param filmes A lista de filmes na qual realizar a busca.
     * @param diretor O diretor dos filmes a serem buscados.
     * @return Uma lista de filmes que correspondem ao diretor fornecido.
     */
    public List<Filme> buscarFilmesPorDiretor(List<Filme> filmes, String diretor) {
        return filmes.stream()
                .filter(f -> f.getDirecao().equalsIgnoreCase(diretor))
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por ator.
     *
     * @param filmes A lista de filmes na qual realizar a busca.
     * @param ator O ator dos filmes a serem buscados.
     * @return Uma lista de filmes que contenham o ator fornecido no elenco.
     */
    public List<Filme> buscarFilmesPorAtor(List<Filme> filmes, String ator) {
        return filmes.stream()
                .filter(f -> f.getElenco().stream().anyMatch(a -> a.equalsIgnoreCase(ator)))
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por gênero.
     *
     * @param filmes A lista de filmes na qual realizar a busca.
     * @param genero O gênero dos filmes a serem buscados.
     * @return Uma lista de filmes que correspondem ao gênero fornecido.
     */
    public List<Filme> buscarFilmesPorGenero(List<Filme> filmes, String genero) {
        return filmes.stream()
                .filter(f -> f.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por ano de lançamento.
     *
     * @param filmes A lista de filmes na qual realizar a busca.
     * @param ano O ano de lançamento dos filmes a serem buscados.
     * @return Uma lista de filmes que correspondem ao ano de lançamento fornecido.
     */
    public List<Filme> buscarFilmesPorAno(List<Filme> filmes, int ano) {
        return filmes.stream()
                .filter(f -> f.getAnoLancamento() == ano)
                .collect(Collectors.toList());
    }

    /**
     * Busca séries por título.
     *
     * @param series A lista de séries na qual realizar a busca.
     * @param titulo O título da série a ser buscada.
     * @return Uma lista de séries que correspondem ao título fornecido.
     */
    public List<Serie> buscarSeriesPorTitulo(List<Serie> series, String titulo) {
        return series.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    /**
     * Busca uma série por título.
     *
     * @param series A lista de séries na qual realizar a busca.
     * @param titulo O título da série a ser buscada.
     * @return A série correspondente ao título fornecido, ou null se não for encontrada.
     */
    public Serie buscarSeriePorTitulo(List<Serie> series, String titulo) {
        return series.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
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