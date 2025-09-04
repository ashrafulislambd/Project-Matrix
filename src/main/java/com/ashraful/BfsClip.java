package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class BfsClip extends AbstractClip {
    private AbstractNode root;
    private LabelBox[] nodes;
    private List<Integer>[] tree;
    private List<Integer> bfsOrder;
    private AnimationManager[] highlightManagers;

    @SuppressWarnings("unchecked")
    BfsClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        int n = 7;
        nodes = new LabelBox[n];
        tree = new ArrayList[n];
        bfsOrder = new ArrayList<>();
        highlightManagers = new AnimationManager[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            highlightManagers[i] = new AnimationManager();
            // Initially all nodes are not highlighted (0)
            highlightManagers[i].addKeyFrame(new Keyframe(0, 0));
        }

        // same tree as DFS
        tree[0].addAll(Arrays.asList(1, 2, 3));
        tree[1].addAll(Arrays.asList(4, 5));
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

        bfs(0);
    }

    private void bfs(int start) {
        boolean[] visited = new boolean[tree.length];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        visited[start] = true;

        int time = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            bfsOrder.add(u);
            
            // Add keyframe to highlight current node
            highlightManagers[u].addKeyFrame(new Keyframe(time, 1));
            time += 100; // 100 frames interval between nodes
            
            for (int v : tree[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    q.add(v);
                }
            }
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw edges
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for (int u = 0; u < tree.length; u++) {
            for (int v : tree[u]) {
                gc.strokeLine(nodes[u].getX() + 25, nodes[u].getY() + 25,
                              nodes[v].getX() + 25, nodes[v].getY() + 25);
            }
        }

        // Reset and update node highlights based on animation managers
        for (LabelBox node : nodes) node.reset();
        for (int i = 0; i < nodes.length; i++) {
            if (highlightManagers[i].getProperty() == 1) {
                nodes[i].highlight();
            }
        }
        root.draw();
    }

    @Override
    public void play() {
        Config.getInstance().time = 0;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                drawFrame();
                Config.getInstance().time++;
            }
        };
        timer.start();
    }
}
