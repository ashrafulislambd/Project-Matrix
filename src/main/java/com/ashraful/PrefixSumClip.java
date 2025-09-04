package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class PrefixSumClip extends AbstractClip {
    private AbstractNode root;
    private int[] arr = {3, 1, 4, 1, 5, 9};
    private int[] prefix;
    private LabelBox[] arrayBoxes;
    private int step = 0;  // current index being processed

    PrefixSumClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        arrayBoxes = new LabelBox[arr.length];
        prefix = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            arrayBoxes[i] = new LabelBox(gc, 50 + i * 60, 100, 50, 50, String.valueOf(arr[i]));
            root.addChildren(arrayBoxes[i]);
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        int rowHeight = 80;

        // Draw original array
        for (int i = 0; i < arr.length; i++) {
            LabelBox box = new LabelBox(gc, 50 + i * 60, 100, 50, 50, String.valueOf(arr[i]));
            if (i == step && step < arr.length) box.highlight(); // highlight current element
            box.draw();
        }

        // Compute prefix sums up to current step
        for (int i = 0; i <= step && i < arr.length; i++) {
            if (i == 0) prefix[i] = arr[i];
            else prefix[i] = prefix[i - 1] + arr[i];
        }

        // Draw prefix sum row
        for (int i = 0; i <= step && i < arr.length; i++) {
            LabelBox box = new LabelBox(gc, 50 + i * 60, 100 + rowHeight, 50, 50, String.valueOf(prefix[i]));
            if (i == step) box.highlight(); // highlight current prefix sum
            box.draw();
        }
    }

    @Override
    public void play() {
        step = 0;

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long interval = 800_000_000L; // 0.8 second per step

            @Override
            public void handle(long now) {
                if (last == 0) last = now - interval;
                if (now - last > interval) {
                    last = now;
                    drawFrame(); // draw current step

                    step++;
                    if (step >= arr.length) {
                        step = arr.length - 1; // final step
                        stop();
                    }

                    last = now;
                }
            }
        };
        timer.start();
    }
}