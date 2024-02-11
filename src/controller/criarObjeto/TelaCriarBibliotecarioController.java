package controller.criarObjeto;

import controller.dadosObjeto.TelaDadosAdmEBibliotecarioController;
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

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaCriarBibliotecario" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

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

    /**
     * Inicializa o TableView com os Bibliotecarios registrados no sistema.
     */

    @FXML
    void initialize(){
        carregaTabela();
    }

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltarMenu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Ação de clicar no botão de criar Bibliotecario.
     */

    @FXML
    void criarBibliotecario(){
        if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try{
                Bibliotecario novoBibliotecario = new Bibliotecario(this.digitaNome.getText(),this.digitaSenha.getText());
                Adm.criarBibliotecario(novoBibliotecario);

                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);
                this.digitaNome.clear();
                this.digitaSenha.clear();

                carregaTabela();

                StageController.sucesso(this.mensagemErro,"BIBLIOTECARIO CRIADO");
            }catch (ObjetoNaoCriado e){
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de mostrar dados.
     *
     * A depender do objeto Bibliotecario escolhido no TableView, carrega a
     * tela "TelaDadosAdmEBibliotecario" em um novo stage, que exibe os dados
     * que um bibliotecario possui.
     * Como esta tela é usada tanto para exibir dados de um Administrador ou Bibliotecario,
     * é necessário setar a qual dos dois tipos a tela deve exibir os dados.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void mostrarDados() throws IOException {
        this.mensagemErro.setText("");
        this.alertaNome.setVisible(false);
        this.alertaSenha.setVisible(false);

        if(this.tabelaBibliotecario.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum bibliotecario selecionado","Para obter dados de um bibliotecario, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("/view/TelaDadosAdmEBibliotecario.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosAdmEBibliotecarioController controller = loader.getController();

            controller.setBibliotecario(this.tabelaBibliotecario.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    /**
     * Método responsável por carregar o TableView com todos os Bibliotecarios do sistema.
     *
     * Caso não exista nenhum bibliotecario registrado no banco de dados, ao carregar o TableView,
     * o botão de dados de um bibliotecario é desabilitado.
     */

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