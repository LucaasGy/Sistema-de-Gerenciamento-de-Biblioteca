package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.MainApplication;

import java.io.IOException;

/**
 * Classe responsável pela manipulação dos controllers do sistema.
 * Ela contém diferentes métodos que auxiliam na otimização do sistema.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class StageController {

    /**
     * Método responsável por setar as configurações padrão de um stage
     * do sistema.
     *
     * @param stage stage a ser setado
     * @param loader objeto FXMLLoader de algum arquivo view
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    public static void criaStage(Stage stage, FXMLLoader loader) throws IOException {
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sistema de Gerenciamento de Biblioteca");
        stage.centerOnScreen();
    }

    /**
     * Método responsável por retornar o stage atual ao realizar determinada ação
     * em um controller.
     *
     * Ex: Ao clicar no botão de fazer login, é necessário saber qual stage atual ao realizar
     * essa ação, para assim, setar uma nova scene (tela inicial), naquele mesmo stage.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @return retorna stage atual
     */

    public static Stage getStage(ActionEvent event){
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }

    /**
     * Método responsável por retornar objeto FXMLLoader a depender do arquivo do view.
     *
     * @param nomeTela arquivo do view
     * @return retorna objeto FXMLLoader
     */

    public static FXMLLoader retornaLoader(String nomeTela){
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(nomeTela));
        return loader;
    }

    /**
     * Método responsável por criar um Alert no controller.
     *
     * @param tipo tipo do Alert
     * @param titulo titulo do Alert
     * @param cabeca cabeça do Alert
     * @param conteudo conteúdo do Alert
     */

    public static void criaAlert(Alert.AlertType tipo, String titulo, String cabeca, String conteudo){
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabeca);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }

    /**
     * Método para verificar se é um número inteiro.
     *
     * Este método é utilizado para verificar se o usuário digitou
     * apenas números inteiros em alguma entrada de dado destinada
     * a somente números inteiros.
     *
     * @param numero conteúdo digitado pelo usuário
     * @return retorna true ou false
     */

    public static boolean tryParseInt(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Método para verificar se é um número fracionado.
     *
     * Este método é utilizado para verificar se o usuário digitou
     * apenas números fracionados em alguma entrada de dado destinada
     * a somente números fracionados.
     *
     * @param numero conteúdo digitado pelo usuário
     * @return retorna true ou false
     */

    public static boolean tryParseDouble(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Método para verificar se é um número do tipo long.
     *
     * Este método é utilizado para verificar se o usuário digitou
     * apenas números long em alguma entrada de dado destinada
     * a somente números long.
     *
     * @param numero conteúdo digitado pelo usuário
     * @return retorna true ou false
     */

    public static boolean tryParseLong(String numero) {
        try {
            Long.parseLong(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Método responsável por setar o texto de um label que
     * representa alguma operação que apresentou erro.
     *
     * @param label label a ser setado
     * @param msg mensagem a ser setada no label
     * @return retorna label atualizado
     */

    public static Label error(Label label, String msg){
        label.setText(msg);
        label.setStyle("-fx-text-fill: red;");
        return label;
    }

    /**
     * Método responsável por setar o texto de um label que
     * representa alguma operação que apresentou sucesso.
     *
     * @param label label a ser setado
     * @param msg mensagem a ser setada no label
     * @return retorna label atualizado
     */

    public static Label sucesso(Label label, String msg){
        label.setText(msg);
        label.setStyle("-fx-text-fill: green;");
        return label;
    }
}