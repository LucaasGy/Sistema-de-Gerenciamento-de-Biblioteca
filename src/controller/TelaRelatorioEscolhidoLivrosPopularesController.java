package controller;

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
import model.Livro;
import utils.StageController;

import java.io.IOException;
import java.util.List;

public class TelaRelatorioEscolhidoLivrosPopularesController {

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
    private Label totalEmpLivro;

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
    private Label mensagemTotal;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private Label tituloLivro;

    public void setMensagemTotal(String mensagemTotal) {
        this.mensagemTotal.setText(mensagemTotal);
    }

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    public void carregarTabela(List<Livro> livros){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livros);
        this.tabelaLivros.setItems(listaLivros);
    }

    public void selecionarLivroTabela(Livro livro){
        this.tituloLivro.setText(livro.getTitulo());
        this.isbnLivro.setText(Double.toString(livro.getISBN()));
        this.autorLivro.setText(livro.getAutor());
        this.editoraLivro.setText(livro.getEditora());
        this.anoLivro.setText(Integer.toString(livro.getAno()));
        this.categoriaLivro.setText(livro.getCategoria());
        this.totalEmpLivro.setText(Integer.toString(livro.getQtdEmprestimo()));

        if(livro.getDisponivel())
            this.disponibilidadeLivro.setText("Sim");

        else
            this.disponibilidadeLivro.setText("Não");
    }
}
