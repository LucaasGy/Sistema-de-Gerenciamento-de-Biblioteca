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

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaDigiteID" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

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

    /**
     * Ação de clicar no botão de voltar.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltar(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Ação de clicar no botão de confirmar.
     *
     * Como esta tela é usada tanto para confirmar empréstimo de um livro,
     * bloquear/desbloquear um leitor e tirar multa de um leitor, a depender
     * do valor setado no atributo "qualOperacao", o botão desempenhará diferentes
     * funcionalidades.
     */

    @FXML
    void confirmarID(){
        if(this.digitaID.getText().isEmpty())
            StageController.error(this.mensagemErro,"INSIRA ID DO LEITOR");

        else if(!StageController.tryParseInt(this.digitaID.getText()))
            StageController.error(this.mensagemErro,"ID É COMPOSTO APENAS POR NÚMEROS");

        else {
            if (this.qualOperacao.equals("emprestimo")) {
                try{
                    //realiza empréstimo com o livro recebido do controller da tela "TelaLivrosPesquisados"
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
                    //Caso o leitor encontrado esteja bloqueado, desbloqueia e informa em tela
                    if (leitor.getBloqueado()) {
                        Adm.desbloquearLeitor(Integer.parseInt(this.digitaID.getText()));
                        StageController.sucesso(this.mensagemErro,"LEITOR DESBLOQUEADO");
                    }

                    //Caso o leitor encontrado não esteja bloqueado, bloqueia e informa em tela
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

    /**
     * Método responsável por setar qual livro foi escolhido do TableView do controller
     * da tela "TelaLivrosPesquisados".
     *
     * @param livro livro escolhido
     */

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    /**
     * Método responsável por setar qual funcionalidade o botão confirmar irá realizar.
     *
     * @param qualOperacao operação setada
     */
    public void setQualOperacao(String qualOperacao) {
        this.qualOperacao = qualOperacao;
    }
}