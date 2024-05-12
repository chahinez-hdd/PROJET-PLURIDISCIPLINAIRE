package application.FrontEnd;
import application.BackEnd.Package;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.BackEnd.Java;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class MenuController implements Initializable{
	
	
	
	
	
	@FXML
	private ComboBox<String> MenuBox;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		File file = new File(MetricController.PathProject);
		File[]SrcFile = file.listFiles();
		ArrayList<Package>ListPackage = new ArrayList<Package>();
		Java.FetchSrcJavaFile(SrcFile, ListPackage);
		ArrayList<String> PkgNameList = new ArrayList<>();
		Java.FetchPkgName(ListPackage,"",PkgNameList);
		PkgNameList.add(0,"Src Folder");
		ObservableList<String> PackageMenuList = FXCollections.observableArrayList(PkgNameList); 
		MenuBox.setItems(PackageMenuList);
		MenuBox.setValue("Src Folder");
		
	}
	
}
