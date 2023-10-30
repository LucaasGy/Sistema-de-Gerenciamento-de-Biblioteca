package view;

import erros.objetos.ObjetoInvalido;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, ObjetoInvalido {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();

        StageController.criaStage(stage,StageController.retornaLoader("TelaLogin.fxml"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
