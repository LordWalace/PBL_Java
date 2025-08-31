// === TestesUnitariosDiarioCultural.java ===

package com.diariocultural.testes.unitarios;

// Testes Unitários

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Filme;
import com.diariocultural.model.Livro;
import com.diariocultural.model.Serie;
import com.diariocultural.service.Catalogo;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários do Controller DiarioCultural")
class TestesUnitariosDiarioCultural {

    private DiarioCultural controller;
    private Catalogo catalogo;

    private static final Path ARQUIVO_TESTE_UNITARIO = Paths.get("data", "diario_unit_teste.json");

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(ARQUIVO_TESTE_UNITARIO);

        catalogo = new Catalogo(ARQUIVO_TESTE_UNITARIO.toString());
        controller = new DiarioCultural(catalogo);
    }

    @AfterAll
    static void tearDownAfterAll() throws IOException {
        Files.deleteIfExists(ARQUIVO_TESTE_UNITARIO);
    }

    @Nested
    @DisplayName("Funcionalidades de Livro")
    class TestesDeLivro {
        @Test
        @DisplayName("Deve cadastrar um livro e verificar a sua existência")
        void deveCadastrarUmLivro() {
            Livro livro = controller.cadastrarLivro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Agir", "9788522005470", 1943, "Infantil", true);
            assertNotNull(livro);
            assertTrue(catalogo.encontrarLivroPorTitulo("O Pequeno Príncipe").isPresent());
        }

        @Test
        @DisplayName("Deve avaliar um livro e verificar a sua avaliação")
        void deveAvaliarUmLivro() {
            Livro livro = controller.cadastrarLivro("1984", "George Orwell", "Companhia das Letras", "9780451524935", 1949, "Distopia", true);
            controller.avaliarMidia(livro, 5, "Excelente livro!");
            Livro livroAvaliado = catalogo.encontrarLivroPorTitulo("1984").get();
            assertEquals(5, livroAvaliado.getAvaliacao());
            assertEquals("Excelente livro!", livroAvaliado.getReview().getTexto());
        }
    }

    @Nested
    @DisplayName("Funcionalidades de Filme")
    class TestesDeFilme {
        @Test
        @DisplayName("Deve cadastrar um filme e verificar a sua existência")
        void deveCadastrarUmFilme() {
            Filme filme = controller.cadastrarFilme("Forrest Gump", "Drama", 1994, 142, "Robert Zemeckis", "Eric Roth", List.of("Tom Hanks"), "Forrest Gump", "Telecine Play");
            assertNotNull(filme);
            assertTrue(catalogo.encontrarFilmePorTitulo("Forrest Gump").isPresent());
        }

        @Test
        @DisplayName("Deve avaliar um filme e verificar a sua avaliação")
        void deveAvaliarUmFilme() {
            Filme filme = controller.cadastrarFilme("Inception", "Sci-Fi", 2010, 148, "Christopher Nolan", "Christopher Nolan", List.of("Leonardo DiCaprio"), "Inception", "Netflix");
            controller.avaliarMidia(filme, 4, "Ótimo filme!");
            Filme filmeAvaliado = catalogo.encontrarFilmePorTitulo("Inception").get();
            assertEquals(4, filmeAvaliado.getAvaliacao());
            assertEquals("Ótimo filme!", filmeAvaliado.getReview().getTexto());
        }
    }

    @Nested
    @DisplayName("Funcionalidades de Série")
    class TestesDeSerie {
        @Test
        @DisplayName("Deve cadastrar uma série e adicionar uma temporada")
        void deveCadastrarSerieComTemporada() {
            controller.cadastrarSerie("The Mandalorian", "Sci-Fi", 2019, List.of("Pedro Pascal"), "The Mandalorian", "Disney+");
            controller.adicionarTemporadaSerie("The Mandalorian", 1, 2019, null, 8);
            Serie serieCadastrada = catalogo.encontrarSeriePorTitulo("The Mandalorian").get();
            assertNotNull(serieCadastrada);
            assertEquals(1, serieCadastrada.getNumeroTemporadas());
        }

        @Test
        @DisplayName("Deve avaliar uma temporada e verificar a avaliação da série")
        void deveAvaliarTemporadaDeSerie() {
            controller.cadastrarSerie("Breaking Bad", "Drama", 2008, List.of("Bryan Cranston"), "Breaking Bad", "Netflix");
            controller.adicionarTemporadaSerie("Breaking Bad", 1, 2008, 2008, 7);
            controller.adicionarTemporadaSerie("Breaking Bad", 2, 2009, 2009, 13);

            controller.avaliarTemporadaSerie("Breaking Bad", 1, 5, "Ótima");
            controller.avaliarTemporadaSerie("Breaking Bad", 2, 3, "Regular");

            Serie serieAvaliada = catalogo.encontrarSeriePorTitulo("Breaking Bad").get();
            assertEquals(4, serieAvaliada.getAvaliacao(), "A avaliação da série deveria ser a média arredondada (4).");
        }
    }

    @Nested
    @DisplayName("Funcionalidades de Busca e Filtro")
    class TestesDeBuscaFiltro {
        @BeforeEach
        void popularCatalogo() {
            controller.cadastrarLivro("1984", "George Orwell", "Cia", "1111111111111", 1949, "Distopia", true);
            controller.cadastrarLivro("O Senhor dos Anéis", "J.R.R. Tolkien", "WMF", "2222222222222", 1954, "Fantasia", true);
            controller.cadastrarFilme("Inception", "Sci-Fi", 2010, 148, "C. Nolan", "C. Nolan", List.of("Leo DiCaprio"), "Inception", "Netflix");
            controller.cadastrarFilme("Pulp Fiction", "Crime", 1994, 154, "Q. Tarantino", "Q. Tarantino", List.of("John Travolta"), "Pulp Fiction", "Star+");
            controller.cadastrarSerie("Friends", "Comédia", 1994, List.of("Jennifer Aniston"), "Friends", "HBO");
        }

        @Test
        @DisplayName("Deve buscar livros por todos os critérios individuais")
        void deveBuscarLivrosPorCriterios() {
            assertEquals(1, controller.buscarLivros("1984", null, null, null, null).size(), "Busca por Título falhou.");
            assertEquals(1, controller.buscarLivros(null, "Tolkien", null, null, null).size(), "Busca por Autor falhou.");
            assertEquals(1, controller.buscarLivros(null, null, "Distopia", null, null).size(), "Busca por Gênero falhou.");
            assertEquals(1, controller.buscarLivros(null, null, null, 1954, null).size(), "Busca por Ano falhou.");
            assertEquals(1, controller.buscarLivros(null, null, null, null, "1111111111111").size(), "Busca por ISBN falhou.");
        }

        @Test
        @DisplayName("Deve buscar filmes por todos os critérios individuais")
        void deveBuscarFilmesPorCriterios() {
            assertEquals(1, controller.buscarFilmes("Inception", null, null, null, null).size(), "Busca por Título falhou.");
            assertEquals(1, controller.buscarFilmes(null, "Tarantino", null, null, null).size(), "Busca por Diretor falhou.");
            assertEquals(1, controller.buscarFilmes(null, null, "Travolta", null, null).size(), "Busca por Ator falhou.");
            assertEquals(1, controller.buscarFilmes(null, null, null, "Sci-Fi", null).size(), "Busca por Gênero falhou.");
            assertEquals(1, controller.buscarFilmes(null, null, null, null, 1994).size(), "Busca por Ano falhou.");
        }

        @Test
        @DisplayName("Deve buscar e filtrar séries por todos os critérios")
        void deveBuscarEFiltrarSeriesPorCriterios() {
            List<Serie> resultadoBusca = controller.buscarSeriesPorTitulo("Friends");
            assertEquals(1, resultadoBusca.size(), "Deveria encontrar 1 série pelo título.");
            assertEquals("Friends", resultadoBusca.get(0).getTitulo());

            List<Serie> resultadoFiltroGenero = controller.listarSeries("Comédia", null, false, false);
            assertEquals(1, resultadoFiltroGenero.size(), "Deveria encontrar 1 série pelo gênero Comédia.");
            assertEquals("Friends", resultadoFiltroGenero.get(0).getTitulo());

            List<Serie> resultadoFiltroAno = controller.listarSeries(null, 1994, false, false);
            assertEquals(1, resultadoFiltroAno.size(), "Deveria encontrar 1 série pelo ano 1994.");
            assertEquals("Friends", resultadoFiltroAno.get(0).getTitulo());
        }
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