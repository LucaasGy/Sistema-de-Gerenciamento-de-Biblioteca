package controller.editarExcluirObjeto;

import controller.procurarObjeto.TelaProcurarUsuarioController;
import dao.DAO;
import erros.leitor.LeitorTemEmprestimo;
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
import model.Leitor;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaEditarExcluirLeitor" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaEditarExcluirLeitorController {

    @FXML
    private Label alertaEndereco;

    @FXML
    private Label alertaNome;

    @FXML
    private Label alertaSenha;

    @FXML
    private Label alertaTelefone;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Leitor, Integer> colunaID;

    @FXML
    private TableColumn<Leitor, String> colunaNome;

    @FXML
    private TextField digitaEndereco;

    @FXML
    private TextField digitaID;

    @FXML
    private TextField digitaNome;

    @FXML
    private TextField digitaSenha;

    @FXML
    private TextField digitaTelefone;

    @FXML
    private TextField digitaTipo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Leitor> tabelaLeitor;

    private int idLeitorAAlterar;

    private String nomeLeitorAAlterar;

    private String qualTabelaCarregar;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.digitaID.setStyle("-fx-text-fill: gray;");
        this.digitaTipo.setStyle("-fx-text-fill: gray;");
        this.tabelaLeitor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLeitorTabela(newValue));
    }

    /**
     * Ação de clicar no botão de atualizar leitor.
     */

    @FXML
    void atualizarLeitor() {
        Leitor leitorSelecionado = this.tabelaLeitor.getSelectionModel().getSelectedItem();

        if(leitorSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar leitor", "Escolha um leitor antes de atualizar");

        else if(this.digitaNome.getText().isEmpty() || this.digitaSenha.getText().isEmpty() || this.digitaEndereco.getText().isEmpty() || this.digitaTelefone.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaNome.setVisible(this.digitaNome.getText().isEmpty());
            this.alertaSenha.setVisible(this.digitaSenha.getText().isEmpty());
            this.alertaEndereco.setVisible(this.digitaEndereco.getText().isEmpty());
            this.alertaTelefone.setVisible(this.digitaTelefone.getText().isEmpty());
        }

        else if (!StageController.tryParseLong(this.digitaTelefone.getText())) {
            this.mensagemErro.setText("TELEFONE É COMPOSTO APENAS POR NÚMEROS");
            this.alertaTelefone.setVisible(true);
            this.alertaNome.setVisible(false);
            this.alertaSenha.setVisible(false);
            this.alertaEndereco.setVisible(false);
        }

        else{
            try {
                this.alertaNome.setVisible(false);
                this.alertaSenha.setVisible(false);
                this.alertaEndereco.setVisible(false);
                this.alertaTelefone.setVisible(false);

                Leitor leitorAtualizado = new Leitor(this.digitaNome.getText(),this.digitaEndereco.getText(),this.digitaTelefone.getText(),this.digitaSenha.getText());
                leitorAtualizado.setID(leitorSelecionado.getID());
                Adm.atualizarDadosLeitor(leitorAtualizado);

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if(this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idLeitorAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeLeitorAAlterar);

                StageController.sucesso(this.mensagemErro,"LEITOR ATUALIZADO");
            }catch (ObjetoInvalido e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de clicar no botão de remover leitor.
     */

    @FXML
    void removerLeitor() {
        Leitor leitorSelecionado = this.tabelaLeitor.getSelectionModel().getSelectedItem();

        if(leitorSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover leitor", "Escolha um leitor antes de remover");

        else{
            try {
                Adm.removerLeitor(leitorSelecionado.getID());

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if (this.qualTabelaCarregar.equals("ID"))
                    carregaTabelaID(this.idLeitorAAlterar);

                else if (this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaNome(this.nomeLeitorAAlterar);

                StageController.sucesso(this.mensagemErro,"LEITOR REMOVIDO");
            }catch (LeitorTemEmprestimo e){
                StageController.error(this.mensagemErro,e.getMessage());
            }
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
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
        TelaProcurarUsuarioController controller = loader.getController();
        controller.setQualOperacao("Leitor");
        controller.setTitulo("Procurar leitor por :");
    }

    /**
     * Método responsável por carregar o TableView com o leitor encontrado
     * com o id do leitor recebido do controller da tela procurar usuário.
     *
     * @param id id do leitor recebido
     */

    public void carregaTabelaID(int id){
        carregaColunas();
        ObservableList<Leitor> listaLeitor = FXCollections.observableArrayList(DAO.getLeitor().encontrarPorId(id));
        //caso ao recarregar o TableView, encontre o id do leitor em questão
        if(listaLeitor.get(0)!=null)
            this.tabelaLeitor.setItems(listaLeitor);

        /*caso ao recarregar o TableView, não seja encontrado o leitor com o id em questão (foi removido),
        os botões de atualizar e remover leitor são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum leitor a ser atualizado.*/
        else {
            this.tabelaLeitor.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
            this.digitaEndereco.setEditable(false);
            this.digitaTelefone.setEditable(false);
        }
    }

    /**
     * Método responsável por carregar o TableView com os leitores encontrados
     * com o nome do leitor recebido do controller da tela procurar usuário.
     *
     * @param nome nome do leitor recebido
     */

    public void carregaTabelaNome(String nome){
        carregaColunas();
        ObservableList<Leitor> listaLeitor = FXCollections.observableArrayList(DAO.getLeitor().encontrarPorNome(nome));
        //caso ao recarregar o TableView, encontre o nome do leitor em questão
        if(!listaLeitor.isEmpty())
            this.tabelaLeitor.setItems(listaLeitor);

        /*caso ao recarregar o TableView, não seja encontrado o leitor com o nome em questão (foi removido),
        os botões de atualizar e remover leitor são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum leitor a ser atualizado.*/
        else {
            this.tabelaLeitor.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaNome.setEditable(false);
            this.digitaSenha.setEditable(false);
            this.digitaEndereco.setEditable(false);
            this.digitaTelefone.setEditable(false);
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
     * Método responsável por setar nos label da tela as informações do leitor escolhido no TableView.
     *
     * @param leitor leitor escolhido no TableView
     */

    public void selecionarLeitorTabela(Leitor leitor){
        this.mensagemErro.setText("");

        if(leitor!=null) {
            this.digitaID.setText(Integer.toString(leitor.getID()));
            this.digitaNome.setText(leitor.getNome());
            this.digitaSenha.setText(leitor.getSenha());
            this.digitaEndereco.setText(leitor.getEndereco());
            this.digitaTelefone.setText(leitor.getTelefone());
            this.digitaTipo.setText(leitor.getTipoUsuario().name());
        }

        else{
            this.digitaID.setText("");
            this.digitaNome.setText("");
            this.digitaSenha.setText("");
            this.digitaTipo.setText("");
            this.digitaEndereco.setText("");
            this.digitaTelefone.setText("");
        }
    }

    /**
     * Método responsável por setar qual método chamar após remover ou alterar um leitor.
     *
     * Como o TableView pode conter os leitores encontrados por nome ou id, esse atributo
     * é necessário para verificar qual operação realizar.
     *
     * @param qualTabelaCarregar atributo para decidir qual tabela recarregar
     */

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    /**
     * Método responsável por setar qual id de leitor o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param idLeitorAAlterar id do leitor recebido
     */

    public void setIdLeitorAAlterar(int idLeitorAAlterar) {
        this.idLeitorAAlterar = idLeitorAAlterar;
    }

    /**
     * Método responsável por setar qual nome de leitor o usuário digitou no controller da tela
     * "TelaProcurarUsuario".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param nomeLeitorAAlterar nome do leitor recebido
     */

    public void setNomeLeitorAAlterar(String nomeLeitorAAlterar) {
        this.nomeLeitorAAlterar = nomeLeitorAAlterar;
    }
}