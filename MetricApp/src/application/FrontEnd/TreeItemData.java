package application.FrontEnd;

import javafx.scene.shape.SVGPath;

public class TreeItemData {
    String label;
    SVGPath svgPath;

    TreeItemData(String label, String svgPathContent) {
        this.label = label;
        this.svgPath = new SVGPath();
        if (svgPathContent != null) {
            this.svgPath.setContent(svgPathContent);
        }
    }

    String GetLabel() {
        return this.label;
    }

    SVGPath GetSVG() {
        return this.svgPath;
    }
}