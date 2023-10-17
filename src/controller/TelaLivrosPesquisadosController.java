package controller;

import dao.DAO;
import erros.leitor.*;
import erros.livro.*;
import erros.objetos.ObjetoInvalido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
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
    private Leitor leitor;

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    @FXML
    void voltarMenu(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void fazerReserva(){
        try{
            this.leitor.reservarLivro(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
            this.mensagemErro.setText("Reserva feita com sucesso");
            this.mensagemErro.setStyle("-fx-text-fill: green;");
        }catch(LeitorBloqueado | LivroLimiteDeReservas | LivroNaoDisponivel | LeitorTemEmprestimoEmAtraso | LivroNaoPossuiEmprestimoNemReserva |
                LeitorMultado | LeitorReservarLivroEmMaos | LeitorLimiteDeReservas | ObjetoInvalido | LeitorPossuiReservaDesseLivro e){
            this.mensagemErro.setText(e.getMessage());
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void fazerDevolucao(){
        try {
            Sistema.devolverLivro(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
            this.mensagemErro.setText("Devolução feita com sucesso");
            this.mensagemErro.setStyle("-fx-text-fill: green;");
        }catch (LivroNaoPossuiEmprestimo e){
            this.mensagemErro.setText(e.getMessage());
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void fazerEmprestimo(ActionEvent event) throws IOException {
        Livro livro = this.tabelaLivros.getSelectionModel().getSelectedItem();

        if(!livro.getDisponivel()) {
            if(DAO.getEmprestimo().encontrarPorISBN(livro.getISBN())!=null) {
                LivroEmprestado e = new LivroEmprestado();
                this.mensagemErro.setText(e.getMessage());
                this.mensagemErro.setStyle("-fx-text-fill: red;");
                return;
            }
            LivroNaoDisponivel e = new LivroNaoDisponivel();
            this.mensagemErro.setText(e.getMessage());
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }

        else {
            this.mensagemErro.setText("");
            Stage stage = new Stage();
            TelaDigiteIDController controller = StageController.criaStage(stage, "TelaDigiteID.fxml").getController();
            controller.setQualOperacao("emprestimo");
            controller.setLivro(this.tabelaLivros.getSelectionModel().getSelectedItem());

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    public void selecionarLivroTabela(Livro livro){
        this.mensagemErro.setText("");
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

    public void telaAdministradorEConvidado(){
        this.botaoEmprestimo.setDisable(true);
        this.botaoDevolucao.setDisable(true);
        this.botaoReservar.setDisable(true);
    }

    public void telaLeitor(){
        this.botaoDevolucao.setDisable(true);
        this.botaoEmprestimo.setDisable(true);
    }

    public void telaBibliotecario(){
        this.botaoReservar.setDisable(true);
    }
}
