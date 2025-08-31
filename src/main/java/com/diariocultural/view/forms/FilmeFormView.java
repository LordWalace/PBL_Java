// === FilmeFormView.java === "View/Forms"

package com.diariocultural.view.forms;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Filme;
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
 * Representa o formulário de cadastro de Filme, integrado com o Controller.
 */
public class FilmeFormView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    /**
     * Construtor para o formulário de cadastro de Filme.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     */
    public FilmeFormView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("\uD83C\uDFAC Cadastrar Filme \uD83C\uDFAC");

        GridPane filmeForm = UIFactory.criarGridFormulario();

        TextField filmeTituloField = UIFactory.criarTextFieldFormulario();
        TextField filmeGeneroField = UIFactory.criarTextFieldFormulario();
        TextField filmeAnoLancamentoField = UIFactory.criarTextFieldFormulario();
        TextField filmeDuracaoField = UIFactory.criarTextFieldFormulario(); // NOVO CAMPO
        TextField filmeDirecaoField = UIFactory.criarTextFieldFormulario();
        TextField filmeRoteiroField = UIFactory.criarTextFieldFormulario();
        TextField filmeElencoField = UIFactory.criarTextFieldFormulario();
        TextField filmeTituloOriginalField = UIFactory.criarTextFieldFormulario();
        TextField filmeOndeAssistirField = UIFactory.criarTextFieldFormulario();

        filmeElencoField.setPromptText("Ex: Ator Um, Atriz Dois, Ator Três");

        filmeForm.add(UIFactory.criarLabelFormulario("Título do Filme:"), 0, 0);
        filmeForm.add(filmeTituloField, 1, 0);
        filmeForm.add(UIFactory.criarLabelFormulario("Gênero:"), 0, 1);
        filmeForm.add(filmeGeneroField, 1, 1);
        filmeForm.add(UIFactory.criarLabelFormulario("Ano Lançamento:"), 0, 2);
        filmeForm.add(filmeAnoLancamentoField, 1, 2);
        filmeForm.add(UIFactory.criarLabelFormulario("Duração (min):"), 0, 3); // NOVO LABEL
        filmeForm.add(filmeDuracaoField, 1, 3); // NOVO CAMPO ADICIONADO
        filmeForm.add(UIFactory.criarLabelFormulario("Direção:"), 0, 4);
        filmeForm.add(filmeDirecaoField, 1, 4);
        filmeForm.add(UIFactory.criarLabelFormulario("Roteiro:"), 0, 5);
        filmeForm.add(filmeRoteiroField, 1, 5);
        filmeForm.add(UIFactory.criarLabelFormulario("Elenco (separado por vírgulas):"), 0, 6);
        filmeForm.add(filmeElencoField, 1, 6);
        filmeForm.add(UIFactory.criarLabelFormulario("Título Original:"), 0, 7);
        filmeForm.add(filmeTituloOriginalField, 1, 7);
        filmeForm.add(UIFactory.criarLabelFormulario("Onde Assistir:"), 0, 8);
        filmeForm.add(filmeOndeAssistirField, 1, 8);

        Button cadastrarFilmeBtn = UIFactory.criarBotaoEstilizado("Cadastrar Filme", "#008CBA");
        cadastrarFilmeBtn.setOnAction(e -> {
            try {
                String titulo = filmeTituloField.getText();
                String genero = filmeGeneroField.getText();
                int anoLancamento = Integer.parseInt(filmeAnoLancamentoField.getText());
                int duracao = Integer.parseInt(filmeDuracaoField.getText()); // LÊ O NOVO CAMPO
                String direcao = filmeDirecaoField.getText();
                String roteiro = filmeRoteiroField.getText();
                String tituloOriginal = filmeTituloOriginalField.getText();
                String ondeAssistir = filmeOndeAssistirField.getText();

                List<String> elenco = Arrays.stream(filmeElencoField.getText().split(","))
                        .map(String::trim)
                        .filter(nome -> !nome.isEmpty())
                        .collect(Collectors.toList());

                // PASSA O NOVO PARÂMETRO PARA O CONTROLLER
                Filme filmeCadastrado = controller.cadastrarFilme(titulo, genero, anoLancamento, duracao, direcao, roteiro, elenco, tituloOriginal, ondeAssistir);

                String detalhes = MidiaViewHelper.formatarParaExibicao(filmeCadastrado);
                showMessage.accept("Sucesso!", "Filme cadastrado com sucesso:\n\n" + detalhes);

                clearFields(filmeTituloField, filmeGeneroField, filmeAnoLancamentoField, filmeDuracaoField, filmeDirecaoField, filmeRoteiroField, filmeElencoField, filmeTituloOriginalField, filmeOndeAssistirField);

            } catch (NumberFormatException ex) {
                showMessage.accept("Erro de Entrada", "Ano de lançamento e duração devem ser números válidos.");
            } catch (IllegalArgumentException | IllegalStateException ex) {
                showMessage.accept("Erro de Validação", ex.getMessage());
            } catch (Exception ex) {
                showMessage.accept("Erro Inesperado", "Ocorreu um erro ao cadastrar o filme: " + ex.getMessage());
            }
        });

        this.getChildren().addAll(title, filmeForm, cadastrarFilmeBtn);
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