package controller.dadosObjeto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Adm;
import model.Bibliotecario;
import utils.StageController;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaDadosAdmEBibliotecario" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaDadosAdmEBibliotecarioController {

    @FXML
    private Button botaoOK;

    @FXML
    private Label idUsuario;

    @FXML
    private Label mensagemTitulo;

    @FXML
    private Label nomeUsuario;

    @FXML
    private Label senhaUsuario;

    @FXML
    private Label tipoUsuario;

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
     * Método responsável por setar nos label da tela as informações do Administrador escolhido no TableView.
     *
     * @param adm administrador escolhido
     */

    public void setAdm(Adm adm) {
        this.mensagemTitulo.setText("Detalhes do Administrador");

        this.idUsuario.setText(Integer.toString(adm.getID()));
        this.nomeUsuario.setText(adm.getNome());
        this.senhaUsuario.setText(adm.getSenha());
        this.tipoUsuario.setText(adm.getTipoUsuario().name());
    }

    /**
     * Método responsável por setar nos label da tela as informações do Bibliotecario escolhido no TableView.
     *
     * @param bibliotecario bibliotecario escolhido
     */

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.mensagemTitulo.setText("Detalhes do Bibliotecario");

        this.idUsuario.setText(Integer.toString(bibliotecario.getID()));
        this.nomeUsuario.setText(bibliotecario.getNome());
        this.senhaUsuario.setText(bibliotecario.getSenha());
        this.tipoUsuario.setText(bibliotecario.getTipoUsuario().name());
    }
}