package aplicativo;

import erros.objetos.ObjetoInvalido;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Sistema;
import utils.StageController;

import java.io.IOException;

/**
 * Classe principal do sistema responsável pelo ponto de entrada do aplicativo Java,
 * ou seja, onde a execução do programa começa.
 *
 * Contém um método main que define a interface do usuário, a lógica do aplicativo JavaFX,
 * e inicia a aplicação JavaFX.
 * O método main é o ponto de entrada para o aplicativo, mas ele delega o controle para o JavaFX,
 * que posteriormente chama o método start. Isso permite que a estrutura do JavaFX inicialize e
 * gerencie o aplicativo.
 *
 * @author Lucas Gabriel.
 * @author Rodrigo Nazareth.
 */

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException, ObjetoInvalido {
        Sistema.verificarMultasLeitores();
        Sistema.verificarPrazosEReservas();

        StageController.criaStage(stage,StageController.retornaLoader("/view/TelaLogin.fxml"));
        stage.show();
    }
}