// === TestesPersistenciaDiarioCultural.java ===

package com.diariocultural.testes.persistencia;

// Testes para Persistência

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Filme;
import com.diariocultural.model.Livro;
import com.diariocultural.model.Serie;
import com.diariocultural.model.Temporada;
import com.diariocultural.service.Catalogo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Persistência do Catálogo")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestesPersistenciaDiarioCultural {

    private DiarioCultural controller;
    private static Path arquivoDeTeste;

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void beforeAll() {
        arquivoDeTeste = tempDir.resolve("persistencia_teste.json");
    }

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(arquivoDeTeste);
        Catalogo catalogo = new Catalogo(arquivoDeTeste.toString());
        controller = new DiarioCultural(catalogo);
    }

    @AfterAll
    static void tearDownAfterAll() throws IOException {
        Files.deleteIfExists(arquivoDeTeste);
    }

    @Test
    @Order(1)
    @DisplayName("Deve persistir um livro após o cadastro")
    void testPersistenciaAdicionarLivro() {
        controller.cadastrarLivro("O Chamado Selvagem", "Jack London", "Editora A", "978-85-0600-476-0", 1903, "Aventura", true);

        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());
        List<Livro> livrosCarregados = novoCatalogo.getLivros();

        assertFalse(livrosCarregados.isEmpty());
        assertEquals(1, livrosCarregados.size());
        assertEquals("O Chamado Selvagem", livrosCarregados.get(0).getTitulo());
    }

    @Test
    @Order(2)
    @DisplayName("Deve persistir a remoção de um livro")
    void testPersistenciaRemoverLivro() {
        Livro livro = controller.cadastrarLivro("O Pequeno Príncipe", "Antoine de Saint-Exupéry", "Editora B", "978-85-3590-406-0", 1943, "Infantil", true);

        controller.removerMidia(livro);
        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());

        assertTrue(novoCatalogo.getLivros().isEmpty(), "A lista de livros deveria estar vazia após a remoção e recarregamento.");
    }

    @Test
    @Order(3)
    @DisplayName("Deve persistir a avaliação de um livro")
    void testPersistenciaAvaliacaoLivro() {
        Livro livro = controller.cadastrarLivro("1984", "George Orwell", "Editora C", "978-85-3590-407-7", 1949, "Distopia", true);

        controller.avaliarMidia(livro, 5, "Obra prima!");
        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());
        Livro livroCarregado = novoCatalogo.encontrarLivroPorTitulo("1984").get();

        assertTrue(livroCarregado.isConsumido());
        assertEquals(5, livroCarregado.getAvaliacao());
        assertEquals("Obra prima!", livroCarregado.getReview().getTexto());
    }

    @Test
    @Order(4)
    @DisplayName("Deve persistir um filme após cadastro e avaliação")
    void testPersistenciaFilme() {
        controller.cadastrarFilme("Pulp Fiction", "Crime", 1994, 154, "Q. Tarantino", "Q. Tarantino", List.of("John Travolta"), "Pulp Fiction", "Star+");
        Catalogo catalogoAtual = new Catalogo(arquivoDeTeste.toString());
        Filme filmeOriginal = catalogoAtual.encontrarFilmePorTitulo("Pulp Fiction").get();
        controller.avaliarMidia(filmeOriginal, 5, "Clássico!");

        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());
        Filme filmeCarregado = novoCatalogo.encontrarFilmePorTitulo("Pulp Fiction").get();

        assertNotNull(filmeCarregado);
        assertEquals("Pulp Fiction", filmeCarregado.getTitulo());
        assertEquals(5, filmeCarregado.getAvaliacao());
        assertTrue(filmeCarregado.isConsumido());
        assertEquals("Clássico!", filmeCarregado.getReview().getTexto());
    }

    @Test
    @Order(5)
    @DisplayName("Deve persistir uma série com suas temporadas")
    void testPersistenciaSerieComTemporadas() {
        controller.cadastrarSerie("Breaking Bad", "Drama", 2008, List.of("Bryan Cranston", "Aaron Paul"), "Breaking Bad", "Netflix");
        controller.adicionarTemporadaSerie("Breaking Bad", 1, 2008, 2008, 7);
        controller.adicionarTemporadaSerie("Breaking Bad", 2, 2009, 2009, 13);
        controller.avaliarTemporadaSerie("Breaking Bad", 1, 5, "Temporada inicial fantástica!");

        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());
        Serie serieCarregada = novoCatalogo.encontrarSeriePorTitulo("Breaking Bad").get();

        assertNotNull(serieCarregada);
        assertEquals(2, serieCarregada.getNumeroTemporadas());

        Temporada temp1Carregada = serieCarregada.getTemporada(1).get();
        assertNotNull(temp1Carregada);
        assertTrue(temp1Carregada.isConsumido());
        assertEquals(5, temp1Carregada.getAvaliacao());
    }

    @Test
    @Order(6)
    @DisplayName("Deve carregar um catálogo vazio se o arquivo não existir")
    void testCarregarArquivoInexistente() throws IOException {
        Files.deleteIfExists(arquivoDeTeste);

        Catalogo novoCatalogo = new Catalogo(arquivoDeTeste.toString());

        assertTrue(novoCatalogo.getLivros().isEmpty());
        assertTrue(novoCatalogo.getFilmes().isEmpty());
        assertTrue(novoCatalogo.getSeries().isEmpty());
    }
}

/******************************************************************************************

 Autor: Walace de Jesus Venas
 Componente Curricular: EXA863 MI-PROGRAMAÇÃO
 Concluído em: 20/05/2025
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 *******************************************************************************************/