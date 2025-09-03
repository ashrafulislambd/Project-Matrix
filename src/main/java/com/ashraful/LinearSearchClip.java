package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class LinearSearchClip extends AbstractClip {
    private AbstractNode root;
    private LabelBox[] arrayBoxes;
    private int[] arr = {1, 3, 5, 7, 9, 11, 13};
    private int target = 9;

    private int step = -1; 

    LinearSearchClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        arrayBoxes = new LabelBox[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrayBoxes[i] = new LabelBox(gc, 50 + i * 60, 200, 50, 50, String.valueOf(arr[i]));
            root.addChildren(arrayBoxes[i]);
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        int rowHeight = 60;

        for (int s = 0; s <= step; s++) {

            for (int i = 0; i < arr.length; i++) {
                LabelBox box = new LabelBox(gc, 50 + i * 60, 50 + s * rowHeight, 50, 50, String.valueOf(arr[i]));
                if (i == s) box.highlight(); 
                box.draw();
            }
        }
    }

    @Override
    public void play() {
        step = -1;

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long interval = 1000_000_000L; 

            @Override
            public void handle(long now) {
                if (last == 0) last = now - interval; 
                if (now - last > interval) {
                    step++;
                    last = now;

                    if (step >= arr.length || arr[step] == target) {
                        stop(); 
                    }

                    drawFrame();
                }
            }
        };
        timer.start();
    }
}
