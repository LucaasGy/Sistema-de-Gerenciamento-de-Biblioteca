package controller.procurarObjeto;

import controller.editarExcluirObjeto.TelaEditarExcluirLivroController;
import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaProcurarLivro" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaProcurarLivroController {

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

    @FXML
    private Label mensagemTitulo;

    /**
     * Ação de clicar no botão de confirmar livro a ser procurado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException {
        String dado = digitaEscolha.getText();

        if(dado.isEmpty())
            StageController.error(this.mensagemErro,"INSIRA DADO DO LIVRO");

        else{
            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("ISBN") && !StageController.tryParseDouble(dado))
                StageController.error(this.mensagemErro,"ISBN É COMPOSTO APENAS POR NÚMEROS");

            else{
                if(escolha.equals("ISBN")){
                    if(DAO.getLivro().encontrarPorISBN(Double.parseDouble(dado))==null)
                        StageController.error(this.mensagemErro,"LIVRO NÃO ENCONTRADO");

                    else{
                        FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirLivro.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaEditarExcluirLivroController controller = loader.getController();

                        //seta o ISBN do livro encontrado no controller da tela "TelaEditarExcluirLivroController"
                        controller.setIsbnLivroAAlterar(Double.parseDouble(dado));
                        //carrega o TableView do controller da tela "TelaEditarExcluirLivroController"
                        // com o livro encontrado por ISBN
                        controller.carregaTabelaISBN(Double.parseDouble(dado));
                        //seta qual tabela recarregar após remoção/alteração de livros
                        controller.setQualTabelaCarregar("ISBN");
                    }
                }

                else if(escolha.equals("Titulo")){
                    if(DAO.getLivro().encontrarPorTitulo(dado).isEmpty())
                        StageController.error(this.mensagemErro,"LIVRO NÃO ENCONTRADO");

                    else{
                        FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirLivro.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaEditarExcluirLivroController controller = loader.getController();

                        //seta o titulo dos livros encontrados no controller da tela "TelaEditarExcluirLivroController"
                        controller.setTituloLivroAAlterar(dado);
                        //carrega o TableView do controller da tela "TelaEditarExcluirLivroController" com os livros encontrados
                        //por titulo
                        controller.carregaTabelaTitulo(dado);
                        //seta qual tabela recarregar após remoção/alteração de livros
                        controller.setQualTabelaCarregar("Titulo");
                    }
                }
            }
        }
    }

    /**
     * Ação de clicar no botão de voltar.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltar(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }
}