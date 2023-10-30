package controller.procurarObjeto;

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
        if(this.digitaID.getText().isEmpty())
            StageController.error(this.mensagemErro,"INSIRA ID DO LEITOR");

        else if(!StageController.tryParseInt(this.digitaID.getText()))
            StageController.error(this.mensagemErro,"ID É COMPOSTO APENAS POR NÚMEROS");

        else {
            if (this.qualOperacao.equals("emprestimo")) {
                try{
                    Bibliotecario.fazerEmprestimo(Integer.parseInt(this.digitaID.getText()),this.livro.getISBN());
                    StageController.sucesso(this.mensagemErro,"EMPRÉTIMO FEITO COM SUCESSO");
                }catch ( LeitorBloqueado | LivroReservado | LeitorMultado | LeitorTemEmprestimo | ObjetoInvalido e){
                    StageController.error(this.mensagemErro,e.getMessage());
                }
            }

            else if(this.qualOperacao.equals("Bloqueio/Desbloqueio")){
                Leitor leitor = DAO.getLeitor().encontrarPorId(Integer.parseInt(this.digitaID.getText()));

                if(leitor==null) {
                    ObjetoInvalido e = new ObjetoInvalido("LEITOR NÃO ENCONTRADO");
                    StageController.error(this.mensagemErro,e.getMessage());
                }

                else {
                    if (leitor.getBloqueado()) {
                        Adm.desbloquearLeitor(Integer.parseInt(this.digitaID.getText()));
                        StageController.sucesso(this.mensagemErro,"LEITOR DESBLOQUEADO");
                    }

                    else{
                        Adm.bloquearLeitor(Integer.parseInt(this.digitaID.getText()));
                        StageController.sucesso(this.mensagemErro,"LEITOR BLOQUEADO");
                    }
                }
            }

            else if(this.qualOperacao.equals("Tirar multa")){
                try{
                    Adm.tirarMulta(Integer.parseInt(this.digitaID.getText()));
                    StageController.sucesso(this.mensagemErro,"MULTA RETIRADA");
                }catch (ObjetoInvalido | LeitorNaoMultado e){
                    StageController.error(this.mensagemErro,e.getMessage());
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
