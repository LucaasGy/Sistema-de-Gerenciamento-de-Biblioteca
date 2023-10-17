package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainApplication;

import java.io.IOException;

public class StageController {

    public static void criaStage(Stage stage, FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Gerenciamento de Biblioteca");
        stage.centerOnScreen();
    }

    public static Stage getStage(ActionEvent event){
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }

    public static FXMLLoader retornaLoader(String nomeTela){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(nomeTela));
        return loader;
    }
}
