package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Livro;
import utils.StageController;

public class TelaDadosLivroController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoOK;

    @FXML
    private Label categoriaLivro;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label tituloLivro;

    @FXML
    private Label totalEmpLivro;

    @FXML
    void clicarOk(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    public void setLivro(Livro livro) {
        this.isbnLivro.setText(Double.toString(livro.getISBN()));
        this.tituloLivro.setText(livro.getTitulo());
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
