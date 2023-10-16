package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();

        StageController.criaStage(stage,"TelaLogin.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
