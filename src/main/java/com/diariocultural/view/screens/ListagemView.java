// === ListagemView.java === "View/Screens"

package com.diariocultural.view.screens;

import com.diariocultural.controller.DiarioCultural;
import com.diariocultural.model.*;
import com.diariocultural.view.forms.TemporadaFormView;
import com.diariocultural.view.utils.MidiaViewHelper;
import com.diariocultural.view.utils.UIFactory;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Representa a tela de listagem de mídias, com funcionalidades de filtro, ordenação e ações contextuais.
 */
public class ListagemView extends VBox {
    private final Consumer<VBox> navigateTo;
    private final Stage primaryStage;
    private final BiConsumer<String, String> showMessage;
    private final DiarioCultural controller;

    private ListView<Midia> livrosListView;
    private ListView<Midia> filmesListView;
    private ListView<Midia> seriesListView;
    private HBox painelAcoesContextuais;
    private VBox painelDetalhesSerie;
    private Midia midiaSelecionada;
    private Temporada temporadaSelecionada;

    /**
     * Construtor para a tela de listagem de mídias.
     */
    public ListagemView(Consumer<VBox> navigateTo, Stage primaryStage, BiConsumer<String, String> showMessage, DiarioCultural controller) {
        super();
        UIFactory.configurarTelaFormulario(this);

        this.navigateTo = navigateTo;
        this.primaryStage = primaryStage;
        this.showMessage = showMessage;
        this.controller = controller;

        Label title = UIFactory.criarTituloTela("Catálogo de Mídias");
        GridPane filtroPane = criarPainelDeFiltros();
        HBox listasBox = criarPainelDeListas();

        painelAcoesContextuais = new HBox(10);
        painelAcoesContextuais.setAlignment(Pos.CENTER);
        painelAcoesContextuais.setMinHeight(50);

        painelDetalhesSerie = new VBox(10);
        painelDetalhesSerie.setAlignment(Pos.CENTER);

        Button aplicarFiltrosBtn = UIFactory.criarBotaoEstilizado("Aplicar Filtros e Listar", "#6a5acd");
        aplicarFiltrosBtn.setOnAction(e -> carregarTodasAsMidias(filtroPane));

        this.getChildren().addAll(title, filtroPane, aplicarFiltrosBtn, listasBox, painelAcoesContextuais, painelDetalhesSerie);

        carregarTodasAsMidias(filtroPane);
    }

    /**
     * Cria e configura o painel com os controles de filtro e ordenação.
     */
    private GridPane criarPainelDeFiltros() {
        GridPane filtroPane = UIFactory.criarGridFormulario();

        TextField generoField = UIFactory.criarTextFieldFormulario();
        generoField.setPromptText("Ex: Ficção Científica");

        TextField anoField = UIFactory.criarTextFieldFormulario();
        anoField.setPromptText("Ex: 2023");

        CheckBox ordenarCheck = new CheckBox("Ordenar por Avaliação");
        ordenarCheck.setStyle("-fx-text-fill: white;");

        ChoiceBox<String> ordemBox = new ChoiceBox<>(FXCollections.observableArrayList("Melhores Primeiro", "Piores Primeiro"));
        ordemBox.getSelectionModel().selectFirst();
        ordemBox.setDisable(true);

        ordenarCheck.selectedProperty().addListener((obs, oldVal, newVal) -> ordemBox.setDisable(!newVal));

        filtroPane.add(UIFactory.criarLabelFormulario("Filtrar por Gênero:"), 0, 0);
        filtroPane.add(generoField, 1, 0);
        filtroPane.add(UIFactory.criarLabelFormulario("Filtrar por Ano:"), 0, 1);
        filtroPane.add(anoField, 1, 1);
        filtroPane.add(ordenarCheck, 0, 2);
        filtroPane.add(ordemBox, 1, 2);

        return filtroPane;
    }

    /**
     * Cria e configura o HBox com as três ListViews.
     */
    private HBox criarPainelDeListas() {
        livrosListView = criarListView();
        filmesListView = criarListView();
        seriesListView = criarListView();

        VBox livrosBox = new VBox(5, UIFactory.criarLabelFormulario("LIVROS"), livrosListView);
        VBox filmesBox = new VBox(5, UIFactory.criarLabelFormulario("FILMES"), filmesListView);
        VBox seriesBox = new VBox(5, UIFactory.criarLabelFormulario("SÉRIES"), seriesListView);

        livrosBox.setAlignment(Pos.CENTER);
        filmesBox.setAlignment(Pos.CENTER);
        seriesBox.setAlignment(Pos.CENTER);

        HBox listasBox = new HBox(15, livrosBox, filmesBox, seriesBox);
        listasBox.setAlignment(Pos.CENTER);
        return listasBox;
    }

    /**
     * Método auxiliar para criar e configurar uma ListView.
     */
    private ListView<Midia> criarListView() {
        ListView<Midia> listView = new ListView<>();
        listView.setPrefHeight(250);

        listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Midia item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : MidiaViewHelper.formatarParaLista(item));
                setStyle("-fx-text-fill: black;");
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                if (listView != livrosListView) livrosListView.getSelectionModel().clearSelection();
                if (listView != filmesListView) filmesListView.getSelectionModel().clearSelection();
                if (listView != seriesListView) seriesListView.getSelectionModel().clearSelection();

                midiaSelecionada = newSelection;
                temporadaSelecionada = null;
                atualizarPaineisContextuais();
            }
        });

        return listView;
    }

    /**
     * Carrega todas as mídias nas suas respectivas listas com base nos filtros.
     */
    private void carregarTodasAsMidias(GridPane filtroPane) {
        try {
            TextField generoField = (TextField) filtroPane.getChildren().get(1);
            TextField anoField = (TextField) filtroPane.getChildren().get(3);
            CheckBox ordenarCheck = (CheckBox) filtroPane.getChildren().get(4);
            ChoiceBox<String> ordemBox = (ChoiceBox<String>) filtroPane.getChildren().get(5);

            String genero = generoField.getText().trim().isEmpty() ? null : generoField.getText().trim();
            Integer ano = anoField.getText().trim().isEmpty() ? null : Integer.parseInt(anoField.getText().trim());
            boolean ordenar = ordenarCheck.isSelected();
            boolean ascendente = ordemBox.getValue().equals("Piores Primeiro");

            List<Livro> livros = controller.listarLivros(genero, ano, ordenar, ascendente);
            livrosListView.setItems(FXCollections.observableArrayList(livros));

            List<Filme> filmes = controller.listarFilmes(genero, ano, ordenar, ascendente);
            filmesListView.setItems(FXCollections.observableArrayList(filmes));

            List<Serie> series = controller.listarSeries(genero, ano, ordenar, ascendente);
            seriesListView.setItems(FXCollections.observableArrayList(series));

        } catch (Exception e) {
            showMessage.accept("Erro", "Ocorreu um erro ao carregar as mídias: " + e.getMessage());
        }
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
                carregarTodasAsMidias((GridPane) this.getChildren().get(1));
                atualizarPaineisContextuais();
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
                carregarTodasAsMidias((GridPane) this.getChildren().get(1));
                atualizarPaineisContextuais();
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
            carregarTodasAsMidias((GridPane) this.getChildren().get(1));
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
            carregarTodasAsMidias((GridPane) this.getChildren().get(1));
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
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #3c3c3c;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #3c3c3c;");
        dialogPane.lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ButtonType okButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.criarGridFormulario();

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
                carregarTodasAsMidias((GridPane) this.getChildren().get(1));
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
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #3c3c3c;");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: #3c3c3c;");
        dialogPane.lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ButtonType okButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = UIFactory.criarGridFormulario();

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
                carregarTodasAsMidias((GridPane) this.getChildren().get(1));
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