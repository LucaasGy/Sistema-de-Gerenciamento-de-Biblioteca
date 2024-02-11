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
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.Emprestimo;
import utils.StageController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaRelatorioEscolhidoEmprestimo" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaRelatorioEscolhidoEmprestimoController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoVoltar;

    @FXML
    private Label categoriaLivro;

    @FXML
    private TableColumn<Emprestimo, LocalDate> colunaDataFim;

    @FXML
    private TableColumn<Emprestimo, LocalDate> colunaDataInicio;

    @FXML
    private TableColumn<Emprestimo, Double> colunasISBN;

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
    private Label idLeitor;

    @FXML
    private TableView<Emprestimo> tabelaLivros;

    @FXML
    private Label telefoneLeitor;

    @FXML
    private Label mensagemTotal;

    @FXML
    private Label tituloLivro;

    @FXML
    private Label labelDiasEmAtraso;

    @FXML
    private Label labelNumeroDias;

    @FXML
    private RowConstraints linhaDiasEmAtraso;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarEmprestimoTabela(newValue));
    }

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void menu(ActionEvent event){
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
     * Método responsável por carregar o TableView com a lista de empréstimos recebida do
     * controller da tela relatórios.
     *
     * ps: Esta lista pode ser de empréstimos atuais, empréstimo atuais em atraso ou
     * histórico de empréstimos de um leitor.
     *
     * @param emprestimos lista de empréstimos
     */

    public void carregarTabela(List<Emprestimo> emprestimos){
        this.colunasISBN.setCellValueFactory(new PropertyValueFactory<>("ISBNlivro"));
        this.colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataPegou"));
        this.colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataPrevista"));

        ObservableList<Emprestimo> listaEmprestimos = FXCollections.observableArrayList(emprestimos);
        this.tabelaLivros.setItems(listaEmprestimos);
    }

    /**
     * Método responsável por setar nos label da tela as informações do livro e do leitor
     * do empréstimo escolhido no TableView.
     *
     * @param emprestimo empréstimo escolhido no TableView
     */

    public void selecionarEmprestimoTabela(Emprestimo emprestimo){
        this.tituloLivro.setText(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getTitulo());
        this.isbnLivro.setText(Double.toString(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getISBN()));
        this.autorLivro.setText(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getAutor());
        this.editoraLivro.setText(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getEditora());
        this.anoLivro.setText(Integer.toString(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getAno()));
        this.categoriaLivro.setText(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getCategoria());

        if(DAO.getLivro().encontrarPorISBN(emprestimo.getISBNlivro()).getDisponivel())
            this.disponibilidadeLivro.setText("Sim");

        else
            this.disponibilidadeLivro.setText("Não");

        this.nomeLeitor.setText(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getNome());
        this.idLeitor.setText(Integer.toString(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getID()));
        this.telefoneLeitor.setText(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getTelefone());
        this.enderecoLeitor.setText(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getEndereco());
        this.labelNumeroDias.setText(Long.toString(ChronoUnit.DAYS.between(emprestimo.getDataPrevista(),LocalDate.now())));
    }

    /**
     * Método responsável por tornar visível campo que mostra dias em atraso de um empréstimo.
     *
     * Estes campos só irão ser visíveis caso o usuário escolha na tela de relatórios, a opção
     * de obter relatório de empréstimos em atraso.
     */

    public void mostraDiasEmAtraso(){
        this.labelDiasEmAtraso.setVisible(true);
        this.labelNumeroDias.setVisible((true));
        this.linhaDiasEmAtraso.setMinHeight(10);
        this.linhaDiasEmAtraso.setPrefHeight(40);
        this.linhaDiasEmAtraso.setMaxHeight(95);
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