package com.ashraful.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends AbstractNode {
    double size;
    int colorR, colorG, colorB;
    TriangleType type;
    
    public enum TriangleType {
        EQUILATERAL,  
        RIGHT           
    }

    public Triangle(GraphicsContext g, double x, double y, double size, TriangleType type,
             int colorR, int colorG, int colorB) {
        super(g, x, y);
        this.size = size;
        this.type = type;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public void setSize(double size) {
        this.size = size;
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

    public void setType(TriangleType type) {
        this.type = type;
    }

   
    public void draw() {


        applyTransformation();

    
         g.setFill(Color.rgb(colorR, colorG, colorB));
        
        double centerX = getX();
        double centerY = getY();
        
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        
        switch (type) {
            case EQUILATERAL:
                double height = size * Math.sqrt(3) / 2; 
                
        
                xPoints[0] = centerX;                    
                yPoints[0] = centerY - height / 2;
                
                xPoints[1] = centerX - size / 2;         
                yPoints[1] = centerY + height / 2;
                
                xPoints[2] = centerX + size / 2;         
                yPoints[2] = centerY + height / 2;
                break;
                
            case RIGHT:
                
                xPoints[0] = centerX - size / 2;         
                yPoints[0] = centerY + size / 2;
                
                xPoints[1] = centerX - size / 2;         
                yPoints[1] = centerY - size / 2;
                
                xPoints[2] = centerX + size / 2;         
                yPoints[2] = centerY + size / 2;
                break;
        }
        
        g.fillPolygon(xPoints, yPoints, 3);
     
        
        restoreTransformation();
        
        super.draw();
    }
}