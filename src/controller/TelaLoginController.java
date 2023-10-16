package controller;

import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;
import view.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import utils.StageController;

import java.io.IOException;

public class TelaLoginController{

    @FXML
    private Button botaoFazerLogin;

    @FXML
    private TextField digitaID;

    @FXML
    private TextField digitaSenha;

    @FXML
    private Label mensagemDeErro;

    @FXML
    private ComboBox<TipoUsuario> selecionaTipo;

    @FXML
    void initialize(){
        carregarTipos();
    }

    @FXML
    void confirmaLogin(ActionEvent event) throws IOException{
        if(this.selecionaTipo.getSelectionModel().getSelectedItem()==null)
            this.mensagemDeErro.setText("Selecione um tipo de usuário");

        else{
            if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Convidado){
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TelaInicial.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = StageController.getStage(event);
                stage.setScene(scene);
                stage.setTitle("Sistema de Gerenciamento de Biblioteca");
                stage.setResizable(false);
                stage.centerOnScreen();

                TelaInicialController controller = loader.getController();
                controller.setConvidado(Sistema.loginConvidado());
            }

            else{
                if(this.digitaID.getText().isEmpty() || this.digitaSenha.getText().isEmpty())
                    this.mensagemDeErro.setText("ID e Senha não podem ser vazios");

                else if(!tryParseInt(this.digitaID.getText()))
                    this.mensagemDeErro.setText("ID é composto apenas por números");

                else{
                    if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Administrador){
                        try{
                            Adm adm = Sistema.checarLoginADM(Integer.parseInt(this.digitaID.getText()),this.digitaSenha.getText());

                            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TelaInicial.fxml"));
                            Scene scene = new Scene(loader.load());
                            Stage stage = StageController.getStage(event);
                            stage.setScene(scene);
                            stage.setTitle("Sistema de Gerenciamento de Biblioteca");
                            stage.setResizable(false);
                            stage.centerOnScreen();

                            TelaInicialController controller = loader.getController();
                            controller.setAdm(adm);
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            this.mensagemDeErro.setText(e.getMessage());
                        }
                    }

                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Bibliotecario){
                        try{
                            Bibliotecario bibliotecario = Sistema.checarLoginBibliotecario(Integer.parseInt(this.digitaID.getText()),this.digitaSenha.getText());

                            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TelaInicial.fxml"));
                            Scene scene = new Scene(loader.load());
                            Stage stage = StageController.getStage(event);
                            stage.setScene(scene);
                            stage.setTitle("Sistema de Gerenciamento de Biblioteca");
                            stage.setResizable(false);
                            stage.centerOnScreen();

                            TelaInicialController controller = loader.getController();
                            controller.setBibliotecario(bibliotecario);
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            this.mensagemDeErro.setText(e.getMessage());
                        }
                    }

                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Leitor){
                        try{
                            Leitor leitor = Sistema.checarLoginLeitor(Integer.parseInt(this.digitaID.getText()),this.digitaSenha.getText());

                            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TelaInicial.fxml"));
                            Scene scene = new Scene(loader.load());
                            Stage stage = StageController.getStage(event);
                            stage.setScene(scene);
                            stage.setTitle("Sistema de Gerenciamento de Biblioteca");
                            stage.setResizable(false);
                            stage.centerOnScreen();

                            TelaInicialController controller = loader.getController();
                            controller.setLeitor(leitor);
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            this.mensagemDeErro.setText(e.getMessage());
                        }
                    }
                }
            }
        }
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

    public void carregarTipos(){
        ObservableList<TipoUsuario> tiposUsuarios = FXCollections.observableArrayList(TipoUsuario.Administrador,TipoUsuario.Bibliotecario,TipoUsuario.Leitor,TipoUsuario.Convidado);
        this.selecionaTipo.setItems(tiposUsuarios);
    }
}
