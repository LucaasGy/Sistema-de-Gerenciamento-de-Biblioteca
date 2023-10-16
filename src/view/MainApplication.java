package view;

import dao.DAO;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Adm;
import model.Bibliotecario;
import model.Leitor;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();

        //DAO.getAdm().criar(new Adm("adm1","senha1"));
        //DAO.getBibliotecario().criar(new Bibliotecario("biblio1","senha2"));
        //DAO.getLeitor().criar(new Leitor("leitor1","rua1","telefone1","senha3"));

        StageController.criaStage(stage,"TelaLogin.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
