/* Testes em correcao
// === Testes.java ===
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Testes {
    private Livro livro;
    private Filme filme;
    private Serie serie;

    @BeforeEach
    public void setUp() {
        livro = new Livro("1984", "George Orwell", "Companhia das Letras", "978-0451524935", 1949, "Distopia", true);
        filme = new Filme("Inception", "Sci-Fi", 2010, "Christopher Nolan", "Christopher Nolan", "Leonardo DiCaprio, Joseph Gordon-Levitt", "Netflix", "Inception");
        serie = new Serie("Breaking Bad", "Drama", 2008, 1, 12, "Bryan Cranston, Aaron Paul", "Breaking Bad");
    }

    @Test
    public void testCadastroLivro() {
        assertEquals("1984", livro.getTitulo());
        assertEquals("Distopia", livro.getGenero());
        assertEquals(1949, livro.getAno());
        assertTrue(livro.isPossuiExemplar());
    }

    @Test
    public void testAvaliacaoLivro() {
        livro.setConsumido(true);
        livro.setAvaliacao(5);
        livro.setDataConsumo("01/01/2024");

        assertTrue(livro.isConsumido());
        assertEquals(5, livro.getAvaliacao());
        assertEquals("01/01/2024", livro.getDataConsumo());
    }

    @Test
    public void testCadastroFilme() {
        assertEquals("Inception", filme.getTitulo());
        assertEquals("Netflix", filme.getOndeAssistir());
        assertEquals("Christopher Nolan", filme.getDirecao());
    }

    @Test
    public void testAvaliacaoFilme() {
        filme.setConsumido(true);
        filme.setAvaliacao(4);
        filme.setDataConsumo("02/02/2024");

        assertTrue(filme.isConsumido());
        assertEquals(4, filme.getAvaliacao());
        assertEquals("02/02/2024", filme.getDataConsumo());
    }

    @Test
    public void testCadastroSerie() {
        assertEquals("Breaking Bad", serie.getTitulo());
        assertEquals("Drama", serie.getGenero());
        assertEquals(2008, serie.getAno());
        assertEquals(2013, serie.getAnoEncerramento());
    }

    @Test
    public void testAvaliacaoSerie() {
        serie.adicionarTemporada(new Temporada(2008, 7));
        serie.adicionarTemporada(new Temporada(2009, 13));

        serie.getTemporadas().get(0).setAvaliacao(5);
        serie.getTemporadas().get(1).setAvaliacao(3);

        serie.setConsumido(true);
        serie.setDataConsumo("03/03/2024");

        int media = (5 + 3) / 2;

        assertTrue(serie.isConsumido());
        assertEquals(media, serie.getAvaliacao());
        assertEquals("03/03/2024", serie.getDataConsumo());
    }
}
*/