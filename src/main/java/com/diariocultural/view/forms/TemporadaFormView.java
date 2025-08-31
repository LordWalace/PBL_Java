// === TemporadaFormView.java === "View/Forms"

package com.diariocultural.view.forms;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Serie;
import com.diariocultural.view.utils.UIFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa o formulário para adicionar uma nova temporada a uma série existente.
 */
public class TemporadaFormView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;
    private final Serie serieAlvo;

    /**
     * Construtor para o formulário de cadastro de Temporada.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     * @param serieAlvo A série à qual a nova temporada será adicionada.
     */
    public TemporadaFormView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller, Serie serieAlvo) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;
        this.serieAlvo = serieAlvo;

        Label title = UIFactory.criarTituloTela("Adicionar Temporada para '" + serieAlvo.getTitulo() + "'");

        GridPane form = UIFactory.criarGridFormulario();

        TextField numeroField = UIFactory.criarTextFieldFormulario();
        TextField anoLancamentoField = UIFactory.criarTextFieldFormulario();
        TextField anoEncerramentoField = UIFactory.criarTextFieldFormulario();
        TextField numEpisodiosField = UIFactory.criarTextFieldFormulario();

        form.add(UIFactory.criarLabelFormulario("Número da Temporada:"), 0, 0);
        form.add(numeroField, 1, 0);
        form.add(UIFactory.criarLabelFormulario("Ano de Lançamento:"), 0, 1);
        form.add(anoLancamentoField, 1, 1);
        form.add(UIFactory.criarLabelFormulario("Ano de Encerramento (opcional):"), 0, 2);
        form.add(anoEncerramentoField, 1, 2);
        form.add(UIFactory.criarLabelFormulario("Número de Episódios:"), 0, 3);
        form.add(numEpisodiosField, 1, 3);

        Button adicionarBtn = UIFactory.criarBotaoEstilizado("Adicionar Temporada", "#0097a7");
        adicionarBtn.setOnAction(e -> {
            try {
                int numero = Integer.parseInt(numeroField.getText());
                int anoLancamento = Integer.parseInt(anoLancamentoField.getText());
                Integer anoEncerramento = anoEncerramentoField.getText().trim().isEmpty() ? null : Integer.parseInt(anoEncerramentoField.getText());
                int numEpisodios = Integer.parseInt(numEpisodiosField.getText());

                controller.adicionarTemporadaSerie(serieAlvo.getTitulo(), numero, anoLancamento, anoEncerramento, numEpisodios);

                showMessage.accept("Sucesso", "Temporada " + numero + " adicionada com sucesso!");

                numeroField.clear();
                anoLancamentoField.clear();
                anoEncerramentoField.clear();
                numEpisodiosField.clear();

            } catch (NumberFormatException ex) {
                showMessage.accept("Erro de Entrada", "Os campos de número, ano e episódios devem ser números válidos.");
            } catch (Exception ex) {
                showMessage.accept("Erro ao Adicionar", ex.getMessage());
            }
        });

        this.getChildren().addAll(title, form, adicionarBtn);
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