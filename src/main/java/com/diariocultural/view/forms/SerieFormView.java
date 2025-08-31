// === SerieFormView.java === "View/Forms"

package com.diariocultural.view.forms;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Serie;
import com.diariocultural.view.utils.MidiaViewHelper;
import com.diariocultural.view.utils.UIFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Representa o formulário de cadastro de Série, integrado com o Controller.
 */
public class SerieFormView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    /**
     * Construtor para o formulário de cadastro de Série.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     */
    public SerieFormView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("\uD83D\uDCFA Cadastrar Série \uD83D\uDCFA");

        GridPane serieForm = UIFactory.criarGridFormulario();

        TextField serieTituloField = UIFactory.criarTextFieldFormulario();
        TextField serieGeneroField = UIFactory.criarTextFieldFormulario();
        TextField serieAnoLancamentoField = UIFactory.criarTextFieldFormulario();
        TextField serieElencoField = UIFactory.criarTextFieldFormulario();
        TextField serieTituloOriginalField = UIFactory.criarTextFieldFormulario();
        TextField serieOndeAssistirField = UIFactory.criarTextFieldFormulario();

        serieElencoField.setPromptText("Ex: Ator Um, Atriz Dois, Ator Três");

        serieForm.add(UIFactory.criarLabelFormulario("Título da Série:"), 0, 0);
        serieForm.add(serieTituloField, 1, 0);
        serieForm.add(UIFactory.criarLabelFormulario("Gênero:"), 0, 1);
        serieForm.add(serieGeneroField, 1, 1);
        serieForm.add(UIFactory.criarLabelFormulario("Ano Lançamento:"), 0, 2);
        serieForm.add(serieAnoLancamentoField, 1, 2);
        serieForm.add(UIFactory.criarLabelFormulario("Elenco (separado por vírgulas):"), 0, 3);
        serieForm.add(serieElencoField, 1, 3);
        serieForm.add(UIFactory.criarLabelFormulario("Título Original:"), 0, 4);
        serieForm.add(serieTituloOriginalField, 1, 4);
        serieForm.add(UIFactory.criarLabelFormulario("Onde Assistir:"), 0, 5);
        serieForm.add(serieOndeAssistirField, 1, 5);

        Button cadastrarSerieBtn = UIFactory.criarBotaoEstilizado("Cadastrar Série", "#f44336");
        cadastrarSerieBtn.setOnAction(e -> {
            try {
                String titulo = serieTituloField.getText();
                String genero = serieGeneroField.getText();
                int anoLancamento = Integer.parseInt(serieAnoLancamentoField.getText());
                String tituloOriginal = serieTituloOriginalField.getText();
                String ondeAssistir = serieOndeAssistirField.getText();

                List<String> elenco = Arrays.stream(serieElencoField.getText().split(","))
                        .map(String::trim)
                        .filter(nome -> !nome.isEmpty())
                        .collect(Collectors.toList());

                Serie serieCadastrada = controller.cadastrarSerie(titulo, genero, anoLancamento, elenco, tituloOriginal, ondeAssistir);

                String detalhes = MidiaViewHelper.formatarParaExibicao(serieCadastrada);
                showMessage.accept("Sucesso!", "Série cadastrada com sucesso:\n\n" + detalhes);

                clearFields(serieTituloField, serieGeneroField, serieAnoLancamentoField, serieElencoField, serieTituloOriginalField, serieOndeAssistirField);

            } catch (NumberFormatException ex) {
                showMessage.accept("Erro de Entrada", "Ano de lançamento inválido. Por favor, insira um número.");
            } catch (IllegalArgumentException | IllegalStateException ex) {
                showMessage.accept("Erro de Validação", ex.getMessage());
            } catch (Exception ex) {
                showMessage.accept("Erro Inesperado", "Ocorreu um erro ao cadastrar a série: " + ex.getMessage());
            }
        });

        this.getChildren().addAll(title, serieForm, cadastrarSerieBtn);
    }

    /**
     * Limpa os campos de texto do formulário.
     * @param fields Os campos de texto a serem limpos.
     */
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
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