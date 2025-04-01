import java.util.*;

class DiarioCultural {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("Diário Cultural");
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
                Midia.cadastrar(scanner, "Livro");
            } else if (opcao == 2) {
                Midia.cadastrar(scanner, "Filme");
            } else if (opcao == 3) {
                Midia.cadastrar(scanner, "Serie");
            } else if (opcao == 4) {
                Midia.avaliar(scanner, "Livro");
            } else if (opcao == 5) {
                Midia.avaliar(scanner, "Filme");
            } else if (opcao == 6) {
                Midia.avaliar(scanner, "Serie");
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
    }
}
