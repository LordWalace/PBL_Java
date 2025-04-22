// === TestesUnitariosDiarioCultural.java ===

// Testes Unitários

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class TestesUnitariosDiarioCultural {
    private Livro livro1, livro2, livro3, livro4, livroNaoAvaliado;
    private Filme filme1, filme2, filmeNaoAvaliado, filme3;
    private Serie serie1, serieNaoAvaliada;
    private DiarioCultural diario;
    private Catalogo catalogo;
    private ListaMidia listaMidia;
    private BuscarMidia buscarMidia;

    @BeforeEach
    public void setUp() {
        catalogo = new Catalogo();
        listaMidia = new ListaMidia(catalogo);
        buscarMidia = new BuscarMidia();
        diario = new DiarioCultural(catalogo, listaMidia, buscarMidia);

        livro1 = new Livro("1984", "George Orwell", "Companhia das Letras", "978-0451524935", 1949, "Distopia", true);
        livro2 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", "Martins Fontes", "978-8578276377", 1954, "Fantasia", true);
        livro3 = new Livro("Neuromancer", "William Gibson", "Aleph", "978-8576570470", 1984, "Ficção Científica", true);
        livro4 = new Livro("A Revolução dos Bichos", "George Orwell", "Companhia das Letras", "978-8572328738", 1945, "Distopia", true);
        livroNaoAvaliado = new Livro("Admirável Mundo Novo", "Aldous Huxley", "Globo", "978-8525048473", 1932, "Distopia", true);

        List<String> elencoFilme1 = new ArrayList<>();
        elencoFilme1.add("Leonardo DiCaprio");
        elencoFilme1.add("Joseph Gordon-Levitt");
        filme1 = new Filme("Inception", "Sci-Fi", 2010, "Christopher Nolan", "Christopher Nolan", elencoFilme1, "Inception", "Netflix");
        List<String> elencoFilme2 = new ArrayList<>();
        elencoFilme2.add("Joseph Gordon-Levitt");
        elencoFilme2.add("Martins Fontes");
        filme2 = new Filme("Fight Club", "Drama", 1999, "David Fincher", "Jim Uhls", elencoFilme2, "Fight Club", "Prime Video");
        List<String> elencoFilmeNaoAvaliado = new ArrayList<>();
        elencoFilmeNaoAvaliado.add("Brad Pitt");
        elencoFilmeNaoAvaliado.add("Edward Norton");
        filmeNaoAvaliado = new Filme("Seven", "Suspense", 1995, "David Fincher", "Andrew Kevin Walker", elencoFilmeNaoAvaliado, "Se7en", "Prime Video");
        List<String> elencoFilme3 = new ArrayList<>();
        elencoFilme3.add("Leonardo DiCaprio");
        elencoFilme3.add("Kate Winslet");
        filme3 = new Filme("Titanic", "Drama", 1997, "James Cameron", "James Cameron", elencoFilme3, "Titanic", "Prime Video");

        List<String> elencoSerie1 = new ArrayList<>();
        elencoSerie1.add("Bryan Cranston");
        elencoSerie1.add("Aaron Paul");
        serie1 = new Serie("Breaking Bad", "Drama", 2008, elencoSerie1, "Breaking Bad", "Netflix");

        List<String> elencoSerieNaoAvaliada = new ArrayList<>();
        elencoSerieNaoAvaliada.add("David Schwimmer");
        elencoSerieNaoAvaliada.add("Lisa Kudrow");
        serieNaoAvaliada = new Serie("Friends", "Comédia", 1994, elencoSerieNaoAvaliada, "Friends", "HBO Max");


        catalogo.adicionarLivro(livro1);
        catalogo.adicionarLivro(livro2);
        catalogo.adicionarLivro(livro3);
        catalogo.adicionarLivro(livro4);
        catalogo.adicionarLivro(livroNaoAvaliado);
        catalogo.adicionarFilme(filme1);
        catalogo.adicionarFilme(filme2);
        catalogo.adicionarFilme(filme3);
        catalogo.adicionarFilme(filmeNaoAvaliado);
        catalogo.adicionarSerie(serie1);
        catalogo.adicionarSerie(serieNaoAvaliada);
    }

    @Test
    public void testCadastroLivro() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> livrosCadastrados = buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), livro1.getTitulo());
        assertFalse(livrosCadastrados.isEmpty());
        Livro livroCadastrado = livrosCadastrados.get(0);
        assertNotNull(livroCadastrado);
        assertEquals(livro1.getAutor(), livroCadastrado.getAutor());
        assertEquals(livro1.getIsbn(), livroCadastrado.getIsbn());
    }

    @Test
    public void testAvaliacaoLivro() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        assertFalse(diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente livro!", "01/01/2024"));
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        assertTrue(diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente livro!", "01/01/2024"));
        List<Livro> livrosAvaliados = buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), livro1.getTitulo());
        assertFalse(livrosAvaliados.isEmpty());
        Livro livroAvaliado = livrosAvaliados.get(0);
        assertEquals(5, livroAvaliado.getAvaliacao());
        assertEquals("Excelente livro!", livroAvaliado.getReviewTexto());
    }

    @Test
    public void testCadastroFilme() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesCadastrados = buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), filme1.getTitulo());
        assertFalse(filmesCadastrados.isEmpty());
        Filme filmeCadastrado = filmesCadastrados.get(0);
        assertNotNull(filmeCadastrado);
        assertEquals(filme1.getDirecao(), filmeCadastrado.getDirecao());
        assertEquals(filme1.getElenco(), filmeCadastrado.getElenco());
    }

    @Test
    public void testAvaliacaoFilme() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        assertFalse(diario.avaliarFilme(filme1.getTitulo(), 4, "Ótimo filme!", "02/02/2024"));
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "02/02/2024");
        assertTrue(diario.avaliarFilme(filme1.getTitulo(), 4, "Ótimo filme!", "02/02/2024"));
        List<Filme> filmesAvaliados = buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), filme1.getTitulo());
        assertFalse(filmesAvaliados.isEmpty());
        Filme filmeAvaliado = filmesAvaliados.get(0);
        assertEquals(4, filmeAvaliado.getAvaliacao());
        assertEquals("Ótimo filme!", filmeAvaliado.getReviewTexto());
    }

    @Test
    public void testCadastroSerieComTemporadas() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        assertTrue(diario.adicionarTemporadaSerie(serie1.getTitulo(), 1, 2008, 2008, 7));
        List<Serie> seriesCadastradas = buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), serie1.getTitulo());
        assertFalse(seriesCadastradas.isEmpty());
        Serie serieCadastrada = seriesCadastradas.get(0);
        assertNotNull(serieCadastrada);
        assertEquals(1, serieCadastrada.getNumeroTemporadas());
    }

    @Test
    public void testAvaliacaoSerieComTemporadas() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 1, 2008, 2008, 7);
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 2, 2009, 2009, 13);
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 1", "temporada", "03/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 1, 5, "Ótima", "03/03/2024");
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 2", "temporada", "04/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 2, 3, "Regular", "04/03/2024");

        diario.cadastrarSerie(serieNaoAvaliada.getTitulo(), serieNaoAvaliada.getGenero(), serieNaoAvaliada.getAnoLancamento(), serieNaoAvaliada.getElenco(), serieNaoAvaliada.getTituloOriginal(), serieNaoAvaliada.getOndeAssistir());
        diario.adicionarTemporadaSerie(serieNaoAvaliada.getTitulo(), 1, 1994, 1995, 24);
        diario.marcarComoConsumido(serieNaoAvaliada.getTitulo() + " - Temporada 1", "temporada", "10/03/2024");
        diario.avaliarTemporadaSerie(serieNaoAvaliada.getTitulo(), 1, 4, "Boa", "10/03/2024");


        List<Serie> seriesAvaliadas = buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), serie1.getTitulo());
        assertFalse(seriesAvaliadas.isEmpty());
        assertEquals((5 + 3) / 2.0, seriesAvaliadas.get(0).getAvaliacao());
    }

    @Test
    public void testBuscaLivroPorTitulo() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> resultados = diario.buscarLivrosPorTitulo("1984");
        assertEquals(1, resultados.size());
        assertEquals("1984", resultados.get(0).getTitulo());
        assertTrue(diario.buscarLivrosPorTitulo("Outro Livro").isEmpty());
    }

    @Test
    public void testBuscaLivroPorAutor() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.cadastrarLivro(livro4.getTitulo(), livro4.getAutor(), livro4.getEditora(), livro4.getIsbn(), livro4.getAnoLancamento(), livro4.getGenero(), livro4.isPossuiExemplar());
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente", "01/01/2024");
        diario.marcarComoConsumido(livro4.getTitulo(), "livro", "06/01/2024");
        diario.avaliarLivro(livro4.getTitulo(), 4, "Muito bom", "06/01/2024");
        List<Livro> resultados = diario.buscarLivrosPorAutor("George Orwell");
        assertEquals(2, resultados.size());
        assertEquals("1984", resultados.get(0).getTitulo());
        assertEquals("A Revolução dos Bichos", resultados.get(1).getTitulo());
    }

    @Test
    public void testBuscaLivroPorGenero() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.cadastrarLivro(livro4.getTitulo(), livro4.getAutor(), livro4.getEditora(), livro4.getIsbn(), livro4.getAnoLancamento(), livro4.getGenero(), livro4.isPossuiExemplar());
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente", "01/01/2024");
        diario.marcarComoConsumido(livro4.getTitulo(), "livro", "06/01/2024");
        diario.avaliarLivro(livro4.getTitulo(), 4, "Muito bom", "06/01/2024");
        List<Livro> resultados = diario.buscarLivrosPorGenero("Distopia");
        assertEquals(3, resultados.size());
        assertEquals("1984", resultados.get(0).getTitulo());
        assertEquals("A Revolução dos Bichos", resultados.get(1).getTitulo());
        assertEquals("Admirável Mundo Novo", resultados.get(2).getTitulo());
    }

    @Test
    public void testBuscaLivroPorAno() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente", "01/01/2024");
        List<Livro> resultados = diario.buscarLivrosPorAno(1949);
        assertEquals(1, resultados.size());
        assertEquals("1984", resultados.get(0).getTitulo());
    }

    @Test
    public void testBuscaLivroPorISBN() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> resultados = diario.buscarLivrosPorISBN("978-0451524935");
        assertEquals(1, resultados.size());
        assertEquals("1984", resultados.get(0).getTitulo());
    }

    @Test
    public void testBuscaFilmePorTitulo() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> resultados = diario.buscarFilmePorTitulo("Inception");
        assertEquals(1, resultados.size());
        assertEquals("Inception", resultados.get(0).getTitulo());
        assertTrue(diario.buscarFilmePorTitulo("Outro Filme").isEmpty());
    }

    @Test
    public void testBuscaFilmePorDiretor() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");
        List<Filme> resultados = diario.buscarFilmesPorDiretor("Christopher Nolan");
        assertEquals(1, resultados.size());
        assertEquals("Inception", resultados.get(0).getTitulo());
    }
    @Test
    public void testBuscaFilmePorAtor() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.cadastrarFilme(filme3.getTitulo(), filme3.getGenero(), filme3.getAnoLancamento(), filme3.getDirecao(), filme3.getRoteiro(), filme3.getElenco(), filme3.getTituloOriginal(), filme3.getOndeAssistir());
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");
        diario.marcarComoConsumido(filme3.getTitulo(), "filme", "07/02/2024");
        diario.avaliarFilme(filme3.getTitulo(), 3, "Ok filme", "07/02/2024");
        List<Filme> resultados = diario.buscarFilmesPorAtor("Leonardo DiCaprio");
        assertEquals(2, resultados.size());
        assertEquals("Inception", resultados.get(0).getTitulo());
        assertEquals("Titanic", resultados.get(1).getTitulo());
    }

    @Test
    public void testBuscaFilmePorGenero() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");
        List<Filme> resultados = diario.buscarFilmesPorGenero("Sci-Fi");
        assertEquals(1, resultados.size());
        assertEquals("Inception", resultados.get(0).getTitulo());
    }
    @Test
    public void testBuscaFilmePorAno() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");
        List<Filme> resultados = diario.buscarFilmesPorAno(2010);
        assertEquals(1, resultados.size());
        assertEquals("Inception", resultados.get(0).getTitulo());
    }

    @Test
    public void testBuscaSeriePorTitulo() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        List<Serie> resultados = diario.buscarSeriePorTitulo("Breaking Bad");
        assertEquals(1, resultados.size());
        assertEquals("Breaking Bad", resultados.get(0).getTitulo());
        assertTrue(diario.buscarSeriePorTitulo("Outra Série").isEmpty());
    }

    @Test
    public void testListarLivrosOrdenadoPorAvaliacao() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.cadastrarLivro(livro2.getTitulo(), livro2.getAutor(), livro2.getEditora(), livro2.getIsbn(), livro2.getAnoLancamento(), livro2.getGenero(), livro2.isPossuiExemplar());
        diario.cadastrarLivro(livroNaoAvaliado.getTitulo(), livroNaoAvaliado.getAutor(), livroNaoAvaliado.getEditora(), livroNaoAvaliado.getIsbn(), livroNaoAvaliado.getAnoLancamento(), livroNaoAvaliado.getGenero(), livroNaoAvaliado.isPossuiExemplar());
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente", "01/01/2024");
        diario.marcarComoConsumido(livro2.getTitulo(), "livro", "02/01/2024");
        diario.avaliarLivro(livro2.getTitulo(), 3, "Bom", "02/01/2024");
        diario.marcarComoConsumido(livroNaoAvaliado.getTitulo(), "livro", "05/01/2024");

        List<Livro> livrosOrdenados = new ArrayList<>();
        List<Livro>[] livrosOrdenadosArray = diario.listarLivros(true, false);
        if (livrosOrdenadosArray != null && livrosOrdenadosArray.length > 0) {
            livrosOrdenados = livrosOrdenadosArray[0];
        }

        assertFalse(livrosOrdenados.isEmpty(), "A lista de livros ordenados não deveria estar vazia");
        assertEquals("1984", livrosOrdenados.get(0).getTitulo());
        assertEquals("O Senhor dos Anéis", livrosOrdenados.get(1).getTitulo());
        assertTrue(livrosOrdenados.size() >= 2, "A lista de livros deve ter pelo menos dois livros avaliados");
    }

    @Test
    public void testListarFilmesOrdenadoPorAvaliacao() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.cadastrarFilme(filme2.getTitulo(), filme2.getGenero(), filme2.getAnoLancamento(), filme2.getDirecao(), filme2.getRoteiro(), filme2.getElenco(), filme2.getTituloOriginal(), filme2.getOndeAssistir());
        diario.cadastrarFilme(filmeNaoAvaliado.getTitulo(), filmeNaoAvaliado.getGenero(), filmeNaoAvaliado.getAnoLancamento(), filmeNaoAvaliado.getDirecao(), filmeNaoAvaliado.getRoteiro(), filmeNaoAvaliado.getElenco(), filmeNaoAvaliado.getTituloOriginal(), filmeNaoAvaliado.getOndeAssistir());

        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");
        diario.marcarComoConsumido(filme2.getTitulo(), "filme", "02/02/2024");
        diario.avaliarFilme(filme2.getTitulo(), 5, "Ótimo filme", "02/02/2024");
        diario.marcarComoConsumido(filmeNaoAvaliado.getTitulo(), "filme", "03/02/2024");

        List<Filme> filmesOrdenados = new ArrayList<>();
        List<Filme>[] filmesOrdenadosArray = diario.listarFilmes(true, false);
        if(filmesOrdenadosArray != null && filmesOrdenadosArray.length > 0){
            filmesOrdenados = filmesOrdenadosArray[0];
        }

        assertFalse(filmesOrdenados.isEmpty(), "A lista de filmes ordenados não deveria estar vazia");
        assertEquals("Fight Club", filmesOrdenados.get(0).getTitulo());
        assertEquals("Inception", filmesOrdenados.get(1).getTitulo());
        assertTrue(filmesOrdenados.size() >= 2, "A lista de filmes deve ter pelo menos dois filmes avaliados");
    }


    @Test
    public void testListarSeriesOrdenadoPorAvaliacao() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        diario.cadastrarSerie(serieNaoAvaliada.getTitulo(), serieNaoAvaliada.getGenero(), serieNaoAvaliada.getAnoLancamento(), serieNaoAvaliada.getElenco(), serieNaoAvaliada.getTituloOriginal(), serieNaoAvaliada.getOndeAssistir());
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 1, 2008, 2008, 7);
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 2, 2009, 2009, 13);
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 1", "temporada", "03/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 1, 5, "Ótima", "03/03/2024");
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 2", "temporada", "04/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 2, 3, "Regular", "04/03/2024");

        diario.adicionarTemporadaSerie(serieNaoAvaliada.getTitulo(), 1, 1994, 1995, 24);
        diario.marcarComoConsumido(serieNaoAvaliada.getTitulo() + " - Temporada 1", "temporada", "10/03/2024");
        diario.avaliarTemporadaSerie(serieNaoAvaliada.getTitulo(), 1, 4, "Boa", "10/03/2024");

        List<Serie> seriesOrdenadas = new ArrayList<>();
        List<Serie>[] seriesOrdenadasArray = diario.listarSeries(true, false);
        if (seriesOrdenadasArray != null && seriesOrdenadasArray.length > 0) {
            seriesOrdenadas = seriesOrdenadasArray[0];
        }
        assertFalse(seriesOrdenadas.isEmpty(), "A lista de séries ordenadas não deveria estar vazia");
        assertEquals("Breaking Bad", seriesOrdenadas.get(0).getTitulo());
        assertEquals("Friends", seriesOrdenadas.get(1).getTitulo());
    }
}