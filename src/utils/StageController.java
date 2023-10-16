package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainApplication;

import java.io.IOException;

public class StageController {

    public static FXMLLoader criaStage(Stage stage,String nomeTela) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(nomeTela));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Gerenciamento de Biblioteca");
        stage.centerOnScreen();
        stage.show();
        return loader;
    }

    public static Stage getStage(ActionEvent event){
        Node source = (Node)  event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
