package application.FrontEnd;
import application.BackEnd.CollaborationClasses;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.BackEnd.Java;
import application.BackEnd.Package;
import application.FrontEnd.MetricController.CustomTreeCell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CollaborationController {

	 @FXML
	    private TreeView<TreeItemData> treeView;
	 


	    public void initialize(String PkgPath) {
	    	System.out.println(PkgPath);
	        ArrayList<Package> listPackage = new ArrayList<>();
	        File projectFile = new File(PkgPath);
	        File[] srcFile = projectFile.listFiles();
	        Java.FetchSrcJavaFile(srcFile, listPackage);
	        TreeItemData rootItemData;
	        ArrayList<CollaborationClasses> CollaborationList= new ArrayList<>();
	        ArrayList<String> ClassNames = new ArrayList<>();
	        CollaborationClasses.FetchClassInstaciationName(new File(MetricController.FileSelectedPath), ClassNames);
	        CollaborationClasses.NbCollaborationClass("", listPackage, ClassNames,CollaborationList);
	        if(PkgPath.equals(MetricController.PathProject)) {
	         rootItemData = new TreeItemData("Src Folder","M 10 4 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 18 c 0 1.1 0.9 2 2 2 h 16 c 1.1 0 2 -0.9 2 -2 V 8 c 0 -1.1 -0.9 -2 -2 -2 h -8 l -2 -2 Z");
	        }
	        else {
	        	 rootItemData = new TreeItemData(PkgPath.substring(PkgPath.lastIndexOf(File.pathSeparator)+1),"M 10 4 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 18 c 0 1.1 0.9 2 2 2 h 16 c 1.1 0 2 -0.9 2 -2 V 8 c 0 -1.1 -0.9 -2 -2 -2 h -8 l -2 -2 Z");
		        
	        }
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	        for (Package pkg : listPackage) {
	            TreeItem<TreeItemData> packageItem = createTreeItem(pkg);
	            rootItem.getChildren().add(packageItem);
	        }
	       
	        setTreeViewStyle();
	        
	      




	                    
	                    //alert.getButtonTypes().setAll(buttonType1, buttonType2, buttonType3, buttonTypeCancel);
	                    
	                   // alert.getDialogPane().requestFocus();
	                   
	                    
	                    
	                    //buttonBox.setPadding(new Insets(10));
	                    //buttonBox.setAlignment(javafx.geometry.Pos.CENTER); // Center buttons horizontally

	                    // Set HBox as the content of the dialog pane
	                  //  alert.getDialogPane().setContent(buttonBox);
	                    
	                        
	    }
	    
	    
	    public TreeView<TreeItemData> getTreeView() {
	        return this.treeView;
	    }
	    
	  
	    String RealPathConcat(String FileHierachy , String JavaProjectPath) {
	    	if(FileHierachy.contains(File.separator+"Default Package"+File.separator)) {
	    		FileHierachy = FileHierachy.replace("Default Package"+File.separator, "");
	    	}
	    	FileHierachy = FileHierachy.replace("Src Folder", "");
	    	FileHierachy=JavaProjectPath+FileHierachy;
	    	return FileHierachy;
	    }

	  


	    private TreeItem<TreeItemData> createTreeItem(Package pkg) {
	        TreeItem<TreeItemData> packageItem = new TreeItem<>(new TreeItemData(pkg.PackageName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z"));
	        
	        for (Package subPackage : pkg.SubPackges) {
	            TreeItem<TreeItemData> subPackageItem = createTreeItem(subPackage);
	            packageItem.getChildren().add(subPackageItem);
	        }
	        for (String fileInfo : pkg.FileNameList) {
	            packageItem.getChildren().add(new TreeItem<>(new TreeItemData(fileInfo,"M 2 1.75 C 2 0.784 2.784 0 3.75 0 h 6.586 c 0.464 0 0.909 0.184 1.237 0.513 l 2.914 2.914 c 0.329 0.328 0.513 0.773 0.513 1.237 v 9.586 A 1.75 1.75 0 0 1 13.25 16 h -9.5 A 1.75 1.75 0 0 1 2 14.25 Z m 1.75 -0.25 a 0.25 0.25 0 0 0 -0.25 0.25 v 12.5 c 0 0.138 0.112 0.25 0.25 0.25 h 9.5 a 0.25 0.25 0 0 0 0.25 -0.25 V 6 h -2.75 A 1.75 1.75 0 0 1 9 4.25 V 1.5 Z m 6.75 0.062 V 4.25 c 0 0.138 0.112 0.25 0.25 0.25 h 2.688 l -0.011 -0.013 l -2.914 -2.914 l -0.013 -0.011 Z")));
	        }
	        return packageItem;
	    }
	    
	    
	    private void setTreeViewStyle() {
	    	treeView.setCellFactory(new Callback<TreeView<TreeItemData>, TreeCell<TreeItemData>>() {
	    	    @Override
	    	    public TreeCell<TreeItemData> call(TreeView<TreeItemData> param) {
	    	        return new CustomTreeCell();
	    	    }
	    	});

	    }
	   
	    
	    public class CustomTreeCell extends TreeCell<TreeItemData> {
	        private SVGPath svgPath;
	        private Label label;
	        private HBox hbox;

	        public CustomTreeCell() {
	            this.svgPath = new SVGPath();
	            this.label = new Label();
	            this.hbox = new HBox(svgPath, label);
	            this.hbox.setAlignment(Pos.CENTER_LEFT);
	            setGraphic(hbox);
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            setFocusTraversable(true); 
	        }

	        @Override
	        protected void updateItem(TreeItemData item, boolean empty) {
	            super.updateItem(item, empty);

	            if (empty || item == null) {
	                // Clear the content when the item is empty or null
	                setText(null);
	                setGraphic(null);
	                // Reset style classes when the item is empty
	                label.getStyleClass().clear();
	                svgPath.getStyleClass().clear();
	            } else {
	                TreeItem<TreeItemData> treeItem = getTreeItem();
	                if (treeItem != null) {
	                    if (treeItem.getParent() == null) {
	                        label.getStyleClass().setAll("root-node-label");
	                        svgPath.getStyleClass().setAll("root-node-svg");
	                    } else if (!treeItem.isLeaf()) {
	                        label.getStyleClass().setAll("parent-node-label");
	                        svgPath.getStyleClass().setAll("parent-node-svg");
	                    } else {
	                        label.getStyleClass().setAll("leaf-node-label");
	                        svgPath.getStyleClass().setAll("leaf-node-svg");
	                    }
	                } else {
	                    // Clear style for empty cells
	                    setStyle(null);
	                }

	                if (item.GetSVG() != null && item.GetSVG().getContent() != null && !item.GetSVG().getContent().isEmpty()) {
	                    // Set the SVG content if available
	                    svgPath.setContent(item.GetSVG().getContent());
	                } else {
	                    // Clear the SVG content if not available
	                    svgPath.setContent(null);
	                }

	                if (item.GetLabel() != null) {
	                    // Set the text label
	                    label.setText(item.GetLabel());
	                } else {
	                    // Clear the text label if not available
	                    label.setText(null);
	                }
	                label.setPadding(new Insets(0, 0, 0, 10)); // Example padding: 10px on the right
	                // Set the HBox as the graphic content
	                setGraphic(hbox);
	            }
	        }
	    }
	    
	    
	    



	        
	  
	}

