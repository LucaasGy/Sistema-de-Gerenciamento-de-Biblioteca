package controller;

import erros.objetos.ObjetoInvalido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Livro;
import model.Sistema;
import utils.StageController;

import java.io.IOException;
import java.util.List;

public class TelaPesquisaLivroController  {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private TextField digitaEscolha;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException{
        if(digitaEscolha.getText().isEmpty()){
            this.mensagemErro.setText("Insira dado do livro");
        }

        else {
            String dado = digitaEscolha.getText();

            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("Titulo")) {
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorTitulo(dado);

                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("ISBN")) {
                if (!tryParseDouble(dado)){
                    this.mensagemErro.setText("ISBN é composto apenas por números");
                }

                else {
                    try {
                        Livro livro = Sistema.pesquisarLivroPorISBN(Double.parseDouble(dado));

                        TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                        controller.carregaTableLivroISBN(livro);
                    } catch (ObjetoInvalido e) {
                        this.mensagemErro.setText(e.getMessage());
                    }
                }
            }

            else if (escolha.equals("Categoria")){
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorCategoria(dado);

                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("Autor")){
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorAutor(dado);

                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }
        }
    }


    //Método para verificar se é um número double
    public static boolean tryParseDouble(String isbn) {
        try {
            Double.parseDouble(isbn);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public TelaLivrosPesquisadosController retornaControlador(ActionEvent event) throws IOException {
        Stage stage = StageController.getStage(event);

        TelaLivrosPesquisadosController controller = StageController.criaStage(stage,"TelaLivrosPesquisados.fxml").getController();

        return controller;
    }

}
