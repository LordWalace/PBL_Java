import java.util.Scanner;

public class DiarioCultural {
    private final Scanner scanner;

    public DiarioCultural() {
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;

        do {
            System.out.println("\n=== 🎥📚📺 Diário Cultural 🎥📚📺 ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Avaliar");
            System.out.println("3. Buscar");
            System.out.println("4. Listar");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 1) {
                menuCadastro();
            } else if (opcao == 2) {
                menuAvaliacao();
            } else if (opcao == 3) {
                Midia.buscar(scanner);
            } else if (opcao == 4) {
                Midia.listar();
            } else if (opcao == 5) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 5);

        scanner.close();
    }

    private void menuCadastro() {
        int opcao;
        do {
            System.out.println("\n--- Cadastro ---");
            System.out.println("1. Livro");
            System.out.println("2. Filme");
            System.out.println("3. Série");
            System.out.println("4. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                Livro.cadastrar(scanner);
            } else if (opcao == 2) {
                Filme.cadastrar(scanner);
            } else if (opcao == 3) {
                Serie.cadastrar(scanner);
            } else if (opcao == 4) {
                System.out.println("Voltando ao menu principal...");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

    private void menuAvaliacao() {
        int opcao;
        do {
            System.out.println("\n--- Avaliação ---");
            System.out.println("1. Livro");
            System.out.println("2. Filme");
            System.out.println("3. Série");
            System.out.println("4. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                Livro.avaliar(scanner);
            } else if (opcao == 2) {
                Filme.avaliar(scanner);
            } else if (opcao == 3) {
                Serie.avaliar(scanner);
            } else if (opcao == 4) {
                System.out.println("Voltando ao menu principal...");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

    public static void main(String[] args) {
        DiarioCultural app = new DiarioCultural();
        app.iniciar();
    }
}
