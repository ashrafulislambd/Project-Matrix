package com.ashraful.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextNode extends AbstractNode {
    String text;

    public TextNode(GraphicsContext g, double x, double y, String text) {
        super(g, x, y);
        this.text = text;
    }

     public void setText(String newText) {
        this.text = newText;
    }

    @Override
    public void draw() {
        Font textFont = new Font("Arial", 20);

        Text txtTemp = new Text(text);
        txtTemp.setFont(textFont);
        double textWidth = txtTemp.getLayoutBounds().getWidth();
        double textHeight = txtTemp.getLayoutBounds().getHeight();

        double startX = getX() - textWidth / 2;
        double startY = getY() + textHeight / 4;

        g.setFont(textFont);
        g.fillText(text, startX, startY);

        super.draw();
    }
}
