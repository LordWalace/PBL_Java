import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Serie extends Midia {
    private int temporadas;
    private Map<Integer, Integer> episodiosPorTemporada;
    private Map<Integer, Integer> avaliacaoPorTemporada;
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;

    public Serie(String titulo, String genero, int ano, int temporadas, Map<Integer, Integer> episodiosPorTemporada, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
        this.temporadas = temporadas;
        this.episodiosPorTemporada = episodiosPorTemporada;
        this.avaliacaoPorTemporada = new HashMap<>();
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.avaliacao = 0;
        this.consumido = false;
    }

    public void avaliarTemporadas(Map<Integer, Integer> avaliacoes) {
        int soma = 0;
        for (Map.Entry<Integer, Integer> entry : avaliacoes.entrySet()) {
            avaliacaoPorTemporada.put(entry.getKey(), entry.getValue());
            soma += entry.getValue();
        }
        if (!avaliacoes.isEmpty()) {
            this.avaliacao = soma / avaliacoes.size();
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Temporadas: %d | Elenco: %s | Título Original: %s | Onde Assistir: %s",
                temporadas, elenco, tituloOriginal, ondeAssistir);
    }
}