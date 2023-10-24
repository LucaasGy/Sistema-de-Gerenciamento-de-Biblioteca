package controller;

import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.StageController;

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
    void confirmarEscolha(ActionEvent event) {
        String dado = digitaEscolha.getText();

        if(dado.isEmpty())
            this.mensagemErro.setText("INSIRA DADO DO LIVRO");

        else{
            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("ISBN") && !StageController.tryParseDouble(dado))
                this.mensagemErro.setText("ISBN É COMPOSTO APENAS POR NÚMEROS");

            else{
                if(escolha.equals("ISBN")){
                    if(DAO.getLivro().encontrarPorISBN(Double.parseDouble(dado))==null)
                        this.mensagemErro.setText("LIVRO NÃO ENCONTRADO");

                    else{
                        //carrego tela
                    }
                }

                else if(escolha.equals("Titulo")){
                    if(DAO.getLivro().encontrarPorTitulo(dado).isEmpty())
                        this.mensagemErro.setText("LIVRO NÃO ENCONTRADO");

                    else{
                        //carrego tela
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
