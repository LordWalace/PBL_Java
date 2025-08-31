// === UIFactory.java === "View/Utils"

package com.diariocultural.view.utils;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Fábrica centralizada para criar componentes de UI com um estilo padronizado.
 * Alterar os valores nesta classe afeta a aplicação inteira.
 */
public final class UIFactory {

    private static final String COR_FUNDO_TELA = "#424242";
    private static final String COR_TEXTO_TITULO = "#b0bec5";
    private static final String COR_TEXTO_LABEL = "#eceff1";
    private static final String COR_TEXTO_BOTAO_ESCURO = "white";
    private static final String FONTE_PADRAO = "System";
    private static final String OPACIDADE_BOTAO = "CC"; // ~80% de opacidade

    /**
     * Construtor privado para impedir a instanciação.
     */
    private UIFactory() {}

    /**
     * Configura um VBox para telas de menu, centralizando o conteúdo verticalmente.
     * Ideal para telas com poucos botões, como a tela principal.
     * @param tela O VBox da tela a ser configurado.
     */
    public static void configurarTelaMenu(VBox tela) {
        tela.setSpacing(30);
        tela.setAlignment(Pos.CENTER);
        tela.setStyle("-fx-background-color: " + COR_FUNDO_TELA + "; -fx-padding: 30;");
    }

    /**
     * Configura um VBox para telas de formulário ou listagem, alinhando o conteúdo ao topo.
     * @param tela O VBox da tela a ser configurado.
     */
    public static void configurarTelaFormulario(VBox tela) {
        tela.setSpacing(20);
        tela.setAlignment(Pos.TOP_CENTER);
        tela.setStyle("-fx-background-color: " + COR_FUNDO_TELA + "; -fx-padding: 30;");
    }

    /**
     * Cria um Label de título padrão para as telas.
     * @param texto O texto do título.
     * @return Um Label de título estilizado.
     */
    public static Label criarTituloTela(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font(FONTE_PADRAO, FontWeight.BOLD, 24));
        label.setTextFill(Color.web(COR_TEXTO_TITULO));
        return label;
    }

    /**
     * Cria um Label padrão para formulários.
     * @param texto O texto do label.
     * @return Um Label de formulário estilizado.
     */
    public static Label criarLabelFormulario(String texto) {
        Label label = new Label(texto);
        label.setFont(Font.font(FONTE_PADRAO, FontWeight.NORMAL, 14));
        label.setTextFill(Color.web(COR_TEXTO_LABEL));
        return label;
    }

    /**
     * Cria um TextField padrão para formulários.
     * @return Um TextField estilizado.
     */
    public static TextField criarTextFieldFormulario() {
        TextField textField = new TextField();
        textField.setStyle(
                "-fx-background-color: #616161;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-color: #9e9e9e;" +
                        "-fx-border-radius: 5;" +
                        "-fx-pref-height: 35px;"
        );
        return textField;
    }

    /**
     * Cria um GridPane padrão para organizar formulários.
     * @return Um GridPane pré-configurado.
     */
    public static GridPane criarGridFormulario() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);
        return grid;
    }

    /**
     * Cria um botão padrão com cores personalizáveis, borda preta e transparência.
     * @param texto O texto do botão.
     * @param corFundo A cor de fundo do botão (formato hexadecimal).
     * @return Um Button estilizado.
     */
    public static Button criarBotaoEstilizado(String texto, String corFundo) {
        Button button = new Button(texto);
        // CORREÇÃO: Aumenta a fonte do botão para melhor proporção.
        button.setFont(Font.font(FONTE_PADRAO, FontWeight.BOLD, 16));
        button.setStyle(
                "-fx-background-color: " + corFundo + OPACIDADE_BOTAO + ";" +
                        "-fx-text-fill: " + COR_TEXTO_BOTAO_ESCURO + ";" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 7;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 2, 2);"
        );
        // CORREÇÃO: Aumenta o tamanho do botão para preencher mais espaço.
        button.setPrefSize(240, 55);
        return button;
    }

    /**
     * Cria um botão de navegação menor para a barra inferior.
     * @param texto O texto do botão.
     * @param corFundo A cor de fundo do botão (formato hexadecimal).
     * @return Um Button de navegação estilizado e menor.
     */
    public static Button criarBotaoNavegacao(String texto, String corFundo) {
        Button button = criarBotaoEstilizado(texto, corFundo);
        button.setPrefSize(150, 35);
        button.setFont(Font.font(FONTE_PADRAO, FontWeight.BOLD, 12));
        return button;
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