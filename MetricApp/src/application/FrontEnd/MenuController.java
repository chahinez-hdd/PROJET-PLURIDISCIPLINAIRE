package application.FrontEnd;
import application.BackEnd.Package;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.BackEnd.Java;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

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
	
	public void CollaborationTree(ActionEvent event) {
	    ArrayList<Package>ListPackage = new ArrayList<>();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ressource/Fxml Folder/Collaboration.fxml"));
        Parent root = null;
		try {
			root = fxmlLoader.load();
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
   CollaborationController collaborationController = fxmlLoader.getController();
      System.out.println(MenuBox.getValue());
      System.out.println(MetricController.PathProject+File.separator+MenuBox.getValue().replace(".", File.separator));
      String FilePath ="";
      String FolderName ="";
     if(MenuBox.getValue().equals("Src Folder")) {
    	 File file = new File(MetricController.PathProject);
    	 Java.FetchSrcJavaFile(file.listFiles(), ListPackage);
    	 FolderName = "Src Folder";
     }
     else if(MenuBox.getValue().equals("Default Package")) {
    	 File file = new File(MetricController.PathProject);
    	 Java.FetchJavaFileNoPackage(file.listFiles(), ListPackage);
    	 FolderName = "Default Package";
     }
     else if(MetricController.PathProject.endsWith(File.separator)){
    		FilePath =  MetricController.PathProject+MenuBox.getValue().replace(".", File.separator);
    		File file = new File(FilePath); 
    		Java.FetchJavaFilePkg(file.listFiles(), ListPackage);
    		FolderName = FilePath.substring(FilePath.lastIndexOf("\\")+1);
      }else {
    	  FilePath =  MetricController.PathProject+File.separator+MenuBox.getValue().replace(".", File.separator);
    	  File file = new File(FilePath); 
  		  Java.FetchJavaFilePkg(file.listFiles(), ListPackage);
  		FolderName = FilePath.substring(FilePath.lastIndexOf("\\")+1);
      }
     
     if(!FilePath.endsWith("\\")) {
		 FilePath = FilePath+"\\";
	 }
     
    		  collaborationController.initialize(FolderName,ListPackage);
       Scene scene = new Scene(root);
      
      
       if(MetricController.PathProject.equals(FilePath)) {
    	   scene.getStylesheets().add(this.getClass().getResource("/ressource/Css Folder/application.css").toExternalForm());
       }else {
    	   scene.getStylesheets().add(this.getClass().getResource("/ressource/Css Folder/Collaboration.css").toExternalForm());
       }
       
       
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
	}
	
}
