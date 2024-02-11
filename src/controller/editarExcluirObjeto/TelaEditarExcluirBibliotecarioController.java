package controller.editarExcluirObjeto;

import controller.procurarObjeto.TelaProcurarUsuarioController;
import dao.DAO;
import erros.objetos.ObjetoInvalido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adm;
import model.Bibliotecario;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaEditarExcluirBibliotecario" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaEditarExcluirBibliotecarioController {

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Bibliotecario, Integer> colunaID;

    @FXML
    private TableColumn<Bibliotecario, Integer> colunaNome;

    @FXML
    private TextField digitaID;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private TextField digitaTipo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Bibliotecario> tabelaBibliotecario;

    private int idBibliotecarioAAlterar;

    private String nomeBibliotecarioAAlterar;

    private String qualTabelaCarregar;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaBibliotecario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarBibliotecarioTabela(newValue));
    }

    /**
     * Ação de clicar no botão de atualizar bibliotecario.
     */

    @FXML
    void atualizarBibliotecario() {
        Bibliotecario bibliotecarioSelecionado = this.tabelaBibliotecario.getSelectionModel().getSelectedItem();

        if(bibliotecarioSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar bibliotecario", "Escolha um bibliotecario antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);

                Bibliotecario bibliotecarioAtualizado = new Bibliotecario(this.digitaNome.getText(),this.digitaSenha.getText());
                bibliotecarioAtualizado.setID(bibliotecarioSelecionado.getID());
                Adm.atualizarDadosBibliotecario(bibliotecarioAtualizado);

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idBibliotecarioAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeBibliotecarioAAlterar);

                StageController.sucesso(this.mensagemErro,"BIBLIOTECARIO ATUALIZADO");
            }catch (ObjetoInvalido e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de remover bibliotecario.
     */

    @FXML
    void removerBibliotecario() {
        Bibliotecario bibliotecarioSelecionado = this.tabelaBibliotecario.getSelectionModel().getSelectedItem();

        if(bibliotecarioSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover bibliotecario", "Escolha um bibliotecario antes de remover");

        else{
            Adm.removerBibliotecario(bibliotecarioSelecionado.getID());

            //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
            //"qualTabelaCarregar".
            if(this.qualTabelaCarregar.equals("ID"))
                carregaTabelaID(this.idBibliotecarioAAlterar);

            else if(this.qualTabelaCarregar.equals("Nome"))
                carregaTabelaNome(this.nomeBibliotecarioAAlterar);

            StageController.sucesso(this.mensagemErro,"BIBLIOTECARIO REMOVIDO");
        }
    }

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void menu(ActionEvent event) {
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Ação de clicar no botão de voltar.
     *
     * Stage atual é redefinido para a tela anterior "TelaProcurarUsuario".
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("/view/TelaProcurarUsuario.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
        TelaProcurarUsuarioController controller = loader.getController();
        controller.setQualOperacao("Bibliotecario");
        controller.setTitulo("Procurar bibliotecario por :");
    }

    /**
     * Método responsável por carregar o TableView com o bibliotecario encontrado
     * com o id do bibliotecario recebido do controller da tela procurar usuário.
     *
     * @param id id do bibliotecario recebido
     */

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Bibliotecario> listaBibliotecario = FXCollections.observableArrayList(DAO.getBibliotecario().encontrarPorId(id));
        //caso ao recarregar o TableView, encontre o id do bibliotecario em questão
        if(listaBibliotecario.get(0)!=null)
            this.tabelaBibliotecario.setItems(listaBibliotecario);

        /*caso ao recarregar o TableView, não seja encontrado o bibliotecario com o id em questão (foi removido),
        os botões de atualizar e remover bibliotecario são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum bibliotecario a ser atualizado.*/
        else {
            this.tabelaBibliotecario.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    /**
     * Método responsável por carregar o TableView com os bibliotecarios encontrados
     * com o nome do bibliotecario recebido do controller da tela procurar usuário.
     *
     * @param nome nome do bibliotecario recebido
     */

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Bibliotecario> listaBibliotecario = FXCollections.observableArrayList(DAO.getBibliotecario().encontrarPorNome(nome));
        //caso ao recarregar o TableView, encontre o nome do bibliotecario em questão
        if(!listaBibliotecario.isEmpty())
            this.tabelaBibliotecario.setItems(listaBibliotecario);

        /*caso ao recarregar o TableView, não seja encontrado o bibliotecario com o nome em questão (foi removido),
        os botões de atualizar e remover bibliotecario são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum bibliotecario a ser atualizado.*/
        else {
            this.tabelaBibliotecario.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    /**
     * Método responsável por setar qual dado será inserido em cada coluna do TableView.
     */

    public void carregaColunas(){
        this.colunaID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        this.colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    /**
     * Método responsável por setar nos label da tela as informações do bibliotecario escolhido no TableView.
     *
     * @param bibliotecario bibliotecario escolhido no TableView
     */

    public void selecionarBibliotecarioTabela(Bibliotecario bibliotecario){
        this.mensagemErro.setText("");

        if(bibliotecario!=null) {
            this.digitaID.setText(Integer.toString(bibliotecario.getID()));
            this.digitaNome.setText(bibliotecario.getNome());
            this.digitaSenha.setText(bibliotecario.getSenha());
            this.digitaTipo.setText(bibliotecario.getTipoUsuario().name());
        }

        else{
            this.digitaID.setText("");
            this.digitaNome.setText("");
            this.digitaSenha.setText("");
            this.digitaTipo.setText("");
        }
    }

    /**
     * Método responsável por setar qual método chamar após remover ou alterar um bibliotecario.
     *
     * Como o TableView pode conter os bibliotecarios encontrados por nome ou id, esse atributo
     * é necessário para verificar qual operação realizar.
     *
     * @param qualTabelaCarregar atributo para decidir qual tabela recarregar
     */

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    /**
     * Método responsável por setar qual id de bibliotecario o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param idBibliotecarioAAlterar id do bibliotecario recebido
     */

    public void setIdBibliotecarioAAlterar(int idBibliotecarioAAlterar) {
        this.idBibliotecarioAAlterar = idBibliotecarioAAlterar;
    }

    /**
     * Método responsável por setar qual nome de bibliotecario o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param nomeBibliotecarioAAlterar nome do bibliotecario recebido
     */

    public void setNomeBibliotecarioAAlterar(String nomeBibliotecarioAAlterar) {
        this.nomeBibliotecarioAAlterar = nomeBibliotecarioAAlterar;
    }
}