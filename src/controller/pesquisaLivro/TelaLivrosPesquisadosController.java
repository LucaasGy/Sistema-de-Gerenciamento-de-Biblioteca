package controller.pesquisaLivro;

import controller.procurarObjeto.TelaDigiteIDController;
import controller.telaInicial.TelaInicialController;
import dao.DAO;
import erros.leitor.*;
import erros.livro.*;
import erros.objetos.ObjetoInvalido;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import utils.StageController;

import java.io.IOException;
import java.util.List;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaLivrosPesquisados" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaLivrosPesquisadosController {

    @FXML
    private Label anoLivro;

    @FXML
    private Label mensagemErro;

    @FXML
    private Label autorLivro;

    @FXML
    private Button botaoDevolucao;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoEmprestimo;

    @FXML
    private Button botaoReservar;

    @FXML
    private Label categoriaLivro;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private Label disponibilidadeLivro;

    @FXML
    private Label editoraLivro;

    @FXML
    private Label isbnLivro;

    @FXML
    private Label numeroReservasLivro;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private Label tituloLivro;
    private TelaInicialController telaInicialController;

    /**
     * Observador para coletar objeto escolhio no TableView.
     */

    @FXML
    void initialize(){
        this.tabelaLivros.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    /**
     * Ação de clicar no botão de menu.
     *
     * Stage atual é fechado.
     *
     * @param event evento gerado quando uma ação interativa ocorre
     */

    @FXML
    void voltarMenu(ActionEvent event){
        Stage stage = StageController.getStage(event);
        stage.close();
    }

    /**
     * Ação de cliar no botão de fazer reserva.
     */

    @FXML
    void fazerReserva(){
       Livro livroEscolhido = this.tabelaLivros.getSelectionModel().getSelectedItem();

        //caso não seja escolhido um livro para reservar, cria alert informando o error
        if(livroEscolhido==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar reserva", "Escolha um livro antes de confirmar");

        else {
            //cria reserva para o leitor logado na tela inicial e exibe mensagem de operação bem sucedida
            try {
                this.telaInicialController.getLeitor().reservarLivro(livroEscolhido.getISBN());
                this.tabelaLivros.getSelectionModel().clearSelection();
                StageController.sucesso(this.mensagemErro,"RESERVA FEITA COM SUCESSO");
            } catch (LeitorBloqueado | LivroLimiteDeReservas | LivroNaoDisponivel | LeitorTemEmprestimoEmAtraso |
                     LivroNaoPossuiEmprestimoNemReserva | LeitorMultado | LeitorReservarLivroEmMaos |
                     LeitorLimiteDeReservas | ObjetoInvalido | LeitorPossuiReservaDesseLivro e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de cliar no botão de fazer devolução.
     */

    @FXML
    void fazerDevolucao(){
        Livro livroEscolhido = this.tabelaLivros.getSelectionModel().getSelectedItem();

        //caso não seja escolhido um livro para devolver, cria alert informando o error
        if(livroEscolhido==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar devolução", "Escolha um livro antes de confirmar");

        else {
            //devolve livro escolhido e exibe mensagem de operação bem sucedida
            try {
                Bibliotecario.devolverLivro(livroEscolhido.getISBN());
                this.tabelaLivros.getSelectionModel().clearSelection();
                StageController.sucesso(this.mensagemErro,"DEVOLUÇÃO FEITA COM SUCESSO");
            } catch (LivroNaoPossuiEmprestimo e) {
                StageController.error(this.mensagemErro,e.getMessage());
            }
        }
    }

    /**
     * Ação de cliar no botão de fazer empréstimo.
     */

    @FXML
    void fazerEmprestimo() throws IOException {
        Livro livroEscolhido = this.tabelaLivros.getSelectionModel().getSelectedItem();

        //caso não seja escolhido um livro para emprestimo, cria alert informando o error
        if(livroEscolhido==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao confirmar empréstimo", "Escolha um livro antes de confirmar");

        else {
            /*caso livro escolhido não esteja disponível, é necessário verificar porque não está disponível.
            Pode não ta disponível porque está emprestado ou porque algum Administrador alterou manualmente
            sua disponibilidade. Exibe a mensagem a depender da possibilidade.*/
            if (!livroEscolhido.getDisponivel()) {
                if (DAO.getEmprestimo().encontrarPorISBN(livroEscolhido.getISBN()) != null) {
                    LivroEmprestado e = new LivroEmprestado();
                    StageController.error(this.mensagemErro,e.getMessage());
                }

                else {
                    LivroNaoDisponivel e = new LivroNaoDisponivel();
                    StageController.error(this.mensagemErro,e.getMessage());
                }
            }

            /*carrega a tela de digite ID em um novo stage, seta a operação empréstimo e
            seta o livro escolhido do TableView.*/
            else {
                this.mensagemErro.setText("");
                this.tabelaLivros.getSelectionModel().clearSelection();

                Stage stage = new Stage();
                FXMLLoader loader = StageController.retornaLoader("TelaDigiteID.fxml");
                StageController.criaStage(stage, loader);
                TelaDigiteIDController controller = loader.getController();

                controller.setQualOperacao("emprestimo");
                controller.setLivro(livroEscolhido);

                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }
        }
    }

    /**
     * Método responsável por setar nos label da tela as informações do Livro escolhido no TableView.
     *
     * @param livro livro escolhido
     */

    public void selecionarLivroTabela(Livro livro){
        this.mensagemErro.setText("");

        if(livro!=null) {
            this.tituloLivro.setText(livro.getTitulo());
            this.isbnLivro.setText(Double.toString(livro.getISBN()));
            this.autorLivro.setText(livro.getAutor());
            this.editoraLivro.setText(livro.getEditora());
            this.anoLivro.setText(Integer.toString(livro.getAno()));
            this.categoriaLivro.setText(livro.getCategoria());

            //pega valores atualizados, caso usuario empreste/reserve/devolva livro
            Livro livroATT = DAO.getLivro().encontrarPorISBN(livro.getISBN());
            this.numeroReservasLivro.setText(Integer.toString(DAO.getReserva().encontrarReservasLivro(livroATT.getISBN()).size()));
            if (livroATT.getDisponivel())
                this.disponibilidadeLivro.setText("Sim");
            else
                this.disponibilidadeLivro.setText("Não");
        }

        else{
            this.tituloLivro.setText("");
            this.isbnLivro.setText("");
            this.autorLivro.setText("");
            this.editoraLivro.setText("");
            this.anoLivro.setText("");
            this.categoriaLivro.setText("");
            this.numeroReservasLivro.setText("");
            this.disponibilidadeLivro.setText("");
        }
    }

    /**
     * Método responsável por setar colunas do TableView com dados pré determinados de um objeto Livro.
     */

    public void setaColunas(){
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
    }

    /**
     * Método responsável por carregar o TableView com o livro encontrado do controller da tela
     * "pesquisaLivro".
     *
     * Livro encontrado pelo ISBN.
     *
     * @param livro livro encontrado
     */

    public void carregaTableLivroISBN(Livro livro){
        setaColunas();
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livro);
        this.tabelaLivros.setItems(listaLivros);
    }

    /**
     * Método responsável por carregar o TableView com a lista de livros encontrada do controller da tela
     * "pesquisaLivro".
     *
     * Essa lista pode ser de livros encontrados a partir do titulo, autor ou categoria.
     *
     * @param livros lista de livros encontrada
     */

    public void carregaTableLivroLista(List<Livro> livros){
        setaColunas();
        ObservableList<Livro> listaLivros = FXCollections.observableArrayList(livros);
        this.tabelaLivros.setItems(listaLivros);
    }

    /**
     * Método responsável por desabilitar botões com funcionalidades não permitidas a um Administrador e a um Convidado.
     */

    public void telaAdministradorEConvidado(){
        this.botaoEmprestimo.setDisable(true);
        this.botaoDevolucao.setDisable(true);
        this.botaoReservar.setDisable(true);
    }

    /**
     * Método responsável por desabilitar botões com funcionalidades não permitidas a um Leitor.
     */

    public void telaLeitor(){
        this.botaoDevolucao.setDisable(true);
        this.botaoEmprestimo.setDisable(true);
    }

    /**
     * Método responsável por desabilitar botões com funcionalidades não permitidas a um Bibliotecario.
     */

    public void telaBibliotecario(){
        this.botaoReservar.setDisable(true);
    }

    public void setTelaInicialController(TelaInicialController telaInicialController) {
        this.telaInicialController = telaInicialController;
    }
}