package controller.editarExcluirObjeto;

import controller.procurarObjeto.TelaProcurarUsuarioController;
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
import model.Bibliotecario;
import utils.StageController;

import java.io.IOException;

public class TelaEditarExcluirBibliotecarioController {

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
    private TableColumn<Bibliotecario, Integer> colunaID;

    @FXML
    private TableColumn<Bibliotecario, Integer> colunaNome;

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
    private TableView<Bibliotecario> tabelaBibliotecario;

    private int idBibliotecarioAAlterar;

    private String nomeBibliotecarioAAlterar;

    private String qualTabelaCarregar;

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaBibliotecario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarBibliotecarioTabela(newValue));
    }

    @FXML
    void atualizarBibliotecario() {
        Bibliotecario bibliotecarioSelecionado = this.tabelaBibliotecario.getSelectionModel().getSelectedItem();

        if(bibliotecarioSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar bibliotecario", "Escolha um bibliotecario antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);

                Bibliotecario bibliotecarioAtualizado = new Bibliotecario(this.digitaNome.getText(),this.digitaSenha.getText());
                bibliotecarioAtualizado.setID(bibliotecarioSelecionado.getID());
                Adm.atualizarDadosBibliotecario(bibliotecarioAtualizado);

                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idBibliotecarioAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeBibliotecarioAAlterar);

                StageController.sucesso(this.mensagemErro,"BIBLIOTECARIO ATUALIZADO");
            }catch (ObjetoInvalido e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    @FXML
    void removerBibliotecario() {
        Bibliotecario bibliotecarioSelecionado = this.tabelaBibliotecario.getSelectionModel().getSelectedItem();

        if(bibliotecarioSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover bibliotecario", "Escolha um bibliotecario antes de remover");

        else{
            Adm.removerBibliotecario(bibliotecarioSelecionado.getID());

            if(this.qualTabelaCarregar.equals("ID"))
                carregaTabelaID(this.idBibliotecarioAAlterar);

            else if(this.qualTabelaCarregar.equals("Nome"))
                carregaTabelaNome(this.nomeBibliotecarioAAlterar);

            StageController.sucesso(this.mensagemErro,"BIBLIOTECARIO REMOVIDO");
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
        controller.setQualOperacao("Bibliotecario");
        controller.setTitulo("Procurar bibliotecario por :");
    }

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Bibliotecario> listaBibliotecario = FXCollections.observableArrayList(DAO.getBibliotecario().encontrarPorId(id));
        if(listaBibliotecario.get(0)!=null)
            this.tabelaBibliotecario.setItems(listaBibliotecario);

        else {
            this.tabelaBibliotecario.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Bibliotecario> listaBibliotecario = FXCollections.observableArrayList(DAO.getBibliotecario().encontrarPorNome(nome));
        if(!listaBibliotecario.isEmpty())
            this.tabelaBibliotecario.setItems(listaBibliotecario);

        else {
            this.tabelaBibliotecario.getItems().clear();
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

    public void selecionarBibliotecarioTabela(Bibliotecario bibliotecario){
        this.mensagemErro.setText("");

        if(bibliotecario!=null) {
            this.digitaID.setText(Integer.toString(bibliotecario.getID()));
            this.digitaNome.setText(bibliotecario.getNome());
            this.digitaSenha.setText(bibliotecario.getSenha());
            this.digitaTipo.setText(bibliotecario.getTipoUsuario().name());
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

    public void setIdBibliotecarioAAlterar(int idBibliotecarioAAlterar) {
        this.idBibliotecarioAAlterar = idBibliotecarioAAlterar;
    }

    public void setNomeBibliotecarioAAlterar(String nomeBibliotecarioAAlterar) {
        this.nomeBibliotecarioAAlterar = nomeBibliotecarioAAlterar;
    }
}
