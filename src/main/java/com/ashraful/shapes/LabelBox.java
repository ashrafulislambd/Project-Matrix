package com.ashraful.shapes;

import javafx.scene.canvas.GraphicsContext;

public class LabelBox extends AbstractNode {
    double width;
    double height;

    TextNode label;

    String labelText;

    public LabelBox(GraphicsContext g, double x, double y, double width, double height, String text) {
        super(g, x, y);
        this.addChildren(new Rectangle(g, 0, 0, width, height, 255, 0, 0));
        this.addChildren(new Rectangle(g, 10,10, width - 20, height - 20, 255, 255, 255));
        
        this.width = width;
        this.height = height;
        this.labelText = text;
        label = new TextNode(g, width/2, height/2, text);
        
        this.addChildren(label);
    }
}
