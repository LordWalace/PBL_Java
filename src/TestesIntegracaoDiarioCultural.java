// === TestesIntegracaoDiarioCultural.java === "?"

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import java.util.Arrays;
import org.junit.jupiter.api.Order;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestesIntegracaoDiarioCultural{
    private Livro livro1, livro2, livro3, livro4;
    private Filme filme1, filme2, filme3;
    private Serie serie1, serie2;
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
        livro3 = new Livro("Admirável Mundo Novo", "Aldous Huxley", "Globo", "978-8525048473", 1932, "Distopia", true);
        livro4 = new Livro("A Revolução dos Bichos", "George Orwell", "Companhia das Letras", "978-8572328738", 1945, "Distopia", true);


        List<String> elencoFilme1 = Arrays.asList("Leonardo DiCaprio", "Joseph Gordon-Levitt");
        filme1 = new Filme("Inception", "Sci-Fi", 2010, "Christopher Nolan", "Christopher Nolan", elencoFilme1, "Inception", "Netflix");
        List<String> elencoFilme2 = Arrays.asList("Joseph Gordon-Levitt", "Brad Pitt");
        filme2 = new Filme("Fight Club", "Drama", 1999, "David Fincher", "Jim Uhls", elencoFilme2, "Fight Club", "Prime Video");
        filme3 = new Filme("Seven", "Suspense", 1995, "David Fincher", "Andrew Kevin Walker", Arrays.asList("Brad Pitt", "Edward Norton"), "Se7en", "Prime Video");


        List<String> elencoSerie1 = Arrays.asList("Bryan Cranston", "Aaron Paul");
        serie1 = new Serie("Breaking Bad", "Drama", 2008, elencoSerie1, "Breaking Bad", "Netflix");
        List<String> elencoSerie2 = Arrays.asList("David Schwimmer", "Lisa Kudrow");
        serie2 = new Serie("Friends", "Comédia", 1994, elencoSerie2, "Friends", "HBO Max");

        catalogo.adicionarLivro(livro1);
        catalogo.adicionarLivro(livro2);
        catalogo.adicionarLivro(livro3);
        catalogo.adicionarLivro(livro4);
        catalogo.adicionarFilme(filme1);
        catalogo.adicionarFilme(filme2);
        catalogo.adicionarFilme(filme3);
        catalogo.adicionarSerie(serie1);
        catalogo.adicionarSerie(serie2);
    }

    @Test
    @Order(1)
    public void testIntegracaoCadastroLivroEBuscaPorTitulo() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> livrosEncontrados = diario.buscarLivrosPorTitulo("1984");
        assertFalse(livrosEncontrados.isEmpty());
        assertEquals("1984", livrosEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(2)
    public void testIntegracaoCadastroLivroEBuscaPorAutor() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> livrosEncontrados = diario.buscarLivrosPorAutor("George Orwell");
        assertFalse(livrosEncontrados.isEmpty());
        assertEquals("1984", livrosEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(3)
    public void testIntegracaoCadastroLivroEBuscaPorGenero() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> livrosEncontrados = diario.buscarLivrosPorGenero("Distopia");
        assertFalse(livrosEncontrados.isEmpty());
        assertEquals("1984", livrosEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(4)
    public void testIntegracaoCadastroLivroEBuscaPorISBN() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        List<Livro> livrosEncontrados = diario.buscarLivrosPorISBN("978-0451524935");
        assertFalse(livrosEncontrados.isEmpty());
        assertEquals("1984", livrosEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(5)
    public void testIntegracaoAvaliacaoLivroELListagemOrdenada() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.marcarComoConsumido(livro1.getTitulo(), "livro", "01/01/2024");
        diario.avaliarLivro(livro1.getTitulo(), 5, "Excelente livro!", "01/01/2024");

        List<Livro> livrosOrdenados = diario.listarLivros(true, false);
        assertFalse(livrosOrdenados.isEmpty());
        assertEquals("1984", livrosOrdenados.get(0).getTitulo());
        assertEquals(5, livrosOrdenados.get(0).getAvaliacao());
    }

    @Test
    @Order(6)
    public void testIntegracaoFiltroLivrosPorGenero() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.cadastrarLivro(livro2.getTitulo(), livro2.getAutor(), livro2.getEditora(), livro2.getIsbn(), livro2.getAnoLancamento(), livro2.getGenero(), livro2.isPossuiExemplar());
        List<Livro> livrosFiltrados = diario.filtrarLivros("Fantasia", null);
        assertEquals(1, livrosFiltrados.size());
        assertEquals("O Senhor dos Anéis", livrosFiltrados.get(0).getTitulo());
    }

    @Test
    @Order(7)
    public void testIntegracaoFiltroLivrosPorAno() {
        diario.cadastrarLivro(livro1.getTitulo(), livro1.getAutor(), livro1.getEditora(), livro1.getIsbn(), livro1.getAnoLancamento(), livro1.getGenero(), livro1.isPossuiExemplar());
        diario.cadastrarLivro(livro2.getTitulo(), livro2.getAutor(), livro2.getEditora(), livro2.getIsbn(), livro2.getAnoLancamento(), livro2.getGenero(), livro2.isPossuiExemplar());
        List<Livro> livrosFiltrados = diario.filtrarLivros(null, 1954);
        assertEquals(1, livrosFiltrados.size());
        assertEquals("O Senhor dos Anéis", livrosFiltrados.get(0).getTitulo());
    }

    @Test
    @Order(8)
    public void testIntegracaoCadastroFilmeEBuscaPorTitulo() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmePorTitulo("Inception");
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals("Inception", filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(9)
    public void testIntegracaoCadastroFilmeEBuscaPorDiretor() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmesPorDiretor("Christopher Nolan");
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals("Inception", filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(10)
    public void testIntegracaoCadastroFilmeEBuscaPorAtor() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmesPorAtor("Leonardo DiCaprio");
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals("Inception", filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(11)
    public void testIntegracaoCadastroFilmeEBuscaPorGenero() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmesPorGenero("Sci-Fi");
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals("Inception", filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(12)
    public void testIntegracaoCadastroFilmeEBuscaPorAno() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmesPorAno(2010);
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals("Inception", filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(13)
    public void testIntegracaoAvaliacaoFilmeELListagemOrdenada() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.marcarComoConsumido(filme1.getTitulo(), "filme", "01/02/2024");
        diario.avaliarFilme(filme1.getTitulo(), 4, "Bom filme", "01/02/2024");

        List<Filme> filmesOrdenados = diario.listarFilmes(true, false);
        assertFalse(filmesOrdenados.isEmpty());
        assertEquals("Inception", filmesOrdenados.get(0).getTitulo());
        assertEquals(4, filmesOrdenados.get(0).getAvaliacao());
    }

    @Test
    @Order(14)
    public void testIntegracaoFiltroFilmesPorGenero() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.cadastrarFilme(filme2.getTitulo(), filme2.getGenero(), filme2.getAnoLancamento(), filme2.getDirecao(), filme2.getRoteiro(), filme2.getElenco(), filme2.getTituloOriginal(), filme2.getOndeAssistir());
        List<Filme> filmesFiltrados = diario.filtrarFilmes("Drama", null);
        assertEquals(1, filmesFiltrados.size());
        assertEquals("Fight Club", filmesFiltrados.get(0).getTitulo());
    }

    @Test
    @Order(15)
    public void testIntegracaoFiltroFilmesPorAno() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        diario.cadastrarFilme(filme2.getTitulo(), filme2.getGenero(), filme2.getAnoLancamento(), filme2.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesFiltrados = diario.filtrarFilmes(null, 1999);
        assertEquals(1, filmesFiltrados.size());
        assertEquals("Fight Club", filmesFiltrados.get(0).getTitulo());
    }

    @Test
    @Order(16)
    public void testIntegracaoCadastroSerieEBuscaPorTitulo() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        List<Serie> seriesEncontradas = diario.buscarSeriePorTitulo("Breaking Bad");
        assertFalse(seriesEncontradas.isEmpty());
        assertEquals("Breaking Bad", seriesEncontradas.get(0).getTitulo());
    }

    @Test
    @Order(17)
    public void testIntegracaoAvaliacaoSerieComTemporadasELListagemOrdenada() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 1, 2008, 2008, 7);
        diario.adicionarTemporadaSerie(serie1.getTitulo(), 2, 2009, 2009, 13);
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 1", "temporada", "03/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 1, 5, "Ótima", "03/03/2024");
        diario.marcarComoConsumido(serie1.getTitulo() + " - Temporada 2", "temporada", "04/03/2024");
        diario.avaliarTemporadaSerie(serie1.getTitulo(), 2, 3, "Regular", "04/03/2024");

        List<Serie> seriesOrdenadas = diario.listarSeries(true, false);
        assertFalse(seriesOrdenadas.isEmpty());
        assertEquals("Breaking Bad", seriesOrdenadas.get(0).getTitulo());
        assertEquals((5 + 3) / 2.0, seriesOrdenadas.get(0).getAvaliacao());
    }

    @Test
    @Order(18)
    public void testIntegracaoFiltroSeriesPorGenero() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        diario.cadastrarSerie(serie2.getTitulo(), serie2.getGenero(), serie2.getAnoLancamento(), serie2.getElenco(), serie2.getTituloOriginal(), serie2.getOndeAssistir());
        List<Serie> seriesFiltradas = diario.filtrarSeries("Comédia", null);
        assertEquals(1, seriesFiltradas.size());
        assertEquals("Friends", seriesFiltradas.get(0).getTitulo());
    }

    @Test
    @Order(19)
    public void testIntegracaoFiltroSeriesPorAno() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        diario.cadastrarSerie(serie2.getTitulo(), serie2.getGenero(), serie2.getAnoLancamento(), serie2.getElenco(), serie2.getTituloOriginal(), serie2.getOndeAssistir());
        List<Serie> seriesFiltradas = diario.filtrarSeries(null, 1994);
        assertEquals(1, seriesFiltradas.size());
        assertEquals("Friends", seriesFiltradas.get(0).getTitulo());
    }

    @Test
    @Order(20)
    public void testIntegracaoBuscaFilmeAposCadastro() {
        diario.cadastrarFilme(filme1.getTitulo(), filme1.getGenero(), filme1.getAnoLancamento(), filme1.getDirecao(), filme1.getRoteiro(), filme1.getElenco(), filme1.getTituloOriginal(), filme1.getOndeAssistir());
        List<Filme> filmesEncontrados = diario.buscarFilmePorTitulo(filme1.getTitulo());
        assertFalse(filmesEncontrados.isEmpty());
        assertEquals(filme1.getTitulo(), filmesEncontrados.get(0).getTitulo());
    }

    @Test
    @Order(21)
    public void testIntegracaoBuscaSerieAposCadastro() {
        diario.cadastrarSerie(serie1.getTitulo(), serie1.getGenero(), serie1.getAnoLancamento(), serie1.getElenco(), serie1.getTituloOriginal(), serie1.getOndeAssistir());
        List<Serie> seriesEncontradas = diario.buscarSeriePorTitulo(serie1.getTitulo());
        assertFalse(seriesEncontradas.isEmpty());
        assertEquals(serie1.getTitulo(), seriesEncontradas.get(0).getTitulo());
    }
}