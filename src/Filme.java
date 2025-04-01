import java.util.Scanner;

class Filme extends Midia {
    public Filme(String titulo, String genero, int ano) {
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

        filmes.add(new Filme(titulo, genero, ano));
        System.out.println("🎬 Filme cadastrado com sucesso!");
    }

    public static void avaliar(Scanner scanner) {
        System.out.print("Informe o título do filme: ");
        String titulo = scanner.nextLine();

        for (Filme filme : filmes) {
            if (filme.titulo.equalsIgnoreCase(titulo)) {
                System.out.print("Você assistiu esse filme? (true/false): ");
                filme.consumido = scanner.nextBoolean();
                scanner.nextLine();

                if (filme.consumido) {
                    System.out.print("Avaliação (1-5 estrelas): ");
                    filme.avaliacao = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("✅ Avaliação registrada!");
                } else {
                    System.out.println("❌ O filme precisa ser assistido antes de ser avaliado!");
                }
                return;
            }
        }
        System.out.println("❌ Filme não encontrado!");
    }
}