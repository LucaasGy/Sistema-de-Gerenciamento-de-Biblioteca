package controller.editarExcluirObjeto;

import controller.procurarObjeto.TelaProcurarUsuarioController;
import dao.DAO;
import erros.leitor.LeitorTemEmprestimo;
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
import model.Leitor;
import utils.StageController;

import java.io.IOException;

public class TelaEditarExcluirLeitorController {

    @FXML
    private Label alertaEndereco;

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Label alertaTelefone;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Leitor, Integer> colunaID;

    @FXML
    private TableColumn<Leitor, String> colunaNome;

    @FXML
    private TextField digitaEndereco;

    @FXML
    private TextField digitaID;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private TextField digitaTelefone;

    @FXML
    private TextField digitaTipo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Leitor> tabelaLeitor;

    private int idLeitorAAlterar;

    private String nomeLeitorAAlterar;

    private String qualTabelaCarregar;

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaLeitor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLeitorTabela(newValue));
    }

    @FXML
    void atualizarLeitor() {
        Leitor leitorSelecionado = this.tabelaLeitor.getSelectionModel().getSelectedItem();

        if(leitorSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar leitor", "Escolha um leitor antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty() || this.digitaEndereco.getText().isEmpty() || this.digitaTelefone.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
            this.alertaEndereco.setVisible(this.digitaEndereco.getText().isEmpty());
            this.alertaTelefone.setVisible(this.digitaTelefone.getText().isEmpty());
        }

        else if (!StageController.tryParseLong(this.digitaTelefone.getText())) {
            this.mensagemErro.setText("TELEFONE É COMPOSTO APENAS POR NÚMEROS");
            this.alertaTelefone.setVisible(true);
            this.alertaNome.setVisible(false);
            this.alertaSenha.setVisible(false);
            this.alertaEndereco.setVisible(false);
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);
                this.alertaEndereco.setVisible(false);
                this.alertaTelefone.setVisible(false);

                Leitor leitorAtualizado = new Leitor(this.digitaNome.getText(),this.digitaEndereco.getText(),this.digitaTelefone.getText(),this.digitaSenha.getText());
                leitorAtualizado.setID(leitorSelecionado.getID());
                Adm.atualizarDadosLeitor(leitorAtualizado);

                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idLeitorAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeLeitorAAlterar);

                StageController.sucesso(this.mensagemErro,"LEITOR ATUALIZADO");
            }catch (ObjetoInvalido e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    @FXML
    void removerLeitor() {
        Leitor leitorSelecionado = this.tabelaLeitor.getSelectionModel().getSelectedItem();

        if(leitorSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover leitor", "Escolha um leitor antes de remover");

        else{
            try {
                Adm.removerLeitor(leitorSelecionado.getID());

                if (this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idLeitorAAlterar);

                else if (this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeLeitorAAlterar);

                StageController.sucesso(this.mensagemErro,"LEITOR REMOVIDO");
            }catch (LeitorTemEmprestimo e){
                StageController.error(this.mensagemErro,e.getMessage());
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
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
        TelaProcurarUsuarioController controller = loader.getController();
        controller.setQualOperacao("Leitor");
        controller.setTitulo("Procurar leitor por :");
    }

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Leitor> listaLeitor = FXCollections.observableArrayList(DAO.getLeitor().encontrarPorId(id));
        if(listaLeitor.get(0)!=null)
            this.tabelaLeitor.setItems(listaLeitor);

        else {
            this.tabelaLeitor.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
            this.digitaEndereco.setEditable(false);
            this.digitaTelefone.setEditable(false);
        }
    }

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Leitor> listaLeitor = FXCollections.observableArrayList(DAO.getLeitor().encontrarPorNome(nome));
        if(!listaLeitor.isEmpty())
            this.tabelaLeitor.setItems(listaLeitor);

        else {
            this.tabelaLeitor.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
            this.digitaEndereco.setEditable(false);
            this.digitaTelefone.setEditable(false);
        }
    }

    public void carregaColunas(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    public void selecionarLeitorTabela(Leitor leitor){
        this.mensagemErro.setText("");

        if(leitor!=null) {
            this.digitaID.setText(Integer.toString(leitor.getID()));
            this.digitaNome.setText(leitor.getNome());
            this.digitaSenha.setText(leitor.getSenha());
            this.digitaEndereco.setText(leitor.getEndereco());
            this.digitaTelefone.setText(leitor.getTelefone());
            this.digitaTipo.setText(leitor.getTipoUsuario().name());
        }

        else{
            this.digitaID.setText("");
            this.digitaNome.setText("");
            this.digitaSenha.setText("");
            this.digitaTipo.setText("");
            this.digitaEndereco.setText("");
            this.digitaTelefone.setText("");
        }
    }

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    public void setIdLeitorAAlterar(int idLeitorAAlterar) {
        this.idLeitorAAlterar = idLeitorAAlterar;
    }

    public void setNomeLeitorAAlterar(String nomeLeitorAAlterar) {
        this.nomeLeitorAAlterar = nomeLeitorAAlterar;
    }
}
