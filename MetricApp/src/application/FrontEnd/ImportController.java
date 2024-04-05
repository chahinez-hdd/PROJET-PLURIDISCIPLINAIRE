package application.FrontEnd;

	import javafx.fxml.FXML;

	import javafx.fxml.FXMLLoader;
	import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.control.Alert;
	import javafx.scene.control.ButtonType;
	import javafx.scene.control.ContentDisplay;
	import javafx.scene.control.Label;
	import javafx.scene.control.TreeCell;
	import javafx.scene.control.TreeItem;
	import javafx.scene.control.TreeView;
	import javafx.scene.input.KeyCode;
	import javafx.scene.layout.HBox;
	import javafx.scene.shape.SVGPath;
	import javafx.stage.Stage;

	import java.io.File;
	import java.io.IOException;
	import java.util.ArrayList;

import application.BackEnd.ImportStatus;
import application.BackEnd.Package;
import javafx.util.Callback;
public class ImportController {

	    @FXML
	    private TreeView<TreeItemData> treeView;
	    @FXML
	    private Label ImportLabel;

	    public void initialize(String FilePath) {
	        File file = new File(FilePath);
	        ImportLabel.setText("Imports of "+file.getName());
	        ArrayList<ImportStatus>ListImport = new ArrayList<ImportStatus>();
	       ListImport = Package.update(file,(Package.ImportFetch(file)));
	       System.out.println(Package.ImportFetch(file));
	        TreeItemData rootItemData = new TreeItemData("Imports","M 2 2.5 A 2.5 2.5 0 0 1 4.5 0 h 8.75 a 0.75 0.75 0 0 1 0.75 0.75 v 12.5 a 0.75 0.75 0 0 1 -0.75 0.75 h -2.5 a 0.75 0.75 0 0 1 0 -1.5 h 1.75 v -2 h -8 a 1 1 0 0 0 -0.714 1.7 a 0.75 0.75 0 1 1 -1.072 1.05 A 2.495 2.495 0 0 1 2 11.5 Z m 10.5 -1 h -8 a 1 1 0 0 0 -1 1 v 6.708 A 2.486 2.486 0 0 1 4.5 9 h 8 Z M 5 12.25 a 0.25 0.25 0 0 1 0.25 -0.25 h 3.5 a 0.25 0.25 0 0 1 0.25 0.25 v 3.25 a 0.25 0.25 0 0 1 -0.4 0.2 l -1.45 -1.087 a 0.249 0.249 0 0 0 -0.3 0 L 5.4 15.7 a 0.25 0.25 0 0 1 -0.4 -0.2 Z");
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	        TreeItem<TreeItemData> UsedImportParent = new TreeItem<>(new TreeItemData("Used Imports","M 9 16.17 L 4.83 12 l -1.42 1.41 L 9 19 L 21 7 l -1.41 -1.41 Z"));
	        TreeItem<TreeItemData> NotUsedImportParent = new TreeItem<>(new TreeItemData("Unused Imports","M 19 6.41 L 17.59 5 L 12 10.59 L 6.41 5 L 5 6.41 L 10.59 12 L 5 17.59 L 6.41 19 L 12 13.41 L 17.59 19 L 19 17.59 L 13.41 12 Z"));
	        for (ImportStatus Import : ListImport) {
	            //TreeItem<TreeItemData> ImportItem = createTreeItem(Import);
	            if(Import.ImportStatus == 1) {
	            UsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData(Import.ImportName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z")));
	            }
	            else {
	            	NotUsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData(Import.ImportName,"M 3 3 v 8 h 8 V 3 H 3 Z m 6 6 H 5 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 H 3 Z m 6 6 H 5 v -4 h 4 v 4 Z m 4 -16 v 8 h 8 V 3 h -8 Z m 6 6 h -4 V 5 h 4 v 4 Z m -6 4 v 8 h 8 v -8 h -8 Z m 6 6 h -4 v -4 h 4 v 4 Z")));
	            }
	        }
	        if(UsedImportParent.getChildren().isEmpty()) {
	        	UsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	         if(NotUsedImportParent.getChildren().isEmpty()) {
	        	NotUsedImportParent.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	        rootItem.getChildren().add(UsedImportParent);
	        rootItem.getChildren().add(NotUsedImportParent);
	        setTreeViewStyle();
	        
	     
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
	                        if(treeItem.getValue().label.equals("Used Imports")) {
	                        svgPath.getStyleClass().setAll("parent-node-used-svg");
	                        }
	                        else {
	                        	svgPath.getStyleClass().setAll("parent-node-notused-svg");
	                        }
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



