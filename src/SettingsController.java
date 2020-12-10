import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    public TextField vertexField;
    @FXML
    public TextField edgesField;
    @FXML
    public TextField sumField;
    @FXML
    public TextField timeField;
    @FXML
    public TextField fliesField;

    public Settings settings;

    @FXML
    public void OkOnClick() {

       int vertex = Integer.parseInt(vertexField.getText());
       int edges = Integer.parseInt(edgesField.getText());
       int flies = Integer.parseInt(fliesField.getText());
       int sum = Integer.parseInt(sumField.getText());
       int time = Integer.parseInt(timeField.getText());
       settings = new Settings(vertex, edges, flies, sum, time);

       Stage stage = (Stage) sumField.getScene().getWindow();
       stage.close();
    }

    @FXML
    public void initialize() {
    }
}
