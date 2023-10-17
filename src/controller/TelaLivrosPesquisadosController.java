package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Livro;
import utils.StageController;

import java.io.IOException;
import java.util.List;

public class TelaLivrosPesquisadosController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label mensagemErro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoDevolucao;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoEmprestimo;

    @FXML
    private Button botaoReservar;

    @FXML
    private Label categoriaLivro;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private Label tituloLivro;

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    @FXML
    void voltarMenu(ActionEvent event) throws IOException {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

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

    }

    public void setaColunas(){
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    }

    public void carregaTableLivroISBN(Livro livro){
        setaColunas();
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livro);
        this.tabelaLivros.setItems(listaLivros);
    }

    public void carregaTableLivroLista(List<Livro> livros){
        setaColunas();
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livros);
        this.tabelaLivros.setItems(listaLivros);
    }
}
