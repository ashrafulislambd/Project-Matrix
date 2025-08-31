package com.ashraful;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Arrow extends AbstractNode {
    double width, height;
    double arrowHeadWidth; // Width of the arrow head (triangle part)
    int colorR, colorG, colorB;
    
    Arrow(GraphicsContext g, double x, double y, double width, double height,
          int colorR, int colorG, int colorB) {
        super(g, x, y);
        this.width = width;
        this.height = height;
        this.arrowHeadWidth = width * 0.4; // Default arrow head is 40% of total width
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    // Constructor with custom arrow head width
    Arrow(GraphicsContext g, double x, double y, double width, double height,
          double arrowHeadWidth, int colorR, int colorG, int colorB) {
        super(g, x, y);
        this.width = width;
        this.height = height;
        this.arrowHeadWidth = arrowHeadWidth;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setArrowHeadWidth(double arrowHeadWidth) {
        this.arrowHeadWidth = arrowHeadWidth;
    }

    public void setColorR(int colorR) {
        this.colorR = colorR;
    }

    public void setColorG(int colorG) {
        this.colorG = colorG;
    }

    public void setColorB(int colorB) {
        this.colorB = colorB;
    }

    public void draw() {
        g.setFill(Color.rgb(colorR, colorG, colorB));
        
        double centerX = getX();
        double centerY = getY();
        
        // Calculate arrow dimensions
        double shaftWidth = width - arrowHeadWidth;
        double shaftHeight = height * 0.4; // Shaft is 40% of total height
        double shaftY = centerY - shaftHeight / 2;
        
        // Create arrow points (right-pointing arrow)
        double[] xPoints = new double[7];
        double[] yPoints = new double[7];
        
        // Start from top-left of shaft and go clockwise
        xPoints[0] = centerX;                           // Top-left of shaft
        yPoints[0] = shaftY;
        
        xPoints[1] = centerX + shaftWidth;              // Top-right of shaft
        yPoints[1] = shaftY;
        
        xPoints[2] = centerX + shaftWidth;              // Top of arrow head
        yPoints[2] = centerY - height / 2;
        
        xPoints[3] = centerX + width;                   // Arrow tip
        yPoints[3] = centerY;
        
        xPoints[4] = centerX + shaftWidth;              // Bottom of arrow head
        yPoints[4] = centerY + height / 2;
        
        xPoints[5] = centerX + shaftWidth;              // Bottom-right of shaft
        yPoints[5] = shaftY + shaftHeight;
        
        xPoints[6] = centerX;                           // Bottom-left of shaft
        yPoints[6] = shaftY + shaftHeight;
        
        // Draw the arrow using polygon
        g.fillPolygon(xPoints, yPoints, 7);
        
        super.draw();
    }
}