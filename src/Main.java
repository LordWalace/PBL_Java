import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu: ");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Avaliar Livro");
            System.out.println("3. Listar Livros");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                Livro.cadastrarLivro();
            } else if (opcao == 2) {
                Livro.avaliarLivro();
            } else if (opcao == 3) {
                Livro.listarLivros();
            } else if (opcao == 4) {
                System.out.println("Saindo...");
                scanner.close();
                break;
            } else {
                System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}