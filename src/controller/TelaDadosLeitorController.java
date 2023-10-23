package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Leitor;
import utils.StageController;

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

    @FXML
    void clicarOk(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

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
