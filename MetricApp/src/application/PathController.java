package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PathController {

    @FXML
    private TextField PathField;

    @FXML
    private Label ErrorLabelPath;

    @FXML
    public void PathTest(ActionEvent event) {
        String path = PathField.getText();
        if (path.isEmpty()) {
            setErrorLabel("Field Path Empty. Please Input Java Project Path", "red");
        } else {
            switch (Java.IsJavaProject(path)) {
                case -2:
                    setErrorLabel("Src Folder Doesn't Exist", "red");
                    break;
                case -1:
                    setErrorLabel("Error Path Doesn't Exist", "red");
                    break;
                case 0:
                    setErrorLabel("Src Folder Is Empty", "red");
                    break;
                case 1:
                	 setErrorLabel("Java Project", "green");
                    openMetricScene(path);
                    break;
                case 2:
                    setErrorLabel("Not A Java Project", "red");
                    break;
            }
        }
    }

    private void setErrorLabel(String text, String color) {
        ErrorLabelPath.setStyle("-fx-text-fill: " + color + ";");
        ErrorLabelPath.setText(text);
    }

    private void openMetricScene(String PathProject) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Metric.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Package> ListPackage = new ArrayList<Package>();
		File ProjectFile = new File(PathProject);
		File[] SrcFile =ProjectFile.listFiles();
		Java.FetchSrcJavaFile(SrcFile,ListPackage);
    }
}
