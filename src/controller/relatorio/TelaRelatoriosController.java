package controller.relatorio;

import controller.relatorio.TelaRelatorioEscolhidoEmprestimoController;
import controller.relatorio.TelaRelatorioEscolhidoLeitoresBloqueadosController;
import controller.relatorio.TelaRelatorioEscolhidoLivrosPopularesController;
import controller.relatorio.TelaRelatorioEscolhidoReservadosController;
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
        this.grupo.selectedToggleProperty().addListener((observable, oldValue, newValue) -> visibilidadeID((RadioButton) newValue));
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

            if(emprestimosAtuais.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS EMPRESTADOS");

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

            if(livros.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS RESERVADOS");

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

            if(emprestimosAtrasados.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS ATRASADOS");

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                controller.carregarTabela(emprestimosAtrasados);
                controller.mostraDiasEmAtraso();

                String total = Integer.toString(emprestimosAtrasados.size());
                controller.setMensagemTotal("Total de livros atrasados: "+total);
            }
        }

        else if(escolha.equals("Livros mais populares")){
            List<Livro> livrosPopulares = Adm.livrosMaisPopulares();

            if(livrosPopulares.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS NO SISTEMA");

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoLivrosPopulares.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLivrosPopularesController controller = loader.getController();
                controller.carregarTabela(livrosPopulares);
            }
        }

        else if(escolha.equals("Estoque")){
            List<Livro> todosLivros = Adm.estoque();

            if(todosLivros.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS NO SISTEMA");

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

            if(leitoresBloqueados.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LEITORES BLOQUEADOS NO SISTEMA");

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
                StageController.error(this.mensagemErro,"INSIRA ID DO LEITOR");

            else if(!StageController.tryParseInt(this.digiteID.getText()))
                StageController.error(this.mensagemErro,"ID É COMPOSTO APENAS POR NÚMEROS");

            else{
                try{
                    List<Emprestimo> historicoLeitor = Adm.historicoEmprestimoDeUmLeitor(Integer.parseInt(this.digiteID.getText()));

                    if(historicoLeitor.isEmpty())
                        StageController.error(this.mensagemErro,"NÃO HÁ EMPRÉSTIMOS DESSE LEITOR");

                    else{
                        FXMLLoader loader = StageController.retornaLoader("TelaRelatorioEscolhidoEmprestimo.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                        controller.carregarTabela(historicoLeitor);

                        String total = Integer.toString(historicoLeitor.size());
                        controller.setMensagemTotal("Total de empréstimos do leitor: "+total);
                    }

                }catch (ObjetoInvalido e){
                    StageController.error(this.mensagemErro,e.getMessage());
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
