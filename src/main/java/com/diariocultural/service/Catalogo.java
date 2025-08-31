// === Catalogo.java === "Service"

package com.diariocultural.service;

import com.diariocultural.model.Filme;
import com.diariocultural.model.Livro;
import com.diariocultural.model.Serie;
import com.diariocultural.utils.DurationAdapter;
import com.diariocultural.utils.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Gerencia a coleção de mídias, utilizando JSON para a persistência em arquivo.
 * Atua como a camada de serviço (Service/Repository) da aplicação.
 */
public class Catalogo {
    private List<Livro> livros;
    private List<Filme> filmes;
    private List<Serie> series;
    private final Path caminhoDoArquivo;
    private final Gson gson;

    /**
     * Construtor padrão para a aplicação principal.
     * Usa o caminho de persistência padrão.
     */
    public Catalogo() {
        this(Paths.get("data", "diario_cultural.json").toString());
    }

    /**
     * Construtor para testes.
     * Permite especificar um caminho de arquivo diferente para isolar os testes.
     * @param caminhoArquivo O caminho para o arquivo de persistência (ex: um arquivo temporário).
     */
    public Catalogo(String caminhoArquivo) {
        // CORREÇÃO: Adicionado o registro do DurationAdapter.
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter()) // Garante que o Duration seja tratado corretamente
                .setPrettyPrinting()
                .create();

        this.caminhoDoArquivo = Paths.get(caminhoArquivo);
        this.livros = new ArrayList<>();
        this.filmes = new ArrayList<>();
        this.series = new ArrayList<>();
        carregarDados();
    }

    /**
     * Retorna uma cópia da lista de livros para evitar modificações externas.
     * @return a lista de todos os livros.
     */
    public List<Livro> getLivros() { return new ArrayList<>(this.livros); }

    /**
     * Retorna uma cópia da lista de filmes para evitar modificações externas.
     * @return a lista de todos os filmes.
     */
    public List<Filme> getFilmes() { return new ArrayList<>(this.filmes); }

    /**
     * Retorna uma cópia da lista de séries para evitar modificações externas.
     * @return a lista de todas as séries.
     */
    public List<Serie> getSeries() { return new ArrayList<>(this.series); }

    /**
     * Encontra um livro pelo título.
     * @param titulo O título a ser buscado.
     * @return um Optional contendo o Livro se encontrado, ou um Optional vazio.
     */
    public Optional<Livro> encontrarLivroPorTitulo(String titulo) {
        return livros.stream()
                .filter(l -> l.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    /**
     * Encontra um filme pelo título.
     * @param titulo O título a ser buscado.
     * @return um Optional contendo o Filme se encontrado, ou um Optional vazio.
     */
    public Optional<Filme> encontrarFilmePorTitulo(String titulo) {
        return filmes.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    /**
     * Encontra uma série pelo título.
     * @param titulo O título a ser buscado.
     * @return um Optional contendo a Serie se encontrada, ou um Optional vazio.
     */
    public Optional<Serie> encontrarSeriePorTitulo(String titulo) {
        return series.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    /**
     * Adiciona um livro ao catálogo se ele ainda não existir.
     * @param livro O livro a ser adicionado.
     * @return true se o livro foi adicionado com sucesso, false caso contrário.
     */
    public boolean adicionarLivro(Livro livro) {
        if (livros.contains(livro)) {
            return false;
        }
        livros.add(livro);
        salvarDados();
        return true;
    }

    /**
     * Adiciona um filme ao catálogo se ele ainda não existir.
     * @param filme O filme a ser adicionado.
     * @return true se o filme foi adicionado com sucesso, false caso contrário.
     */
    public boolean adicionarFilme(Filme filme) {
        if (filmes.contains(filme)) {
            return false;
        }
        filmes.add(filme);
        salvarDados();
        return true;
    }

    /**
     * Adiciona uma série ao catálogo se ela ainda não existir.
     * @param serie A série a ser adicionada.
     * @return true se a série foi adicionada com sucesso, false caso contrário.
     */
    public boolean adicionarSerie(Serie serie) {
        if (series.contains(serie)) {
            return false;
        }
        series.add(serie);
        salvarDados();
        return true;
    }

    /**
     * Atualiza um livro existente no catálogo. Remove o antigo e adiciona a nova versão.
     * @param livroAtualizado O objeto Livro com os dados atualizados.
     */
    public void atualizarLivro(Livro livroAtualizado) {
        livros.remove(livroAtualizado);
        livros.add(livroAtualizado);
        salvarDados();
    }

    /**
     * Atualiza um filme existente no catálogo. Remove o antigo e adiciona a nova versão.
     * @param filmeAtualizado O objeto Filme com os dados atualizados.
     */
    public void atualizarFilme(Filme filmeAtualizado) {
        filmes.remove(filmeAtualizado);
        filmes.add(filmeAtualizado);
        salvarDados();
    }

    /**
     * Atualiza uma série existente no catálogo. Remove a antiga e adiciona a nova versão.
     * @param serieAtualizada O objeto Serie com os dados atualizados.
     */
    public void atualizarSerie(Serie serieAtualizada) {
        series.remove(serieAtualizada);
        series.add(serieAtualizada);
        salvarDados();
    }

    /**
     * Remove um livro do catálogo pelo título.
     * @param titulo O título do livro a ser removido.
     * @return true se o livro foi removido, false caso contrário.
     */
    public boolean removerLivro(String titulo) {
        boolean removido = livros.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
        if (removido) salvarDados();
        return removido;
    }

    /**
     * Remove um filme do catálogo pelo título.
     * @param titulo O título do filme a ser removido.
     * @return true se o filme foi removido, false caso contrário.
     */
    public boolean removerFilme(String titulo) {
        boolean removido = filmes.removeIf(f -> f.getTitulo().equalsIgnoreCase(titulo));
        if (removido) salvarDados();
        return removido;
    }

    /**
     * Remove uma série do catálogo pelo título.
     * @param titulo O título da série a ser removida.
     * @return true se a série foi removida, false caso contrário.
     */
    public boolean removerSerie(String titulo) {
        boolean removido = series.removeIf(s -> s.getTitulo().equalsIgnoreCase(titulo));
        if (removido) salvarDados();
        return removido;
    }

    /**
     * Remove uma temporada específica de uma série.
     * @param tituloSerie O título da série da qual a temporada será removida.
     * @param numeroTemporada O número da temporada a ser removida.
     * @return true se a temporada foi encontrada e removida, false caso contrário.
     */
    public boolean removerTemporada(String tituloSerie, int numeroTemporada) {
        Optional<Serie> serieOpt = encontrarSeriePorTitulo(tituloSerie);
        if (serieOpt.isPresent()) {
            Serie serie = serieOpt.get();
            boolean removido = serie.removerTemporada(numeroTemporada);
            if (removido) {
                salvarDados();
                return true;
            }
        }
        return false;
    }

    /**
     * Salva o conteúdo de todas as listas de mídias em um único arquivo JSON.
     * Garante que o diretório de destino exista antes de salvar.
     */
    private void salvarDados() {
        try {
            Files.createDirectories(caminhoDoArquivo.getParent());

            CatalogoWrapper wrapper = new CatalogoWrapper();
            wrapper.livros = this.livros;
            wrapper.filmes = this.filmes;
            wrapper.series = this.series;

            try (Writer writer = Files.newBufferedWriter(caminhoDoArquivo)) {
                gson.toJson(wrapper, writer);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha crítica ao salvar dados no arquivo JSON.", e);
        }
    }

    /**
     * Carrega os dados do arquivo JSON de persistência.
     */
    private void carregarDados() {
        if (Files.notExists(caminhoDoArquivo)) {
            return;
        }

        try (Reader reader = Files.newBufferedReader(caminhoDoArquivo)) {
            CatalogoWrapper wrapper = gson.fromJson(reader, CatalogoWrapper.class);

            if (wrapper != null) {
                this.livros = wrapper.livros != null ? new ArrayList<>(wrapper.livros) : new ArrayList<>();
                this.filmes = wrapper.filmes != null ? new ArrayList<>(wrapper.filmes) : new ArrayList<>();
                this.series = wrapper.series != null ? new ArrayList<>(wrapper.series) : new ArrayList<>();
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Falha crítica ao carregar dados do arquivo JSON.", e);
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