package controller;

import dao.DAO;
import erros.livro.LivroEmprestado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adm;
import model.Livro;
import utils.StageController;

import java.io.IOException;

public class TelaEditarExcluirLivroController {

    @FXML
    private Label alertaAno;

    @FXML
    private Label alertaAutor;

    @FXML
    private Label alertaCategoria;

    @FXML
    private Label alertaDisponibilidade;

    @FXML
    private Label alertaEditora;

    @FXML
    private Label alertaTitulo;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private TextField digitaAno;

    @FXML
    private TextField digitaAutor;

    @FXML
    private TextField digitaCategoria;

    @FXML
    private TextField digitaEditora;

    @FXML
    private TextField digitaISBN;

    @FXML
    private TextField digitaTitulo;

    @FXML
    private RadioButton disponibilidadeNao;

    @FXML
    private RadioButton disponibilidadeSim;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Livro> tabelaLivro;

    private double isbnLivroAAlterar;

    private String tituloLivroAAlterar;

    private String qualTabelaCarregar;

    @FXML
    void initialize(){
        this.digitaISBN.setStyle("-fx-text-fill: gray;");
        this.tabelaLivro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    @FXML
    void atualizarLivro() {
        Livro livroSelecionado = this.tabelaLivro.getSelectionModel().getSelectedItem();

        if(livroSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar livro", "Escolha um livro antes de atualizar");

        else if(this.digitaTitulo.getText().isEmpty() || this.digitaAutor.getText().isEmpty() || this.digitaEditora.getText().isEmpty() || this.digitaAno.getText().isEmpty() || this.digitaCategoria.getText().isEmpty()) {
            this.mensagemErro.setText("PREENCHA OS CAMPOS");
            this.mensagemErro.setStyle("-fx-text-fill: red;");

            this.alertaTitulo.setVisible(this.digitaTitulo.getText().isEmpty());
            this.alertaAutor.setVisible(this.digitaAutor.getText().isEmpty());
            this.alertaEditora.setVisible(this.digitaEditora.getText().isEmpty());
            this.alertaAno.setVisible(this.digitaAno.getText().isEmpty());
            this.alertaCategoria.setVisible(this.digitaCategoria.getText().isEmpty());
        }

        else if (!StageController.tryParseInt(this.digitaAno.getText())) {
            this.mensagemErro.setText("ANO É COMPOSTO APENAS POR NÚMEROS");
            this.alertaAno.setVisible(true);
            this.alertaTitulo.setVisible(false);
            this.alertaAutor.setVisible(false);
            this.alertaEditora.setVisible(false);
            this.alertaCategoria.setVisible(false);
        }

        else{
            try {
                this.alertaAno.setVisible(false);
                this.alertaTitulo.setVisible(false);
                this.alertaAutor.setVisible(false);
                this.alertaEditora.setVisible(false);
                this.alertaCategoria.setVisible(false);

                Livro livroAtualizado = new Livro(this.digitaTitulo.getText(), this.digitaAutor.getText(), this.digitaEditora.getText(), Integer.parseInt(this.digitaAno.getText()), this.digitaCategoria.getText());
                livroAtualizado.setISBN(livroSelecionado.getISBN());

                RadioButton radio = (RadioButton) grupo.getSelectedToggle();
                String escolha = radio.getText();

                if(escolha.equals("Sim"))
                    livroAtualizado.setDisponivel(true);

                else if(escolha.equals("Não"))
                    livroAtualizado.setDisponivel(false);

                Adm.atualizarDadosLivro(livroAtualizado);

                if(this.qualTabelaCarregar.equals("ISBN"))
                    carregaTabelaISBN(this.isbnLivroAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaTitulo(this.tituloLivroAAlterar);

                this.mensagemErro.setText("LIVRO ATUALIZADO");
                this.mensagemErro.setStyle("-fx-text-fill: green;");
            }catch (LivroEmprestado e) {
                this.mensagemErro.setText(e.getMessage());
                this.mensagemErro.setStyle("-fx-text-fill: red;");
                this.disponibilidadeNao.setSelected(true);
            }
        }
    }

    @FXML
    void removerLivro() {
        Livro livroSelecionado = this.tabelaLivro.getSelectionModel().getSelectedItem();

        if(livroSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover livro", "Escolha um livro antes de remover");

        else{
            try {
                Adm.removerUmLivro(livroSelecionado.getISBN());

                if (this.qualTabelaCarregar.equals("ISBN"))
                    carregaTabelaISBN(this.isbnLivroAAlterar);

                else if (this.qualTabelaCarregar.equals("Titulo"))
                    carregaTabelaTitulo(this.tituloLivroAAlterar);

                this.mensagemErro.setText("LIVRO REMOVIDO");
                this.mensagemErro.setStyle("-fx-text-fill: green;");
            }catch (LivroEmprestado e){
                this.mensagemErro.setText(e.getMessage());
                this.mensagemErro.setStyle("-fx-text-fill: red;");
                this.disponibilidadeNao.setSelected(true);
            }
        }
    }

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarLivro.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    public void carregaTabelaISBN(double isbn){
        carregaColunas();
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarPorISBN(isbn));
        if(listaLivro.get(0)!=null)
            this.tabelaLivro.setItems(listaLivro);

        else {
            this.tabelaLivro.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaTitulo.setEditable(false);
            this.digitaAutor.setEditable(false);
            this.digitaEditora.setEditable(false);
            this.digitaAno.setEditable(false);
            this.digitaCategoria.setEditable(false);

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
            this.disponibilidadeSim.setDisable(true);
            this.disponibilidadeNao.setDisable(true);
        }
    }

    public void carregaTabelaTitulo(String titulo){
        carregaColunas();
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarPorTitulo(titulo));
        if(!listaLivro.isEmpty())
            this.tabelaLivro.setItems(listaLivro);

        else {
            this.tabelaLivro.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaTitulo.setEditable(false);
            this.digitaAutor.setEditable(false);
            this.digitaEditora.setEditable(false);
            this.digitaAno.setEditable(false);
            this.digitaCategoria.setEditable(false);

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
            this.disponibilidadeSim.setDisable(true);
            this.disponibilidadeNao.setDisable(true);
        }
    }

    public void carregaColunas(){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    }

    public void selecionarLivroTabela(Livro livro){
        this.mensagemErro.setText("");

        if(livro!=null) {
            this.digitaISBN.setText(Double.toString(livro.getISBN()));
            this.digitaTitulo.setText(livro.getTitulo());
            this.digitaAutor.setText(livro.getAutor());
            this.digitaEditora.setText(livro.getEditora());
            this.digitaAno.setText(Integer.toString(livro.getAno()));
            this.digitaCategoria.setText(livro.getCategoria());

            if(livro.getDisponivel())
                this.disponibilidadeSim.setSelected(true);

            else
                this.disponibilidadeNao.setSelected(true);
        }

        else{
            this.digitaISBN.setText("");
            this.digitaTitulo.setText("");
            this.digitaAutor.setText("");
            this.digitaEditora.setText("");
            this.digitaAno.setText("");
            this.digitaCategoria.setText("");

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
        }
    }

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    public void setIsbnLivroAAlterar(double isbnLivroAAlterar) {
        this.isbnLivroAAlterar = isbnLivroAAlterar;
    }

    public void setTituloLivroAAlterar(String tituloLivroAAlterar) {
        this.tituloLivroAAlterar = tituloLivroAAlterar;
    }
}

