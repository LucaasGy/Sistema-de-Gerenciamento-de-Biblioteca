package controller;

import dao.DAO;
import erros.leitor.*;
import erros.livro.LivroReservado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
        this.adm = null;
        this.bibliotecario = null;
        this.leitor = null;
        this.convidado = null;
        FXMLLoader loader = StageController.retornaLoader("TelaLogin.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    @FXML
    void selecionaFuncionalidade() throws IOException {
        if(this.listaFuncionalidades.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.WARNING,"ERROR","Erro ao confirmar uma funcionalidade","Escolha uma funcionalidade antes de confirmar");

        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Pesquisar livros")) {
            FXMLLoader loader = StageController.retornaLoader("TelaPesquisaLivro.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaPesquisaLivroController controller = loader.getController();

            controller.setTelaInicialController(this);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }

        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Renovar empréstimo")){
            try{
                this.leitor.renovarEmprestimo();
                StageController.criaAlert(Alert.AlertType.INFORMATION,"Operação bem-sucedida","Renovação de empréstimo realizado com sucesso","Seu empréstimo foi renovado");
            }catch (LeitorNaoPossuiEmprestimo | LeitorBloqueado | LivroReservado | LeitorLimiteDeRenovacao | LeitorTemEmprestimoEmAtraso e){
                StageController.criaAlert(Alert.AlertType.WARNING,"ERROR","Erro ao confirmar uma funcionalidade",e.getMessage());
            }
        }

        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Minhas reservas")){
            if(DAO.getReserva().encontrarReservasLeitor(this.leitor.getID()).isEmpty()) {
                LeitorNaoPossuiReservas e = new LeitorNaoPossuiReservas();
                StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar uma funcionalidade", e.getMessage());
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaMinhasReservas.fxml");
                Stage stage = new Stage();
                StageController.criaStage(stage, loader);
                TelaMinhasReservasController controller = loader.getController();

                controller.setTelaInicialController(this);
                controller.carregaTabela();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }

        }

        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Relatórios")){
            FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
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
