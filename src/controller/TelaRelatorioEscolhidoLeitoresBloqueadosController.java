package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Leitor;
import utils.StageController;

import java.io.IOException;
import java.util.List;

public class TelaRelatorioEscolhidoLeitoresBloqueadosController {

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Leitor, Integer> colunaID;

    @FXML
    private TableColumn<Leitor, String> colunaNome;

    @FXML
    private Label enderecoLeitor;

    @FXML
    private Label idLeitor;

    @FXML
    private Label nomeLeitor;

    @FXML
    private TableView<Leitor> tabelaLeitor;

    @FXML
    private Label telefoneLeitor;

    @FXML
    private Label mensagemTotal;

    public void setMensagemTotal(String mensagemTotal) {
        this.mensagemTotal.setText(mensagemTotal);
    }

    @FXML
    void initialize(){
        this.tabelaLeitor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLeitorTabela(newValue));
    }

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    public void carregarTabela(List<Leitor> leitores){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        ObservableList<Leitor> listaLeitoresBloqueados = FXCollections.observableArrayList(leitores);
        this.tabelaLeitor.setItems(listaLeitoresBloqueados);
    }

    public void selecionarLeitorTabela(Leitor leitor){
        this.nomeLeitor.setText(leitor.getNome());
        this.idLeitor.setText(Integer.toString(leitor.getID()));
        this.telefoneLeitor.setText(leitor.getTelefone());
        this.enderecoLeitor.setText(leitor.getEndereco());
    }
}
