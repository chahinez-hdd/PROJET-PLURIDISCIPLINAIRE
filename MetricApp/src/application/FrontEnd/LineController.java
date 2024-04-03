package application.FrontEnd;

import java.io.File;
import java.util.ArrayList;

import application.BackEnd.Line;
import application.FrontEnd.ImportController.CustomTreeCell;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

public class LineController {

    @FXML
    private TreeView<TreeItemData> treeView;
    @FXML
    private Label LineLabel;

    public void initialize(String FilePath) {
        File file = new File(FilePath);
        LineLabel.setText("Number Of Lines Of "+file.getName());
        TreeItemData rootItemData = new TreeItemData("Number Of Line "+Line.CountLineAllLines(file),"M 2 17 h 2 v 0.5 H 3 v 1 h 1 v 0.5 H 2 v 1 h 3 v -4 H 2 v 1 Z m 1 -9 h 1 V 4 H 2 v 1 h 1 v 3 Z m -1 3 h 1.8 L 2 13.1 v 0.9 h 3 v -1 H 3.2 L 5 10.9 V 10 H 2 v 1 Z m 5 -6 v 2 h 14 V 5 H 7 Z m 0 14 h 14 v -2 H 7 v 2 Z m 0 -6 h 14 v -2 H 7 v 2 Z");
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
        treeView.setRoot(rootItem);
        rootItem.getChildren().add(new TreeItem<>(new TreeItemData("Line Of Comment Only "+Line.CountLineCommentOnly(file),"M 21.99 4 c 0 -1.1 -0.89 -2 -1.99 -2 H 4 c -1.1 0 -2 0.9 -2 2 v 12 c 0 1.1 0.9 2 2 2 h 14 l 4 4 l -0.01 -18 Z M 18 14 H 6 v -2 h 12 v 2 Z m 0 -3 H 6 V 9 h 12 v 2 Z m 0 -3 H 6 V 6 h 12 v 2 Z")));
        rootItem.getChildren().add(new TreeItem<>(new TreeItemData("Line Of Cotains Code "+Line.CountLinesContainsCode(file),"m 11.28 3.22 l 4.25 4.25 a 0.75 0.75 0 0 1 0 1.06 l -4.25 4.25 a 0.749 0.749 0 0 1 -1.275 -0.326 a 0.749 0.749 0 0 1 0.215 -0.734 L 13.94 8 l -3.72 -3.72 a 0.749 0.749 0 0 1 0.326 -1.275 a 0.749 0.749 0 0 1 0.734 0.215 Z m -6.56 0 a 0.751 0.751 0 0 1 1.042 0.018 a 0.751 0.751 0 0 1 0.018 1.042 L 2.06 8 l 3.72 3.72 a 0.749 0.749 0 0 1 -0.326 1.275 a 0.749 0.749 0 0 1 -0.734 -0.215 L 0.47 8.53 a 0.75 0.75 0 0 1 0 -1.06 Z")));
        rootItem.getChildren().add(new TreeItem<>(new TreeItemData("Non Bracket Non Empty Line "+Line.CountLineNotEmptyNotBracket(file),"M 14 2 H 6 c -1.1 0 -1.99 0.9 -1.99 2 L 4 20 c 0 1.1 0.89 2 1.99 2 H 18 c 1.1 0 2 -0.9 2 -2 V 8 l -6 -6 Z m 2 16 H 8 v -2 h 8 v 2 Z m 0 -4 H 8 v -2 h 8 v 2 Z m -3 -5 V 3.5 L 18.5 9 H 13 Z")));
        
        
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
                label.setPadding(new Insets(0, 0, 0, 15)); // Example padding: 10px on the right
                // Set the HBox as the graphic content
                setGraphic(hbox);
            }
        }
    }
    
    
    



        
  
}


