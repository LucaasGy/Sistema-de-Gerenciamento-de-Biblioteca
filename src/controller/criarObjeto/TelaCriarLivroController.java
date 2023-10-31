package controller.criarObjeto;

import controller.dadosObjeto.TelaDadosLivroController;
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
import model.Livro;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaCriarLivro" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaCriarLivroController {

    @FXML
    private Label alertaAno;

    @FXML
    private Label alertaAutor;

    @FXML
    private Label alertaCategoria;

    @FXML
    private Label alertaEditora;

    @FXML
    private Label alertaTitulo;

    @FXML
    private Button botaoCriar;

    @FXML
    private Button botaoDados;

    @FXML
    private Button botaoMenu;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private TextField digitaAno;

    @FXML
    private TextField digitaAutor;

    @FXML
    private TextField digitaCategoria;

    @FXML
    private TextField digitaEditora;

    @FXML
    private TextField digitaTitulo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Livro> tabelaLivro;

    /**
     * Inicializa o TableView com os Livros registrados no sistema.
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
     * Ação de clicar no botão de criar Livro.
     */

    @FXML
    void criarLivro(){
        if(this.digitaTitulo.getText().isEmpty() || this.digitaAutor.getText().isEmpty() || this.digitaEditora.getText().isEmpty() || this.digitaAno.getText().isEmpty() || this.digitaCategoria.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaTitulo.setVisible(this.digitaTitulo.getText().isEmpty());
            this.alertaAutor.setVisible(this.digitaAutor.getText().isEmpty());
            this.alertaEditora.setVisible(this.digitaEditora.getText().isEmpty());
            this.alertaAno.setVisible(this.digitaAno.getText().isEmpty());
            this.alertaCategoria.setVisible(this.digitaCategoria.getText().isEmpty());
        }

        else if (!StageController.tryParseInt(this.digitaAno.getText())) {
            StageController.error(this.mensagemErro,"ANO É COMPOSTO APENAS POR NÚMEROS");

            this.alertaAno.setVisible(true);
            this.alertaTitulo.setVisible(false);
            this.alertaAutor.setVisible(false);
            this.alertaEditora.setVisible(false);
            this.alertaCategoria.setVisible(false);
        }

        else{
            try{
                Livro novoLivro = new Livro(this.digitaTitulo.getText(),this.digitaAutor.getText(),this.digitaEditora.getText(),Integer.parseInt(this.digitaAno.getText()),this.digitaCategoria.getText());
                Sistema.registrarLivro(novoLivro);

                this.alertaAno.setVisible(false);
                this.alertaTitulo.setVisible(false);
                this.alertaAutor.setVisible(false);
                this.alertaEditora.setVisible(false);
                this.alertaCategoria.setVisible(false);

                this.digitaTitulo.clear();
                this.digitaAutor.clear();
                this.digitaEditora.clear();
                this.digitaAno.clear();
                this.digitaCategoria.clear();

                carregaTabela();

                StageController.sucesso(this.mensagemErro,"LIVRO CRIADO");
            }catch (ObjetoNaoCriado e){
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de mostrar dados.
     *
     * A depender do objeto Livro escolhido no TableView, carrega a
     * tela "TelaDadosLivro" em um novo stage, que exibe os dados
     * que um livro possui.
     * É necessário setar a qual o objeto livro deve ser exibido os dados.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void mostrarDados() throws IOException {
        this.mensagemErro.setText("");
        this.alertaAno.setVisible(false);
        this.alertaTitulo.setVisible(false);
        this.alertaAutor.setVisible(false);
        this.alertaEditora.setVisible(false);
        this.alertaCategoria.setVisible(false);

        if(this.tabelaLivro.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.ERROR,"ERROR","Nenhum livro selecionado","Para obter dados de um livro, selecione um primeiramente");

        else{
            FXMLLoader loader = StageController.retornaLoader("TelaDadosLivro.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaDadosLivroController controller = loader.getController();

            controller.setLivro(this.tabelaLivro.getSelectionModel().getSelectedItem());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    /**
     * Método responsável por carregar o TableView com todos os Livros do sistema.
     *
     * Caso não exista nenhum livro registrado no banco de dados, ao carregar o TableView,
     * o botão de dados de um livro é desabilitado.
     */

    public void carregaTabela(){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarTodos());

        if(!listaLivro.isEmpty()) {
            this.tabelaLivro.setItems(listaLivro);
            this.botaoDados.setDisable(false);
        }

        else
            this.botaoDados.setDisable(true);
    }
}