package controller.relatorio;

import dao.DAO;
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
import model.Livro;
import model.Reserva;
import utils.StageController;

import java.io.IOException;
import java.util.List;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaRelatorioEscolhidoReservados" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaRelatorioEscolhidoReservadosController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label autorLivro;

    @FXML
    private Label idLeitor;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label categoriaLivro;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunasTitulo;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label enderecoLeitor;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label nomeLeitor;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private Label telefoneLeitor;

    @FXML
    private Label tituloLivro;

    @FXML
    private Label mensagemTotal;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
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
        FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    /**
     * Método responsável por carregar o TableView com a lista de livros
     * reservados recebida do controller da tela relatórios.
     *
     * @param livros lista de livros reservados
     */

    public void carregarTabela(List<Livro> livros){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunasTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livros);
        this.tabelaLivros.setItems(listaLivros);
    }

    /**
     * Método responsável por setar nos label da tela as informações do livro escolhido no TableView.
     *
     * Seta também os dados do primeiro leitor da fila de reservas desse livro.
     *
     * @param livro livro escolhido no TableView
     */

    public void selecionarLivroTabela(Livro livro){
        this.tituloLivro.setText(livro.getTitulo());
        this.isbnLivro.setText(Double.toString(livro.getISBN()));
        this.autorLivro.setText(livro.getAutor());
        this.editoraLivro.setText(livro.getEditora());
        this.anoLivro.setText(Integer.toString(livro.getAno()));
        this.categoriaLivro.setText(livro.getCategoria());

        if(livro.getDisponivel())
            this.disponibilidadeLivro.setText("Sim");

        else
            this.disponibilidadeLivro.setText("Não");

        //encontra top 1 leitor da fila de reservas do livro escolhido
        Reserva reserva = DAO.getReserva().top1Reserva(livro.getISBN());
        Leitor leitor = DAO.getLeitor().encontrarPorId(reserva.getIDleitor());

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