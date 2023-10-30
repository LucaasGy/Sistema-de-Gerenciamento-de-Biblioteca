package controller.dadosObjeto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Livro;
import utils.StageController;

/**
 * Controller  responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaDadosLivro" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaDadosLivroController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoOK;

    @FXML
    private Label categoriaLivro;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label tituloLivro;

    @FXML
    private Label totalEmpLivro;

    /**
     * Ação de clicar no botão de ok.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void clicarOk(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Método responsável por setar nos label da tela as informações do Livro escolhido.
     *
     * @param livro livro escolhido
     */

    public void setLivro(Livro livro) {
        this.isbnLivro.setText(Double.toString(livro.getISBN()));
        this.tituloLivro.setText(livro.getTitulo());
        this.autorLivro.setText(livro.getAutor());
        this.editoraLivro.setText(livro.getEditora());
        this.anoLivro.setText(Integer.toString(livro.getAno()));
        this.categoriaLivro.setText(livro.getCategoria());
        this.totalEmpLivro.setText(Integer.toString(livro.getQtdEmprestimo()));

        if(livro.getDisponivel())
            this.disponibilidadeLivro.setText("Sim");

        else
            this.disponibilidadeLivro.setText("Não");
    }
}
