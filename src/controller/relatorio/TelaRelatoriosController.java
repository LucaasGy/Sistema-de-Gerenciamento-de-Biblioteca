package controller.relatorio;

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

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaRelatorios" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

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

    /**
     * Observador para coletar RadioButton escolhido no ToggleGroup.
     */

    @FXML
    void initialize(){
        this.grupo.selectedToggleProperty().addListener((observable, oldValue, newValue)
                -> visibilidadeID((RadioButton) newValue));
    }

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
     * Ação de clicar no botão de confirmar tipo de relatório.
     *
     * A depender do tipo de relatório escolhido, abre uma nova tela em
     * um novo stage.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException{
        RadioButton radio = (RadioButton) grupo.getSelectedToggle();
        String escolha = radio.getText();

        if(escolha.equals("Livros emprestados")){
            List<Emprestimo> emprestimosAtuais = Adm.livrosEmprestados();

            if(emprestimosAtuais.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS EMPRESTADOS");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                //carrega o TableView com os empréstimos atuais encontrados
                controller.carregarTabela(emprestimosAtuais);

                /*como a tela "TelaRelatorioEscolhidoEmprestimo" é utilizada tanto para relatório
                de livros emprestados, livros atrasados e histórico de um leitor, é necessário setar
                qual o título que a tela deve ter.*/
                String total = Integer.toString(emprestimosAtuais.size());
                controller.setMensagemTotal("Total de livros emprestados: "+total);
            }
        }

        else if(escolha.equals("Livros reservados")){
            List<Livro> livros = Adm.livrosReservados();

            if(livros.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS RESERVADOS");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoReservados.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoReservadosController controller = loader.getController();
                //carrega o TableView com os livros reservados encontrados
                controller.carregarTabela(livros);

                //altera mensagem titulo da tela com o total de livros reservados.
                String total = Integer.toString(livros.size());
                controller.setMensagemTotal("Total de livros reservados: "+total);
            }
        }

        else if(escolha.equals("Livros atrasados")){
            List<Emprestimo> emprestimosAtrasados = Adm.livrosAtrasados();

            if(emprestimosAtrasados.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS ATRASADOS");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoEmprestimo.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                //carrega o TableView com os empréstimos atuais em atraso encontrados
                controller.carregarTabela(emprestimosAtrasados);
                //este método torna visível a linha no gridpane que exibe a quantidade de dias em atraso
                controller.mostraDiasEmAtraso();

                /*como a tela "TelaRelatorioEscolhidoEmprestimo" é utilizada tanto para relatório
                de livros emprestados, livros atrasados e histórico de um leitor, é necessário setar
                qual o título que a tela deve ter.*/
                String total = Integer.toString(emprestimosAtrasados.size());
                controller.setMensagemTotal("Total de livros atrasados: "+total);
            }
        }

        else if(escolha.equals("Livros mais populares")){
            List<Livro> livrosPopulares = Adm.livrosMaisPopulares();

            if(livrosPopulares.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS NO SISTEMA");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoLivrosPopulares.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLivrosPopularesController controller = loader.getController();
                //carrega o TableView com os 10 livros mais populares encontrados
                //( se possuir pelo menos 10 livros no sistema )
                controller.carregarTabela(livrosPopulares);
            }
        }

        else if(escolha.equals("Estoque")){
            List<Livro> todosLivros = Adm.estoque();

            if(todosLivros.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LIVROS NO SISTEMA");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoLivrosPopulares.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLivrosPopularesController controller = loader.getController();
                //carrega o TableView com todos os livros encontrados
                controller.carregarTabela(todosLivros);

                //altera mensagem titulo da tela com o total de livros no estoque.
                String total = Integer.toString(todosLivros.size());
                controller.setMensagemTotal("Total de livros no estoque: "+total);
            }
        }

        else if(escolha.equals("Leitores bloqueados")){
            List<Leitor> leitoresBloqueados = Adm.leitoresBloqueados();

            if(leitoresBloqueados.isEmpty())
                StageController.error(this.mensagemErro,"NÃO HÁ LEITORES BLOQUEADOS NO SISTEMA");

            else{
                FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoLeitoresBloqueados.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaRelatorioEscolhidoLeitoresBloqueadosController controller = loader.getController();
                //carrega o TableView com os leitores bloqueados encontrados
                controller.carregarTabela(leitoresBloqueados);

                //altera mensagem titulo da tela com o total de leitores bloqueados.
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
                        FXMLLoader loader = StageController.retornaLoader("/view/TelaRelatorioEscolhidoEmprestimo.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaRelatorioEscolhidoEmprestimoController controller = loader.getController();
                        //carrega o TableView com todos os empréstimos encontrados de um leitor
                        controller.carregarTabela(historicoLeitor);

                        /*como a tela "TelaRelatorioEscolhidoEmprestimo" é utilizada tanto para relatório
                        de livros emprestados, livros atrasados e histórico de um leitor, é necessário setar
                        qual o título que a tela deve ter.*/
                        String total = Integer.toString(historicoLeitor.size());
                        controller.setMensagemTotal("Total de empréstimos do leitor: "+total);
                    }
                }catch (ObjetoInvalido e){
                    StageController.error(this.mensagemErro,e.getMessage());
                }
            }
        }
    }

    /**
     * Método responsável por tornar visível o label e textfield para digitar ID de um leitor.
     *
     * Esses campos só irão ser visíveis, caso o usuário selecione a opção
     * de ter acesso ao histórico de empréstimos de um leitor.
     *
     * @param radio radiobutton selecionado no grupo
     */

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