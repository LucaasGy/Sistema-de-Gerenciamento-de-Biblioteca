package controller;

import dao.DAO;
import erros.objetos.ObjetoNaoCriado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Livro;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

public class TelaCriarLivroController {

    @FXML
    private Label alertaAno;

    @FXML
    private Label alertaAutor;

    @FXML
    private Label alertaCategoria;

    @FXML
    private Label alertaEditora;

    @FXML
    private Label alertaTitulo;

    @FXML
    private Button botaoCriar;

    @FXML
    private Button botaoDados;

    @FXML
    private Button botaoMenu;

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
    private TextField digitaTitulo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Livro> tabelaLivro;

    @FXML
    void initialize(){
        carregaTabela();
    }

    @FXML
    void voltarMenu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void criarLeitor(){
        if(this.digitaTitulo.getText().isEmpty() || this.digitaAutor.getText().isEmpty() || this.digitaEditora.getText().isEmpty() || this.digitaAno.getText().isEmpty() || this.digitaCategoria.getText().isEmpty()) {
            this.mensagemErro.setText("PREENCHA OS CAMPOS");

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
            try{
                Livro novoLivro = new Livro(this.digitaTitulo.getText(),this.digitaAutor.getText(),this.digitaEditora.getText(),Integer.parseInt(this.digitaAno.getText()),this.digitaCategoria.getText());
                Sistema.registrarLivro(novoLivro);

                this.mensagemErro.setText("");
                this.alertaAno.setVisible(false);
                this.alertaTitulo.setVisible(false);
                this.alertaAutor.setVisible(false);
                this.alertaEditora.setVisible(false);
                this.alertaCategoria.setVisible(false);

                this.digitaTitulo.clear();
                this.digitaAutor.clear();
                this.digitaEditora.clear();
                this.digitaAno.clear();
                this.digitaCategoria.clear();

                carregaTabela();
            }catch (ObjetoNaoCriado e){
                this.mensagemErro.setText(e.getMessage());
            }
        }
    }

    @FXML
    void mostrarDados() throws IOException {
        if(this.tabelaLivro.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum livro selecionado","Para obter dados de um livro, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("TelaDadosLivro.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosLivroController controller = loader.getController();

            controller.setLivro(this.tabelaLivro.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void carregaTabela(){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarTodos());

        if(!listaLivro.isEmpty()) {
            this.tabelaLivro.setItems(listaLivro);
            this.botaoDados.setDisable(false);
        }

        else
            this.botaoDados.setDisable(true);
    }
}
