// Ta dando erro no JUnit ver na seçao como resolver

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Scanner;

class Testes {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new Scanner(System.in);
        Midia.livros.clear();
        Midia.filmes.clear();
        Midia.series.clear();
    }

    @Test
    void testCadastroSerie() {
        // Testa se uma série pode ser cadastrada corretamente
        Serie serie = new Serie("Titulo", "Genero", 2023);
        Midia.series.add(serie);
        assertFalse(Midia.series.isEmpty()); // Verifica se a lista de séries não está vazia
        assertNotNull(serie.getTitulo()); // Verifica se o título foi preenchido
        assertNotNull(serie.getGenero()); // Verifica se o gênero foi preenchido
        assertTrue(serie.getAno() > 0); // Verifica se o ano é válido
    }

    @Test
    void testAvaliacaoSerie() {
        // Testa se a avaliação da série é calculada corretamente como média das temporadas
        Serie serie = new Serie("Titulo", "Genero", 2023);
        serie.setAvaliacaoPorTemporada(1, 5);
        serie.setAvaliacaoPorTemporada(2, 4);
        assertEquals(4, serie.calcularMediaAvaliacao()); // A média deve ser (5 + 4) / 2 = 4
    }

    @Test
    void testCadastroLivro() {
        // Testa se um livro pode ser cadastrado corretamente
        Livro livro = new Livro("Titulo", "Autor", "Genero", 2023, "Editora", "ISBN");
        Midia.livros.add(livro);
        assertFalse(Midia.livros.isEmpty()); // Verifica se a lista de livros não está vazia
        assertNotNull(livro.getTitulo()); // Verifica se o título foi preenchido
        assertNotNull(livro.getAutor()); // Verifica se o autor foi preenchido
        assertNotNull(livro.getGenero()); // Verifica se o gênero foi preenchido
    }

    @Test
    void testAvaliacaoLivro() {
        // Testa se um livro pode ser marcado como lido e avaliado corretamente
        Livro livro = new Livro("Titulo", "Autor", "Genero", 2023, "Editora", "ISBN");
        livro.setLido(true);
        livro.setAvaliacao(5);
        assertTrue(livro.isLido()); // Verifica se o livro foi marcado como lido
        assertEquals(5, livro.getAvaliacao()); // Verifica se a avaliação foi registrada corretamente
    }

    @Test
    void testCadastroFilme() {
        // Testa se um filme pode ser cadastrado corretamente
        Filme filme = new Filme("Titulo", "Genero", 2023, "Diretor");
        Midia.filmes.add(filme);
        assertFalse(Midia.filmes.isEmpty()); // Verifica se a lista de filmes não está vazia
        assertNotNull(filme.getTitulo()); // Verifica se o título foi preenchido
        assertNotNull(filme.getGenero()); // Verifica se o gênero foi preenchido
        assertTrue(filme.getAno() > 0); // Verifica se o ano é válido
    }

    @Test
    void testAvaliacaoFilme() {
        // Testa se um filme pode ser marcado como assistido e avaliado corretamente
        Filme filme = new Filme("Titulo", "Genero", 2023, "Diretor");
        filme.setAssistido(true);
        filme.setAvaliacao(5);
        assertTrue(filme.isAssistido()); // Verifica se o filme foi marcado como assistido
        assertEquals(5, filme.getAvaliacao()); // Verifica se a avaliação foi registrada corretamente
    }
}