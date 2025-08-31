package com.ashraful;

import java.util.ArrayList;
import javafx.scene.transform.Rotate;
import javafx.scene.canvas.GraphicsContext;

public class AbstractNode {
    double x, y;

    AbstractNode parent;
    protected double rotation = 0; //this is the rotation in degree. i mean angle
    ArrayList<AbstractNode> children = new ArrayList<>();

    GraphicsContext g;

    AbstractNode(GraphicsContext g, double x, double y) {
        this.g = g;
        this.x = x;
        this.y = y;
    }

    public void setParent(AbstractNode parent) {
        this.parent = parent;
    }

    public double getX() {
        if(parent != null) {
            return parent.getX() + x;
        }
        return x;
    }

    public double getY() {
        if(parent != null) {
            return parent.getY() + y;
        }
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void draw() {
        for(AbstractNode node : children) {
            node.draw();
        }
    }

    public void addChildren(AbstractNode node) {
        this.children.add(node);
        node.setParent(this);
    }

     public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

      protected void applyTransformation() {
    
        g.save();
        
        if (rotation != 0) {
            g.translate(x, y);
            g.rotate(rotation);
            g.translate(-x, -y);
        }
    }

     protected void restoreTransformation() {
        g.restore();
    }

}
