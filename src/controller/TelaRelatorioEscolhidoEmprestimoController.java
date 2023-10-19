package controller;

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
import model.Emprestimo;
import model.Livro;
import utils.StageController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
    private TableView<Emprestimo> tabelaLivros;

    @FXML
    private Label telefoneLeitor;

    @FXML
    private Label tituloLivro;

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    @FXML
    void menu(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    public void carregarTabela(List<Emprestimo> emprestimos){
        this.colunasISBN.setCellValueFactory(new PropertyValueFactory<>("ISBNlivro"));
        this.colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataPegou"));
        this.colunaDataFim.setCellValueFactory(new PropertyValueFactory<>("dataPrevista"));

        ObservableList<Emprestimo> listaEmprestimos = FXCollections.observableArrayList(emprestimos);
        this.tabelaLivros.setItems(listaEmprestimos);
    }

    public void selecionarLivroTabela(Emprestimo emprestimo){
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
        this.telefoneLeitor.setText(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getTelefone());
        this.enderecoLeitor.setText(DAO.getLeitor().encontrarPorId(emprestimo.getIDleitor()).getEndereco());
    }
}
