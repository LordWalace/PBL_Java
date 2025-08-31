// === MidiaViewHelper.java === "View/Utils"

package com.diariocultural.view.utils;

import com.diariocultural.model.*;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária (Helper) para a camada de Visão (View).
 * Sua única responsabilidade é pegar objetos do modelo (como Midia)
 * e formatá-los em Strings legíveis para exibição ao usuário.
 */
public final class MidiaViewHelper {

    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor privado para impedir que esta classe utilitária seja instanciada.
     */
    private MidiaViewHelper() {}

    /**
     * Formata um objeto Midia em uma String completa e detalhada para exibição.
     * Este método agora verifica o tipo específico da mídia para incluir detalhes exclusivos,
     * com cada informação em uma nova linha.
     *
     * @param midia O objeto Midia a ser formatado.
     * @return Uma String formatada para a interface do usuário.
     */
    public static String formatarParaExibicao(Midia midia) {
        if (midia == null) {
            return "Mídia não encontrada.";
        }

        StringBuilder sb = new StringBuilder();

        // --- PARTE COMUM A TODAS AS MÍDIAS ---
        sb.append("Título: ").append(midia.getTitulo()).append("\n");
        sb.append("Gênero: ").append(midia.getGenero()).append("\n");
        sb.append("Ano de Lançamento: ").append(midia.getAnoLancamento()).append("\n");

        if (midia.getAvaliacao() > 0) {
            sb.append("Avaliação: ").append(midia.getAvaliacao()).append("/5\n");
        } else {
            sb.append("Avaliação: N/A\n");
        }

        if (midia.isConsumido()) {
            sb.append("Status: Consumido");
            if (midia.getDataConsumo() != null) {
                sb.append(" em ").append(midia.getDataConsumo().format(FORMATADOR_DATA));
            }
            sb.append("\n");
        } else {
            sb.append("Status: Não Consumido\n");
        }

        // --- PARTE ESPECÍFICA PARA CADA TIPO DE MÍDIA ---
        if (midia instanceof Livro livro) {
            sb.append("Autor: ").append(livro.getAutor()).append("\n");
            sb.append("Editora: ").append(livro.getEditora()).append("\n");
            sb.append("ISBN: ").append(livro.getIsbn()).append("\n");
            sb.append("Possui Exemplar Físico: ").append(livro.isPossuiExemplar() ? "Sim" : "Não").append("\n");
        } else if (midia instanceof Filme filme) {
            sb.append("Título Original: ").append(filme.getTituloOriginal()).append("\n");

            // Formata a duração de forma amigável (ex: 2h 8min)
            Duration duracao = filme.getTempoDeDuracao();
            if (duracao != null) {
                long horas = duracao.toHours();
                long minutos = duracao.toMinutesPart();
                if (horas > 0) {
                    sb.append(String.format("Duração: %dh %dmin\n", horas, minutos));
                } else {
                    sb.append(String.format("Duração: %dmin\n", minutos));
                }
            }

            sb.append("Direção: ").append(filme.getDirecao()).append("\n");
            sb.append("Roteiro: ").append(filme.getRoteiro()).append("\n");
            sb.append("Onde Assistir: ").append(filme.getOndeAssistir()).append("\n");
            sb.append("Elenco: ").append(String.join(", ", filme.getElenco())).append("\n");
        } else if (midia instanceof Serie serie) {
            sb.append("Título Original: ").append(serie.getTituloOriginal()).append("\n");
            sb.append("Onde Assistir: ").append(serie.getOndeAssistir()).append("\n");
            sb.append("Elenco: ").append(String.join(", ", serie.getElenco())).append("\n");
            sb.append("Temporadas Cadastradas: ").append(serie.getNumeroTemporadas()).append("\n");
        }

        // --- PARTE DA REVIEW (COMUM A TODOS) ---
        Review review = midia.getReview();
        if (review != null) {
            sb.append("Review: \"").append(review.getTexto()).append("\"");
            if (review.getDataCriacao() != null) {
                sb.append(" (escrita em ").append(review.getDataCriacao().format(FORMATADOR_DATA)).append(")");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Formata um objeto Midia em uma linha única e concisa, ideal para listas.
     * Este método agora segue o formato "Nome - Gênero (Ano) - Avaliação".
     *
     * @param midia O objeto Midia a ser formatado.
     * @return Uma String de resumo formatada para listas.
     */
    public static String formatarParaLista(Midia midia) {
        if (midia == null) {
            return "Mídia inválida.";
        }

        String avaliacaoStr = midia.getAvaliacao() > 0 ? midia.getAvaliacao() + "/5" : "N/A";

        return String.format("%s - %s (%d) - Avaliação: %s",
                midia.getTitulo(),
                midia.getGenero(),
                midia.getAnoLancamento(),
                avaliacaoStr);
    }
}

/******************************************************************************************

 Autor: Walace de Jesus Venas
 Componente Curricular: EXA863 MI-PROGRAMAÇÃO
 Concluído em: 01/07/2025
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 *******************************************************************************************/