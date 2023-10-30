package controller.dadosObjeto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Leitor;
import utils.StageController;

/**
 * Controller  responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaDadosLeitor" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaDadosLeitorController {

    @FXML
    private Label bloqueadoLeitor;

    @FXML
    private Button botaoOK;

    @FXML
    private Label enderecoLeitor;

    @FXML
    private Label idUsuario;

    @FXML
    private Label multadoLeitor;

    @FXML
    private Label nomeUsuario;

    @FXML
    private Label renovacaoLeitor;

    @FXML
    private Label senhaUsuario;

    @FXML
    private Label telefoneLeitor;

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
     * Método responsável por setar nos label da tela as informações do Leitor escolhido.
     *
     * @param leitor leitor escolhido
     */

    public void setLeitor(Leitor leitor) {
        this.idUsuario.setText(Integer.toString(leitor.getID()));
        this.nomeUsuario.setText(leitor.getNome());
        this.senhaUsuario.setText(leitor.getSenha());
        this.enderecoLeitor.setText(leitor.getEndereco());
        this.telefoneLeitor.setText(leitor.getTelefone());

        if(leitor.getBloqueado())
            this.bloqueadoLeitor.setText("Sim");

        else
            this.bloqueadoLeitor.setText("Não");

        if(leitor.getDataMulta()!=null)
            this.multadoLeitor.setText(leitor.getDataMulta().toString());

        else
            this.multadoLeitor.setText("Não");

        this.renovacaoLeitor.setText(Integer.toString(leitor.getLimiteRenova()));
        this.tipoUsuario.setText(leitor.getTipoUsuario().name());
    }
}
