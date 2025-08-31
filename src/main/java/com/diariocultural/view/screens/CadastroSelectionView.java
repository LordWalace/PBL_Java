// === CadastroSelectionView.java === "View/Screens"

package com.diariocultural.view.screens;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.view.forms.FilmeFormView;
import com.diariocultural.view.forms.LivroFormView;
import com.diariocultural.view.forms.SerieFormView;
import com.diariocultural.view.utils.UIFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa a tela de seleção para cadastro de diferentes tipos de mídia.
 * Utiliza a UIFactory para um estilo consistente.
 */
public class CadastroSelectionView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    /**
     * Construtor para a tela de seleção de cadastro.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     */
    public CadastroSelectionView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        // CORREÇÃO: Usa o método específico para telas de menu, que centraliza o conteúdo.
        UIFactory.configurarTelaMenu(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("Selecione o tipo de mídia para cadastrar:");

        Button cadastrarLivroBtn = UIFactory.criarBotaoEstilizado("\uD83D\uDCD6 Cadastrar Livro \uD83D\uDCD6", "#4CAF50");
        cadastrarLivroBtn.setOnAction(e -> {
            navigateTo.accept(new LivroFormView(this.navigateTo, this.primaryStage, this.showMessage, this.controller));
        });

        Button cadastrarFilmeBtn = UIFactory.criarBotaoEstilizado("\uD83C\uDFAC Cadastrar Filme \uD83C\uDFAC", "#008CBA");
        cadastrarFilmeBtn.setOnAction(e -> {
            navigateTo.accept(new FilmeFormView(this.navigateTo, this.primaryStage, this.showMessage, this.controller));
        });

        Button cadastrarSerieBtn = UIFactory.criarBotaoEstilizado("\uD83D\uDCFA Cadastrar Série \uD83D\uDCFA", "#f44336");
        cadastrarSerieBtn.setOnAction(e -> {
            navigateTo.accept(new SerieFormView(this.navigateTo, this.primaryStage, this.showMessage, this.controller));
        });

        this.getChildren().addAll(title, cadastrarLivroBtn, cadastrarFilmeBtn, cadastrarSerieBtn);
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