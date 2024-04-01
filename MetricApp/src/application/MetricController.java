package application;

import javafx.fxml.FXML;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;

import javafx.util.Callback;

public class MetricController {

    @FXML
    private TreeView<String> treeView;

    public void initialize(String pathProject) {
        ArrayList<Package> listPackage = new ArrayList<>();
        File projectFile = new File(pathProject);
        File[] srcFile = projectFile.listFiles();
        Java.FetchSrcJavaFile(srcFile, listPackage);
        TreeItem<String> rootItem = new TreeItem<>("Src Folder");
        treeView.setRoot(rootItem);
        for (Package pkg : listPackage) {
            TreeItem<String> packageItem = createTreeItem(pkg);
            rootItem.getChildren().add(packageItem);
        }
        setTreeViewStyle();
    }

    


    private TreeItem<String> createTreeItem(Package pkg) {
        TreeItem<String> packageItem = new TreeItem<>(pkg.PackageName);
        
        for (Package subPackage : pkg.SubPackges) {
            TreeItem<String> subPackageItem = createTreeItem(subPackage);
            packageItem.getChildren().add(subPackageItem);
        }
        for (FileInfo fileInfo : pkg.FileList) {
            packageItem.getChildren().add(new TreeItem<>(fileInfo.FileName));
        }
        return packageItem;
    }
    private void setTreeViewStyle() {
    	treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
    	    @Override
    	    public TreeCell<String> call(TreeView<String> param) {
    	        return new CustomTreeCell();
    	    }
    	});

    }

    // Custom TreeCell to style parent nodes
 // Custom TreeCell to style parent and child nodes
    private class CustomTreeCell extends TreeCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && item != null) {
                TreeItem<String> treeItem = getTreeItem();
                if (treeItem != null) {
                	if(treeItem.getParent()==null) {
                		setStyle("-fx-font-size: 25px; -fx-text-fill: red;");
                	}
                	
                	else if (!treeItem.isLeaf()) {
                        // Apply style to parent nodes
                        setStyle("-fx-font-size: 25px; -fx-text-fill: blue;"); // Example style
                    } else {
                        // Apply style to leaf nodes
                        setStyle("-fx-font-size: 20px; -fx-text-fill: green;"); // Example style
                    }
                } else {
                    // Clear style for empty cells
                    setStyle(null);
                }
                setText(item);
            } else {
                // Clear style for empty cells
                setText(null);
                setStyle(null);
            }
        }
    }

}
