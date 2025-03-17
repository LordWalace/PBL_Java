import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.Duration;

public class Filme{
    String titulo, genero, direcao, roteiro, elenco, tituloOriginal, ondeAssistir;
    int anoDeLancamento;
    Duration tempoDeDuracao;
    boolean visualizado;
    int avaliacao;
    String review;
    String dataVisualizacao;
    static List<Filme> biblioteca = new ArrayList<> ();
    static Scanner scanner = new Scanner (System.in);

    public Filme(String titulo, String genero, String direcao, String roteiro, String elenco, String tituloOriginal, String ondeAssistir, int anoDeLancamento, Duration tempoDeDuracao){
        this.titulo = titulo;
        this.genero = genero;
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.anoDeLancamento = anoDeLancamento;
        this.tempoDeDuracao = tempoDeDuracao;
        this.visualizado = false;
        this.avaliacao = 0;
        this.review = "";
        this.dataVisualizacao = "";

    }


    public void avaliarFilme(int avaliacao, String dataVisualizacao, String review) {
        this.visualizado = true;
        this.avaliacao = avaliacao;
        this.dataVisualizacao = dataVisualizacao;
        this.review = review;
    }

    public String toString() {
        return "Título: " + titulo + " | Gênero: " + genero + " | Direção: " + direcao + " | Roteiro: " + roteiro +
                " | Elenco: " + elenco + " | Titulo Original: " + tituloOriginal + " | Onde Assistir: " + ondeAssistir +
                " | Ano de Lançamento " + anoDeLancamento + "Duração: " + tempoDeDuracao + " | Visualizado: " + (visualizado ? "Sim" : "Não") +
                "| Avaliação: " + (visualizado ? avaliacao + " estrelas" : "N/A") + " | Data de Visualização: " + (visualizado ? dataVisualizacao : "N/A") +
                "| Review: " + (visualizado ? review : "N/A");

        public static void cadastrarFilme() {
            System.out.print("Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Genero: ");
            String genero = scanner.nextLine();
            System.out.print("Direção: ");
            String direcao = scanner.nextLine();
            System.out.print("Roteiro: ");
            String roteiro = scanner.nextLine();
            System.out.print("Elenco: ");
            String elenco = scanner.nextLine();
            System.out.print("Titulo Original: ");
            String tituloOriginal = scanner.nextLine();
            System.out.print("Onde Assistir: ");
            String ondeAssistir = scanner.nextLine();
            System.out.print("Ano de Lançamento: ");
            int anoDeLancamento = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Gênero: ");
            String genero = scanner.nextLine();
            System.out.print("Possui um exemplar? (true/false): ");
            boolean possuiExemplar = scanner.nextBoolean();
            scanner.nextLine();

            biblioteca.add(new Filme(titulo, genero, direcao, roteiro, elenco, tituloOriginal,  anoPublicacao, genero, possuiExemplar));
            System.out.println("Livro cadastrado com sucesso!");
        }

        public static void avaliarLivro() {
            System.out.print("Digite o título do livro para avaliar: ");
            String titulo = scanner.nextLine();

            for (Livro livro : biblioteca) {
                if (livro.titulo.equalsIgnoreCase(titulo)) {
                    System.out.print("Nota (1-5): ");
                    int avaliacao = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Data de leitura (DD/MM/AAAA): ");
                    String dataLeitura = scanner.nextLine();
                    System.out.print("Review: ");
                    String review = scanner.nextLine();

                    livro.avaliarLivro(avaliacao, dataLeitura, review);
                    System.out.println("Livro avaliado com sucesso!");
                    return;
                }
            }
            System.out.println("Livro não encontrado!");
        }

        public static void listarLivros() {
            if (biblioteca.isEmpty()) {
                System.out.println("Nenhum livro cadastrado.");
            } else {
                for (Livro livro : biblioteca) {
                    System.out.println(livro);
                }
            }
        }
    }
}