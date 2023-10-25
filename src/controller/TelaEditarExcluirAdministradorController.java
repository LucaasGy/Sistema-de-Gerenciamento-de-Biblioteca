package controller;

import dao.DAO;
import erros.objetos.ObjetoInvalido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adm;
import utils.StageController;

import java.io.IOException;

public class TelaEditarExcluirAdministradorController {

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Adm, Integer> colunaID;

    @FXML
    private TableColumn<Adm, String> colunaNome;

    @FXML
    private TextField digitaID;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private TextField digitaTipo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Adm> tabelaAdm;

    private Adm admLogado;

    private int idAdmAAlterar;

    private String nomeAdmAAlterar;

    private String qualTabelaCarregar;

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaAdm.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarAdmTabela(newValue));
    }

    @FXML
    void atualizarAdm() {
        Adm admSelecionado = this.tabelaAdm.getSelectionModel().getSelectedItem();

        if(admSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar administrador", "Escolha um administrador antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            this.mensagemErro.setText("PREENCHA OS CAMPOS");
            this.mensagemErro.setStyle("-fx-text-fill: red;");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);

                Adm admAtualizado = new Adm(this.digitaNome.getText(),this.digitaSenha.getText());
                admAtualizado.setID(admSelecionado.getID());
                Adm.atualizarDadosAdministrador(admAtualizado);

                if(admAtualizado.getID()==this.admLogado.getID()) {
                    StageController.criaAlert(Alert.AlertType.INFORMATION, "Observação", "Administrador logado atualizado", "Para salvar as alterações, saia e entre na conta novamente!");
                    this.admLogado = admAtualizado;
                }

                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idAdmAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeAdmAAlterar);

                this.mensagemErro.setText("ADMINISTRADOR ATUALIZADO");
                this.mensagemErro.setStyle("-fx-text-fill: green;");
            }catch (ObjetoInvalido e) {
                this.mensagemErro.setText(e.getMessage());
                this.mensagemErro.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    void removerAdm() {
        Adm admSelecionado = this.tabelaAdm.getSelectionModel().getSelectedItem();

        if(admSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover administrador", "Escolha um administrador antes de remover");

        else if(admSelecionado.getID()==this.admLogado.getID())
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover administrador", "Não é possível remover administrador logado");

        else{
            Adm.removerAdm(admSelecionado.getID());

            if(this.qualTabelaCarregar.equals("ID"))
                carregaTabelaID(this.idAdmAAlterar);

            else if(this.qualTabelaCarregar.equals("Nome"))
                carregaTabelaNome(this.nomeAdmAAlterar);

            this.mensagemErro.setText("ADMINISTRADOR REMOVIDO");
            this.mensagemErro.setStyle("-fx-text-fill: green;");
        }
    }

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
        TelaProcurarUsuarioController controller = loader.getController();
        controller.setQualOperacao("Administrador");
        controller.setTitulo("Procurar administrador por :");
        controller.setAdm(this.admLogado);
    }

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Adm> listaAdm = FXCollections.observableArrayList(DAO.getAdm().encontrarPorId(id));
        if(listaAdm.get(0)!=null)
            this.tabelaAdm.setItems(listaAdm);

        else {
            this.tabelaAdm.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Adm> listaAdm = FXCollections.observableArrayList(DAO.getAdm().encontrarPorNome(nome));
        if(!listaAdm.isEmpty())
            this.tabelaAdm.setItems(listaAdm);

        else {
            this.tabelaAdm.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    public void carregaColunas(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    public void selecionarAdmTabela(Adm adm){
        this.mensagemErro.setText("");

        if(adm!=null) {
            this.digitaID.setText(Integer.toString(adm.getID()));
            this.digitaNome.setText(adm.getNome());
            this.digitaSenha.setText(adm.getSenha());
            this.digitaTipo.setText(adm.getTipoUsuario().name());
        }

        else{
            this.digitaID.setText("");
            this.digitaNome.setText("");
            this.digitaSenha.setText("");
            this.digitaTipo.setText("");
        }
    }

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    public void setAdmLogado(Adm admLogado) {
        this.admLogado = admLogado;
    }

    public void setIdAdmAAlterar(int idAdmAAlterar) {
        this.idAdmAAlterar = idAdmAAlterar;
    }

    public void setNomeAdmAAlterar(String nomeAdmAAlterar) {
        this.nomeAdmAAlterar = nomeAdmAAlterar;
    }
}
