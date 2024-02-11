package controller.procurarObjeto;

import controller.editarExcluirObjeto.TelaEditarExcluirAdministradorController;
import controller.editarExcluirObjeto.TelaEditarExcluirBibliotecarioController;
import controller.editarExcluirObjeto.TelaEditarExcluirLeitorController;
import dao.DAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Adm;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaProcurarUsuario" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaProcurarUsuarioController {

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField digitaEscolha;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;

    @FXML
    private Label mensagemTitulo;

    private Adm adm;

    private String qualOperacao;

    /**
     * Ação de clicar no botão de confirmar usuário a ser procurado.
     *
     * Como esta tela é usada tanto para encontrar Administradores, Bibliotecarios
     * ou Leitores, a depender do valor setado no atributo "qualOperacao",
     * o botão desempenhará diferentes funcionalidades.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void confirmarEscolha(ActionEvent event) throws IOException {
        String dado = digitaEscolha.getText();

        if(dado.isEmpty()) {
            if(this.qualOperacao.equals("Administrador"))
                StageController.error(this.mensagemErro,"INSIRA DADO DO ADMINISTRADOR");

            else if(this.qualOperacao.equals("Bibliotecario"))
                StageController.error(this.mensagemErro,"INSIRA DADO DO BIBLIOTECARIO");

            else if(this.qualOperacao.equals("Leitor"))
                StageController.error(this.mensagemErro,"INSIRA DADO DO LEITOR");
        }

        else{
            RadioButton radio = (RadioButton) grupo.getSelectedToggle();
            String escolha = radio.getText();

            if (escolha.equals("ID") && !StageController.tryParseInt(dado))
                StageController.error(this.mensagemErro,"ID É COMPOSTO APENAS POR NÚMEROS");

            else{
                if(this.qualOperacao.equals("Administrador")){
                    if(escolha.equals("ID")){
                        if(DAO.getAdm().encontrarPorId(Integer.parseInt(dado))==null)
                            StageController.error(this.mensagemErro,"ADMINISTRADOR NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirAdministrador.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirAdministradorController controller = loader.getController();

                            //seta o administrador logado para impedir que ele possa se excluir ou caso ele altere
                            //a ele mesmo, informe a mudança
                            controller.setAdmLogado(this.adm);
                            //seta no controller da tela "TelaEditarExcluirAdministradorController", o ID do administrador encontrado
                            controller.setIdAdmAAlterar(Integer.parseInt(dado));
                            //carrega o TableView do controller da tela "TelaEditarExcluirAdministradorController"
                            //com o administrador encontrado por ID
                            controller.carregaTabelaID(Integer.parseInt(dado));
                            //seta qual tabela recarregar após remoção/alteração de administradores
                            controller.setQualTabelaCarregar("ID");
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getAdm().encontrarPorNome(dado).isEmpty())
                            StageController.error(this.mensagemErro,"ADMINISTRADOR NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirAdministrador.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirAdministradorController controller = loader.getController();

                            //seta o administrador logado para impedir que ele possa se excluir ou caso ele altere
                            //a ele mesmo, informe a mudança
                            controller.setAdmLogado(this.adm);
                            //seta  no controller da tela "TelaEditarExcluirAdministradorController", o nome dos administradores encontrados
                            controller.setNomeAdmAAlterar(dado);
                            //carrega o TableView do controller da tela "TelaEditarExcluirAdministradorController"
                            //com os administradores encontrados por nome
                            controller.carregaTabelaNome(dado);
                            //seta qual tabela recarregar após remoção/alteração de administradores
                            controller.setQualTabelaCarregar("Nome");
                        }
                    }
                }

                else if(this.qualOperacao.equals("Bibliotecario")){
                    if(escolha.equals("ID")){
                        if(DAO.getBibliotecario().encontrarPorId(Integer.parseInt(dado))==null)
                            StageController.error(this.mensagemErro,"BIBLIOTECARIO NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirBibliotecario.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirBibliotecarioController controller = loader.getController();

                            //seta no controller da tela "TelaEditarExcluirBibliotecarioController", o ID do bibliotecario encontrado
                            controller.setIdBibliotecarioAAlterar(Integer.parseInt(dado));
                            //carrega o TableView do controller da tela "TelaEditarExcluirBibliotecarioController"
                            //com o bibliotecario encontrado por ID
                            controller.carregaTabelaID(Integer.parseInt(dado));
                            //seta qual tabela recarregar após remoção/alteração de bibliotecarios
                            controller.setQualTabelaCarregar("ID");
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getBibliotecario().encontrarPorNome(dado).isEmpty())
                            StageController.error(this.mensagemErro,"BIBLIOTECARIO NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirBibliotecario.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirBibliotecarioController controller = loader.getController();

                            //seta no controller da tela "TelaEditarExcluirBibliotecarioController", o nome dos bibliotecarios encontrados
                            controller.setNomeBibliotecarioAAlterar(dado);
                            //carrega o TableView do controller da tela "TelaEditarExcluirBibliotecarioController"
                            //com os bibliotecarios encontrados por nome
                            controller.carregaTabelaNome(dado);
                            //seta qual tabela recarregar após remoção/alteração de bibliotecarios
                            controller.setQualTabelaCarregar("Nome");
                        }
                    }
                }

                else if(this.qualOperacao.equals("Leitor")){
                    if(escolha.equals("ID")){
                        if(DAO.getLeitor().encontrarPorId(Integer.parseInt(dado))==null)
                            StageController.error(this.mensagemErro,"LEITOR NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirLeitor.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirLeitorController controller = loader.getController();

                            //seta no controller da tela "TelaEditarExcluirLeitorController", o ID do leitor encontrado
                            controller.setIdLeitorAAlterar(Integer.parseInt(dado));
                            //carrega o TableView do controller da tela "TelaEditarExcluirLeitorController"
                            //com o leitor encontrado por ID
                            controller.carregaTabelaID(Integer.parseInt(dado));
                            //seta qual tabela recarregar após remoção/alteração de leitores
                            controller.setQualTabelaCarregar("ID");
                        }
                    }

                    else if(escolha.equals("Nome")){
                        if(DAO.getBibliotecario().encontrarPorNome(dado).isEmpty())
                            StageController.error(this.mensagemErro,"LEITOR NÃO ENCONTRADO");

                        else{
                            FXMLLoader loader = StageController.retornaLoader("/view/TelaEditarExcluirLeitor.fxml");
                            StageController.criaStage(StageController.getStage(event), loader);
                            TelaEditarExcluirLeitorController controller = loader.getController();

                            //seta no controller da tela "TelaEditarExcluirLeitorController", o nome dos leitores encontrados
                            controller.setNomeLeitorAAlterar(dado);
                            //carrega o TableView do controller da tela "TelaEditarExcluirLeitorController"
                            //com os leitores encontrados por nome
                            controller.carregaTabelaNome(dado);
                            //seta qual tabela recarregar após remoção/alteração de leitores
                            controller.setQualTabelaCarregar("Nome");
                        }
                    }
                }
            }
        }
    }

    /**
     * Ação de clicar no botão de voltar.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltar(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Método responsável por setar Administrador logado na tela inicial.
     *
     * @param adm administrador logado
     */

    public void setAdm(Adm adm) {
        this.adm = adm;
    }

    /**
     * Método responsável por setar qual funcionalidade o botão confirmar irá realizar.
     *
     * @param qualOperacao operação setada
     */

    public void setQualOperacao(String qualOperacao) {
        this.qualOperacao = qualOperacao;
    }

    /**
     * Método responsável por setar o título da tela a depender da escolha do usuário na tela
     * inicial.
     *
     * @param titulo titulo da tela
     */

    public void setTitulo(String titulo){
        this.mensagemTitulo.setText(titulo);
    }
}