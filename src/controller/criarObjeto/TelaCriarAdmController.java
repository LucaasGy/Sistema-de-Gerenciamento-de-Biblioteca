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
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaCriarAdm" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaCriarAdmController {

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
    private TableColumn<Adm, Integer> colunaID;

    @FXML
    private TableColumn<Adm, String> colunaNome;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Adm> tabelaAdm;

    /**
     * Inicializa o TableView com os Administradores registrados no sistema.
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
     * Ação de clicar no botão de criar Administrador.
     */

    @FXML
    void criarAdm(){
        if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try{
                Adm novoAdm = new Adm(this.digitaNome.getText(),this.digitaSenha.getText());
                Adm.criarAdm(novoAdm);

                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);
                this.digitaNome.clear();
                this.digitaSenha.clear();

                carregaTabela();

                StageController.sucesso(this.mensagemErro,"ADMINISTRADOR CRIADO");
            }catch (ObjetoNaoCriado e){
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de mostrar dados.
     *
     * A depender do objeto Administrador escolhido no TableView, carrega a
     * tela "TelaDadosAdmEBibliotecario" em um novo stage, que exibe os dados
     * que um administrador possui.
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

        if(this.tabelaAdm.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum administrador selecionado","Para obter dados de um administrador, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("TelaDadosAdmEBibliotecario.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosAdmEBibliotecarioController controller = loader.getController();

            //deve exibir dados de um administrador
            controller.setAdm(this.tabelaAdm.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    /**
     * Método responsável por carregar o TableView com todos os Administradores do sistema.
     *
     * Caso não exista nenhum administrador registrado no banco de dados, ao carregar o TableView,
     * o botão de dados de um administrador é desabilitado.
     */

    public void carregaTabela(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        ObservableList<Adm> listaAdm = FXCollections.observableArrayList(DAO.getAdm().encontrarTodos());

        if(!listaAdm.isEmpty()) {
            this.tabelaAdm.setItems(listaAdm);
            this.botaoDados.setDisable(false);
        }

        else
            this.botaoDados.setDisable(true);
    }
}