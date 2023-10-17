package controller;

import erros.objetos.ObjetoInvalido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import model.*;
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
    private TelaInicialController telaInicialController;

    public void setTelaInicialController(TelaInicialController telaInicialController) {
        this.telaInicialController = telaInicialController;
    }

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException {
        if(digitaEscolha.getText().isEmpty()){
            this.mensagemErro.setText("Insira dado do livro");
        }

        else {
            String dado = digitaEscolha.getText();

            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("ISBN") && !tryParseDouble(dado)) {
                this.mensagemErro.setText("ISBN é composto apenas por números");
                return;
            }

            if (escolha.equals("Titulo")) {
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorTitulo(dado);

                    FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                    StageController.criaStage(StageController.getStage(event), loader);
                    TelaLivrosPesquisadosController controller = loader.getController();

                    controller.carregaTableLivroLista(livros);

                    if(this.telaInicialController.getAdm()!=null)
                        controller.telaAdministradorEConvidado();

                    else if(this.telaInicialController.getBibliotecario()!=null)
                        controller.telaBibliotecario();

                    else if(this.telaInicialController.getLeitor()!=null) {
                        controller.setTelaInicialController(this.telaInicialController);
                        controller.telaLeitor();
                    }

                    else if(this.telaInicialController.getConvidado()!=null)
                        controller.telaAdministradorEConvidado();
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("ISBN")) {
                try {
                    Livro livro = Sistema.pesquisarLivroPorISBN(Double.parseDouble(dado));

                    FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                    StageController.criaStage(StageController.getStage(event), loader);
                    TelaLivrosPesquisadosController controller = loader.getController();

                    controller.carregaTableLivroISBN(livro);

                    if(this.telaInicialController.getAdm()!=null)
                        controller.telaAdministradorEConvidado();

                    else if(this.telaInicialController.getBibliotecario()!=null)
                        controller.telaBibliotecario();

                    else if(this.telaInicialController.getLeitor()!=null) {
                        controller.setTelaInicialController(this.telaInicialController);
                        controller.telaLeitor();
                    }

                    else if(this.telaInicialController.getConvidado()!=null)
                        controller.telaAdministradorEConvidado();
                }catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("Categoria")) {
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorCategoria(dado);

                    FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                    StageController.criaStage(StageController.getStage(event), loader);
                    TelaLivrosPesquisadosController controller = loader.getController();

                    controller.carregaTableLivroLista(livros);

                    if(this.telaInicialController.getAdm()!=null)
                        controller.telaAdministradorEConvidado();

                    else if(this.telaInicialController.getBibliotecario()!=null)
                        controller.telaBibliotecario();

                    else if(this.telaInicialController.getLeitor()!=null) {
                        controller.setTelaInicialController(this.telaInicialController);
                        controller.telaLeitor();
                    }

                    else if(this.telaInicialController.getConvidado()!=null)
                        controller.telaAdministradorEConvidado();
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("Autor")) {
                try{
                    List<Livro> livros = Sistema.pesquisarLivroPorAutor(dado);

                    FXMLLoader loader = StageController.retornaLoader("TelaLivrosPesquisados.fxml");
                    StageController.criaStage(StageController.getStage(event), loader);
                    TelaLivrosPesquisadosController controller = loader.getController();

                    controller.carregaTableLivroLista(livros);

                    if(this.telaInicialController.getAdm()!=null)
                        controller.telaAdministradorEConvidado();

                    else if(this.telaInicialController.getBibliotecario()!=null)
                        controller.telaBibliotecario();

                    else if(this.telaInicialController.getLeitor()!=null) {
                        controller.setTelaInicialController(this.telaInicialController);
                        controller.telaLeitor();
                    }

                    else if(this.telaInicialController.getConvidado()!=null)
                        controller.telaAdministradorEConvidado();
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
}
