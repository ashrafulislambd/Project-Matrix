package com.ashraful;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class AbstractNode {
    double x, y;

    AbstractNode parent;

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
}
