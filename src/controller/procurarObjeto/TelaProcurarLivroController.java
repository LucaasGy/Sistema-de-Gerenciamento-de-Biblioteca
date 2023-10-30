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
                        FXMLLoader loader = StageController.retornaLoader("TelaEditarExcluirLivro.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaEditarExcluirLivroController controller = loader.getController();

                        controller.setIsbnLivroAAlterar(Double.parseDouble(dado));
                        controller.carregaTabelaISBN(Double.parseDouble(dado));
                        controller.setQualTabelaCarregar("ISBN");
                    }
                }

                else if(escolha.equals("Titulo")){
                    if(DAO.getLivro().encontrarPorTitulo(dado).isEmpty())
                        StageController.error(this.mensagemErro,"LIVRO NÃO ENCONTRADO");

                    else{
                        FXMLLoader loader = StageController.retornaLoader("TelaEditarExcluirLivro.fxml");
                        StageController.criaStage(StageController.getStage(event), loader);
                        TelaEditarExcluirLivroController controller = loader.getController();

                        controller.setTituloLivroAAlterar(dado);
                        controller.carregaTabelaTitulo(dado);
                        controller.setQualTabelaCarregar("Titulo");
                    }
                }
            }
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }
}
