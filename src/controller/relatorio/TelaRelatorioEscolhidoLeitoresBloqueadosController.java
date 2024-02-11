package controller.relatorio;

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

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaRelatorioEscolhidoLeitoresBloqueados" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

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

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.tabelaLeitor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLeitorTabela(newValue));
    }

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Ação de clicar no botão de voltar.
     *
     * Stage atual é redefinido para a tela anterior "TelaRelatórios".
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorios.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    /**
     * Método responsável por carregar o TableView com a lista de leitores
     * bloqueados recebida do controller da tela relatórios.
     *
     * @param leitores lista de leitores
     */

    public void carregarTabela(List<Leitor> leitores){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        ObservableList<Leitor> listaLeitoresBloqueados = FXCollections.observableArrayList(leitores);
        this.tabelaLeitor.setItems(listaLeitoresBloqueados);
    }

    /**
     * Método responsável por setar nos label da tela as informações do leitor escolhido no TableView.
     *
     * @param leitor leitor escolhido no TableView
     */

    public void selecionarLeitorTabela(Leitor leitor){
        this.nomeLeitor.setText(leitor.getNome());
        this.idLeitor.setText(Integer.toString(leitor.getID()));
        this.telefoneLeitor.setText(leitor.getTelefone());
        this.enderecoLeitor.setText(leitor.getEndereco());
    }

    /**
     * Método responsável por setar o título da tela a depender da escolha do usuário na tela relatórios.
     *
     * @param mensagemTotal titulo da tela
     */

    public void setMensagemTotal(String mensagemTotal) {
        this.mensagemTotal.setText(mensagemTotal);
    }
}