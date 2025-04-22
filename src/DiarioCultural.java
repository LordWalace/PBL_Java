// === DiarioCultural.java === "Controller"

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DiarioCultural {
    private final Catalogo catalogo;
    private final ListaMidia listaMidia;
    private final BuscarMidia buscarMidia;

    public DiarioCultural(Catalogo catalogo, ListaMidia listaMidia, BuscarMidia buscarMidia) {
        this.catalogo = catalogo;
        this.listaMidia = listaMidia;
        this.buscarMidia = buscarMidia;
    }

    public void cadastrarLivro(String titulo, String autor, String editora, String isbn, int anoLancamento, String genero, boolean possuiExemplar) {
        Livro livro = new Livro(titulo, autor, editora, isbn, anoLancamento, genero, possuiExemplar);
        catalogo.adicionarLivro(livro);
    }

    public void cadastrarFilme(String titulo, String genero, int anoLancamento, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        Filme filme = new Filme(titulo, genero, anoLancamento, direcao, roteiro, elenco, tituloOriginal, ondeAssistir);
        catalogo.adicionarFilme(filme);
    }

    public boolean cadastrarSerie(String titulo, String genero, int anoLancamento, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        Serie serie = new Serie(titulo, genero, anoLancamento, elenco, tituloOriginal, ondeAssistir);
        return catalogo.adicionarSerie(serie);
    }

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

    public List<Livro> listarLivros(boolean ordenarPorAvaliacao, boolean ascendente) {
        return listaMidia.listarLivros(null, null);
    }

    public List<Filme> listarFilmes(boolean ordenarPorAvaliacao, boolean ascendente) {
        return listaMidia.listarFilmes(null, null);
    }

    public List<Serie> listarSeries(boolean ordenarPorAvaliacao, boolean ascendente) {
        return listaMidia.listarSeries(null, null);
    }

    public List<Livro> filtrarLivros(String genero, Integer ano) {
        return catalogo.getLivros().stream()
                .filter(l -> (genero == null || genero.isEmpty() || l.getGenero().equalsIgnoreCase(genero)))
                .filter(l -> (ano == null || l.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    public List<Filme> filtrarFilmes(String genero, Integer ano) {
        return catalogo.getFilmes().stream()
                .filter(f -> (genero == null || genero.isEmpty() || f.getGenero().equalsIgnoreCase(genero)))
                .filter(f -> (ano == null || f.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    public List<Serie> filtrarSeries(String genero, Integer ano) {
        return catalogo.getSeries().stream()
                .filter(s -> (genero == null || genero.isEmpty() || s.getGenero().equalsIgnoreCase(genero)))
                .filter(s -> (ano == null || s.getAnoLancamento() == ano))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return buscarMidia.buscarLivrosPorTitulo(catalogo.getLivros(), titulo).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return buscarMidia.buscarLivrosPorAutor(catalogo.getLivros(), autor).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorGenero(String genero) {
        return buscarMidia.buscarLivrosPorGenero(catalogo.getLivros(), genero).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorAno(int ano) {
        return buscarMidia.buscarLivrosPorAno(catalogo.getLivros(), ano).stream()
                .sorted(Comparator.comparingInt(Livro::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Livro> buscarLivrosPorISBN(String isbn) {
        Livro res = buscarMidia.buscarLivroPorISBN(catalogo.getLivros(), isbn);
        return res != null ? List.of(res) : List.of();
    }

    public List<Filme> buscarFilmePorTitulo(String titulo) {
        return buscarMidia.buscarFilmesPorTitulo(catalogo.getFilmes(), titulo).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorDiretor(String diretor) {
        return buscarMidia.buscarFilmesPorDiretor(catalogo.getFilmes(), diretor).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorAtor(String ator) {
        return buscarMidia.buscarFilmesPorAtor(catalogo.getFilmes(), ator).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorGenero(String genero) {
        return buscarMidia.buscarFilmesPorGenero(catalogo.getFilmes(), genero).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Filme> buscarFilmesPorAno(int ano) {
        return buscarMidia.buscarFilmesPorAno(catalogo.getFilmes(), ano).stream()
                .sorted(Comparator.comparingInt(Filme::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }

    public List<Serie> buscarSeriePorTitulo(String titulo) {
        return buscarMidia.buscarSeriesPorTitulo(catalogo.getSeries(), titulo).stream()
                .sorted(Comparator.comparingDouble(Serie::getAvaliacao).reversed())
                .collect(Collectors.toList());
    }
}