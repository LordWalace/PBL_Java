// === BuscaView.java === "Forms/Screens"

package com.diariocultural.view.screens;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.Midia;
import com.diariocultural.model.Serie;
import com.diariocultural.model.Temporada;
import com.diariocultural.view.forms.TemporadaFormView;
import com.diariocultural.view.utils.MidiaViewHelper;
import com.diariocultural.view.utils.UIFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa a tela de busca de mídias, com funcionalidades de busca avançada e ações contextuais.
 */
public class BuscaView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    private ListView<Midia> resultadosListView;
    private HBox painelAcoesContextuais;
    private VBox painelDetalhesSerie;
    private Midia midiaSelecionada;
    private Temporada temporadaSelecionada;

    /**
     * Construtor para a tela de busca de mídias.
     */
    public BuscaView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("Buscar Mídias");

        GridPane buscaPane = criarPainelDeBusca();

        resultadosListView = new ListView<>();
        resultadosListView.setPrefHeight(250);
        configurarListView();

        Button searchButton = UIFactory.criarBotaoEstilizado("Buscar", "#f44336");
        searchButton.setOnAction(e -> realizarBusca(buscaPane));

        painelAcoesContextuais = new HBox(10);
        painelAcoesContextuais.setAlignment(Pos.CENTER);
        painelAcoesContextuais.setMinHeight(50);

        painelDetalhesSerie = new VBox(10);
        painelDetalhesSerie.setAlignment(Pos.CENTER);

        this.getChildren().addAll(title, buscaPane, searchButton, resultadosListView, painelAcoesContextuais, painelDetalhesSerie);
    }

    /**
     * Cria e configura o painel com os controles de busca.
     */
    private GridPane criarPainelDeBusca() {
        GridPane buscaPane = UIFactory.criarGridFormulario();

        ChoiceBox<String> tipoMidiaBox = new ChoiceBox<>(FXCollections.observableArrayList("Livro", "Filme", "Série"));
        ChoiceBox<String> criterioBuscaBox = new ChoiceBox<>();

        tipoMidiaBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if ("Livro".equals(newVal)) {
                criterioBuscaBox.setItems(FXCollections.observableArrayList("Título", "Autor", "Gênero", "Ano", "ISBN"));
            } else if ("Filme".equals(newVal)) {
                criterioBuscaBox.setItems(FXCollections.observableArrayList("Título", "Diretor", "Ator", "Gênero", "Ano"));
            } else if ("Série".equals(newVal)) {
                criterioBuscaBox.setItems(FXCollections.observableArrayList("Título"));
            }
            criterioBuscaBox.getSelectionModel().selectFirst();
        });
        tipoMidiaBox.getSelectionModel().selectFirst();

        TextField termoBuscaField = UIFactory.criarTextFieldFormulario();
        termoBuscaField.setPromptText("Digite o termo da busca...");

        buscaPane.add(UIFactory.criarLabelFormulario("Buscar por:"), 0, 0);
        buscaPane.add(tipoMidiaBox, 1, 0);
        buscaPane.add(UIFactory.criarLabelFormulario("Critério:"), 0, 1);
        buscaPane.add(criterioBuscaBox, 1, 1);
        buscaPane.add(UIFactory.criarLabelFormulario("Termo:"), 0, 2);
        buscaPane.add(termoBuscaField, 1, 2);

        return buscaPane;
    }

    /**
     * Configura a ListView para exibir resultados detalhados e lidar com seleções.
     */
    private void configurarListView() {
        resultadosListView.setCellFactory(param -> new ListCell<>() {
            private final Label label = new Label();
            {
                label.setWrapText(true);
                label.setStyle("-fx-text-fill: black; -fx-padding: 5px;");
            }
            @Override
            protected void updateItem(Midia item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(MidiaViewHelper.formatarParaExibicao(item));
                    setGraphic(label);
                }
            }
        });

        resultadosListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            midiaSelecionada = newSelection;
            temporadaSelecionada = null;
            atualizarPaineisContextuais();
        });
    }

    /**
     * Executa a busca com base nos critérios selecionados e exibe os resultados.
     */
    private void realizarBusca(GridPane buscaPane) {
        ChoiceBox<String> tipoMidiaBox = (ChoiceBox<String>) buscaPane.getChildren().get(1);
        ChoiceBox<String> criterioBuscaBox = (ChoiceBox<String>) buscaPane.getChildren().get(3);
        TextField termoBuscaField = (TextField) buscaPane.getChildren().get(5);

        String tipo = tipoMidiaBox.getValue();
        String criterio = criterioBuscaBox.getValue();
        String termo = termoBuscaField.getText();

        if (termo == null || termo.trim().isEmpty()) {
            showMessage.accept("Aviso", "Por favor, digite um termo para buscar.");
            return;
        }

        try {
            List<? extends Midia> resultados = Collections.emptyList();

            if ("Livro".equals(tipo)) {
                switch (criterio) {
                    case "Título": resultados = controller.buscarLivros(termo, null, null, null, null); break;
                    case "Autor": resultados = controller.buscarLivros(null, termo, null, null, null); break;
                    case "Gênero": resultados = controller.buscarLivros(null, null, termo, null, null); break;
                    case "Ano": resultados = controller.buscarLivros(null, null, null, Integer.parseInt(termo), null); break;
                    case "ISBN": resultados = controller.buscarLivros(null, null, null, null, termo); break;
                }
            } else if ("Filme".equals(tipo)) {
                switch (criterio) {
                    case "Título": resultados = controller.buscarFilmes(termo, null, null, null, null); break;
                    case "Diretor": resultados = controller.buscarFilmes(null, termo, null, null, null); break;
                    case "Ator": resultados = controller.buscarFilmes(null, null, termo, null, null); break;
                    case "Gênero": resultados = controller.buscarFilmes(null, null, null, termo, null); break;
                    case "Ano": resultados = controller.buscarFilmes(null, null, null, null, Integer.parseInt(termo)); break;
                }
            } else if ("Série".equals(tipo)) {
                resultados = controller.buscarSeriesPorTitulo(termo);
            }

            exibirResultados(resultados);

        } catch (NumberFormatException e) {
            showMessage.accept("Erro de Entrada", "Para busca por ano, por favor, insira um número válido.");
        } catch (Exception e) {
            showMessage.accept("Erro", "Ocorreu um erro ao realizar a busca: " + e.getMessage());
        }
    }

    /**
     * Formata e exibe a lista de resultados na ListView.
     */
    private void exibirResultados(List<? extends Midia> resultados) {
        resultadosListView.setItems(FXCollections.observableArrayList(resultados));
        if (resultados.isEmpty()) {
            Label placeholder = new Label("Nenhum resultado encontrado para os critérios informados.");
            placeholder.setTextFill(Color.web("#222"));
            resultadosListView.setPlaceholder(placeholder);
        }
        atualizarPaineisContextuais();
    }

    /**
     * Mostra/esconde os painéis contextuais com base na mídia selecionada.
     */
    private void atualizarPaineisContextuais() {
        painelAcoesContextuais.getChildren().clear();
        painelDetalhesSerie.getChildren().clear();
        if (midiaSelecionada == null) return;

        if (midiaSelecionada instanceof Serie serieSelecionada) {
            exibirAcoesDaSerie(serieSelecionada);
            if (temporadaSelecionada != null) {
                exibirAcoesTemporada();
            }
        } else {
            exibirAcoesDeMidiaSimples();
        }

        Button removerBtn = UIFactory.criarBotaoEstilizado("Remover " + midiaSelecionada.getClass().getSimpleName(), "#d32f2f");
        removerBtn.setOnAction(e -> removerMidiaSelecionada());
        painelAcoesContextuais.getChildren().add(removerBtn);
    }

    /**
     * Exibe os detalhes e ações para uma série selecionada.
     */
    private void exibirAcoesDaSerie(Serie serie) {
        Button adicionarTemporadaBtn = UIFactory.criarBotaoEstilizado("Adicionar Temporada", "#0097a7");
        adicionarTemporadaBtn.setOnAction(e -> {
            navigateTo.accept(new TemporadaFormView(navigateTo, primaryStage, showMessage, controller, serie));
        });
        painelAcoesContextuais.getChildren().add(adicionarTemporadaBtn);

        Label temporadasTitle = UIFactory.criarLabelFormulario("Temporadas de '" + serie.getTitulo() + "':");
        ListView<Temporada> temporadasListView = new ListView<>();
        temporadasListView.setPrefHeight(100);
        temporadasListView.setItems(FXCollections.observableArrayList(serie.getTemporadasList()));

        temporadasListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Temporada item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String status = item.isConsumido() ? " (Consumida)" : "";
                    setText("T" + item.getNumero() + " - " + item.getNumeroEpisodios() + " episódios. Nota: " + (item.getAvaliacao() > 0 ? item.getAvaliacao()+"/5" : "N/A") + status);
                    setStyle("-fx-text-fill: black;");
                }
            }
        });

        temporadasListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            temporadaSelecionada = newVal;
            atualizarPaineisContextuais();
        });

        painelDetalhesSerie.getChildren().addAll(temporadasTitle, temporadasListView);
    }

    /**
     * Exibe as ações para mídias simples (Livro ou Filme).
     */
    private void exibirAcoesDeMidiaSimples() {
        if (!midiaSelecionada.isConsumido()) {
            Button marcarConsumidoBtn = UIFactory.criarBotaoEstilizado("Marcar como Visualizado", "#0288d1");
            marcarConsumidoBtn.setOnAction(e -> marcarComoConsumido());
            painelAcoesContextuais.getChildren().add(marcarConsumidoBtn);
        } else {
            Button fazerReviewBtn = UIFactory.criarBotaoEstilizado("Fazer/Editar Review", "#558b2f");
            fazerReviewBtn.setOnAction(e -> abrirDialogoDeReview());
            painelAcoesContextuais.getChildren().add(fazerReviewBtn);
        }
    }

    /**
     * Atualiza o painel de ações quando uma temporada é selecionada.
     */
    private void exibirAcoesTemporada() {
        if (!temporadaSelecionada.isConsumido()) {
            Button marcarConsumidoBtn = UIFactory.criarBotaoEstilizado("Marcar T" + temporadaSelecionada.getNumero() + " Consumida", "#0288d1");
            marcarConsumidoBtn.setOnAction(e -> marcarTemporadaComoConsumida());
            painelAcoesContextuais.getChildren().add(marcarConsumidoBtn);
        } else {
            Button fazerReviewBtn = UIFactory.criarBotaoEstilizado("Review da T" + temporadaSelecionada.getNumero(), "#558b2f");
            fazerReviewBtn.setOnAction(e -> abrirDialogoDeReviewTemporada());
            painelAcoesContextuais.getChildren().add(fazerReviewBtn);
        }

        Button removerTemporadaBtn = UIFactory.criarBotaoEstilizado("Remover T" + temporadaSelecionada.getNumero(), "#d32f2f");
        removerTemporadaBtn.setOnAction(e -> removerTemporadaSelecionada());
        painelAcoesContextuais.getChildren().add(removerTemporadaBtn);
    }

    /**
     * Lógica para remover a mídia selecionada com confirmação.
     */
    private void removerMidiaSelecionada() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Remover Mídia");
        alert.setContentText("Tem a certeza que deseja remover '" + midiaSelecionada.getTitulo() + "'? Esta ação não pode ser desfeita.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                controller.removerMidia(midiaSelecionada);
                showMessage.accept("Sucesso", "Mídia removida com sucesso.");
                realizarBusca((GridPane) this.getChildren().get(1));
            } catch (Exception e) {
                showMessage.accept("Erro", "Não foi possível remover a mídia: " + e.getMessage());
            }
        }
    }

    /**
     * Lógica para remover a temporada selecionada com confirmação.
     */
    private void removerTemporadaSelecionada() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Remoção");
        alert.setHeaderText("Remover Temporada");
        alert.setContentText("Tem a certeza que deseja remover a Temporada " + temporadaSelecionada.getNumero() + " de '" + midiaSelecionada.getTitulo() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                controller.removerTemporada(midiaSelecionada.getTitulo(), temporadaSelecionada.getNumero());
                showMessage.accept("Sucesso", "Temporada removida com sucesso.");
                realizarBusca((GridPane) this.getChildren().get(1));
            } catch (Exception e) {
                showMessage.accept("Erro", "Não foi possível remover a temporada: " + e.getMessage());
            }
        }
    }

    /**
     * Lógica para marcar a mídia selecionada como consumida.
     */
    private void marcarComoConsumido() {
        try {
            controller.marcarComoConsumido(midiaSelecionada);
            showMessage.accept("Sucesso", "'" + midiaSelecionada.getTitulo() + "' marcada como consumida!");
            atualizarPaineisContextuais();
            resultadosListView.refresh();
        } catch (Exception e) {
            showMessage.accept("Erro", "Não foi possível marcar a mídia como consumida: " + e.getMessage());
        }
    }

    /**
     * Lógica para marcar a temporada selecionada como consumida.
     */
    private void marcarTemporadaComoConsumida() {
        try {
            controller.marcarTemporadaComoConsumida(midiaSelecionada.getTitulo(), temporadaSelecionada.getNumero());
            showMessage.accept("Sucesso", "Temporada " + temporadaSelecionada.getNumero() + " marcada como consumida!");
            atualizarPaineisContextuais();
            resultadosListView.refresh();
        } catch (Exception e) {
            showMessage.accept("Erro", "Não foi possível marcar a temporada como consumida: " + e.getMessage());
        }
    }

    /**
     * Abre um pop-up para o usuário inserir nota e texto da review para Livros e Filmes.
     */
    private void abrirDialogoDeReview() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Fazer Review");
        dialog.setHeaderText("Avalie '" + midiaSelecionada.getTitulo() + "'");
        dialog.getDialogPane().setStyle("-fx-background-color: #3c3c3c;");
        dialog.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #3c3c3c;");
        dialog.getDialogPane().lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ButtonType okButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ChoiceBox<Integer> notaBox = new ChoiceBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        notaBox.setValue(midiaSelecionada.getAvaliacao() > 0 ? midiaSelecionada.getAvaliacao() : 3);

        TextArea reviewArea = new TextArea();
        reviewArea.setPromptText("Escreva a sua opinião aqui...");
        if (midiaSelecionada.getReview() != null) {
            reviewArea.setText(midiaSelecionada.getReview().getTexto());
        }

        grid.add(UIFactory.criarLabelFormulario("Nota:"), 0, 0);
        grid.add(notaBox, 1, 0);
        grid.add(UIFactory.criarLabelFormulario("Review:"), 0, 1);
        grid.add(reviewArea, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(notaBox.getValue().toString(), reviewArea.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(notaETexto -> {
            try {
                int nota = Integer.parseInt(notaETexto.getKey());
                String texto = notaETexto.getValue();

                controller.avaliarMidia(midiaSelecionada, nota, texto);

                showMessage.accept("Sucesso", "Review para '" + midiaSelecionada.getTitulo() + "' foi salva!");
                resultadosListView.refresh();
            } catch (Exception e) {
                showMessage.accept("Erro", "Não foi possível salvar a review: " + e.getMessage());
            }
        });
    }

    /**
     * Abre um pop-up para o usuário inserir nota e texto da review para uma Temporada.
     */
    private void abrirDialogoDeReviewTemporada() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Review da Temporada");
        dialog.setHeaderText("Avalie a Temporada " + temporadaSelecionada.getNumero() + " de '" + midiaSelecionada.getTitulo() + "'");
        dialog.getDialogPane().setStyle("-fx-background-color: #3c3c3c;");
        dialog.getDialogPane().lookup(".header-panel").setStyle("-fx-background-color: #3c3c3c;");
        dialog.getDialogPane().lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ButtonType okButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        ChoiceBox<Integer> notaBox = new ChoiceBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        notaBox.setValue(temporadaSelecionada.getAvaliacao() > 0 ? temporadaSelecionada.getAvaliacao() : 3);

        TextArea reviewArea = new TextArea();
        reviewArea.setPromptText("Escreva a sua opinião sobre esta temporada...");
        if (temporadaSelecionada.getReview() != null) {
            reviewArea.setText(temporadaSelecionada.getReview().getTexto());
        }

        grid.add(UIFactory.criarLabelFormulario("Nota:"), 0, 0);
        grid.add(notaBox, 1, 0);
        grid.add(UIFactory.criarLabelFormulario("Review:"), 0, 1);
        grid.add(reviewArea, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(notaBox.getValue().toString(), reviewArea.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(notaETexto -> {
            try {
                int nota = Integer.parseInt(notaETexto.getKey());
                String texto = notaETexto.getValue();

                controller.avaliarTemporadaSerie(midiaSelecionada.getTitulo(), temporadaSelecionada.getNumero(), nota, texto);

                showMessage.accept("Sucesso", "Review para a Temporada " + temporadaSelecionada.getNumero() + " foi salva!");
                resultadosListView.refresh();
            } catch (Exception e) {
                showMessage.accept("Erro", "Não foi possível salvar a review da temporada: " + e.getMessage());
            }
        });
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