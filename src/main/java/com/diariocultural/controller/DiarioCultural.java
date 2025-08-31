// === DiarioCultural.java === "Controller"

package com.diariocultural.controller;

import com.diariocultural.model.*;
import com.diariocultural.service.Catalogo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller principal da aplicação. Orquestra as ações entre a View e o Model.
 * Esta versão inclui métodos genéricos para facilitar a integração com a View.
 */
public class DiarioCultural {
    private final Catalogo catalogo;

    /**
     * Construtor da classe DiarioCultural.
     * @param catalogo O catálogo que armazena e gerencia as mídias.
     */
    public DiarioCultural(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    /**
     * Tenta cadastrar um novo livro no catálogo.
     * @param titulo         O título do livro.
     * @param autor          O autor do livro.
     * @param editora        A editora do livro.
     * @param isbn           O ISBN do livro.
     * @param anoLancamento  O ano de lançamento do livro.
     * @param genero         O gênero do livro.
     * @param possuiExemplar Indica se possui um exemplar físico.
     * @return O objeto Livro criado com sucesso.
     * @throws IllegalArgumentException se os dados fornecidos para o livro forem inválidos.
     * @throws IllegalStateException se um livro idêntico já existir no catálogo.
     */
    public Livro cadastrarLivro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        try {
            Livro novoLivro = new Livro(titulo, autor, editora, isbn, anoLancamento, genero, possuiExemplar);
            if (!catalogo.adicionarLivro(novoLivro)) {
                throw new IllegalStateException("Um livro idêntico já existe no catálogo.");
            }
            return novoLivro;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Dados inválidos para o cadastro do livro: " + e.getMessage(), e);
        }
    }

    /**
     * Tenta cadastrar um novo filme no catálogo.
     * @return O objeto Filme criado com sucesso.
     * @throws IllegalArgumentException se os dados do filme forem inválidos.
     * @throws IllegalStateException se um filme idêntico já existir no catálogo.
     */
    public Filme cadastrarFilme(String titulo, String genero, int anoLancamento, int tempoDuracao, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        try {
            Filme novoFilme = new Filme(titulo, genero, anoLancamento, tempoDuracao, direcao, roteiro, elenco, tituloOriginal, ondeAssistir);
            if (!catalogo.adicionarFilme(novoFilme)) {
                throw new IllegalStateException("Um filme idêntico já existe no catálogo.");
            }
            return novoFilme;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Dados inválidos para o cadastro do filme: " + e.getMessage(), e);
        }
    }

    /**
     * Tenta cadastrar uma nova série no catálogo.
     * @return O objeto Serie criado com sucesso.
     * @throws IllegalArgumentException se os dados da série forem inválidos.
     * @throws IllegalStateException se uma série idêntica já existir no catálogo.
     */
    public Serie cadastrarSerie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        try {
            Serie novaSerie = new Serie(titulo, genero, anoLancamento, elenco, tituloOriginal, ondeAssistir);
            if (!catalogo.adicionarSerie(novaSerie)) {
                throw new IllegalStateException("Uma série idêntica já existe no catálogo.");
            }
            return novaSerie;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Dados inválidos para o cadastro da série: " + e.getMessage(), e);
        }
    }

    /**
     * Tenta adicionar uma temporada a uma série existente.
     * @return A Temporada criada e adicionada com sucesso.
     * @throws NoSuchElementException se a série não for encontrada.
     * @throws IllegalArgumentException se os dados da temporada forem inválidos ou se ela já existir.
     */
    public Temporada adicionarTemporadaSerie(String tituloSerie, int numeroTemporada, int anoLancamento, Integer anoEncerramento, int numeroEpisodios) {
        Optional<Serie> serieOpt = catalogo.encontrarSeriePorTitulo(tituloSerie);
        if (serieOpt.isEmpty()) {
            throw new NoSuchElementException("Série com o título '" + tituloSerie + "' não encontrada.");
        }
        Serie serie = serieOpt.get();

        try {
            Temporada novaTemporada = new Temporada(numeroTemporada, anoLancamento, anoEncerramento, numeroEpisodios);
            serie.adicionarTemporada(novaTemporada);
            catalogo.atualizarSerie(serie);
            return novaTemporada;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Não foi possível adicionar a temporada: " + e.getMessage(), e);
        }
    }

    /**
     * Marca uma mídia (Livro ou Filme) como consumida.
     * @param midia A mídia a ser marcada como consumida.
     */
    public void marcarComoConsumido(Midia midia) {
        if (midia == null) {
            throw new IllegalArgumentException("Mídia não pode ser nula.");
        }
        if (!(midia instanceof Livro) && !(midia instanceof Filme)) {
            throw new IllegalArgumentException("Esta ação é suportada apenas para Livros e Filmes.");
        }

        midia.setDataConsumo(LocalDate.now());
        atualizarMidia(midia);
    }

    /**
     * Marca uma temporada específica como consumida e atualiza o estado da série.
     * @param tituloSerie O título da série à qual a temporada pertence.
     * @param numeroTemporada O número da temporada a ser marcada.
     */
    public void marcarTemporadaComoConsumida(String tituloSerie, int numeroTemporada) {
        Serie serie = catalogo.encontrarSeriePorTitulo(tituloSerie)
                .orElseThrow(() -> new NoSuchElementException("Série com o título '" + tituloSerie + "' não encontrada."));

        Temporada temporada = serie.getTemporada(numeroTemporada)
                .orElseThrow(() -> new NoSuchElementException("Temporada número " + numeroTemporada + " não encontrada para a série '" + tituloSerie + "'."));

        temporada.setDataConsumo(LocalDate.now());

        serie.atualizarStatusConsumo();

        catalogo.atualizarSerie(serie);
    }

    /**
     * Avalia uma mídia (Livro ou Filme), definindo nota e review.
     * @param midia A mídia a ser avaliada.
     * @param nota A nota de 1 a 5.
     * @param textoReview O texto da review.
     */
    public void avaliarMidia(Midia midia, int nota, String textoReview) {
        if (midia == null) {
            throw new IllegalArgumentException("Mídia não pode ser nula.");
        }
        if (!(midia instanceof Livro) && !(midia instanceof Filme)) {
            throw new IllegalArgumentException("Esta ação é suportada apenas para Livros e Filmes.");
        }

        try {
            LocalDate hoje = LocalDate.now();
            midia.setAvaliacao(nota);
            midia.setReviewTexto(textoReview, hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());
            atualizarMidia(midia);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Dados de avaliação inválidos: " + e.getMessage(), e);
        }
    }

    /**
     * Tenta avaliar uma temporada de uma série.
     * @return A Série com a temporada atualizada.
     */
    public Serie avaliarTemporadaSerie(String tituloSerie, int numeroTemporada, int nota, String textoReview) {
        Serie serie = catalogo.encontrarSeriePorTitulo(tituloSerie)
                .orElseThrow(() -> new NoSuchElementException("Série com o título '" + tituloSerie + "' não encontrada."));

        Temporada temporada = serie.getTemporada(numeroTemporada)
                .orElseThrow(() -> new NoSuchElementException("Temporada número " + numeroTemporada + " não encontrada para a série '" + tituloSerie + "'."));

        try {
            LocalDate hoje = LocalDate.now();
            temporada.setAvaliacao(nota);
            temporada.setReviewTexto(textoReview, hoje.getDayOfMonth(), hoje.getMonthValue(), hoje.getYear());
            catalogo.atualizarSerie(serie);
            return serie;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Não foi possível avaliar a temporada: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar privado para chamar o método de atualização no catálogo.
     */
    private void atualizarMidia(Midia midia) {
        if (midia instanceof Livro) {
            catalogo.atualizarLivro((Livro) midia);
        } else if (midia instanceof Filme) {
            catalogo.atualizarFilme((Filme) midia);
        } else if (midia instanceof Serie) {
            catalogo.atualizarSerie((Serie) midia);
        }
    }

    /**
     * Remove uma mídia do catálogo.
     * @param midia A mídia a ser removida.
     * @return true se a mídia foi removida com sucesso, false caso contrário.
     */
    public boolean removerMidia(Midia midia) {
        if (midia == null) return false;

        if (midia instanceof Livro) {
            return catalogo.removerLivro(midia.getTitulo());
        } else if (midia instanceof Filme) {
            return catalogo.removerFilme(midia.getTitulo());
        } else if (midia instanceof Serie) {
            return catalogo.removerSerie(midia.getTitulo());
        }
        return false;
    }

    /**
     * Remove uma temporada específica de uma série.
     * @param tituloSerie O título da série.
     * @param numeroTemporada O número da temporada a ser removida.
     * @throws NoSuchElementException se a temporada ou a série não forem encontradas.
     */
    public void removerTemporada(String tituloSerie, int numeroTemporada) {
        boolean removido = catalogo.removerTemporada(tituloSerie, numeroTemporada);
        if (!removido) {
            throw new NoSuchElementException("Não foi possível remover a Temporada " + numeroTemporada + " da série '" + tituloSerie + "'. Verifique se ambos existem.");
        }
    }

    /**
     * Retorna a lista de todos os livros, com filtros e ordenação opcionais.
     * @param genero O gênero para filtrar (null para não filtrar).
     * @param ano O ano para filtrar (null para não filtrar).
     * @param ordenarPorAvaliacao Se true, a lista será ordenada pela avaliação.
     * @param ascendente Se true, a ordenação será ascendente, caso contrário, descendente.
     * @return Uma lista de livros.
     */
    public List<Livro> listarLivros(String genero, Integer ano, boolean ordenarPorAvaliacao, boolean ascendente) {
        Stream<Livro> streamDeLivros = catalogo.getLivros().stream();
        if (genero != null && !genero.trim().isEmpty()) {
            streamDeLivros = streamDeLivros.filter(livro -> livro.getGenero().equalsIgnoreCase(genero));
        }
        if (ano != null) {
            streamDeLivros = streamDeLivros.filter(livro -> livro.getAnoLancamento() == ano);
        }
        if (ordenarPorAvaliacao) {
            Comparator<Livro> comparator = Comparator.comparingInt(Livro::getAvaliacao);
            if (!ascendente) {
                comparator = comparator.reversed();
            }
            streamDeLivros = streamDeLivros.sorted(comparator);
        }
        return streamDeLivros.collect(Collectors.toList());
    }

    /**
     * Retorna a lista de todos os filmes, com filtros e ordenação opcionais.
     */
    public List<Filme> listarFilmes(String genero, Integer ano, boolean ordenarPorAvaliacao, boolean ascendente) {
        Stream<Filme> streamDeFilmes = catalogo.getFilmes().stream();
        if (genero != null && !genero.trim().isEmpty()) {
            streamDeFilmes = streamDeFilmes.filter(filme -> filme.getGenero().equalsIgnoreCase(genero));
        }
        if (ano != null) {
            streamDeFilmes = streamDeFilmes.filter(filme -> filme.getAnoLancamento() == ano);
        }
        if (ordenarPorAvaliacao) {
            Comparator<Filme> comparator = Comparator.comparingInt(Filme::getAvaliacao);
            if (!ascendente) {
                comparator = comparator.reversed();
            }
            streamDeFilmes = streamDeFilmes.sorted(comparator);
        }
        return streamDeFilmes.collect(Collectors.toList());
    }

    /**
     * Retorna a lista de todas as séries, com filtros e ordenação opcionais.
     */
    public List<Serie> listarSeries(String genero, Integer ano, boolean ordenarPorAvaliacao, boolean ascendente) {
        Stream<Serie> streamDeSeries = catalogo.getSeries().stream();
        if (genero != null && !genero.trim().isEmpty()) {
            streamDeSeries = streamDeSeries.filter(serie -> serie.getGenero().equalsIgnoreCase(genero));
        }
        if (ano != null) {
            streamDeSeries = streamDeSeries.filter(serie -> serie.getAnoLancamento() == ano);
        }
        if (ordenarPorAvaliacao) {
            Comparator<Serie> comparator = Comparator.comparingInt(Serie::getAvaliacao);
            if (!ascendente) {
                comparator = comparator.reversed();
            }
            streamDeSeries = streamDeSeries.sorted(comparator);
        }
        return streamDeSeries.collect(Collectors.toList());
    }

    /**
     * Busca por livros que correspondam aos critérios fornecidos.
     */
    public List<Livro> buscarLivros(String titulo, String autor, String genero, Integer ano, String isbn) {
        return catalogo.getLivros().stream()
                .filter(livro -> titulo == null || livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .filter(livro -> autor == null || livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .filter(livro -> genero == null || livro.getGenero().equalsIgnoreCase(genero))
                .filter(livro -> ano == null || livro.getAnoLancamento() == ano)
                .filter(livro -> isbn == null || livro.getIsbn().equals(isbn))
                .collect(Collectors.toList());
    }

    /**
     * Busca por filmes que correspondam aos critérios fornecidos.
     */
    public List<Filme> buscarFilmes(String titulo, String diretor, String ator, String genero, Integer ano) {
        return catalogo.getFilmes().stream()
                .filter(filme -> titulo == null || filme.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .filter(filme -> diretor == null || filme.getDirecao().toLowerCase().contains(diretor.toLowerCase()))
                .filter(filme -> ator == null || filme.getElenco().stream().anyMatch(a -> a.toLowerCase().contains(ator.toLowerCase())))
                .filter(filme -> genero == null || filme.getGenero().equalsIgnoreCase(genero))
                .filter(filme -> ano == null || filme.getAnoLancamento() == ano)
                .collect(Collectors.toList());
    }

    /**
     * Busca por séries que contenham o termo de busca no título.
     */
    public List<Serie> buscarSeriesPorTitulo(String termoBusca) {
        return catalogo.getSeries().stream()
                .filter(serie -> serie.getTitulo().toLowerCase().contains(termoBusca.toLowerCase()))
                .collect(Collectors.toList());
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