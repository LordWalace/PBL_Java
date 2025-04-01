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
}
