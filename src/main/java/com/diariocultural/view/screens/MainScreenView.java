// === MainScreenView.java === "View/Screens"

package com.diariocultural.view.screens;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.view.utils.UIFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa a tela principal com os botões de navegação.
 * Esta versão é responsável por passar a instância do controller para as outras telas
 * e utiliza a UIFactory para um estilo consistente.
 */
public class MainScreenView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    /**
     * Construtor para a tela principal.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     */
    public MainScreenView(Consumer<VBox> navigateTo,
                          Stage primaryStage,
                          BiConsumer<String, String> showMessage,
                          DiarioCultural controller) {
        super();
        UIFactory.configurarTelaMenu(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        var welcomeLabel = UIFactory.criarTituloTela("\uD83D\uDCD6 \uD83C\uDFAC \uD83D\uDCFA Diário Cultural \uD83D\uDCFA \uD83C\uDFAC \uD83D\uDCD6");

        var cadastrarBtn = UIFactory.criarBotaoEstilizado("Cadastrar Mídia \uD83D\uDCDD", "#4CAF50");
        cadastrarBtn.setOnAction(e -> navigateTo.accept(new CadastroSelectionView(
                this.navigateTo, this.primaryStage, this.showMessage, this.controller
        )));

        var listarBtn = UIFactory.criarBotaoEstilizado("Listar Mídias \uD83D\uDCCB", "#008CBA");
        listarBtn.setOnAction(e -> navigateTo.accept(new ListagemView(
                this.navigateTo, this.primaryStage, this.showMessage, this.controller
        )));

        var buscarBtn = UIFactory.criarBotaoEstilizado("Buscar Mídia \uD83D\uDD0D", "#f44336");
        buscarBtn.setOnAction(e -> navigateTo.accept(new BuscaView(
                this.navigateTo, this.primaryStage, this.showMessage, this.controller
        )));

        this.getChildren().addAll(welcomeLabel, cadastrarBtn, listarBtn, buscarBtn);
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