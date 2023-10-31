package controller.editarExcluirObjeto;

import dao.DAO;
import erros.livro.LivroEmprestado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Adm;
import model.Livro;
import utils.StageController;

import java.io.IOException;

/**
 * Controller responsável por intermediar a interação entre a interface
 * gráfica definida no arquivo FXML "TelaEditarExcluirLivro" e a lógica da aplicação Java,
 * permitindo uma interação eficaz entre os elementos visuais e a funcionalidade da aplicação.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class TelaEditarExcluirLivroController {

    @FXML
    private Label alertaAno;

    @FXML
    private Label alertaAutor;

    @FXML
    private Label alertaCategoria;

    @FXML
    private Label alertaDisponibilidade;

    @FXML
    private Label alertaEditora;

    @FXML
    private Label alertaTitulo;

    @FXML
    private Button botaoAtualizar;

    @FXML
    private Button botaoMenu;

    @FXML
    private Button botaoRemover;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TableColumn<Livro, Double> colunaISBN;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private TextField digitaAno;

    @FXML
    private TextField digitaAutor;

    @FXML
    private TextField digitaCategoria;

    @FXML
    private TextField digitaEditora;

    @FXML
    private TextField digitaISBN;

    @FXML
    private TextField digitaTitulo;

    @FXML
    private RadioButton disponibilidadeNao;

    @FXML
    private RadioButton disponibilidadeSim;

    @FXML
    private ToggleGroup grupo;

    @FXML
    private Label mensagemErro;

    @FXML
    private TableView<Livro> tabelaLivro;

    private double isbnLivroAAlterar;

    private String tituloLivroAAlterar;

    private String qualTabelaCarregar;

    /**
     * Observador para coletar objeto escolhido no TableView.
     */

    @FXML
    void initialize(){
        this.digitaISBN.setStyle("-fx-text-fill: gray;");
        this.tabelaLivro.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue)->selecionarLivroTabela(newValue));
    }

    /**
     * Ação de clicar no botão de atualizar livro.
     */

    @FXML
    void atualizarLivro() {
        Livro livroSelecionado = this.tabelaLivro.getSelectionModel().getSelectedItem();

        if(livroSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao atualizar livro", "Escolha um livro antes de atualizar");

        else if(this.digitaTitulo.getText().isEmpty() || this.digitaAutor.getText().isEmpty() || this.digitaEditora.getText().isEmpty() || this.digitaAno.getText().isEmpty() || this.digitaCategoria.getText().isEmpty()) {
            StageController.error(this.mensagemErro,"PREENCHA OS CAMPOS");

            this.alertaTitulo.setVisible(this.digitaTitulo.getText().isEmpty());
            this.alertaAutor.setVisible(this.digitaAutor.getText().isEmpty());
            this.alertaEditora.setVisible(this.digitaEditora.getText().isEmpty());
            this.alertaAno.setVisible(this.digitaAno.getText().isEmpty());
            this.alertaCategoria.setVisible(this.digitaCategoria.getText().isEmpty());
        }

        else if (!StageController.tryParseInt(this.digitaAno.getText())) {
            StageController.error(this.mensagemErro,"ANO É COMPOSTO APENAS POR NÚMEROS");
            this.alertaAno.setVisible(true);
            this.alertaTitulo.setVisible(false);
            this.alertaAutor.setVisible(false);
            this.alertaEditora.setVisible(false);
            this.alertaCategoria.setVisible(false);
        }

        else{
            try {
                this.alertaAno.setVisible(false);
                this.alertaTitulo.setVisible(false);
                this.alertaAutor.setVisible(false);
                this.alertaEditora.setVisible(false);
                this.alertaCategoria.setVisible(false);

                Livro livroAtualizado = new Livro(this.digitaTitulo.getText(), this.digitaAutor.getText(), this.digitaEditora.getText(), Integer.parseInt(this.digitaAno.getText()), this.digitaCategoria.getText());
                livroAtualizado.setISBN(livroSelecionado.getISBN());

                RadioButton radio = (RadioButton) grupo.getSelectedToggle();
                String escolha = radio.getText();

                if(escolha.equals("Sim"))
                    livroAtualizado.setDisponivel(true);

                else if(escolha.equals("Não"))
                    livroAtualizado.setDisponivel(false);

                Adm.atualizarDadosLivro(livroAtualizado);

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if(this.qualTabelaCarregar.equals("ISBN"))
                    carregaTabelaISBN(this.isbnLivroAAlterar);

                else if(this.qualTabelaCarregar.equals("Nome"))
                    carregaTabelaTitulo(this.tituloLivroAAlterar);

                StageController.sucesso(this.mensagemErro,"LIVRO ATUALIZADO");
            }catch (LivroEmprestado e) {
                StageController.error(this.mensagemErro,e.getMessage());
                this.disponibilidadeNao.setSelected(true);
            }
        }
    }

    /**
     * Ação de clicar no botão de remover livro.
     */

    @FXML
    void removerLivro() {
        Livro livroSelecionado = this.tabelaLivro.getSelectionModel().getSelectedItem();

        if(livroSelecionado==null)
            StageController.criaAlert(Alert.AlertType.WARNING, "ERROR", "Erro ao remover livro", "Escolha um livro antes de remover");

        else{
            try {
                Adm.removerUmLivro(livroSelecionado.getISBN());

                //verifica qual método chamar para recarregar a tabela a depender do dado setado no atributo
                //"qualTabelaCarregar".
                if (this.qualTabelaCarregar.equals("ISBN"))
                    carregaTabelaISBN(this.isbnLivroAAlterar);

                else if (this.qualTabelaCarregar.equals("Titulo"))
                    carregaTabelaTitulo(this.tituloLivroAAlterar);

                StageController.sucesso(this.mensagemErro,"LIVRO REMOVIDO");
            }catch (LivroEmprestado e){
                StageController.error(this.mensagemErro,e.getMessage());
                this.disponibilidadeNao.setSelected(true);
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
     * Stage atual é redefinido para a tela anterior "TelaProcurarLivro".
     *
     * @param event evento gerado quando uma ação interativa ocorre
     * @throws IOException caso o stage não possa ser setado,
     * retorna uma exceção informando o ocorrido
     */

    @FXML
    void voltar(ActionEvent event) throws IOException {
        FXMLLoader loader = StageController.retornaLoader("TelaProcurarLivro.fxml");
        StageController.criaStage(StageController.getStage(event), loader);
    }

    /**
     * Método responsável por carregar o TableView com o livro encontrado
     * com o isbn do livro recebido do controller da tela procurar livro.
     *
     * @param isbn isbn do livro recebido
     */

    public void carregaTabelaISBN(double isbn){
        carregaColunas();
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarPorISBN(isbn));
        //caso ao recarregar o TableView, encontre o isbn do livro em questão
        if(listaLivro.get(0)!=null)
            this.tabelaLivro.setItems(listaLivro);

        /*caso ao recarregar o TableView, não seja encontrado o livro com o isbn em questão (foi removido),
        os botões de atualizar e remover livro são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum livro a ser atualizado.*/
        else {
            this.tabelaLivro.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaTitulo.setEditable(false);
            this.digitaAutor.setEditable(false);
            this.digitaEditora.setEditable(false);
            this.digitaAno.setEditable(false);
            this.digitaCategoria.setEditable(false);

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
            this.disponibilidadeSim.setDisable(true);
            this.disponibilidadeNao.setDisable(true);
        }
    }

    /**
     * Método responsável por carregar o TableView com os livros encontrados
     * com o titulo do livro recebido do controller da tela procurar livro.
     *
     * @param titulo titulo do livro recebido
     */

    public void carregaTabelaTitulo(String titulo){
        carregaColunas();
        ObservableList<Livro> listaLivro = FXCollections.observableArrayList(DAO.getLivro().encontrarPorTitulo(titulo));
        //caso ao recarregar o TableView, encontre o titulo do livro em questão
        if(!listaLivro.isEmpty())
            this.tabelaLivro.setItems(listaLivro);

        /*caso ao recarregar o TableView, não seja encontrado o livro com o titulo em questão (foi removido),
        os botões de atualizar e remover livro são desabilitados e também os campos de alterar os dados
        deixam de ser editáveis, afinal, não existe mais nenhum livro a ser atualizado.*/
        else {
            this.tabelaLivro.getItems().clear();
            this.botaoAtualizar.setDisable(true);
            this.botaoRemover.setDisable(true);

            this.digitaTitulo.setEditable(false);
            this.digitaAutor.setEditable(false);
            this.digitaEditora.setEditable(false);
            this.digitaAno.setEditable(false);
            this.digitaCategoria.setEditable(false);

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
            this.disponibilidadeSim.setDisable(true);
            this.disponibilidadeNao.setDisable(true);
        }
    }

    /**
     * Método responsável por setar qual dado será inserido em cada coluna do TableView.
     */

    public void carregaColunas(){
        this.colunaISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        this.colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    }

    /**
     * Método responsável por setar nos label da tela as informações do livro escolhido no TableView.
     *
     * @param livro livro escolhido no TableView
     */

    public void selecionarLivroTabela(Livro livro){
        this.mensagemErro.setText("");

        if(livro!=null) {
            this.digitaISBN.setText(Double.toString(livro.getISBN()));
            this.digitaTitulo.setText(livro.getTitulo());
            this.digitaAutor.setText(livro.getAutor());
            this.digitaEditora.setText(livro.getEditora());
            this.digitaAno.setText(Integer.toString(livro.getAno()));
            this.digitaCategoria.setText(livro.getCategoria());

            if(livro.getDisponivel())
                this.disponibilidadeSim.setSelected(true);

            else
                this.disponibilidadeNao.setSelected(true);
        }

        else{
            this.digitaISBN.setText("");
            this.digitaTitulo.setText("");
            this.digitaAutor.setText("");
            this.digitaEditora.setText("");
            this.digitaAno.setText("");
            this.digitaCategoria.setText("");

            this.disponibilidadeSim.setSelected(false);
            this.disponibilidadeNao.setSelected(false);
        }
    }

    /**
     * Método responsável por setar qual método chamar após remover ou alterar um livro.
     *
     * Como o TableView pode conter os livros encontrados por titulo ou isbn, esse atributo
     * é necessário para verificar qual operação realizar.
     *
     * @param qualTabelaCarregar atributo para decidir qual tabela recarregar
     */

    public void setQualTabelaCarregar(String qualTabelaCarregar) {
        this.qualTabelaCarregar = qualTabelaCarregar;
    }

    /**
     * Método responsável por setar qual isbn de livro o usuário digitou no controller da tela
     * "TelaProcurarLivro".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param isbnLivroAAlterar isbn do livro recebido
     */

    public void setIsbnLivroAAlterar(double isbnLivroAAlterar) {
        this.isbnLivroAAlterar = isbnLivroAAlterar;
    }

    /**
     * Método responsável por setar qual titulo de livro o usuário digitou no controller da tela
     * "TelaProcurarLivro".
     *
     * Atributo necessário para recarregar o TabelView.
     *
     * @param tituloLivroAAlterar titulo do livro recebido
     */

    public void setTituloLivroAAlterar(String tituloLivroAAlterar) {
        this.tituloLivroAAlterar = tituloLivroAAlterar;
    }
}