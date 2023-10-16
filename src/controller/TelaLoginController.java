package controller;

import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private PasswordField digitaSenha;

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
                TelaInicialController controller = this.retornaControlador(event);
                controller.setConvidado(Sistema.loginConvidado());
                controller.telaLeitorEConvidado();
            }

            else{
                if(this.digitaID.getText().isEmpty() || this.digitaSenha.getText().isEmpty())
                    this.mensagemDeErro.setText("ID e Senha não podem ser vazios");

                else if(!tryParseInt(this.digitaID.getText()))
                    this.mensagemDeErro.setText("ID é composto apenas por números");

                else{
                    if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Administrador){
                        try{
                            Adm adm = Sistema.checarLoginADM(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());
                            TelaInicialController controller = this.retornaControlador(event);
                            controller.setAdm(adm);
                            controller.listaFuncionalidadesAdm();

                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            this.mensagemDeErro.setText(e.getMessage());
                        }
                    }

                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Bibliotecario){
                        try{
                            Bibliotecario bibliotecario = Sistema.checarLoginBibliotecario(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());
                            TelaInicialController controller = this.retornaControlador(event);
                            controller.setBibliotecario(bibliotecario);
                            controller.telaBibliotecario();

                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            this.mensagemDeErro.setText(e.getMessage());
                        }
                    }

                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Leitor){
                        try{
                            Leitor leitor = Sistema.checarLoginLeitor(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());
                            TelaInicialController controller = this.retornaControlador(event);
                            controller.setLeitor(leitor);
                            controller.telaLeitorEConvidado();
                            controller.listaFuncionalidadesLeitor();

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

    public TelaInicialController retornaControlador(ActionEvent event) throws IOException {
        Stage stage = StageController.getStage(event);

        TelaInicialController controller = StageController.criaStage(stage,"TelaInicial.fxml").getController();

        return controller;
    }

    public void carregarTipos(){
        ObservableList<TipoUsuario> tiposUsuarios = FXCollections.observableArrayList(TipoUsuario.Administrador,TipoUsuario.Bibliotecario,TipoUsuario.Leitor,TipoUsuario.Convidado);
        this.selecionaTipo.setItems(tiposUsuarios);
    }
}