package controller.pesquisaLivro;

import controller.telaInicial.TelaInicialController;
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
 * Controller  responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaPesquisaLivro" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaPesquisaLivroController  {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField digitaEscolha;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;
    private TelaInicialController telaInicialController;

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
     * Ação de cliar no botão de confirmar.
     *
     * A depender da escolha de pesquisa do livro (titulo, autor, isbn ou categoria),
     * carrega o TableView da tela livros pesquisados com os livros encontrados e a depender
     * do tipo de usuário logado na tela inicial, modifica a tela de livros pesquisados com
     * as funcionalidades permitidas a cada tipo.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException {
        String dado = digitaEscolha.getText();

        //caso não seja digitado um dado a pesquisar de um livro, exibe error
        if(dado.isEmpty())
            StageController.error(this.mensagemErro,"INSIRA DADO DO LIVRO");

        else {
            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            //caso o tipo de pesquisa escolhido for ISBN e o dado digitado não for composto apenas por números, exibe error
            if (escolha.equals("ISBN") && !StageController.tryParseDouble(dado))
                StageController.error(this.mensagemErro,"ISBN É COMPOSTO APENAS POR NÚMEROS");

            else {
                /*caso o tipo de pesquisa escolhido for titulo, carrega a tela de livros pesquisados em um novo stage,
                carrega o TableView com os livros encontrados e seta as funcionalidades permitidas.*/
                if (escolha.equals("Titulo")) {
                    try {
                        List<Livro> livros = Sistema.pesquisarLivroPorTitulo(dado);

                        FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaLivrosPesquisadosController controller = loader.getController();

                        controller.carregaTableLivroLista(livros);

                        if (this.telaInicialController.getAdm() != null)
                            controller.telaAdministradorEConvidado();

                        else if (this.telaInicialController.getBibliotecario() != null)
                            controller.telaBibliotecario();

                        else if (this.telaInicialController.getLeitor() != null) {
                            controller.setTelaInicialController(this.telaInicialController);
                            controller.telaLeitor();
                        }

                        else if (this.telaInicialController.getConvidado() != null)
                            controller.telaAdministradorEConvidado();
                    } catch (ObjetoInvalido e) {
                        StageController.error(this.mensagemErro,e.getMessage());
                    }
                }

                /*caso o tipo de pesquisa escolhido for isbn, carrega a tela de livros pesquisados em um novo stage,
                carrega o TableView com o livro encontrado e seta as funcionalidades permitidas.*/
                else if (escolha.equals("ISBN")) {
                    try {
                        Livro livro = Sistema.pesquisarLivroPorISBN(Double.parseDouble(dado));

                        FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaLivrosPesquisadosController controller = loader.getController();

                        controller.carregaTableLivroISBN(livro);

                        if (this.telaInicialController.getAdm() != null)
                            controller.telaAdministradorEConvidado();

                        else if (this.telaInicialController.getBibliotecario() != null)
                            controller.telaBibliotecario();

                        else if (this.telaInicialController.getLeitor() != null) {
                            controller.setTelaInicialController(this.telaInicialController);
                            controller.telaLeitor();
                        }

                        else if (this.telaInicialController.getConvidado() != null)
                            controller.telaAdministradorEConvidado();
                    } catch (ObjetoInvalido e) {
                        StageController.error(this.mensagemErro,e.getMessage());
                    }
                }

                /*caso o tipo de pesquisa escolhido for categoria, carrega a tela de livros pesquisados em um novo stage,
                carrega o TableView com os livros encontrados e seta as funcionalidades permitidas.*/
                else if (escolha.equals("Categoria")) {
                    try {
                        List<Livro> livros = Sistema.pesquisarLivroPorCategoria(dado);

                        FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaLivrosPesquisadosController controller = loader.getController();

                        controller.carregaTableLivroLista(livros);

                        if (this.telaInicialController.getAdm() != null)
                            controller.telaAdministradorEConvidado();

                        else if (this.telaInicialController.getBibliotecario() != null)
                            controller.telaBibliotecario();

                        else if (this.telaInicialController.getLeitor() != null) {
                            controller.setTelaInicialController(this.telaInicialController);
                            controller.telaLeitor();
                        }

                        else if (this.telaInicialController.getConvidado() != null)
                            controller.telaAdministradorEConvidado();
                    } catch (ObjetoInvalido e) {
                        StageController.error(this.mensagemErro,e.getMessage());
                    }
                }

                /*caso o tipo de pesquisa escolhido for autor, carrega a tela de livros pesquisados em um novo stage,
                carrega o TableView com os livros encontrados e seta as funcionalidades permitidas.*/
                else if (escolha.equals("Autor")) {
                    try {
                        List<Livro> livros = Sistema.pesquisarLivroPorAutor(dado);

                        FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaLivrosPesquisadosController controller = loader.getController();

                        controller.carregaTableLivroLista(livros);

                        if (this.telaInicialController.getAdm() != null)
                            controller.telaAdministradorEConvidado();

                        else if (this.telaInicialController.getBibliotecario() != null)
                            controller.telaBibliotecario();

                        else if (this.telaInicialController.getLeitor() != null) {
                            controller.setTelaInicialController(this.telaInicialController);
                            controller.telaLeitor();
                        }

                        else if (this.telaInicialController.getConvidado() != null)
                            controller.telaAdministradorEConvidado();
                    } catch (ObjetoInvalido e) {
                        StageController.error(this.mensagemErro,e.getMessage());
                    }
                }
            }
        }
    }

    public void setTelaInicialController(TelaInicialController telaInicialController) {
        this.telaInicialController = telaInicialController;
    }
}