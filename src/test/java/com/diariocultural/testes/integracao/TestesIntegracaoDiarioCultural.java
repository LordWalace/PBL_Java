// === TestesIntegracaoDiarioCultural.java ===

package com.diariocultural.testes.integracao;

// Testes de Integração

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Filme;
import com.diariocultural.model.Livro;
import com.diariocultural.model.Serie;
import com.diariocultural.model.Temporada;
import com.diariocultural.service.Catalogo;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Integração do Diário Cultural")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestesIntegracaoDiarioCultural {

    private DiarioCultural controller;
    private Catalogo catalogo;
    private static final Path ARQUIVO_PERSISTENCIA = Paths.get("data", "diario_test_Inte.json");

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(ARQUIVO_PERSISTENCIA);
        catalogo = new Catalogo(ARQUIVO_PERSISTENCIA.toString());
        controller = new DiarioCultural(catalogo);
    }

    @AfterAll
    static void tearDownAfterAll() throws IOException {
        Files.deleteIfExists(ARQUIVO_PERSISTENCIA);
    }

    @Nested
    @DisplayName("Testes para Livros")
    class LivroTests {
        @Test
        @DisplayName("Deve cadastrar um livro com sucesso")
        void deveCadastrarUmLivroComSucesso() {
            Livro livro = controller.cadastrarLivro("1984", "George Orwell", "Companhia das Letras", "978-0451524935", 1949, "Distopia", true);

            assertNotNull(livro);
            assertEquals(1, catalogo.getLivros().size());
        }

        @Test
        @DisplayName("Deve lançar exceção ao cadastrar livro com dados inválidos")
        void deveLancarExcecaoAoCadastrarLivroInvalido() {
            assertThrows(IllegalArgumentException.class, () -> {
                controller.cadastrarLivro("", "Autor Válido", "Editora", "1234567890", 2023, "Gênero", false);
            });
        }

        @Test
        @DisplayName("Deve lançar exceção ao cadastrar livro duplicado")
        void deveLancarExcecaoAoCadastrarLivroDuplicado() {
            controller.cadastrarLivro("Duna", "Frank Herbert", "Editora Aleph", "978-8576572916", 1965, "Ficção Científica", true);
            assertThrows(IllegalStateException.class, () -> {
                controller.cadastrarLivro("Duna", "Frank Herbert", "Editora Aleph", "978-8576572916", 1965, "Ficção Científica", true);
            });
        }

        @Test
        @DisplayName("Deve avaliar um livro e marcar como consumido")
        void deveAvaliarLivro() {
            Livro livro = controller.cadastrarLivro("O Guia do Mochileiro", "Douglas Adams", "Arqueiro", "978-8599296222", 1979, "Ficção", true);
            controller.avaliarMidia(livro, 5, "Fantástico!");

            Livro livroAvaliado = catalogo.encontrarLivroPorTitulo("O Guia do Mochileiro").get();
            assertTrue(livroAvaliado.isConsumido());
            assertEquals(5, livroAvaliado.getAvaliacao());
            assertEquals("Fantástico!", livroAvaliado.getReview().getTexto());
        }

        @Test
        @DisplayName("Deve buscar livros por múltiplos critérios")
        void deveBuscarLivros() {
            controller.cadastrarLivro("1984", "George Orwell", "Companhia das Letras", "978-0451524935", 1949, "Distopia", true);
            controller.cadastrarLivro("A Revolução dos Bichos", "George Orwell", "Companhia das Letras", "978-8572328738", 1945, "Distopia", true);

            assertEquals(2, controller.buscarLivros(null, "Orwell", null, null, null).size());
            assertEquals(1, controller.buscarLivros("1984", null, null, null, null).size());
        }
    }

    @Nested
    @DisplayName("Testes para Filmes")
    class FilmeTests {
        @Test
        @DisplayName("Deve cadastrar um filme com sucesso")
        void deveCadastrarUmFilmeComSucesso() {
            Filme filme = controller.cadastrarFilme("Inception", "Sci-Fi", 2010, 148 , "C. Nolan", "C. Nolan", List.of("Leo DiCaprio"), "Inception", "Netflix");
            assertNotNull(filme);
            assertEquals(1, catalogo.getFilmes().size());
        }

        @Test
        @DisplayName("Deve buscar filmes por múltiplos critérios")
        void deveBuscarFilmes() {
            controller.cadastrarFilme("Inception", "Sci-Fi", 2010, 148,"C. Nolan", "C. Nolan", List.of("Leo DiCaprio"), "Inception", "Netflix");
            controller.cadastrarFilme("The Dark Knight", "Ação", 2008, 152, "C. Nolan", "J. Nolan", List.of("Christian Bale"), "The Dark Knight", "HBO");

            assertEquals(2, controller.buscarFilmes(null, "Nolan", null, null, null).size(), "Deveria encontrar 2 filmes do diretor Nolan");
            assertEquals(1, controller.buscarFilmes(null, null, "Bale", null, null).size(), "Deveria encontrar 1 filme com o ator Bale");
            assertEquals(1, controller.buscarFilmes("Inception", null, null, null, null).size(), "Deveria encontrar 1 filme pelo título Inception");
        }

        @Test
        @DisplayName("Deve avaliar um filme e marcar como consumido")
        void deveAvaliarFilme() {
            Filme filme = controller.cadastrarFilme("Pulp Fiction", "Crime", 1994, 154, "Q. Tarantino", "Q. Tarantino", List.of("John Travolta"), "Pulp Fiction", "Star+");
            controller.avaliarMidia(filme, 5, "Obra-prima!");

            Filme filmeAvaliado = catalogo.encontrarFilmePorTitulo("Pulp Fiction").get();
            assertTrue(filmeAvaliado.isConsumido());
            assertEquals(5, filmeAvaliado.getAvaliacao());
            assertEquals("Obra-prima!", filmeAvaliado.getReview().getTexto());
        }
    }

    @Nested
    @DisplayName("Testes para Séries e Temporadas")
    class SerieTests {
        @Test
        @DisplayName("Deve adicionar e avaliar uma temporada de uma série")
        void deveAdicionarEAvaliarTemporada() {
            controller.cadastrarSerie("Breaking Bad", "Drama", 2008, List.of("Bryan Cranston"), "Breaking Bad", "Netflix");
            controller.adicionarTemporadaSerie("Breaking Bad", 1, 2008, 2008, 7);
            controller.avaliarTemporadaSerie("Breaking Bad", 1, 5, "Início perfeito!");

            Serie serieAtualizada = catalogo.encontrarSeriePorTitulo("Breaking Bad").get();
            Temporada temporada = serieAtualizada.getTemporada(1).get();

            assertEquals(1, serieAtualizada.getNumeroTemporadas());
            assertEquals(5, temporada.getAvaliacao());
            assertTrue(temporada.isConsumido());
            assertEquals(5, serieAtualizada.getAvaliacao());
        }

        @Test
        @DisplayName("Deve marcar uma série como consumida apenas quando todas as temporadas estiverem")
        void deveAtualizarStatusConsumoDaSerie() {
            controller.cadastrarSerie("The Office", "Comédia", 2005, List.of("Steve Carell"), "The Office", "Prime");
            controller.adicionarTemporadaSerie("The Office", 1, 2005, 2005, 6);
            controller.adicionarTemporadaSerie("The Office", 2, 2005, 2006, 22);

            controller.marcarTemporadaComoConsumida("The Office", 1);
            Serie serie = catalogo.encontrarSeriePorTitulo("The Office").get();
            assertFalse(serie.isConsumido());

            controller.marcarTemporadaComoConsumida("The Office", 2);
            serie = catalogo.encontrarSeriePorTitulo("The Office").get();
            assertTrue(serie.isConsumido());
        }

        @Test
        @DisplayName("Deve remover uma temporada e atualizar o catálogo")
        void deveRemoverTemporada() {
            controller.cadastrarSerie("Game of Thrones", "Fantasia", 2011, List.of("Kit Harington"), "Game of Thrones", "HBO");
            controller.adicionarTemporadaSerie("Game of Thrones", 1, 2011, 2011, 10);

            controller.removerTemporada("Game of Thrones", 1);

            Serie serie = catalogo.encontrarSeriePorTitulo("Game of Thrones").get();
            assertEquals(0, serie.getNumeroTemporadas());
        }

        @Test
        @DisplayName("Deve lançar exceção ao remover temporada inexistente")
        void deveLancarExcecaoAoRemoverTemporadaInexistente() {
            controller.cadastrarSerie("Game of Thrones", "Fantasia", 2011, List.of("Kit Harington"), "Game of Thrones", "HBO");
            assertThrows(NoSuchElementException.class, () -> {
                controller.removerTemporada("Game of Thrones", 99);
            });
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