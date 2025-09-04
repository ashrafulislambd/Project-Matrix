package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class BubbleSortClip extends AbstractClip {
    private AbstractNode root;
    private int[] arr = {5, 2, 9, 1, 6};
    private LabelBox[] arrayBoxes;
    private int step = 0;      
    private int index = 0;     

    BubbleSortClip(GraphicsContext gc) {
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

    int[] tempArr = arr.clone();

    for (int pass = 0; pass <= step; pass++) {
       
        for (int j = 0; j < tempArr.length - pass - 1; j++) {
            if (pass == step && j == index) break; 

            if (tempArr[j] > tempArr[j + 1]) {
                int tmp = tempArr[j];
                tempArr[j] = tempArr[j + 1];
                tempArr[j + 1] = tmp;
            }
        }

        for (int i = 0; i < tempArr.length; i++) {
            LabelBox box = new LabelBox(gc, 50 + i * 60, 50 + pass * rowHeight, 50, 50, String.valueOf(tempArr[i]));

            
            if (pass < step && i >= tempArr.length - (pass + 1)) {
                box.setBorderColor(0, 0, 255); 
            }


            if (pass == step && (i == index || i == index + 1)) {
                box.highlight(); 
            }

        
            if (step == arr.length - 1 && pass == step) {
                box.setBorderColor(0, 0, 255);
            }

            box.draw();
        }
    }
}


    @Override
    public void play() {
        step = 0;
        index = 0;

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long interval = 800_000_000L; 

            @Override
            public void handle(long now) {
                if (last == 0) last = now - interval;
                if (now - last > interval) {
                    last = now;

                    index++;
                    if (index >= arr.length - step - 1) { 
                        index = 0;
                        step++;
                    }

                    if (step >= arr.length - 1) { 
                        step = arr.length - 1; 
                        index = 0;
                        stop();
                    }

                    drawFrame();
                }
            }
        };
        timer.start();
    }
}