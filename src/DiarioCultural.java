import java.util.Scanner;

public class DiarioCultural {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== 🎥📚📺 Diário Cultural ===");
            System.out.println("1. Cadastrar Livro");
            System.out.println("2. Cadastrar Filme");
            System.out.println("3. Cadastrar Série");
            System.out.println("4. Avaliar Livro");
            System.out.println("5. Avaliar Filme");
            System.out.println("6. Avaliar Série");
            System.out.println("7. Buscar");
            System.out.println("8. Listar");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 1) {
                Livro.cadastrar(scanner);
            } else if (opcao == 2) {
                Filme.cadastrar(scanner);
            } else if (opcao == 3) {
                Serie.cadastrar(scanner);
            } else if (opcao == 4) {
                Livro.avaliar(scanner);
            } else if (opcao == 5) {
                Filme.avaliar(scanner);
            } else if (opcao == 6) {
                Serie.avaliar(scanner);
            } else if (opcao == 7) {
                Midia.buscar(scanner);
            } else if (opcao == 8) {
                Midia.listar();
            } else if (opcao == 9) {
                System.out.println("Saindo...");
            } else {
                System.out.println("Opção inválida!");
            }
        } while (opcao != 9);

        scanner.close();
    }
}