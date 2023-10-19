package controller;

import erros.objetos.ObjetoInvalido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
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

                String total = Integer.toString(emprestimosAtuais.size());
                controller.setMensagemTotal("Total de livros emprestados: "+total);
            }
        }

        else if(escolha.equals("Livros reservados")){
            List<Livro> livros = Adm.livrosReservados();

            if(livros.isEmpty()){
                this.mensagemErro.setText("Não há livros reservados");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoReservados.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoReservadosController controller = loader.getController();
                controller.carregarTabela(livros);

                String total = Integer.toString(livros.size());
                controller.setMensagemTotal("Total de livros reservados: "+total);
            }
        }

        else if(escolha.equals("Livros atrasados")){
            List<Emprestimo> emprestimosAtrasados = Adm.livrosAtrasados();

            if(emprestimosAtrasados.isEmpty()){
                this.mensagemErro.setText("Não há livros atrasados");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                controller.carregarTabela(emprestimosAtrasados);

                String total = Integer.toString(emprestimosAtrasados.size());
                controller.setMensagemTotal("Total de livros atrasados: "+total);
            }
        }

        else if(escolha.equals("Livros mais populares")){
            List<Livro> livrosPopulares = Adm.livrosMaisPopulares();

            if(livrosPopulares.isEmpty()){
                this.mensagemErro.setText("Não há livros no sistema");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoLivrosPopulares.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLivrosPopularesController controller = loader.getController();
                controller.carregarTabela(livrosPopulares);
            }
        }

        else if(escolha.equals("Estoque")){
            List<Livro> todosLivros = Adm.estoque();

            if(todosLivros.isEmpty()){
                this.mensagemErro.setText("Não há livros no sistema");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoLivrosPopulares.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLivrosPopularesController controller = loader.getController();
                controller.carregarTabela(todosLivros);

                String total = Integer.toString(todosLivros.size());
                controller.setMensagemTotal("Total de livros no estoque: "+total);
            }
        }

        else if(escolha.equals("Leitores bloqueados")){
            List<Leitor> leitoresBloqueados = Adm.leitoresBloqueados();

            if(leitoresBloqueados.isEmpty()){
                this.mensagemErro.setText("Não há leitores bloqueados no sistema");
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoLeitoresBloqueados.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLeitoresBloqueadosController controller = loader.getController();
                controller.carregarTabela(leitoresBloqueados);

                String total = Integer.toString(leitoresBloqueados.size());
                controller.setMensagemTotal("Total de leitores bloqueados: "+total);
            }
        }

        else{
            if(this.digiteID.getText().isEmpty())
                this.mensagemErro.setText("Insira ID do leitor");

            else if(!StageController.tryParseInt(this.digiteID.getText()))
                this.mensagemErro.setText("ID é composto apenas por números");

            else{
                try{
                    List<Emprestimo> historicoLeitor = Adm.historicoEmprestimoDeUmLeitor(Integer.parseInt(this.digiteID.getText()));

                    if(historicoLeitor.isEmpty()){
                        this.mensagemErro.setText("Não há empréstimos desse leitor");
                    }

                    else{
                        FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                        controller.carregarTabela(historicoLeitor);

                        String total = Integer.toString(historicoLeitor.size());
                        controller.setMensagemTotal("Total de empréstimos do leitor: "+total);
                    }

                }catch (ObjetoInvalido e){
                    this.mensagemErro.setText(e.getMessage());
                }
            }
        }
    }

    public void visibilidadeID(RadioButton radio){
        this.mensagemErro.setText("");

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
