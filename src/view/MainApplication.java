package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Sistema;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TelaLogin.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Gerenciamento de Biblioteca");
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
