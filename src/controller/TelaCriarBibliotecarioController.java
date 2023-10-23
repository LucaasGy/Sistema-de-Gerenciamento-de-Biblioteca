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
import model.Bibliotecario;
import utils.StageController;

import java.io.IOException;

public class TelaCriarBibliotecarioController {

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Button botaoCriar;

    @FXML
    private Button botaoDados;

    @FXML
    private Button botaoMenu;

    @FXML
    private TableColumn<Bibliotecario, Integer> colunaID;

    @FXML
    private TableColumn<Bibliotecario, String> colunaNome;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Bibliotecario> tabelaBibliotecario;

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
    void criarBibliotecario(){
        if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            this.mensagemErro.setText("Preencha os campos");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try{
                Bibliotecario novoBibliotecario = new Bibliotecario(this.digitaNome.getText(),this.digitaSenha.getText());
                Adm.criarBibliotecario(novoBibliotecario);

                this.mensagemErro.setText("");
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);

                this.digitaNome.clear();
                this.digitaSenha.clear();

                carregaTabela();
            }catch (ObjetoNaoCriado e){
                this.mensagemErro.setText(e.getMessage());
            }
        }
    }

    @FXML
    void mostrarDados() throws IOException {
        if(this.tabelaBibliotecario.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum bibliotecario selecionado","Para obter dados de um bibliotecario, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("TelaDadosAdmEBibliotecario.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosAdmEBibliotecarioController controller = loader.getController();

            controller.setBibliotecario(this.tabelaBibliotecario.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void carregaTabela(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Bibliotecario> listaBibliotecario = FXCollections.observableArrayList(DAO.getBibliotecario().encontrarTodos());

        if(!listaBibliotecario.isEmpty()) {
            this.tabelaBibliotecario.setItems(listaBibliotecario);
            this.botaoDados.setDisable(false);
        }

        else
            this.botaoDados.setDisable(true);
    }

}
