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
import model.Adm;
import model.Leitor;
import utils.StageController;

import java.io.IOException;

public class TelaCriarLeitorController {

    @FXML
    private Label alertaEndereco;

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Label alertaTelefone;

    @FXML
    private Button botaoCriar;

    @FXML
    private Button botaoDados;

    @FXML
    private Button botaoMenu;

    @FXML
    private TableColumn<Leitor, Integer> colunaID;

    @FXML
    private TableColumn<Leitor, String> colunaNome;

    @FXML
    private TextField digitaEndereco;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private TextField digitaTelefone;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Leitor> tabelaLeitor;

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
        if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty() || this.digitaEndereco.getText().isEmpty() || this.digitaTelefone.getText().isEmpty()) {
            this.mensagemErro.setText("PREENCHA OS CAMPOS");
            this.mensagemErro.setStyle("-fx-text-fill: red;");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
            this.alertaEndereco.setVisible(this.digitaEndereco.getText().isEmpty());
            this.alertaTelefone.setVisible(this.digitaTelefone.getText().isEmpty());
        }

        else if (!StageController.tryParseLong(this.digitaTelefone.getText())) {
            this.mensagemErro.setText("TELEFONE É COMPOSTO APENAS POR NÚMEROS");
            this.mensagemErro.setStyle("-fx-text-fill: red;");

            this.alertaTelefone.setVisible(true);
            this.alertaNome.setVisible(false);
            this.alertaSenha.setVisible(false);
            this.alertaEndereco.setVisible(false);
        }

        else{
            try{
                Leitor novoLeitor = new Leitor(this.digitaNome.getText(),this.digitaEndereco.getText(),this.digitaTelefone.getText(),this.digitaSenha.getText());
                Adm.criarLeitor(novoLeitor);

                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);
                this.alertaEndereco.setVisible(false);
                this.alertaTelefone.setVisible(false);

                this.digitaNome.clear();
                this.digitaSenha.clear();
                this.digitaEndereco.clear();
                this.digitaTelefone.clear();

                carregaTabela();

                this.mensagemErro.setText("LEITOR CRIADO");
                this.mensagemErro.setStyle("-fx-text-fill: green;");
            }catch (ObjetoNaoCriado e){
                this.mensagemErro.setText(e.getMessage());
            }
        }
    }

    @FXML
    void mostrarDados() throws IOException {
        this.mensagemErro.setText("");
        this.alertaNome.setVisible(false);
        this.alertaSenha.setVisible(false);
        this.alertaEndereco.setVisible(false);
        this.alertaTelefone.setVisible(false);

        if(this.tabelaLeitor.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum leitor selecionado","Para obter dados de um leitor, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("TelaDadosLeitor.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosLeitorController controller = loader.getController();

            controller.setLeitor(this.tabelaLeitor.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void carregaTabela(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Leitor> listaLeitor = FXCollections.observableArrayList(DAO.getLeitor().encontrarTodos());

        if(!listaLeitor.isEmpty()) {
            this.tabelaLeitor.setItems(listaLeitor);
            this.botaoDados.setDisable(false);
        }

        else
            this.botaoDados.setDisable(true);
    }
}
