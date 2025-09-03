package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class MaxSubarrayClip extends AbstractClip {
    private AbstractNode root;
    private int[] arr = {3, -2, 5, -1, 4, -3, 2};
    private LabelBox[] arrayBoxes;

    private int step = 0;     
    private int currentSum = 0;
    private int maxSum = Integer.MIN_VALUE;
    private int startIndex = 0; 
    private int maxStart = 0;   
    private int maxEnd = 0;     

    MaxSubarrayClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        root = new AbstractNode(gc, 0, 0);

        arrayBoxes = new LabelBox[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrayBoxes[i] = new LabelBox(gc, 50 + i * 60, 100, 50, 50, String.valueOf(arr[i]));
            root.addChildren(arrayBoxes[i]);
        }
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        int rowHeight = 80;

    
        for (int i = 0; i < arr.length; i++) {
            LabelBox box = new LabelBox(gc, 50 + i * 60, 100, 50, 50, String.valueOf(arr[i]));
            
            if (i >= step - 1 && i <= step && i >= startIndex) box.highlight();
            
            if (i >= maxStart && i <= maxEnd) box.setBorderColor(0, 0, 255);
            
            box.draw();
        }

        String info = "Current Sum: " + currentSum + "   Max Sum: " + maxSum;
        LabelBox infoBox = new LabelBox(gc, 50, 100 + rowHeight, arr.length * 60, 50, info);
        infoBox.draw();
    }

    @Override
    public void play() {
        step = 0;
        currentSum = 0;
        maxSum = Integer.MIN_VALUE;
        startIndex = 0;
        maxStart = 0;
        maxEnd = 0;

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long interval = 1000_000_000L; 

            @Override
            public void handle(long now) {
                if (last == 0) last = now - interval;
                if (now - last > interval) {
                    last = now;

                    if (step >= arr.length) {
                        stop();
                        return;
                    }

                    if (currentSum + arr[step] > arr[step]) {
                        currentSum += arr[step];
                    } else {
                        currentSum = arr[step];
                        startIndex = step;
                    }

                    if (currentSum > maxSum) {
                        maxSum = currentSum;
                        maxStart = startIndex;
                        maxEnd = step;
                    }

                    drawFrame();
                    step++;
                }
            }
        };
        timer.start();
    }
}
