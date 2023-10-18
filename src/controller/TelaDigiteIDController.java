package controller;

import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bibliotecario;
import model.Livro;
import utils.StageController;

import javafx.event.ActionEvent;

public class TelaDigiteIDController {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField digitaID;

    @FXML
    private Label mensagemErro;

    private String qualOperacao;

    private Livro livro;

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void setQualOperacao(String qualOperacao) {
        this.qualOperacao = qualOperacao;
    }

    @FXML
    void confirmarID(){
        if(this.digitaID.getText().isEmpty()) {
            this.mensagemErro.setText("Insira ID do leitor");
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }

        else if(!StageController.tryParseInt(this.digitaID.getText())){
            this.mensagemErro.setText("ID é composto apenas por números");
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }

        else {
            if (this.qualOperacao.equals("emprestimo")) {
                try{
                    Bibliotecario.fazerEmprestimo(Integer.parseInt(this.digitaID.getText()),this.livro.getISBN());
                    this.mensagemErro.setText("Emprétimo feito com sucesso");
                    this.mensagemErro.setStyle("-fx-text-fill: green;");
                }catch ( LeitorBloqueado | LivroReservado | LeitorMultado | LeitorTemEmprestimo | ObjetoInvalido e){
                    this.mensagemErro.setText(e.getMessage());
                    this.mensagemErro.setStyle("-fx-text-fill: red;");
                }
            }

        }
    }

    @FXML
    void voltar(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }
}
