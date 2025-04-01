import java.util.*;

abstract class Midia {
    protected String titulo;
    protected String genero;
    protected int ano;
    protected int avaliacao;
    protected boolean consumido; // Indica se foi lido/assistido

    static List<Livro> livros = new ArrayList<>();
    static List<Filme> filmes = new ArrayList<>();
    static List<Serie> series = new ArrayList<>();

    public static void buscar(Scanner scanner) {
        System.out.print("Digite o título que deseja buscar: ");
        String busca = scanner.nextLine().toLowerCase();

        System.out.println("\n🔎 Resultados da Busca:");
        boolean encontrado = false;

        for (Midia m : livros) if (m.titulo.toLowerCase().contains(busca)) { System.out.println("📚 Livro: " + m.titulo); encontrado = true; }
        for (Midia m : filmes) if (m.titulo.toLowerCase().contains(busca)) { System.out.println("🎬 Filme: " + m.titulo); encontrado = true; }
        for (Midia m : series) if (m.titulo.toLowerCase().contains(busca)) { System.out.println("📺 Série: " + m.titulo); encontrado = true; }

        if (!encontrado) System.out.println("Nenhuma mídia encontrada.");
    }

    public static void listar() {
        Comparator<Midia> comparator = Comparator.comparingInt(m -> -m.avaliacao);

        System.out.println("\n=== ⭐ Avaliados ===");
        listarPorCategoria("📚 Livros Avaliados", livros, comparator, true);
        listarPorCategoria("🎬 Filmes Avaliados", filmes, comparator, true);
        listarPorCategoria("📺 Séries Avaliadas", series, comparator, true);

        System.out.println("\n=== ❌ Não Avaliados ===");
        listarPorCategoria("📚 Livros Não Lidos", livros, comparator, false);
        listarPorCategoria("🎬 Filmes Não Assistidos", filmes, comparator, false);
        listarPorCategoria("📺 Séries Não Assistidas", series, comparator, false);
    }

    private static void listarPorCategoria(String titulo, List<? extends Midia> lista, Comparator<Midia> comparator, boolean avaliados) {
        System.out.println("\n" + titulo);
        lista.stream()
                .filter(m -> (avaliados ? m.avaliacao > 0 : !m.consumido))
                .sorted(comparator)
                .forEach(m -> System.out.println(m.titulo + (avaliados ? " - " + m.avaliacao + " estrelas" : "")));
    }
}