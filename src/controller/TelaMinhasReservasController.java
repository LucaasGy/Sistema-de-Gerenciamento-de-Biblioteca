package controller;

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

    @FXML
    void voltarMenu(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void retirarReserva(){
        if(this.tabelaLivros.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao retirar reserva", "Escolha um livro antes de retirar");

        else {
            this.telaInicialController.getLeitor().retirarReserva(this.tabelaLivros.getSelectionModel().getSelectedItem().getISBN());
            this.mensagemErro.setText("RESERVA RETIRADA COM SUCESSO");
            this.mensagemErro.setStyle("-fx-text-fill: green;");
            carregaTabela();
        }
    }

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

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
