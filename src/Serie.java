import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Serie extends Midia {
    private int temporadas;
    private Map<Integer, Integer> episodiosPorTemporada; // Temporada -> Número de episódios
    private Map<Integer, Integer> avaliacaoPorTemporada; // Temporada -> Nota da temporada

    public Serie(String titulo, String genero, int ano, int temporadas, Map<Integer, Integer> episodios) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
        this.avaliacao = 0;
        this.consumido = false;
        this.temporadas = temporadas;
        this.episodiosPorTemporada = episodios;
        this.avaliacaoPorTemporada = new HashMap<>();
    }

    public static void cadastrar(Scanner scanner) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        System.out.print("Quantas temporadas essa série possui? ");
        int temporadas = scanner.nextInt();
        scanner.nextLine();

        Map<Integer, Integer> episodiosPorTemporada = new HashMap<>();
        for (int i = 1; i <= temporadas; i++) {
            System.out.print("Quantos episódios tem na temporada " + i + "? ");
            int qtdEpisodios = scanner.nextInt();
            episodiosPorTemporada.put(i, qtdEpisodios);
        }
        scanner.nextLine();

        series.add(new Serie(titulo, genero, ano, temporadas, episodiosPorTemporada));
        System.out.println("📺 Série cadastrada com sucesso!");
    }

    public static void avaliar(Scanner scanner) {
        System.out.print("Informe o título da série: ");
        String titulo = scanner.nextLine();

        for (Serie serie : series) {
            if (serie.titulo.equalsIgnoreCase(titulo)) {
                System.out.print("Você assistiu essa série? (true/false): ");
                serie.consumido = scanner.nextBoolean();
                scanner.nextLine();

                if (serie.consumido) {
                    int somaNotas = 0;
                    for (int i = 1; i <= serie.temporadas; i++) {
                        System.out.print("Avaliação da temporada " + i + " (1-5 estrelas): ");
                        int nota = scanner.nextInt();
                        serie.avaliacaoPorTemporada.put(i, nota);
                        somaNotas += nota;
                    }
                    scanner.nextLine();

                    serie.avaliacao = somaNotas / serie.temporadas; // Média das temporadas
                    System.out.println("✅ Avaliação registrada! Média da série: " + serie.avaliacao + " estrelas");
                } else {
                    System.out.println("❌ A série precisa ser assistida antes de ser avaliada!");
                }
                return;
            }
        }
        System.out.println("❌ Série não encontrada!");
    }
}