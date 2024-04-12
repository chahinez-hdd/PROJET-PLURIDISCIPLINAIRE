package application.FrontEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

import application.BackEnd.Java;

public class DirectoryController {

    @FXML
    private TextField PathField;

    @FXML
    private Label ErrorLabelPath;

    @FXML
    void browseDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Java Project Path");
        Window window = PathField.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(window);
        if (selectedDirectory != null) {
        	String path = selectedDirectory.getAbsolutePath();
            PathField.setText(path);
            if(!path.endsWith("src") || !path.endsWith("src"+File.separator)) {
            	if(path.endsWith(File.separator)) {
            	path+="src";
            	}
            	else {
            		path+=File.separator+"src";
            	}
            }
            //System.out.println(path);
            //System.out.println(Java.IsJavaProject(path));
            switch(Java.IsJavaProject(path)) {
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

        
        } else {
            setErrorLabel("No directory selected", "red");
        }
    }

    private void setErrorLabel(String text, String color) {
        ErrorLabelPath.setStyle("-fx-text-fill: " + color + ";");
        ErrorLabelPath.setText(text);
    }
    
    private void openMetricScene(String pathProject) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/MetricJava.fxml"));
            Parent root = fxmlLoader.load();
            MetricController metricController = fxmlLoader.getController();
             metricController.initialize(pathProject);
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/ressource/Css Folder/application.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
