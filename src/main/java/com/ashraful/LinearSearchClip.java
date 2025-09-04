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
    private AnimationManager[] highlightManagers;
    private int foundIndex = -1;

    LinearSearchClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        arrayBoxes = new LabelBox[arr.length];
        highlightManagers = new AnimationManager[arr.length];

        for (int i = 0; i < arr.length; i++) {
            arrayBoxes[i] = new LabelBox(gc, 50 + i * 60, 200, 50, 50, String.valueOf(arr[i]));
            root.addChildren(arrayBoxes[i]);
            
            highlightManagers[i] = new AnimationManager();
            highlightManagers[i].addKeyFrame(new Keyframe(0, 0)); // Not highlighted initially
        }

        setupAnimation();
    }

    private void setupAnimation() {
        int time = 0;
        
        // Simulate linear search to create animation keyframes
        for (int i = 0; i < arr.length && arr[i] != target; i++) {
            // Highlight current element being checked
            highlightManagers[i].addKeyFrame(new Keyframe(time, 1));
            time += 60; // 60 frames between elements
            
            // Remove highlight and keep red if not target
            highlightManagers[i].addKeyFrame(new Keyframe(time, 2));
            
            if (arr[i] == target) {
                foundIndex = i;
                break;
            }
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for (int i = 0; i < arr.length; i++) {
            arrayBoxes[i].reset();
            
            // Apply highlighting based on animation state
            int state = highlightManagers[i].getProperty();
            if (state == 1) {
                arrayBoxes[i].highlight(); // Current element being checked
            } else if (state == 2) {
                arrayBoxes[i].setBorderColor(255, 0, 0); // Already checked
            }
            
            arrayBoxes[i].draw();
        }
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
