package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Adm;
import model.Bibliotecario;
import utils.StageController;

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

    @FXML
    void clicarOk(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    public void setAdm(Adm adm) {
        this.mensagemTitulo.setText("Detalhes do Administrador");

        this.idUsuario.setText(Integer.toString(adm.getID()));
        this.nomeUsuario.setText(adm.getNome());
        this.senhaUsuario.setText(adm.getSenha());
        this.tipoUsuario.setText(adm.getTipoUsuario().name());
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.mensagemTitulo.setText("Detalhes do Bibliotecario");

        this.idUsuario.setText(Integer.toString(bibliotecario.getID()));
        this.nomeUsuario.setText(bibliotecario.getNome());
        this.senhaUsuario.setText(bibliotecario.getSenha());
        this.tipoUsuario.setText(bibliotecario.getTipoUsuario().name());
    }
}
