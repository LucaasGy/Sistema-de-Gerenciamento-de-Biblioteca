package view;

import dao.DAO;
import erros.objetos.ObjetoInvalido;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import utils.StageController;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, ObjetoInvalido {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();
        System.out.println(DAO.getAdm().encontrarTodos().size());
        System.out.println(DAO.getBibliotecario().encontrarTodos().size());
        System.out.println(DAO.getLeitor().encontrarTodos().size());
        System.out.println(DAO.getLivro().encontrarTodos().size());
        //DAO.getAdm().criar(new Adm("adm1","senha1"));
        //DAO.getBibliotecario().criar(new Bibliotecario("biblio1","senha2"));
        //DAO.getLeitor().criar(new Leitor("leitor1","rua1","telefone1","senha3"));
        //DAO.getLivro().criar(new Livro("lucas","lucas","lucas",2018,"lucas"));
        //DAO.getLivro().criar(new Livro("lucas","lucas","lucas",2018,"lucas"));
        //DAO.getEmprestimo().criar(new Emprestimo(10.0000,1013));
        //DAO.getReserva().criar(new Reserva(10.000,1003));
        //DAO.getReserva().removerTodos();
        //System.out.println(DAO.getLivro().encontrarTodos().get(0).getISBN());
        //System.out.println(DAO.getLivro().encontrarTodos().get(1).getISBN());
        //System.out.println(DAO.getLivro().encontrarTodos().get(2).getISBN());
        //System.out.println(DAO.getAdm().encontrarTodos().get(0).getID());
        //System.out.println(DAO.getBibliotecario().encontrarTodos().get(0).getID());
        //System.out.println(DAO.getLeitor().encontrarTodos().get(0).getID());

        StageController.criaStage(stage,StageController.retornaLoader("TelaLogin.fxml"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
