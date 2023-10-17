package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.StageController;

import java.io.IOException;

public class TelaInicialController {

    @FXML
    private Menu gerirAdministrador;

    @FXML
    private Menu gerirBibliotecario;

    @FXML
    private Menu gerirLeitor;

    @FXML
    private Menu gerirLivro;

    @FXML
    private MenuItem bloquearDesbloquearLeitor;

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoSair;

    @FXML
    private MenuItem criarAdm;

    @FXML
    private MenuItem criarBibliotecario;

    @FXML
    private MenuItem criarLeitor;

    @FXML
    private MenuItem criarLivro;

    @FXML
    private MenuItem editarExcluirAdm;

    @FXML
    private MenuItem editarExcluirBibliotecario;

    @FXML
    private MenuItem editarExcluirLeitor;

    @FXML
    private MenuItem editarExcluirLivro;

    @FXML
    private ListView<String> listaFuncionalidades;

    @FXML
    private Label mensagemBemVindo;

    @FXML
    private MenuItem tirarMultaLeitor;

    private ObservableList<String> funcionalidades;
    private Adm adm;
    private Bibliotecario bibliotecario;
    private Leitor leitor;
    private Convidado convidado;

    @FXML
    void initialize(){
        carregarLista();
    }

    @FXML
    void sairDaConta(ActionEvent event) throws IOException {
        Stage stage = StageController.getStage(event);
        StageController.criaStage(stage,"TelaLogin.fxml");
    }

    @FXML
    void selecionaFuncionalidade() throws IOException {
        if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Pesquisar livros")) {
            Stage stage = new Stage();
            TelaPesquisaLivroController controller = StageController.criaStage(stage, "TelaPesquisaLivro.fxml").getController();

            if (this.getAdm() != null)
                controller.setAdm(this.getAdm());

            else if (this.getBibliotecario() != null)
                controller.setBibliotecario(this.getBibliotecario());

            else if (this.getLeitor() != null)
                controller.setLeitor(this.getLeitor());

            else if (this.getConvidado() != null)
                controller.setConvidado(this.getConvidado());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void carregarLista(){
        this.funcionalidades = FXCollections.observableArrayList("Pesquisar livros");
        this.listaFuncionalidades.setItems(funcionalidades);
    }

    public void listaFuncionalidadesAdm(){
        this.funcionalidades.addAll("Relatórios");
        this.listaFuncionalidades.setItems(this.funcionalidades);
    }

    public void listaFuncionalidadesLeitor(){
        this.funcionalidades.addAll("Renovar empréstimo", "Minhas reservas");
        this.listaFuncionalidades.setItems(this.funcionalidades);
    }

    public void telaBibliotecario(){
        this.gerirAdministrador.setDisable(true);
        this.gerirBibliotecario.setDisable(true);
        this.gerirLeitor.setDisable(true);
        this.editarExcluirLivro.setDisable(true);
    }

    public void telaLeitorEConvidado(){
        this.gerirAdministrador.setDisable(true);
        this.gerirBibliotecario.setDisable(true);
        this.gerirLeitor.setDisable(true);
        this.gerirLivro.setDisable(true);
    }

    public void setAdm(Adm adm) {
        this.adm = adm;
        this.mensagemBemVindo.setText("Bem-vindo, "+adm.getNome()+" -> ID: "+adm.getID());
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
        this.mensagemBemVindo.setText("Bem-vindo, "+bibliotecario.getNome()+" -> ID: "+bibliotecario.getID());
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
        this.mensagemBemVindo.setText("Bem-vindo, "+leitor.getNome()+" -> ID: "+leitor.getID());
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
        this.mensagemBemVindo.setText("Bem-vindo, Convidado");
    }

    public Adm getAdm() {
        return adm;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Convidado getConvidado() {
        return convidado;
    }
}
