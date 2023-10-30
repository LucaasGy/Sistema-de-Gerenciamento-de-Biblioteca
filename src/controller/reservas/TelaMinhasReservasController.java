package controller.reservas;

import controller.telaInicial.TelaInicialController;
import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Livro;
import model.Prazos;
import utils.StageController;

/**
 * Controller  responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaMinhasReservas" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaMinhasReservasController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRetirarReserva;

    @FXML
    private Label categoriaLivro;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableColumn<Livro, Double> tabelaISBN;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private TableColumn<Livro, String> tabelaTitulo;

    @FXML
    private Label tituloLivro;

    @FXML
    private Label prazoReserva;
    private TelaInicialController telaInicialController;

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltarMenu(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Observador para coletar objeto escolhio no TableView.
     */

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    /**
     * Ação de clicar no botão de retirar reserva.
     */

    @FXML
    void retirarReserva(){

        //caso não seja escolhida uma reserva para retirar, cria alert informando error
        if(this.tabelaLivros.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao retirar reserva", "Escolha um livro antes de retirar");

        /*retira reserva do leitor logado na tela inicial, exibe mensagem de operação bem
        sucedida e recarrega o TableView com as reservas atualizadas.*/
        else {
            this.telaInicialController.getLeitor().retirarReserva(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
            StageController.sucesso(this.mensagemErro,"RESERVA RETIRADA COM SUCESSO");
            carregaTabela();
        }
    }

    /**
     * Método responsável por carregar o TableView com as reservas do leitor logado na tela inicial.
     *
     * Caso não exista mais reservas do leitor logado ao recarregar o TableView, o botão de retirar reservas
     * é desabilitado e o TableView é limpo.
     */
    public void carregaTabela(){
        this.tabelaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        this.tabelaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(this.telaInicialController.getLeitor().minhasReservas());
        if(!listaLivros.isEmpty())
            this.tabelaLivros.setItems(listaLivros);

        else {
            this.tabelaLivros.getItems().clear();
            this.botaoRetirarReserva.setDisable(true);
        }
    }

    /**
     * Método responsável por setar nos label da tela as informações do Livro escolhido
     * no TableView.
     *
     * @param livro livro escolhido
     */

    public void selecionarLivroTabela(Livro livro){
        this.mensagemErro.setText("");

        if(livro!=null) {
            this.tituloLivro.setText(livro.getTitulo());
            this.isbnLivro.setText(Double.toString(livro.getISBN()));
            this.autorLivro.setText(livro.getAutor());
            this.editoraLivro.setText(livro.getEditora());
            this.anoLivro.setText(Integer.toString(livro.getAno()));
            this.categoriaLivro.setText(livro.getCategoria());

            if (livro.getDisponivel())
                this.disponibilidadeLivro.setText("Sim");

            else
                this.disponibilidadeLivro.setText("Não");

            Prazos prazo = DAO.getPrazos().encontrarPrazoDeUmLivro(livro.getISBN());

            if(prazo!=null && prazo.getIDleitor()==this.telaInicialController.getLeitor().getID())
                this.prazoReserva.setText(prazo.getDataLimite().toString());

            else
                this.prazoReserva.setText("Não tem");
        }

        else{
            this.tituloLivro.setText("");
            this.isbnLivro.setText("");
            this.autorLivro.setText("");
            this.editoraLivro.setText("");
            this.anoLivro.setText("");
            this.categoriaLivro.setText("");
            this.disponibilidadeLivro.setText("");
            this.prazoReserva.setText("");
        }
    }

    public void setTelaInicialController(TelaInicialController telaInicialController) {
        this.telaInicialController = telaInicialController;
    }
}
