package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

public class DijkstraClip extends AbstractClip {
    private AbstractNode root;
    private LabelBox[] nodes;
    private List<int[]>[] graph;
    private AnimationManager[] nodeManagers;
    private AnimationManager[] distManagers;

    @SuppressWarnings("unchecked")
    DijkstraClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        int n = 5;
        nodes = new LabelBox[n];
        graph = new ArrayList[n];
        nodeManagers = new AnimationManager[n];
        distManagers = new AnimationManager[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            nodeManagers[i] = new AnimationManager();
            nodeManagers[i].addKeyFrame(new Keyframe(0, 0)); // Not visited
            distManagers[i] = new AnimationManager();
            distManagers[i].addKeyFrame(new Keyframe(0, Integer.MAX_VALUE)); // Initial distance is infinity
        }

        addEdge(0, 1, 4);
        addEdge(0, 2, 1);
        addEdge(2, 1, 2);
        addEdge(1, 3, 1);
        addEdge(2, 3, 5);
        addEdge(3, 4, 3);

        double[][] pos = {
            {100, 100}, {250, 50}, {250, 150}, {400, 100}, {550, 100}
        };
        for (int i = 0; i < n; i++) {
            nodes[i] = new LabelBox(gc, pos[i][0], pos[i][1], 50, 50, "∞");
            root.addChildren(nodes[i]);
        }

        setupAnimation();
    }

    private void addEdge(int u, int v, int w) {
        graph[u].add(new int[]{v, w});
        graph[v].add(new int[]{u, w});
    }

    private void setupAnimation() {
        int time = 0;
        int[] dist = new int[graph.length];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0; // Start from node 0

        // Set initial distance for source node
        distManagers[0].addKeyFrame(new Keyframe(0, 0));

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{0, 0});

        boolean[] visited = new boolean[graph.length];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];
            if (visited[u]) continue;
            visited[u] = true;

            // Highlight current node
            nodeManagers[u].addKeyFrame(new Keyframe(time, 1));
            time += 30;

            // Process edges from current node
            for (int[] e : graph[u]) {
                int v = e[0], w = e[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new int[]{v, dist[v]});
                    
                    // Update distance display for the node
                    distManagers[v].addKeyFrame(new Keyframe(time, dist[v]));
                }
            }

            time += 30; // Additional time between node processing
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // Draw edges
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(2);
        for (int u = 0; u < graph.length; u++) {
            for (int[] e : graph[u]) {
                int v = e[0], w = e[1];
                gc.strokeLine(nodes[u].getX() + 25, nodes[u].getY() + 25,
                              nodes[v].getX() + 25, nodes[v].getY() + 25);
                gc.strokeText(String.valueOf(w),
                              (nodes[u].getX() + nodes[v].getX()) / 2 + 25,
                              (nodes[u].getY() + nodes[v].getY()) / 2 + 25);
            }
        }

        // Draw nodes with current state
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].reset();
            
            // Apply visited highlighting if needed
            if (nodeManagers[i].getProperty() == 1) {
                nodes[i].highlight();
            }
            
            // Update distance text
            int distance = distManagers[i].getProperty();
            nodes[i].label.setText(distance == Integer.MAX_VALUE ? "∞" : String.valueOf(distance));
            
            nodes[i].draw();
        }

        root.draw();
    }

    @Override
    public void play() {
        // Reset time to start animation from beginning
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
