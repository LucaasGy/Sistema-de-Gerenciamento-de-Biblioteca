package controller;

import dao.DAO;
import erros.leitor.LeitorBloqueado;
import erros.leitor.LeitorMultado;
import erros.leitor.LeitorNaoMultado;
import erros.leitor.LeitorTemEmprestimo;
import erros.livro.LivroReservado;
import erros.objetos.ObjetoInvalido;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Adm;
import model.Bibliotecario;
import model.Leitor;
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
            this.mensagemErro.setText("INSIRA ID DO LEITOR");
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }

        else if(!StageController.tryParseInt(this.digitaID.getText())){
            this.mensagemErro.setText("ID É COMPOSTO APENAS POR NÚMEROS");
            this.mensagemErro.setStyle("-fx-text-fill: red;");
        }

        else {
            if (this.qualOperacao.equals("emprestimo")) {
                try{
                    Bibliotecario.fazerEmprestimo(Integer.parseInt(this.digitaID.getText()),this.livro.getISBN());
                    this.mensagemErro.setText("EMPRÉTIMO FEITO COM SUCESSO");
                    this.mensagemErro.setStyle("-fx-text-fill: green;");
                }catch ( LeitorBloqueado | LivroReservado | LeitorMultado | LeitorTemEmprestimo | ObjetoInvalido e){
                    this.mensagemErro.setText(e.getMessage());
                    this.mensagemErro.setStyle("-fx-text-fill: red;");
                }
            }

            else if(this.qualOperacao.equals("Bloqueio/Desbloqueio")){
                Leitor leitor = DAO.getLeitor().encontrarPorId(Integer.parseInt(this.digitaID.getText()));

                if(leitor==null) {
                    ObjetoInvalido e = new ObjetoInvalido("LEITOR NÃO ENCONTRADO");
                    this.mensagemErro.setText(e.getMessage());
                    this.mensagemErro.setStyle("-fx-text-fill: red;");
                }

                else {
                    if (leitor.getBloqueado()) {
                        Adm.desbloquearLeitor(Integer.parseInt(this.digitaID.getText()));
                        this.mensagemErro.setText("LEITOR DESBLOQUEADO");
                        this.mensagemErro.setStyle("-fx-text-fill: green;");
                    }

                    else{
                        Adm.bloquearLeitor(Integer.parseInt(this.digitaID.getText()));
                        this.mensagemErro.setText("LEITOR BLOQUEADO");
                        this.mensagemErro.setStyle("-fx-text-fill: green;");
                    }
                }
            }

            else if(this.qualOperacao.equals("Tirar multa")){
                try{
                    Adm.tirarMulta(Integer.parseInt(this.digitaID.getText()));
                    this.mensagemErro.setText("MULTA RETIRADA");
                    this.mensagemErro.setStyle("-fx-text-fill: green;");
                }catch (ObjetoInvalido | LeitorNaoMultado e){
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
