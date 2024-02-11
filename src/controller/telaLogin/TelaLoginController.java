package controller.telaLogin;

import controller.telaInicial.TelaInicialController;
import erros.objetos.ObjetoInvalido;
import erros.objetos.UsuarioSenhaIncorreta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import model.*;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaLogin" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

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

    /**
     * Inicializa o ComboBox com os tipos de usuários do sistema.
     */

    @FXML
    void initialize(){
        carregarTipos();
    }

    /**
     * Ação de clicar no botão de fazer login.
     *
     * A depender do tipo de usário escolhido, a tela inicial é modificada para
     * se adequar as funcionalidade permitidas a cada tipo.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void confirmaLogin(ActionEvent event) throws IOException{

        //caso não seja escolhido nenhum tipo de usuário, informa error
        if(this.selecionaTipo.getSelectionModel().getSelectedItem()==null)
            StageController.error(this.mensagemDeErro,"SELECIONE UM TIPO DE USUÁRIO");

        else{
            /*caso o tipo de usuário escolhido for Convidado, não é necessário verificar ID e Senha digitados,
            carregando a tela inicial com as funcionalidades permitidas a esse tipo e seta o objeto Convidado
            presente no controller da tela inicial.*/
            if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Convidado){
                FXMLLoader loader = StageController.retornaLoader("/view/TelaInicial.fxml");
                StageController.criaStage(StageController.getStage(event), loader);
                TelaInicialController controller = loader.getController();

                controller.setConvidado(Sistema.loginConvidado());
                controller.telaLeitorEConvidado();
            }

            else{
                //caso não seja digitado algum dado no campo de ID ou Senha, informa error
                if(this.digitaID.getText().isEmpty() || this.digitaSenha.getText().isEmpty())
                    StageController.error(this.mensagemDeErro,"PREENCHA OS CAMPOS");

                //caso dado digitado no campo ID não seja números, informa error
                else if(!StageController.tryParseInt(this.digitaID.getText()))
                    StageController.error(this.mensagemDeErro,"ID É COMPOSTO APENAS POR NÚMEROS");

                else{

                    /*caso o tipo de usuário escolhido for Administrador, carrega a tela inicial
                    com as funcionalidades permitidas a esse tipo e seta o objeto Administrador logado
                    presente no controller da tela inicial.*/
                    if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Administrador){
                        try{
                            Adm adm = Sistema.checarLoginADM(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());

                            FXMLLoader loader = StageController.retornaLoader("/view/TelaInicial.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaInicialController controller = loader.getController();

                            controller.setAdm(adm);
                            controller.listaFuncionalidadesAdm();
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            StageController.error(this.mensagemDeErro,e.getMessage());
                        }
                    }

                    /*caso o tipo de usuário escolhido for Bibliotecario, carrega a tela inicial
                    com as funcionalidades permitidas a esse tipo e seta o objeto Bibliotecario logado
                    presente no controller da tela inicial.*/
                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Bibliotecario){
                        try{
                            Bibliotecario bibliotecario = Sistema.checarLoginBibliotecario(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());

                            FXMLLoader loader = StageController.retornaLoader("/view/TelaInicial.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaInicialController controller = loader.getController();

                            controller.setBibliotecario(bibliotecario);
                            controller.telaBibliotecario();
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            StageController.error(this.mensagemDeErro,e.getMessage());
                        }
                    }

                    /*caso o tipo de usuário escolhido for Leitor, carrega a tela inicial
                    com as funcionalidades permitidas a esse tipo e seta o objeto Leitor logado
                    presente no controller da tela inicial.*/
                    else if(this.selecionaTipo.getSelectionModel().getSelectedItem()==TipoUsuario.Leitor){
                        try{
                            Leitor leitor = Sistema.checarLoginLeitor(Integer.parseInt(this.digitaID.getText()), this.digitaSenha.getText());

                            FXMLLoader loader = StageController.retornaLoader("/view/TelaInicial.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaInicialController controller = loader.getController();

                            controller.setLeitor(leitor);
                            controller.telaLeitorEConvidado();
                            controller.listaFuncionalidadesLeitor();
                        } catch (ObjetoInvalido | UsuarioSenhaIncorreta e){
                            StageController.error(this.mensagemDeErro,e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * Método responsável por carregar o ComboBox com os tipos de usuários do sistema.
     */

    public void carregarTipos(){
        ObservableList<TipoUsuario> tiposUsuarios = FXCollections.observableArrayList(TipoUsuario.Administrador,TipoUsuario.Bibliotecario,TipoUsuario.Leitor,TipoUsuario.Convidado);
        this.selecionaTipo.setItems(tiposUsuarios);
    }
}