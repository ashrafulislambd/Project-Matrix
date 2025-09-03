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
    private int[] dist;
    private List<Integer> visitOrder;
    private int step = -1;

    @SuppressWarnings("unchecked")
    DijkstraClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        int n = 5;
        nodes = new LabelBox[n];
        graph = new ArrayList[n];
        dist = new int[n];
        visitOrder = new ArrayList<>();

        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

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

        runDijkstra(0);
    }

    private void addEdge(int u, int v, int w) {
        graph[u].add(new int[]{v, w});
        graph[v].add(new int[]{u, w});
    }

    private void runDijkstra(int src) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{src, 0});

        boolean[] visited = new boolean[graph.length];

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];
            if (visited[u]) continue;
            visited[u] = true;
            visitOrder.add(u);

            for (int[] e : graph[u]) {
                int v = e[0], w = e[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        // draw edges
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

        for (LabelBox node : nodes) node.reset();

        for (int i = 0; i <= step && i < visitOrder.size(); i++) {
            int u = visitOrder.get(i);
            nodes[u].highlight();
            nodes[u].label.setText(dist[u] == Integer.MAX_VALUE ? "∞" : String.valueOf(dist[u]));
        }

        root.draw();
    }

    @Override
    public void play() {
        step = -1;
        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long interval = 1000_000_000L;
            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                if (now - last > interval) {
                    if (step < visitOrder.size() - 1) step++;
                    last = now;
                }
                drawFrame();
            }
        };
        timer.start();
    }
}
