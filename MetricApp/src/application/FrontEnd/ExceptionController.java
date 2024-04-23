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

import application.BackEnd.ExceptionStatus;
import application.BackEnd.ImportStatus;
import application.BackEnd.Package;
import javafx.util.Callback;
public class ExceptionController {

	    @FXML
	    private TreeView<TreeItemData> treeView;
	    @FXML
	    private Label ExceptionLabel;

	    public void initialize(String FilePath) {
	        File file = new File(FilePath);
	        ExceptionLabel.setText("Exceptions Of "+file.getName());
	        ArrayList<ExceptionStatus>ListException = new ArrayList<>();
	       try {
			ListException = ExceptionStatus.FetchThrowable(file, FilePath.substring(0,FilePath.indexOf("\\src\\")+4));
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        TreeItemData rootItemData = new TreeItemData("Exceptions","M 12 2 C 6.48 2 2 6.48 2 12 s 4.48 10 10 10 s 10 -4.48 10 -10 S 17.52 2 12 2 Z m 1 15 h -2 v -2 h 2 v 2 Z m 0 -4 h -2 V 7 h 2 v 6 Z");
	        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
	        treeView.setRoot(rootItem);
	        TreeItem<TreeItemData> DefaultException = new TreeItem<>(new TreeItemData("Default Exceptions","M 19.14 12.94 c 0.04 -0.3 0.06 -0.61 0.06 -0.94 c 0 -0.32 -0.02 -0.64 -0.07 -0.94 l 2.03 -1.58 c 0.18 -0.14 0.23 -0.41 0.12 -0.61 l -1.92 -3.32 c -0.12 -0.22 -0.37 -0.29 -0.59 -0.22 l -2.39 0.96 c -0.5 -0.38 -1.03 -0.7 -1.62 -0.94 L 14.4 2.81 c -0.04 -0.24 -0.24 -0.41 -0.48 -0.41 h -3.84 c -0.24 0 -0.43 0.17 -0.47 0.41 L 9.25 5.35 C 8.66 5.59 8.12 5.92 7.63 6.29 L 5.24 5.33 c -0.22 -0.08 -0.47 0 -0.59 0.22 L 2.74 8.87 C 2.62 9.08 2.66 9.34 2.86 9.48 l 2.03 1.58 C 4.84 11.36 4.8 11.69 4.8 12 s 0.02 0.64 0.07 0.94 l -2.03 1.58 c -0.18 0.14 -0.23 0.41 -0.12 0.61 l 1.92 3.32 c 0.12 0.22 0.37 0.29 0.59 0.22 l 2.39 -0.96 c 0.5 0.38 1.03 0.7 1.62 0.94 l 0.36 2.54 c 0.05 0.24 0.24 0.41 0.48 0.41 h 3.84 c 0.24 0 0.44 -0.17 0.47 -0.41 l 0.36 -2.54 c 0.59 -0.24 1.13 -0.56 1.62 -0.94 l 2.39 0.96 c 0.22 0.08 0.47 0 0.59 -0.22 l 1.92 -3.32 c 0.12 -0.22 0.07 -0.47 -0.12 -0.61 L 19.14 12.94 Z M 12 15.6 c -1.98 0 -3.6 -1.62 -3.6 -3.6 s 1.62 -3.6 3.6 -3.6 s 3.6 1.62 3.6 3.6 S 13.98 15.6 12 15.6 Z"));
	        TreeItem<TreeItemData> NotDefaultException = new TreeItem<>(new TreeItemData("Not Default Exception","M 3 17.25 V 21 h 3.75 L 17.81 9.94 l -3.75 -3.75 L 3 17.25 Z M 20.71 7.04 c 0.39 -0.39 0.39 -1.02 0 -1.41 l -2.34 -2.34 c -0.39 -0.39 -1.02 -0.39 -1.41 0 l -1.83 1.83 l 3.75 3.75 l 1.83 -1.83 Z"));
	        String CheckedSvgPath="M 9 16.17 L 4.83 12 l -1.42 1.41 L 9 19 L 21 7 l -1.41 -1.41 Z";
	        String UncheckedSvgPath="M 19 6.41 L 17.59 5 L 12 10.59 L 6.41 5 L 5 6.41 L 10.59 12 L 5 17.59 L 6.41 19 L 12 13.41 L 17.59 19 L 19 17.59 L 13.41 12 Z";
	        for (ExceptionStatus exception : ListException) {
	            //TreeItem<TreeItemData> ImportItem = createTreeItem(Import);
	            if(exception.DefaultStatus == 0) {
	            	if(exception.CheckedStatus == 0) {
	            	DefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,CheckedSvgPath)));
	            	}
	            	else {
	            		DefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,UncheckedSvgPath)));
	            	}
	            	}
	            else {
	            	if(exception.CheckedStatus == 0) {
		            	NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,CheckedSvgPath)));
		            	}
		            	else {
		            		NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData(exception.ExceptionName,UncheckedSvgPath)));
		            	}
	            }
	        }
	        if(DefaultException.getChildren().isEmpty()) {
	        	DefaultException.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	         if(NotDefaultException.getChildren().isEmpty()) {
	        	NotDefaultException.getChildren().add(new TreeItem<>(new TreeItemData("None","M 12 2 C 6.5 2 2 6.5 2 12 s 4.5 10 10 10 s 10 -4.5 10 -10 S 17.5 2 12 2 Z M 4 12 c 0 -4.4 3.6 -8 8 -8 c 1.8 0 3.5 0.6 4.9 1.7 L 5.7 16.9 C 4.6 15.5 4 13.8 4 12 Z m 8 8 c -1.8 0 -3.5 -0.6 -4.9 -1.7 L 18.3 7.1 C 19.4 8.5 20 10.2 20 12 c 0 4.4 -3.6 8 -8 8 Z")));
	        }
	        rootItem.getChildren().add(DefaultException);
	        rootItem.getChildren().add(NotDefaultException);
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
	                        if(treeItem.getValue().label.equals("DefaultException")) {
	                        svgPath.getStyleClass().setAll("parent-node-default-svg");
	                        }
	                        else {
	                        	svgPath.getStyleClass().setAll("parent-node-notdefault-svg");
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


