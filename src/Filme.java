import java.util.List;

public class Filme extends Midia {
    private String direcao;
    private String roteiro;
    private List<String> elenco;
    private String tituloOriginal;
    private String ondeAssistir;

    public Filme(String titulo, String genero, int ano, String direcao, String roteiro, List<String> elenco, String tituloOriginal, String ondeAssistir) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.avaliacao = 0;
        this.consumido = false;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Direção: %s | Roteiro: %s | Elenco: %s | Título Original: %s | Onde Assistir: %s",
                direcao, roteiro, elenco, tituloOriginal, ondeAssistir);
    }
}