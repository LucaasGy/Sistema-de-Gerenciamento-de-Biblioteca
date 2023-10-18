package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Adm;
import model.Emprestimo;
import model.Livro;
import utils.StageController;

import java.io.IOException;
import java.util.List;

public class TelaRelatoriosController {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField digiteID;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemDigiteID;

    @FXML
    private Label mensagemErro;

    @FXML
    void initialize(){
        grupo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> visibilidadeID((RadioButton) newValue));
    }

    @FXML
    void voltar(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException{
        RadioButton radio = (RadioButton) grupo.getSelectedToggle();
        String escolha = radio.getText();

        if(escolha.equals("Livros emprestados")){
            List<Emprestimo> emprestimosAtuais = Adm.livrosEmprestados();

            if(emprestimosAtuais.isEmpty()){
                this.mensagemErro.setText("Não há livros emprestados");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                controller.carregarTabela(emprestimosAtuais);
            }
        }

        /*else if(escolha.equals("Livros reservados")){
            List<Livro> livros = Adm.livrosReservados();

            if(livros.isEmpty()){
                this.mensagemErro.setText("Não há livros emprestados");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoController controller = loader.getController();
                controller.carregarTabela(livros);
            }

        }*/

        else if(escolha.equals("Livros atrasados")){
            List<Emprestimo> emprestimosAtuais = Adm.livrosAtrasados();

            if(emprestimosAtuais.isEmpty()){
                this.mensagemErro.setText("Não há livros atrasados");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                controller.carregarTabela(emprestimosAtuais);
            }
        }

        else if(escolha.equals("Livros mais populares")){

        }

        else if(escolha.equals("Estoque")){

        }

        else if(escolha.equals("Leitores bloqueados")){

        }

        else{
            if(this.digiteID.getText().isEmpty())
                this.mensagemErro.setText("Insira ID do leitor");

            else if(!StageController.tryParseInt(this.digiteID.getText()))
                this.mensagemErro.setText("ID é composto apenas por números");

            else{

            }
        }
    }

    public void visibilidadeID(RadioButton radio){
        if(radio.getText().equals("Histórico de empréstimos de um leitor")){
            this.mensagemDigiteID.setVisible(true);
            this.digiteID.setVisible(true);
        }

        else{
            this.mensagemDigiteID.setVisible(false);
            this.digiteID.setVisible(false);
        }
    }

}
