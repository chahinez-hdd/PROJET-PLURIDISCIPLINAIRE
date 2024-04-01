package application;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.ArrayList;

public class MetricController {

    @FXML
    private TreeView<String> treeView;

    public void initialize(String pathProject) {
        ArrayList<Package> listPackage = new ArrayList<>();
        File projectFile = new File(pathProject);
        File[] srcFile = projectFile.listFiles();
        Java.FetchSrcJavaFile(srcFile, listPackage);
        TreeItem<String> rootItem = new TreeItem<>("Project");
        treeView.setRoot(rootItem);
        for (Package pkg : listPackage) {
            TreeItem<String> packageItem = createTreeItem(pkg);
            rootItem.getChildren().add(packageItem);
        }
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
}
