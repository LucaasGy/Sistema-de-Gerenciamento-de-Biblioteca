package controller.telaInicial;

import controller.procurarObjeto.TelaDigiteIDController;
import controller.reservas.TelaMinhasReservasController;
import controller.pesquisaLivro.TelaPesquisaLivroController;
import controller.procurarObjeto.TelaProcurarUsuarioController;
import dao.DAO;
import erros.leitor.*;
import erros.livro.LivroReservado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.StageController;

import java.io.IOException;

/**
 * Controller  responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaInicial" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaInicialController {

    @FXML
    private Menu gerirAdministrador;

    @FXML
    private Menu gerirBibliotecario;

    @FXML
    private Menu gerirLeitor;

    @FXML
    private Menu gerirLivro;

    @FXML
    private MenuItem bloquearDesbloquearLeitor;

    @FXML
    private Button botaoConfirmar;

    @FXML
    private Button botaoSair;

    @FXML
    private MenuItem criarAdm;

    @FXML
    private MenuItem criarBibliotecario;

    @FXML
    private MenuItem criarLeitor;

    @FXML
    private MenuItem criarLivro;

    @FXML
    private MenuItem editarExcluirAdm;

    @FXML
    private MenuItem editarExcluirBibliotecario;

    @FXML
    private MenuItem editarExcluirLeitor;

    @FXML
    private MenuItem editarExcluirLivro;

    @FXML
    private ListView<String> listaFuncionalidades;

    @FXML
    private Label mensagemBemVindo;

    @FXML
    private MenuItem tirarMultaLeitor;
    private ObservableList<String> funcionalidades;
    private Adm adm;
    private Bibliotecario bibliotecario;
    private Leitor leitor;
    private Convidado convidado;

    /**
     * Inicializa o ListView com a funcionalidade "Pesquisar livros", permitida
     * a todo tipo de usuário do sistema.
     */

    @FXML
    void initialize(){
        carregarLista();
    }

    /**
     * Ação de clicar no botão de sair da conta.
     *
     * Stage atual é alterado para a tela de login.
     * Usuário logado é "apagado" do objeto.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void sairDaConta(ActionEvent event) throws IOException {
        this.adm = null;
        this.bibliotecario = null;
        this.leitor = null;
        this.convidado = null;
        FXMLLoader loader = StageController.retornaLoader("TelaLogin.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    /**
     * Ação de clicar no MenuItem de criar Administrador.
     *
     * É criado um novo stage da tela de criar administrador.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirAdministradorCriar() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaCriarAdm.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de criar Bibliotecario.
     *
     * É criado um novo stage da tela de criar bibliotecario.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirBibliotecarioCriar() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaCriarBibliotecario.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de criar Leitor.
     *
     * É criado um novo stage da tela de criar leitor.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLeitorCriar() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaCriarLeitor.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de criar Livro.
     *
     * É criado um novo stage da tela de criar livro.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLivroCriar() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaCriarLivro.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de editar ou excluir Administrador.
     *
     * É criado um novo stage da tela de procurar usuário.
     * Como a tela procurar usuário é usada tanto para Administrador, Bibliotecario
     * e Leitor, é necessário setar as informações e funcionalidades que a tela irá conter
     * para administrador que ativou a ação.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirAdministradorEditarExcluir() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        TelaProcurarUsuarioController controller = loader.getController();

        controller.setAdm(this.adm);
        controller.setQualOperacao("Administrador");
        controller.setTitulo("Procurar administrador por :");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de editar ou excluir Bibliotecario.
     *
     * É criado um novo stage da tela de procurar usuário.
     * Como a tela procurar usuário é usada tanto para Administrador, Bibliotecario
     * e Leitor, é necessário setar as informações e funcionalidades que a tela irá conter
     * para bibliotecario que ativou a ação.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirBibliotecarioEditarExcluir() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);

        TelaProcurarUsuarioController controller = loader.getController();

        controller.setQualOperacao("Bibliotecario");
        controller.setTitulo("Procurar bibliotecario por :");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de editar ou excluir Leitor.
     *
     * É criado um novo stage da tela de procurar usuário.
     * Como a tela procurar usuário é usada tanto para Administrador, Bibliotecario
     * e Leitor, é necessário setar as informações e funcionalidades que a tela irá conter
     * para leitor que ativou a ação.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLeitorEditarExcluir() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarUsuario.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);

        TelaProcurarUsuarioController controller = loader.getController();

        controller.setQualOperacao("Leitor");
        controller.setTitulo("Procurar leitor por :");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de editar ou excluir Livro.
     *
     * É criado um novo stage da tela de procurar livro.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLivroEditarExcluir() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarLivro.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de bloquear ou desbloquear um Leitor.
     *
     * É criado um novo stage da tela digita ID.
     * Como a tela digite ID é usada tanto para realizar empréstimo, bloquear ou desbloquear
     * leitor e tirar multa do leitor, é necessário setar qual dessas operações resultou no carregamento
     * da tela. Assim, o botão de confirmar na tela digita ID irá relizar diferentes ações.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLeitorBloquearDesbloquear() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaDigiteID.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);

        TelaDigiteIDController controller = loader.getController();

        controller.setQualOperacao("Bloqueio/Desbloqueio");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no MenuItem de tirar multa de um Leitor.
     *
     * É criado um novo stage da tela digita ID.
     * Como a tela digite ID é usada tanto para realizar empréstimo, bloquear ou desbloquear
     * leitor e tirar multa do leitor, é necessário setar qual dessas operações resultou no carregamento
     * da tela. Assim, o botão de confirmar na tela digita ID irá relizar diferentes ações.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void gerirLeitorTirarMulta() throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaDigiteID.fxml");
        Stage stage = new Stage();
        StageController.criaStage(stage, loader);

        TelaDigiteIDController controller = loader.getController();

        controller.setQualOperacao("Tirar multa");

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Ação de clicar no botão de confirmar funcionalidade.
     *
     * A depender da funcionalidade escolhida, carrega uma nova tela em
     * um novo stage.
     *
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void selecionaFuncionalidade() throws IOException {

        //caso não seja escolhido nenhuma funcionalidade, cria alert informando o error
        if(this.listaFuncionalidades.getSelectionModel().getSelectedItem()==null)
            StageController.criaAlert(Alert.AlertType.WARNING,"ERROR","Erro ao confirmar uma funcionalidade","Escolha uma funcionalidade antes de confirmar");

        /*caso seja escolhida a funcionalidade de pesquisar livros, carrega a tela de pesquisar livros em
        um novo stage e seta o objeto da própra tela.*/
        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Pesquisar livros")) {
            FXMLLoader loader = StageController.retornaLoader("TelaPesquisaLivro.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            TelaPesquisaLivroController controller = loader.getController();

            controller.setTelaInicialController(this);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }

        /*caso seja escolhida a funcionalidade de renovar empréstimo, é criado um alert informando se a operação
        foi bem sucedida ou não.*/
        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Renovar empréstimo")){
            try{
                this.leitor.renovarEmprestimo();
                StageController.criaAlert(Alert.AlertType.INFORMATION,"Operação bem-sucedida","Renovação de empréstimo realizado com sucesso","Seu empréstimo foi renovado");
            }catch (LeitorNaoPossuiEmprestimo | LeitorBloqueado | LivroReservado | LeitorLimiteDeRenovacao | LeitorTemEmprestimoEmAtraso e){
                StageController.criaAlert(Alert.AlertType.WARNING,"ERROR","Erro ao confirmar uma funcionalidade",e.getMessage());
            }
        }

        /*caso seja escolhida a funcionalidade de minhas reservas, é verificado se existe alguma reserva.
        * Caso não existe, cria alert informando error.
        * Caso exista, carrega a tela de minhas reservas em um novo stage e seta o objeto da própra tela.*/
        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Minhas reservas")){
            if(DAO.getReserva().encontrarReservasLeitor(this.leitor.getID()).isEmpty()) {
                LeitorNaoPossuiReservas e = new LeitorNaoPossuiReservas();
                StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar uma funcionalidade", e.getMessage());
            }

            else{
                FXMLLoader loader = StageController.retornaLoader("TelaMinhasReservas.fxml");
                Stage stage = new Stage();
                StageController.criaStage(stage, loader);
                TelaMinhasReservasController controller = loader.getController();

                controller.setTelaInicialController(this);
                controller.carregaTabela();

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }

        }

        //caso seja escolhida a funcionalidade de relatórios, carrega a tela de relatórios em um novo stage
        else if(this.listaFuncionalidades.getSelectionModel().getSelectedItem().equals("Relatórios")){
            FXMLLoader loader = StageController.retornaLoader("TelaRelatorios.fxml");
            Stage stage = new Stage();
            StageController.criaStage(stage, loader);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    /**
     * Método responsável por carregar o ListView com a funcionalidade padrão a todos tipos de usários do sistema.
     */

    public void carregarLista(){
        this.funcionalidades = FXCollections.observableArrayList("Pesquisar livros");
        this.listaFuncionalidades.setItems(funcionalidades);
    }

    /**
     * Método responsável por adicionar no ListView as funcionalidades permitidas a um Administrador.
     */

    public void listaFuncionalidadesAdm(){
        this.funcionalidades.addAll("Relatórios");
        this.listaFuncionalidades.setItems(this.funcionalidades);
    }

    /**
     * Método responsável por adicionar no ListView as funcionalidades permitidas a um Leitor.
     */

    public void listaFuncionalidadesLeitor(){
        this.funcionalidades.addAll("Renovar empréstimo", "Minhas reservas");
        this.listaFuncionalidades.setItems(this.funcionalidades);
    }

    /**
     * Método responsável por desabilitar funcionalidades não permitidas a um Bibliotecario.
     */

    public void telaBibliotecario(){
        this.gerirAdministrador.setDisable(true);
        this.gerirBibliotecario.setDisable(true);
        this.gerirLeitor.setDisable(true);
        this.editarExcluirLivro.setDisable(true);
    }

    /**
     * Método responsável por desabilitar funcionalidades não permitidas a um Leitor e a um Convidado.
     */

    public void telaLeitorEConvidado(){
        this.gerirAdministrador.setDisable(true);
        this.gerirBibliotecario.setDisable(true);
        this.gerirLeitor.setDisable(true);
        this.gerirLivro.setDisable(true);
    }

    /**
     * Método responsável por setar Administrador logado atual e exibir seu nome e ID
     * no label destinado a tal ação.
     *
     * @param adm administrador logado atual
     */

    public void setAdm(Adm adm) {
        this.adm = adm;
        this.mensagemBemVindo.setText("Bem-vindo, "+adm.getNome()+" -> ID: "+adm.getID());
    }

    /**
     * Método responsável por setar Bibliotecario logado atual e exibir seu nome e ID
     * no label destinado a tal ação.
     *
     * @param bibliotecario bibliotecario logado atual
     */

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
        this.mensagemBemVindo.setText("Bem-vindo, "+bibliotecario.getNome()+" -> ID: "+bibliotecario.getID());
    }

    /**
     * Método responsável por setar Leitor logado atual e exibir seu nome e ID
     * no label destinado a tal ação.
     *
     * @param leitor leitor logado atual
     */

    public void setLeitor(Leitor leitor) {
        this.leitor = leitor;
        this.mensagemBemVindo.setText("Bem-vindo, "+leitor.getNome()+" -> ID: "+leitor.getID());
    }

    /**
     * Método responsável por setar Convidado logado atual e exibir seu nome
     * no label destinado a tal ação.
     *
     * @param convidado convidado logado atual
     */

    public void setConvidado(Convidado convidado) {
        this.convidado = convidado;
        this.mensagemBemVindo.setText("Bem-vindo, Convidado");
    }

    public Adm getAdm() {
        return adm;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public Convidado getConvidado() {
        return convidado;
    }
}
