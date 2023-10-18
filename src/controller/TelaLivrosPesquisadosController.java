package controller;

import dao.DAO;
import erros.leitor.*;
import erros.livro.*;
import erros.objetos.ObjetoInvalido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
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
    private TelaInicialController telaInicialController;

    public void setTelaInicialController(TelaInicialController telaInicialController) {
        this.telaInicialController = telaInicialController;
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
        if(this.tabelaLivros.getSelectionModel().getSelectedItem()==null) {
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar reserva", "Escolha um livro antes de confirmar");
            return;
        }

        try{
            this.telaInicialController.getLeitor().reservarLivro(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
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
        if(this.tabelaLivros.getSelectionModel().getSelectedItem()==null) {
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar devolução", "Escolha um livro antes de confirmar");
            return;
        }

        try {
            Bibliotecario.devolverLivro(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
            this.mensagemErro.setText("Devolução feita com sucesso");
            this.mensagemErro.setStyle("-fx-text-fill: green;");

        }catch (LivroNaoPossuiEmprestimo e){
            this.mensagemErro.setText(e.getMessage());
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void fazerEmprestimo() throws IOException {
        if(this.tabelaLivros.getSelectionModel().getSelectedItem()==null) {
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar empréstimo", "Escolha um livro antes de confirmar");
            return;
        }

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
            FXMLLoader loader = StageController.retornaLoader("TelaDigiteID.fxml");
            StageController.criaStage(stage, loader);
            TelaDigiteIDController controller = loader.getController();

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
