package com.ashraful.shapes;

import javafx.scene.canvas.GraphicsContext;

public class LabelBox extends AbstractNode {
    double width;
    double height;

    public TextNode label;
    String labelText;

    // Keep references to the rectangles so we can change colors later
    private Rectangle outerRect;
    private Rectangle innerRect;

    public LabelBox(GraphicsContext g, double x, double y, double width, double height, String text) {
        super(g, x, y);

        this.width = width;
        this.height = height;
        this.labelText = text;

        // Same structure as before, but store references
        outerRect = new Rectangle(g, 0, 0, width, height, 255, 0, 0);
        innerRect = new Rectangle(g, 10, 10, width - 20, height - 20, 255, 255, 255);

        this.addChildren(outerRect);
        this.addChildren(innerRect);

        label = new TextNode(g, width / 2, height / 2, text);
        this.addChildren(label);
    }

    /** Change outer border color (RGB) */
    public void setBorderColor(int r, int g, int b) {
        // These setters exist in your Rectangle class
        outerRect.setColorR(r);
        outerRect.setColorG(g);
        outerRect.setColorB(b);
    }

    /** Quick highlight (gold) */
    public void highlight() {
        setBorderColor(255, 215, 0);
    }

    /** Reset to original red */
    public void reset() {
        setBorderColor(255, 0, 0);
    }

    public void setSortedColor() {
    setBorderColor(0, 0, 255); // blue
}

public void setCompareColor() {
    setBorderColor(255, 0, 0); // red
}

}
