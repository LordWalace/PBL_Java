// === DiarioCultural.java === "Controller"

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Classe que representa o controlador principal da aplicação Diário Cultural.
 * Esta classe coordena as operações de cadastro, busca, avaliação e listagem
 * de livros, filmes e séries, interagindo com as classes de modelo e serviço.
 */
public class DiarioCultural {
    private final Catalogo catalogo;
    private final ListaMidia listaMidia;
    private final BuscarMidia buscarMidia;

    /**
     * Construtor da classe DiarioCultural.
     *
     * @param catalogo    O catálogo que armazena as mídias (livros, filmes e séries).
     * @param listaMidia  A lista que gerencia a exibição ordenada das mídias.
     * @param buscarMidia Classe responsável pela lógica de busca de mídias.
     */
    public DiarioCultural(Catalogo catalogo, ListaMidia listaMidia, BuscarMidia buscarMidia) {
        this.catalogo = catalogo;
        this.listaMidia = listaMidia;
        this.buscarMidia = buscarMidia;
    }

    /**
     * Cadastra um novo livro no catálogo.
     *
     * @param titulo         Título do livro.
     * @param autor          Autor do livro.
     * @param editora        Editora do livro.
     * @param isbn           ISBN do livro.
     * @param anoLancamento  Ano de lançamento do livro.
     * @param genero         Gênero do livro.
     * @param possuiExemplar Indica se o livro possui exemplar disponível.
     */
    public void cadastrarLivro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        Livro livro = new Livro(titulo, autor, editora, isbn, anoLancamento, genero, possuiExemplar);
        catalogo.adicionarLivro(livro);
    }

    /**
     * Cadastra um novo filme no catálogo.
     *
     * @param titulo         Título do filme.
     * @param genero         Gênero do filme.
     * @param anoLancamento  Ano de lançamento do filme.
     * @param direcao        Direção do filme.
     * @param roteiro        Roteiro do filme.
     * @param elenco         Elenco do filme.
     * @param tituloOriginal Título original do filme.
     * @param ondeAssistir   Onde assistir o filme.
     */
    public void cadastrarFilme(String titulo, String genero, int anoLancamento, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        Filme filme = new Filme(titulo, genero, anoLancamento, direcao, roteiro, elenco, tituloOriginal, ondeAssistir);
        catalogo.adicionarFilme(filme);
    }

    /**
     * Cadastra uma nova série no catálogo.
     *
     * @param titulo         Título da série.
     * @param genero         Gênero da série.
     * @param anoLancamento  Ano de lançamento da série.
     * @param elenco         Elenco da série.
     * @param tituloOriginal Título original da série.
     * @param ondeAssistir   Onde assistir a série.
     * @return true se a série foi cadastrada com sucesso, false caso contrário.
     */
    public boolean cadastrarSerie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        Serie serie = new Serie(titulo, genero, anoLancamento, elenco, tituloOriginal, ondeAssistir);
        return catalogo.adicionarSerie(serie);
    }

    /**
     * Adiciona uma temporada a uma série existente.
     *
     * @param tituloSerie      Título da série à qual adicionar a temporada.
     * @param numeroTemporada  Número da temporada.
     * @param anoLancamento    Ano de lançamento da temporada.
     * @param anoEncerramento  Ano de encerramento da temporada (pode ser null).
     * @param numeroEpisodios  Número de episódios da temporada.
     * @return true se a temporada foi adicionada com sucesso, false caso contrário.
     */
    public boolean adicionarTemporadaSerie(String tituloSerie, int numeroTemporada, int anoLancamento, Integer anoEncerramento, int numeroEpisodios) {
        List<Serie> encontrados = buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), tituloSerie);
        if (!encontrados.isEmpty()) {
            Serie serie = encontrados.get(0);
            int anoEncFinal = (anoEncerramento != null) ? anoEncerramento : 0;
            Temporada temp = new Temporada(numeroTemporada, anoLancamento, anoEncFinal, numeroEpisodios);
            serie.adicionarTemporada(temp);
            serie.setNumeroTemporadas(serie.getTemporadasList().size());
            return true;
        }
        return false;
    }

    /**
     * Avalia um livro, adicionando nota e review.
     *
     * @param titulo      Título do livro a ser avaliado.
     * @param nota        Nota de avaliação do livro.
     * @param textoReview Texto da review sobre o livro.
     * @param dataConsumo Data em que o livro foi consumido.
     * @return true se o livro foi avaliado com sucesso, false caso contrário.
     */
    public boolean avaliarLivro(String titulo, int nota, String textoReview, String dataConsumo) {
        List<Livro> encontrados = buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), titulo);
        if (!encontrados.isEmpty()) {
            Livro livro = encontrados.get(0);
            if (livro.isConsumido()) {
                livro.setDataConsumo(dataConsumo);
                livro.setAvaliacao(nota);
                livro.setReviewTexto(textoReview);
                return true;
            }
        }
        return false;
    }

    /**
     * Avalia um filme, adicionando nota e review.
     *
     * @param titulo        Título do filme a ser avaliado.
     * @param nota          Nota de avaliação do filme.
     * @param textoReview   Texto da review sobre o filme.
     * @param dataConsumo   Data em que o filme foi consumido.
     * @return true se o filme foi avaliado com sucesso, false caso contrário.
     */
    public boolean avaliarFilme(String titulo, int nota, String textoReview, String dataConsumo) {
        List<Filme> encontrados = buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), titulo);
        if (!encontrados.isEmpty()) {
            Filme filme = encontrados.get(0);
            if (filme.isConsumido()) {
                filme.setDataConsumo(dataConsumo);
                filme.setAvaliacao(nota);
                filme.setReviewTexto(textoReview);
                return true;
            }
        }
        return false;
    }

    /**
     * Avalia uma temporada de uma série, adicionando nota e review.
     *
     * @param tituloSerie     Título da série.
     * @param numeroTemporada Número da temporada a ser avaliada.
     * @param nota            Nota de avaliação da temporada.
     * @param textoReview     Texto da review sobre a temporada.
     * @param dataConsumo     Data em que a temporada foi consumida.
     * @return true se a temporada foi avaliada com sucesso, false caso contrário.
     */
    public boolean avaliarTemporadaSerie(String tituloSerie, int numeroTemporada, int nota, String textoReview, String dataConsumo) {
        List<Serie> encontrados = buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), tituloSerie);
        if (!encontrados.isEmpty()) {
            Serie serie = encontrados.get(0);
            Temporada temp = serie.getTemporada(numeroTemporada);
            if (temp != null && temp.isConsumido()) {
                temp.setAvaliacao(nota);
                temp.setDataConsumo(dataConsumo);
                temp.setReviewTexto(textoReview);
                atualizarStatusConsumidoSerie(serie);
                return true;
            }
        }
        return false;
    }

    /**
     * Marca um livro, filme ou temporada de série como consumido.
     *
     * @param tituloMidia Título da mídia a ser marcada como consumida.
     * @param tipoMidia   Tipo da mídia ("livro", "filme" ou "temporada").
     * @param dataConsumo Data em que a mídia foi consumida.
     * @return true se a mídia foi marcada como consumida com sucesso, false caso contrário.
     */
    public boolean marcarComoConsumido(String tituloMidia, String tipoMidia, String dataConsumo) {
        String tipo = tipoMidia.toLowerCase();
        if (tipo.equals("livro")) {
            List<Livro> encontrados = buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), tituloMidia);
            if (!encontrados.isEmpty()) {
                Livro livro = encontrados.get(0);
                livro.setConsumido(true);
                livro.setDataConsumo(dataConsumo);
                return true;
            }
        } else if (tipo.equals("filme")) {
            List<Filme> encontrados = buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), tituloMidia);
            if (!encontrados.isEmpty()) {
                Filme filme = encontrados.get(0);
                filme.setConsumido(true);
                filme.setDataConsumo(dataConsumo);
                return true;
            }
        } else if (tipo.equals("temporada")) {
            String[] parts = tituloMidia.split(" - Temporada ");
            if (parts.length == 2) {
                String tituloSerie = parts[0];
                try {
                    int num = Integer.parseInt(parts[1]);
                    List<Serie> encontrados = buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), tituloSerie);
                    if (!encontrados.isEmpty()) {
                        Serie serie = encontrados.get(0);
                        Temporada temp = serie.getTemporada(num);
                        if (temp != null) {
                            temp.setConsumido(true);
                            temp.setDataConsumo(dataConsumo);
                            atualizarStatusConsumidoSerie(serie);
                            return true;
                        }
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Atualiza o status de consumo de uma série com base no status de suas temporadas.
     * Uma série é considerada consumida se todas as suas temporadas foram consumidas.
     *
     * @param serie A série a ter seu status de consumo atualizado.
     */
    private void atualizarStatusConsumidoSerie(Serie serie) {
        List<Temporada> temporadas = serie.getTemporadasList();
        if (temporadas.isEmpty()) {
            serie.setConsumido(false);
            serie.setDataConsumo(null);
            return;
        }
        for (Temporada temp : temporadas) {
            if (!temp.isConsumido()) {
                serie.setConsumido(false);
                serie.setDataConsumo(null);
                return;
            }
        }
        serie.setConsumido(true);
    }

    /**
     * Lista todos os livros do catálogo, separando em avaliados e não avaliados.
     *
     * @param ordenarPorAvaliacao Indica se a lista de avaliados deve ser ordenada por avaliação.
     * @param ascendente          Indica se a ordenação por avaliação deve ser ascendente.
     * @return Um array de duas listas de livros. A primeira lista contém os livros avaliados, a segunda os não avaliados.
     */
    public List<Livro>[] listarLivros(boolean ordenarPorAvaliacao, boolean ascendente) {
        List<Livro> livrosAvaliados = catalogo.getLivros().stream()
                .filter(l -> l.getAvaliacao() > 0)
                .collect(Collectors.toList());
        List<Livro> livrosNaoAvaliados = catalogo.getLivros().stream()
                .filter(l -> l.getAvaliacao() == 0)
                .collect(Collectors.toList());

        if (ordenarPorAvaliacao) {
            if (ascendente) {
                livrosAvaliados.sort(Comparator.comparingInt(Livro::getAvaliacao));
            } else {
                livrosAvaliados.sort(Comparator.comparingInt(Livro::getAvaliacao).reversed());
            }
        }
        return new List[]{livrosAvaliados, livrosNaoAvaliados};
    }

    /**
     * Lista todos os filmes do catálogo, separando em avaliados e não avaliados.
     *
     * @param ordenarPorAvaliacao Indica se a lista de avaliados deve ser ordenada por avaliação.
     * @param ascendente          Indica se a ordenação por avaliação deve ser ascendente.
     * @return Um array de duas listas de filmes. A primeira lista contém os filmes avaliados, a segunda os não avaliados.
     */
    public List<Filme>[] listarFilmes(boolean ordenarPorAvaliacao, boolean ascendente) {
        List<Filme> filmesAvaliados = catalogo.getFilmes().stream()
                .filter(f -> f.getAvaliacao() > 0)
                .collect(Collectors.toList());
        List<Filme> filmesNaoAvaliados = catalogo.getFilmes().stream()
                .filter(f -> f.getAvaliacao() == 0)
                .collect(Collectors.toList());

        if (ordenarPorAvaliacao) {
            if (ascendente) {
                filmesAvaliados.sort(Comparator.comparingInt(Filme::getAvaliacao));
            } else {
                filmesAvaliados.sort(Comparator.comparingInt(Filme::getAvaliacao).reversed());
            }
        }
        return new List[]{filmesAvaliados, filmesNaoAvaliados};
    }

    /**
     * Lista todas as séries do catálogo, separando em avaliados e não avaliados.
     *
     * @param ordenarPorAvaliacao Indica se a lista de avaliados deve ser ordenada por avaliação.
     * @param ascendente          Indica se a ordenação por avaliação deve ser ascendente.
     * @return Um array de duas listas de séries. A primeira lista contém as séries avaliadas, a segunda as não avaliadas.
     */
    public List<Serie>[] listarSeries(boolean ordenarPorAvaliacao, boolean ascendente) {
        List<Serie> seriesAvaliadas = catalogo.getSeries().stream()
                .filter(s -> s.getAvaliacao() > 0)
                .collect(Collectors.toList());
        List<Serie> seriesNaoAvaliadas = catalogo.getSeries().stream()
                .filter(s -> s.getAvaliacao() == 0)
                .collect(Collectors.toList());

        if (ordenarPorAvaliacao) {
            if (ascendente) {
                seriesAvaliadas.sort(Comparator.comparingDouble(Serie::getAvaliacao));
            } else {
                seriesAvaliadas.sort(Comparator.comparingDouble(Serie::getAvaliacao).reversed());
            }
        }
        return new List[]{seriesAvaliadas, seriesNaoAvaliadas};
    }

    /**
     * * Filtra os livros do catálogo por gênero e/ou ano de lançamento.
     *
     * @param genero Gênero dos livros a serem filtrados (pode ser null para não filtrar por gênero).
     * @param ano    Ano de lançamento dos livros a serem filtrados (pode ser null para não filtrar por ano).
     * @return Uma lista de livros que correspondem aos critérios de filtragem.
     * */
    public List<Livro> filtrarLivros(String genero, Integer ano) {
        return catalogo.getLivros().stream()
                .filter(l -> (genero == null || genero.isEmpty() || l.getGenero().equalsIgnoreCase(genero)))
                .filter(l -> (ano == null || l.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    /**
     * Filtra os filmes do catálogo por gênero e/ou ano de lançamento.
     *
     * @param genero Gênero dos filmes a serem filtrados (pode ser null para não filtrar por gênero).
     * @param ano    Ano de lançamento dos filmes a serem filtrados (pode ser null para não filtrar por ano).
     * @return Uma lista de filmes que correspondem aos critérios de filtragem.
     */
    public List<Filme> filtrarFilmes(String genero, Integer ano) {
        return catalogo.getFilmes().stream()
                .filter(f -> (genero == null || genero.isEmpty() || f.getGenero().equalsIgnoreCase(genero)))
                .filter(f -> (ano == null || f.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    /**
     * Filtra as séries do catálogo por gênero e/ou ano de lançamento.
     *
     * @param genero Gênero das séries a serem filtradas (pode ser null para não filtrar por gênero).
     * @param ano    Ano de lançamento das séries a serem filtradas (pode ser null para não filtrar por ano).
     * @return Uma lista de séries que correspondem aos critérios de filtragem.
     */
    public List<Serie> filtrarSeries(String genero, Integer ano) {
        return catalogo.getSeries().stream()
                .filter(s -> (genero == null || genero.isEmpty() || s.getGenero().equalsIgnoreCase(genero)))
                .filter(s -> (ano == null || s.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por título, ordenados por avaliação em ordem decrescente.
     *
     * @param titulo Título do livro a ser buscado.
     * @return Uma lista de livros que correspondem ao título, ordenados por avaliação.
     */
    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), titulo).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por autor, ordenados por avaliação em ordem decrescente.
     *
     * @param autor Autor do livro a ser buscado.
     * @return Uma lista de livros que correspondem ao autor, ordenados por avaliação.
     */
    public List<Livro> buscarLivrosPorAutor(String autor) {
        return buscarMidia.buscarLivrosPorAutor(catalogo.getLivros(), autor).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por gênero, ordenados por avaliação em ordem decrescente.
     *
     * @param genero Gênero do livro a ser buscado.
     * @return Uma lista de livros que correspondem ao gênero, ordenados por avaliação.
     */
    public List<Livro> buscarLivrosPorGenero(String genero) {
        return buscarMidia.buscarLivrosPorGenero(catalogo.getLivros(), genero).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por ano de lançamento, ordenados por avaliação em ordem decrescente.
     *
     * @param ano Ano de lançamento do livro a ser buscado.
     * @return Uma lista de livros que correspondem ao ano de lançamento, ordenados por avaliação.
     */
    public List<Livro> buscarLivrosPorAno(int ano) {
        return buscarMidia.buscarLivrosPorAno(catalogo.getLivros(), ano).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca livros por ISBN.
     *
     * @param isbn ISBN do livro a ser buscado.
     * @return Uma lista contendo o livro correspondem ao ISBN, ou uma lista vazia se não encontrado.
     */
    public List<Livro> buscarLivrosPorISBN(String isbn) {
        Livro res = buscarMidia.buscarLivroPorISBN(catalogo.getLivros(), isbn);
        return res != null ? List.of(res) : List.of();
    }

    /**
     * Busca filmes por título, ordenados por avaliação em ordem decrescente.
     *
     * @param titulo Título do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao título, ordenados por avaliação.
     */
    public List<Filme> buscarFilmePorTitulo(String titulo) {
        return buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), titulo).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por diretor, ordenados por avaliação em ordem decrescente.
     *
     * @param diretor Diretor do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao diretor, ordenados por avaliação.
     */
    public List<Filme> buscarFilmesPorDiretor(String diretor) {
        return buscarMidia.buscarFilmesPorDiretor(catalogo.getFilmes(), diretor).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por ator, ordenados por avaliação em ordem decrescente.
     *
     * @param ator Ator do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao ator, ordenados por avaliação.
     */
    public List<Filme> buscarFilmesPorAtor(String ator) {
        return buscarMidia.buscarFilmesPorAtor(catalogo.getFilmes(), ator).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por gênero, ordenados por avaliação em ordem decrescente.
     *
     * @param genero Gênero do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao gênero, ordenados por avaliação.
     */
    public List<Filme> buscarFilmesPorGenero(String genero) {
        return buscarMidia.buscarFilmesPorGenero(catalogo.getFilmes(), genero).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca filmes por ano de lançamento, ordenados por avaliação em ordem decrescente.
     *
     * @param ano Ano de lançamento do filme a ser buscado.
     * @return Uma lista de filmes que correspondem ao ano de lançamento, ordenados por avaliação.
     */
    public List<Filme> buscarFilmesPorAno(int ano) {
        return buscarMidia.buscarFilmesPorAno(catalogo.getFilmes(), ano).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca séries por título, ordenadas por avaliação em ordem decrescente.
     *
     * @param titulo Título da série a ser buscada.
     * @return Uma lista de séries que correspondem ao título, ordenadas por avaliação.
     */
    public List<Serie> buscarSeriePorTitulo(String titulo) {
        return buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), titulo).stream()
                .sorted(Comparator.comparingDouble(Serie::getAvaliacao).reversed())
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