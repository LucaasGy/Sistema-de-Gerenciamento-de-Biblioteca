package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public static void criaAlert(Alert.AlertType tipo, String titulo, String cabeca, String conteudo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabeca);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    //Método para verificar se é um número inteiro
    public static boolean tryParseInt(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Método para verificar se é um número double
    public static boolean tryParseDouble(String isbn) {
        try {
            Double.parseDouble(isbn);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
