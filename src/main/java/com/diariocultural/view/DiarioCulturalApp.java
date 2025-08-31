// === DiarioCulturalApp.java === "View"

package com.diariocultural.view;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.service.Catalogo;
import com.diariocultural.view.screens.MainScreenView;
import com.diariocultural.view.utils.UIFactory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Classe principal da aplicação JavaFX do Diário Cultural.
 * Responsável por gerenciar a navegação, o controller e os componentes globais da UI.
 */
public class DiarioCulturalApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private DiarioCultural controller;
    private final Deque<VBox> historicoNavegacao = new ArrayDeque<>();
    private VBox telaInicial; // Armazena a referência da tela inicial

    /**
     * Método principal para iniciar a aplicação.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Ponto de entrada principal para todos os aplicativos JavaFX.
     * @param primaryStage O Stage principal para esta aplicação.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Diário Cultural");

        Catalogo catalogo = new Catalogo();
        this.controller = new DiarioCultural(catalogo);

        rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #424242;");

        setupBotoesGlobais();

        Scene scene = new Scene(rootLayout, 800, 700);
        primaryStage.setScene(scene);

        showMainScreen();

        primaryStage.show();
    }

    /**
     * Configura os botões globais "Voltar", "Início" e "Sair" na parte inferior da tela.
     */
    private void setupBotoesGlobais() {
        Button voltarBtn = UIFactory.criarBotaoEstilizado("◀ Voltar", "#757575");
        voltarBtn.setOnAction(e -> navegarParaTras());

        Button inicioBtn = UIFactory.criarBotaoEstilizado("⌂ Início", "#616161");
        inicioBtn.setOnAction(e -> navegarParaInicio());

        Button sairBtn = UIFactory.criarBotaoEstilizado("Sair", "#c62828");
        sairBtn.setOnAction(e -> primaryStage.close());

        HBox barraInferior = new HBox(15, voltarBtn, inicioBtn, sairBtn);
        barraInferior.setPadding(new Insets(15));
        barraInferior.setAlignment(Pos.CENTER);
        barraInferior.setStyle("-fx-background-color: #373737;");

        rootLayout.setBottom(barraInferior);
    }

    /**
     * Exibe a tela principal.
     */
    private void showMainScreen() {
        this.telaInicial = new MainScreenView(
                this::navigateTo,
                primaryStage,
                this::showMessage,
                this.controller
        );
        rootLayout.setCenter(this.telaInicial);
    }

    /**
     * Navega para uma nova tela, empilhando a tela atual no histórico.
     * @param view A nova tela a ser exibida.
     */
    public void navigateTo(VBox view) {
        VBox atual = (VBox) rootLayout.getCenter();
        if (atual != null) {
            historicoNavegacao.push(atual);
        }
        rootLayout.setCenter(view);
    }

    /**
     * Navega para a tela anterior, desempilhando do histórico.
     */
    public void navegarParaTras() {
        if (!historicoNavegacao.isEmpty()) {
            VBox telaAnterior = historicoNavegacao.pop();
            rootLayout.setCenter(telaAnterior);
        } else {
            showMessage("Aviso", "Não há tela anterior para voltar.");
        }
    }

    /**
     * Navega diretamente para a tela inicial, limpando o histórico.
     */
    public void navegarParaInicio() {
        historicoNavegacao.clear();
        rootLayout.setCenter(this.telaInicial);
    }

    /**
     * Exibe uma mensagem em um pop-up simples e estilizado.
     * @param title Título do pop-up.
     * @param message Mensagem a ser exibida.
     */
    public void showMessage(String title, String message) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle(title);

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setPadding(new Insets(25));
        dialogVbox.setStyle("-fx-background-color: #3c3c3c; -fx-border-color: #f44336; -fx-border-width: 2;");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white; -fx-wrap-text: true;");
        messageLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Button okButton = UIFactory.criarBotaoEstilizado("OK", "#f44336");
        okButton.setOnAction(e -> dialogStage.close());

        dialogVbox.getChildren().addAll(messageLabel, okButton);

        Scene dialogScene = new Scene(dialogVbox);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
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