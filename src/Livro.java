import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Livro {
    String titulo, autor, editora, isbn, genero;
    int anoPublicacao;
    boolean possuiExemplar, lido;
    int avaliacao;
    String review;
    String dataLeitura;
    static List<Livro> biblioteca = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public Livro(String titulo, String autor, String editora, String isbn, int anoPublicacao, String genero, boolean possuiExemplar) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.possuiExemplar = possuiExemplar;
        this.lido = false;
        this.avaliacao = 0;
        this.review = "";
        this.dataLeitura = "";
    }

    public void avaliarLivro(int avaliacao, String dataLeitura, String review) {
        this.lido = true;
        this.avaliacao = avaliacao;
        this.dataLeitura = dataLeitura;
        this.review = review;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + " | Autor: " + autor + " | Editora: " + editora + " | ISBN: " + isbn +
                " | Ano: " + anoPublicacao + " | Gênero: " + genero + " | Possui Exemplar: " + (possuiExemplar ? "Sim" : "Não") +
                " | Lido: " + (lido ? "Sim" : "Não") + " | Avaliação: " + (lido ? avaliacao + " estrelas" : "N/A") +
                " | Data de Leitura: " + (lido ? dataLeitura : "N/A") + " | Review: " + (lido ? review : "N/A");
    }

    public static void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        System.out.print("Editora: ");
        String editora = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Ano de Publicação: ");
        int anoPublicacao = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Possui um exemplar? (true/false): ");
        boolean possuiExemplar = scanner.nextBoolean();
        scanner.nextLine();

        biblioteca.add(new Livro(titulo, autor, editora, isbn, anoPublicacao, genero, possuiExemplar));
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