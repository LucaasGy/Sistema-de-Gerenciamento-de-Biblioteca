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
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaEditarExcluirAdministrador" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaEditarExcluirAdministradorController {

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
    private TableColumn<Adm, Integer> colunaID;

    @FXML
    private TableColumn<Adm, String> colunaNome;

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
    private TableView<Adm> tabelaAdm;

    private Adm admLogado;

    private int idAdmAAlterar;

    private String nomeAdmAAlterar;

    private String qualTabelaCarregar;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaAdm.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarAdmTabela(newValue));
    }

    /**
     * Ação de clicar no botão de atualizar administrador.
     */

    @FXML
    void atualizarAdm() {
        Adm admSelecionado = this.tabelaAdm.getSelectionModel().getSelectedItem();

        if(admSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar administrador", "Escolha um administrador antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);

                Adm admAtualizado = new Adm(this.digitaNome.getText(),this.digitaSenha.getText());
                admAtualizado.setID(admSelecionado.getID());
                Adm.atualizarDadosAdministrador(admAtualizado);

                //caso o administrador altere a ele mesmo (adm logado), é informado em um alert que relogue para
                //salvar as mudanças na tela inicial
                if(admAtualizado.getID()==this.admLogado.getID()) {
                    StageController.criaAlert(Alert.AlertType.INFORMATION, "Observação", "Administrador logado atualizado", "Para salvar as alterações, saia e entre na conta novamente!");
                    this.admLogado = admAtualizado;
                }

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idAdmAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeAdmAAlterar);

                StageController.sucesso(this.mensagemErro,"ADMINISTRADOR ATUALIZADO");
            }catch (ObjetoInvalido e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de remover administrador.
     */

    @FXML
    void removerAdm() {
        Adm admSelecionado = this.tabelaAdm.getSelectionModel().getSelectedItem();

        if(admSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover administrador", "Escolha um administrador antes de remover");

        //caso o administrador tente remover a ele mesmo (adm logado), é informado em um alert que não é possível
        else if(admSelecionado.getID()==this.admLogado.getID())
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover administrador", "Não é possível remover administrador logado");

        else{
            Adm.removerAdm(admSelecionado.getID());

            //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
            //"qualTabelaCarregar".
            if(this.qualTabelaCarregar.equals("ID"))
                carregaTabelaID(this.idAdmAAlterar);

            else if(this.qualTabelaCarregar.equals("Nome"))
                carregaTabelaNome(this.nomeAdmAAlterar);

            StageController.sucesso(this.mensagemErro,"ADMINISTRADOR REMOVIDO");
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
     * Administrador atualizado também é setado.
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
        controller.setQualOperacao("Administrador");
        controller.setTitulo("Procurar administrador por :");
        controller.setAdm(this.admLogado);
    }

    /**
     * Método responsável por carregar o TableView com o administrador encontrado
     * com o id do administrador recebido do controller da tela procurar usuário.
     *
     * @param id id do administrador recebido
     */

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Adm> listaAdm = FXCollections.observableArrayList(DAO.getAdm().encontrarPorId(id));
        //caso ao recarregar o TableView, encontre o id do administrador em questão
        if(listaAdm.get(0)!=null)
            this.tabelaAdm.setItems(listaAdm);

        /*caso ao recarregar o TableView, não seja encontrado o administrador com o id em questão (foi removido),
        os botões de atualizar e remover administrador são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum administrador a ser atualizado.*/
        else {
            this.tabelaAdm.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
        }
    }

    /**
     * Método responsável por carregar o TableView com os administradores encontrados
     * com o nome do administrador recebido do controller da tela procurar usuário.
     *
     * @param nome nome do administrador recebido
     */

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Adm> listaAdm = FXCollections.observableArrayList(DAO.getAdm().encontrarPorNome(nome));
        //caso ao recarregar o TableView, encontre o nome do administrador em questão
        if(!listaAdm.isEmpty())
            this.tabelaAdm.setItems(listaAdm);

         /*caso ao recarregar o TableView, não seja encontrado o administrador com o nome em questão (foi removido),
        os botões de atualizar e remover administrador são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum administrador a ser atualizado.*/
        else {
            this.tabelaAdm.getItems().clear();
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
     * Método responsável por setar nos label da tela as informações do administrador escolhido no TableView.
     *
     * @param adm administrador escolhido no TableView
     */

    public void selecionarAdmTabela(Adm adm){
        this.mensagemErro.setText("");

        if(adm!=null) {
            this.digitaID.setText(Integer.toString(adm.getID()));
            this.digitaNome.setText(adm.getNome());
            this.digitaSenha.setText(adm.getSenha());
            this.digitaTipo.setText(adm.getTipoUsuario().name());
        }

        else{
            this.digitaID.setText("");
            this.digitaNome.setText("");
            this.digitaSenha.setText("");
            this.digitaTipo.setText("");
        }
    }

    /**
     * Método responsável por setar qual método chamar após remover ou alterar um administrador.
     *
     * Como o TableView pode conter os administradores encontrados por nome ou id, esse atributo
     * é necessário para verificar qual operação realizar.
     *
     * @param qualTabelaCarregar atributo para decidir qual tabela recarregar
     */

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    /**
     * Método responsável por setar Administrador logado na tela inicial.
     *
     * @param admLogado administrador logado
     */

    public void setAdmLogado(Adm admLogado) {
        this.admLogado = admLogado;
    }

    /**
     * Método responsável por setar qual id de administrador o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param idAdmAAlterar id do administrador recebido
     */

    public void setIdAdmAAlterar(int idAdmAAlterar) {
        this.idAdmAAlterar = idAdmAAlterar;
    }

    /**
     * Método responsável por setar qual nome de administrador o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param nomeAdmAAlterar nome do administrador recebido
     */

    public void setNomeAdmAAlterar(String nomeAdmAAlterar) {
        this.nomeAdmAAlterar = nomeAdmAAlterar;
    }
}