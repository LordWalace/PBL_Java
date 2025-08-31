// === LivroFormView.java === "View/Forms"

package com.diariocultural.view.forms;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Livro;
import com.diariocultural.view.utils.MidiaViewHelper;
import com.diariocultural.view.utils.UIFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa o formulário de cadastro de Livro, integrado com o Controller.
 */
public class LivroFormView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    /**
     * Construtor para o formulário de cadastro de Livro.
     * @param navigateTo Consumidor para navegar entre as telas.
     * @param primaryStage O Stage principal da aplicação.
     * @param showMessage Consumidor para exibir mensagens pop-up.
     * @param controller A instância do controller principal da aplicação.
     */
    public LivroFormView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("\uD83D\uDCD6 Cadastrar Livro \uD83D\uDCD6");

        GridPane livroForm = UIFactory.criarGridFormulario();

        TextField livroTituloField = UIFactory.criarTextFieldFormulario();
        TextField livroAutorField = UIFactory.criarTextFieldFormulario();
        TextField livroGeneroField = UIFactory.criarTextFieldFormulario();
        TextField livroAnoLancamentoField = UIFactory.criarTextFieldFormulario();
        TextField livroEditoraField = UIFactory.criarTextFieldFormulario();
        TextField livroIsbnField = UIFactory.criarTextFieldFormulario();
        CheckBox livroPossuiExemplarCheck = new CheckBox();
        livroPossuiExemplarCheck.setStyle("-fx-mark-color: #4CAF50;");

        livroForm.add(UIFactory.criarLabelFormulario("Título do Livro:"), 0, 0);
        livroForm.add(livroTituloField, 1, 0);
        livroForm.add(UIFactory.criarLabelFormulario("Autor:"), 0, 1);
        livroForm.add(livroAutorField, 1, 1);
        livroForm.add(UIFactory.criarLabelFormulario("Gênero:"), 0, 2);
        livroForm.add(livroGeneroField, 1, 2);
        livroForm.add(UIFactory.criarLabelFormulario("Ano Lançamento:"), 0, 3);
        livroForm.add(livroAnoLancamentoField, 1, 3);
        livroForm.add(UIFactory.criarLabelFormulario("Editora:"), 0, 4);
        livroForm.add(livroEditoraField, 1, 4);
        livroForm.add(UIFactory.criarLabelFormulario("ISBN:"), 0, 5);
        livroForm.add(livroIsbnField, 1, 5);
        livroForm.add(UIFactory.criarLabelFormulario("Possui Exemplar Físico?"), 0, 6);
        livroForm.add(livroPossuiExemplarCheck, 1, 6);

        Button cadastrarLivroBtn = UIFactory.criarBotaoEstilizado("Cadastrar Livro", "#4CAF50");
        cadastrarLivroBtn.setOnAction(e -> {
            try {
                String titulo = livroTituloField.getText();
                String autor = livroAutorField.getText();
                String editora = livroEditoraField.getText();
                String isbn = livroIsbnField.getText();
                int anoLancamento = Integer.parseInt(livroAnoLancamentoField.getText());
                String genero = livroGeneroField.getText();
                boolean possuiExemplar = livroPossuiExemplarCheck.isSelected();

                Livro livroCadastrado = controller.cadastrarLivro(titulo, autor, editora, isbn, anoLancamento, genero, possuiExemplar);

                String detalhes = MidiaViewHelper.formatarParaExibicao(livroCadastrado);
                showMessage.accept("Sucesso!", "Livro cadastrado com sucesso:\n\n" + detalhes);

                clearFields(livroTituloField, livroAutorField, livroEditoraField, livroIsbnField, livroAnoLancamentoField, livroGeneroField);
                livroPossuiExemplarCheck.setSelected(false);

            } catch (NumberFormatException ex) {
                showMessage.accept("Erro de Entrada", "Ano de lançamento inválido. Por favor, insira um número.");
            } catch (IllegalArgumentException | IllegalStateException ex) {
                showMessage.accept("Erro de Validação", ex.getMessage());
            } catch (Exception ex) {
                showMessage.accept("Erro Inesperado", "Ocorreu um erro ao cadastrar o livro: " + ex.getMessage());
            }
        });

        this.getChildren().addAll(title, livroForm, cadastrarLivroBtn);
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