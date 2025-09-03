package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DfsClip extends AbstractClip {
    private AbstractNode root;
    private LabelBox[] nodes;

    private List<Integer>[] tree;   
    private List<Integer> dfsOrder; 

    private int step = -1;

    DfsClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        initializeScene();
    }

    @SuppressWarnings("unchecked")
    private void initializeScene() {
        root = new AbstractNode(gc, 0, 0);

        int n = 7; 
        nodes = new LabelBox[n];
        tree = new ArrayList[n];
        dfsOrder = new ArrayList<>();

        for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();

      
        tree[0].add(1);
        tree[0].add(2);
        tree[0].add(3);
        tree[1].add(4);
        tree[1].add(5);
        tree[3].add(6);

        
        double[][] pos = {
            {300, 50},  
            {150, 150}, {300, 150}, {450, 150}, 
            {100, 250}, {200, 250}, {450, 250}  
        };

        for (int i = 0; i < n; i++) {
            nodes[i] = new LabelBox(gc, pos[i][0], pos[i][1], 50, 50, String.valueOf(i));
            root.addChildren(nodes[i]);
        }

        
        boolean[] visited = new boolean[n];
        dfs(0, visited);
    }

    private void dfs(int u, boolean[] visited) {
        visited[u] = true;
        dfsOrder.add(u);
        for (int v : tree[u]) {
            if (!visited[v]) dfs(v, visited);
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for (int u = 0; u < tree.length; u++) {
            for (int v : tree[u]) {
                gc.strokeLine(
                    nodes[u].getX() + 25, nodes[u].getY() + 25,
                    nodes[v].getX() + 25, nodes[v].getY() + 25
                );
            }
        }


        for (LabelBox node : nodes) node.reset();

        for (int i = 0; i <= step && i < dfsOrder.size(); i++) {
            nodes[dfsOrder.get(i)].highlight();
        }

        root.draw();
    }

    @Override
    public void play() {
        step = -1;
        Config.getInstance().time = 0;

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long interval = 700_000_000L; 

            @Override
            public void handle(long now) {
                if (lastUpdate == 0) lastUpdate = now;
                if (now - lastUpdate >= interval) {
                    if (step < dfsOrder.size() - 1) {
                        step++;
                    }
                    lastUpdate = now;
                }
                drawFrame();
            }
        };
        timer.start();
    }
}
