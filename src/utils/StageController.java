package utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class StageController {

    public static Stage getStage(ActionEvent event){
        Node source = (Node)  event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
