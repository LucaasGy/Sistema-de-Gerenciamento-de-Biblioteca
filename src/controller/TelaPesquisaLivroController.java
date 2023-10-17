package controller;

import erros.objetos.ObjetoInvalido;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Bibliotecario bibliotecario;
    private Adm adm;
    private Leitor leitor;
    private Convidado convidado;

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Adm getAdm() {
        return adm;
    }

    public void setAdm(Adm adm) {
        this.adm = adm;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
    }

    public Convidado getConvidado() {
        return convidado;
    }

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
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
                this.mensagemErro.setText("");
                return;
            }

            if (escolha.equals("Titulo")) {
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorTitulo(dado);
                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                    if(this.getAdm()!=null) {
                        controller.telaAdministradorEConvidado();
                    }

                    else if(this.getBibliotecario()!=null) {
                        controller.telaBibliotecario();
                    }

                    else if(this.getLeitor()!=null) {
                        controller.setLeitor(this.getLeitor());
                        controller.telaLeitor();
                    }

                    else if(this.getConvidado()!=null) {
                        controller.telaAdministradorEConvidado();
                    }
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("ISBN")) {
                try {
                    Livro livro = Sistema.pesquisarLivroPorISBN(Double.parseDouble(dado));
                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroISBN(livro);
                    if(this.getAdm()!=null) {
                        controller.telaAdministradorEConvidado();
                    }

                    else if(this.getBibliotecario()!=null) {
                        controller.telaBibliotecario();
                    }

                    else if(this.getLeitor()!=null) {
                        controller.setLeitor(this.getLeitor());
                        controller.telaLeitor();
                    }

                    else if(this.getConvidado()!=null) {
                        controller.telaAdministradorEConvidado();
                    }
                }catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("Categoria")) {
                try {
                    List<Livro> livros = Sistema.pesquisarLivroPorCategoria(dado);
                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                    if(this.getAdm()!=null) {
                        controller.telaAdministradorEConvidado();
                    }

                    else if(this.getBibliotecario()!=null) {
                        controller.telaBibliotecario();
                    }

                    else if(this.getLeitor()!=null) {
                        controller.setLeitor(this.getLeitor());
                        controller.telaLeitor();
                    }

                    else if(this.getConvidado()!=null) {
                        controller.telaAdministradorEConvidado();
                    }
                } catch (ObjetoInvalido e) {
                    this.mensagemErro.setText(e.getMessage());
                }
            }

            else if (escolha.equals("Autor")) {
                try{
                    List<Livro> livros = Sistema.pesquisarLivroPorAutor(dado);
                    TelaLivrosPesquisadosController controller = this.retornaControlador(event);
                    controller.carregaTableLivroLista(livros);
                    if(this.getAdm()!=null) {
                        controller.telaAdministradorEConvidado();
                    }

                    else if(this.getBibliotecario()!=null) {
                        controller.telaBibliotecario();
                    }

                    else if(this.getLeitor()!=null) {
                        controller.setLeitor(this.getLeitor());
                        controller.telaLeitor();
                    }

                    else if(this.getConvidado()!=null) {
                        controller.telaAdministradorEConvidado();
                    }
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
