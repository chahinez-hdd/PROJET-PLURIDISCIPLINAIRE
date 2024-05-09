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
        TreeItemData rootItemData = new TreeItemData("Total Number Of Line "+Line.CountLineAllLines(file),"M 2 17 h 2 v 0.5 H 3 v 1 h 1 v 0.5 H 2 v 1 h 3 v -4 H 2 v 1 Z m 1 -9 h 1 V 4 H 2 v 1 h 1 v 3 Z m -1 3 h 1.8 L 2 13.1 v 0.9 h 3 v -1 H 3.2 L 5 10.9 V 10 H 2 v 1 Z m 5 -6 v 2 h 14 V 5 H 7 Z m 0 14 h 14 v -2 H 7 v 2 Z m 0 -6 h 14 v -2 H 7 v 2 Z");
        TreeItem<TreeItemData> rootItem = new TreeItem<>(rootItemData);
        treeView.setRoot(rootItem);
        String StatSvgPath="M 9 17 H 7 v -7 h 2 v 7 Z m 4 0 h -2 V 7 h 2 v 10 Z m 4 0 h -2 v -4 h 2 v 4 Z m 2.5 2.1 h -15 V 5 h 15 v 14.1 Z m 0 -16.1 h -15 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 15 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String RatioSvgPath="M 11 5.08 V 2 C 6 2.5 2 6.81 2 12 s 4 9.5 9 10 v -3.08 c -3 -0.48 -6 -3.4 -6 -6.92 S 8 5.56 11 5.08 Z M 18.97 11 H 22 c -0.47 -5 -4 -8.53 -9 -9 v 3.08 C 16 5.51 18.54 8 18.97 11 Z M 13 18.92 V 22 c 5 -0.47 8.53 -4 9 -9 h -3.03 C 18.54 16 16 18.49 13 18.92 Z";
        String CodeSvgPath="m 11.28 3.22 l 4.25 4.25 a 0.75 0.75 0 0 1 0 1.06 l -4.25 4.25 a 0.749 0.749 0 0 1 -1.275 -0.326 a 0.749 0.749 0 0 1 0.215 -0.734 L 13.94 8 l -3.72 -3.72 a 0.749 0.749 0 0 1 0.326 -1.275 a 0.749 0.749 0 0 1 0.734 0.215 Z m -6.56 0 a 0.751 0.751 0 0 1 1.042 0.018 a 0.751 0.751 0 0 1 0.018 1.042 L 2.06 8 l 3.72 3.72 a 0.749 0.749 0 0 1 -0.326 1.275 a 0.749 0.749 0 0 1 -0.734 -0.215 L 0.47 8.53 a 0.75 0.75 0 0 1 0 -1.06 Z";
        String BlankSvgPath="M 19 5 v 14 H 5 V 5 h 14 m 0 -2 H 5 c -1.1 0 -2 0.9 -2 2 v 14 c 0 1.1 0.9 2 2 2 h 14 c 1.1 0 2 -0.9 2 -2 V 5 c 0 -1.1 -0.9 -2 -2 -2 Z";
        String CurlyBracesSvgPath="M 4 6.25 v 1.5 C 4 8.16 3.66 8.5 3.25 8.5 H 2 v 3 h 1.25 C 3.66 11.5 4 11.84 4 12.25 v 1.5 C 4 14.99 5.01 16 6.25 16 H 8 v -1.5 H 6.25 c -0.41 0 -0.75 -0.34 -0.75 -0.75 v -1.5 c 0 -1.16 -0.88 -2.11 -2 -2.24 V 9.99 c 1.12 -0.12 2 -1.08 2 -2.24 v -1.5 c 0 -0.41 0.34 -0.75 0.75 -0.75 H 8 V 4 H 6.25 C 5.01 4 4 5.01 4 6.25 Z M 16.75 8.5 C 16.34 8.5 16 8.16 16 7.75 v -1.5 C 16 5.01 14.99 4 13.75 4 H 12 v 1.5 h 1.75 c 0.41 0 0.75 0.34 0.75 0.75 v 1.5 c 0 1.16 0.88 2.11 2 2.24 v 0.03 c -1.12 0.12 -2 1.08 -2 2.24 v 1.5 c 0 0.41 -0.34 0.75 -0.75 0.75 H 12 V 16 h 1.75 c 1.24 0 2.25 -1.01 2.25 -2.25 v -1.5 c 0 -0.41 0.34 -0.75 0.75 -0.75 H 18 v -3 H 16.75 Z";
        String CommentSvgPath="M 20 2 H 4 c -1.1 0 -1.99 0.9 -1.99 2 L 2 22 l 4 -4 h 14 c 1.1 0 2 -0.9 2 -2 V 4 c 0 -1.1 -0.9 -2 -2 -2 Z M 9 11 H 7 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z m 4 0 h -2 V 9 h 2 v 2 Z";
        String RatioCommentValue = String.format("%.2f", Line.CommentRation(file));
        String RatioCodeValue = String.format("%.2f", Line.CodeRation(file));
        TreeItem<TreeItemData> CodeStat = new TreeItem<>(new TreeItemData("Code Statistique ",StatSvgPath));
        TreeItem<TreeItemData> CommentStat = new TreeItem<>(new TreeItemData("Comment Statistique ",StatSvgPath));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Code Ratio "+RatioCodeValue,RatioSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Line Of Code "+Line.CountLinesContainsCode(file),CodeSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Empty Line "+Line.CountBlankLine(file),BlankSvgPath)));
        CodeStat.getChildren().add(new TreeItem<>(new TreeItemData("Curly Braces Line "+Line.CountCurlyBracesLine(file),CurlyBracesSvgPath)));     
        
        CommentStat.getChildren().add(new TreeItem<>(new TreeItemData("Comment Ratio "+RatioCommentValue,RatioSvgPath)));
        CommentStat.getChildren().add(new TreeItem<>(new TreeItemData("Line Of Comment Only "+Line.CountLineCommentOnly(file),CommentSvgPath)));
        rootItem.getChildren().add(CodeStat);
        rootItem.getChildren().add(CommentStat);
        
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

