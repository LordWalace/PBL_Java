import java.util.Scanner;

class Livro extends Midia {
    private String autor, editora, isbn;
    private String review;

    public Livro(String titulo, String autor, String editora, String isbn, int ano, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.ano = ano;
        this.genero = genero;
        this.avaliacao = 0;
        this.consumido = false;
    }

    public static void cadastrar(Scanner scanner) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Editora: ");
        String editora = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        livros.add(new Livro(titulo, autor, editora, isbn, ano, genero));
        System.out.println("📚 Livro cadastrado com sucesso!");
    }

    public static void avaliar(Scanner scanner) {
        System.out.print("Informe o título do livro: ");
        String titulo = scanner.nextLine();

        for (Livro livro : livros) {
            if (livro.titulo.equalsIgnoreCase(titulo)) {
                System.out.print("Livro lido? (true/false): ");
                livro.consumido = scanner.nextBoolean();
                scanner.nextLine();

                if (livro.consumido) {
                    System.out.print("Avaliação (1-5 estrelas): ");
                    livro.avaliacao = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Review: ");
                    livro.review = scanner.nextLine();
                    System.out.println("✅ Avaliação registrada!");
                } else {
                    System.out.println("❌ O livro precisa ser lido antes de ser avaliado!");
                }
                return;
            }
        }
        System.out.println("❌ Livro não encontrado!");
    }
}