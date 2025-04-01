import java.util.Scanner;

class Serie extends Midia {
    public Serie(String titulo, String genero, int ano) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano = ano;
        this.avaliacao = 0;
        this.consumido = false;
    }

    public static void cadastrar(Scanner scanner) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        series.add(new Serie(titulo, genero, ano));
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
                    System.out.print("Avaliação (1-5 estrelas): ");
                    serie.avaliacao = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("✅ Avaliação registrada!");
                } else {
                    System.out.println("❌ A série precisa ser assistida antes de ser avaliada!");
                }
                return;
            }
        }
        System.out.println("❌ Série não encontrada!");
    }
}