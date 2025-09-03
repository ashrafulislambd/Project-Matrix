package com.ashraful;

import com.ashraful.shapes.AbstractNode;
import com.ashraful.shapes.LabelBox;
import com.ashraful.shapes.Rectangle;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class SelectionSortClip extends AbstractClip {
    public int[] array = {3, 4, 1, 2, 6, 3};
    public int lastT;
    LabelBox[] sortBoxes;
    AnimationManager[] animBoxes;
    AbstractNode root;

    SelectionSortClip(GraphicsContext gc) {
        super();
        super.setGraphicsContext(gc);
        initializeScene();
    }

    private void initializeScene() {
        root = new AbstractNode(gc, 0, 0);
        sortBoxes = new LabelBox[array.length];
        animBoxes = new AnimationManager[array.length];
        
        // Initialize boxes and their initial positions
        for(int i=0; i<array.length; i++) {
            double startX = (i+1)*20 + i*50;
            animBoxes[i] = new AnimationManager();
            animBoxes[i].addKeyFrame(new Keyframe(0, (int)startX));
            sortBoxes[i] = new LabelBox(gc, startX, 100, 50, 50, String.valueOf(array[i]));
            root.addChildren(sortBoxes[i]);
        }

        setupAnimation();
    }

    private void setupAnimation() {
        int t = 0;
        for(int i=0; i<array.length-1; i++) {
            for(int j=i+1; j<array.length; j++) {
                if(array[i] > array[j]) {
                    t += 300;
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;

                    var tmpAnim = animBoxes[i];
                    animBoxes[i] = animBoxes[j];
                    animBoxes[j] = tmpAnim;

                    var tmpBox = sortBoxes[i];
                    sortBoxes[i] = sortBoxes[j];
                    sortBoxes[j] = tmpBox;

                    for(int k=0; k<array.length; k++) {
                        int kJ = (k+1)*20 + k*50;
                        animBoxes[k].addKeyFrame(new Keyframe(t, kJ));
                    }
                }
            }
        }
        lastT = t;
    }

    @Override
    protected void drawFrame() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getWidth());
        int time = Config.getInstance().time;
        for(int i=0; i<sortBoxes.length; i++) {
            sortBoxes[i].setX(animBoxes[i].getProperty());
        }
        root.draw();
    }

    @Override
    public void play() {
        // Reset time to start animation from beginning
        Config.getInstance().time = 0;

        AnimationTimer animTimer = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                drawFrame();
                Config.getInstance().time++;
            }
        };

        animTimer.start();
    }
}
