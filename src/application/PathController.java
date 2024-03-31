package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PathController {
	@FXML
	TextField PathField;
	@FXML
	Label ErrorLabelPath;
	
	public void PathTest(ActionEvent event) {
		String Path = PathField.getText();
		//ErrorLabelPath.setText("");
		if(Path.isEmpty()) {
			ErrorLabelPath.setStyle("-fx-text-fill :  red;");
			ErrorLabelPath.setText("Field Path Empty Please Input Java Project Path");
		}
		else {
			switch(Package.IsJavaProject(Path)) {
			case -2:		
				 ErrorLabelPath.setStyle("-fx-text-fill : red;");
				ErrorLabelPath.setText("Src Folder Doesn't Exist");
				break;
			case -1:

				ErrorLabelPath.setStyle("-fx-text-fill :  red;");
				ErrorLabelPath.setText("Error Path Doesn't Exist");
				break;
			case 0:

				ErrorLabelPath.setStyle("-fx-text-fill :  red;");
				ErrorLabelPath.setText("Src Folder Is Empty");
				break;
			case 1:
				ErrorLabelPath.setStyle("-fx-text-fill : green;");
				ErrorLabelPath.setText("Java Project");
				break;
			case 2:
				ErrorLabelPath.setStyle("-fx-text-fill: red");
				ErrorLabelPath.setText("Not A Java Project");
				break;
			}
		}
	}
}
