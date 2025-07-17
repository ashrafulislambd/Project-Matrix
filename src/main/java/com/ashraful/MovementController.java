package com.ashraful;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

class Keyframe {
    int time;
    
    int x, y;

    Keyframe(int time, int x, int y) {
        this.time = time;
        this.x = x;
        this.y = y;
    }
}

class AnimationManager {
    public List<Keyframe> keyframes;

    AnimationManager() {
        keyframes = new ArrayList<Keyframe>();
    }

    public void addKeyFrame(Keyframe keyFrame) {
        keyframes.add(keyFrame);
    }
}

public class MovementController {
    @FXML
    Canvas canvasMain;

    @FXML
    Button buttonDraw;

    AnimationManager animManager = new AnimationManager();

    GraphicsContext gc;

    int startX = 50, startY = 50, endX, endY;

    @FXML
    void initialize() {
        System.out.println("Hello World");
        gc = canvasMain.getGraphicsContext2D();

        int t = 0;
        for(int i=1; i<=15; i++) {
            t+=250;
            Keyframe key1 = new Keyframe(t, 100, 100);
            t+=250;
            Keyframe key2 = new Keyframe(t, 175, 0);
            t+=250;
            Keyframe key3 = new Keyframe(t, 350, 100);
            t+=250;
            Keyframe key4 = new Keyframe(t, 175, 200);
            
            animManager.addKeyFrame(key1);
            animManager.addKeyFrame(key2);
            animManager.addKeyFrame(key3);
            animManager.addKeyFrame(key4);
        }
        draw();
    }

    @FXML
    private void draw() {
        gc = canvasMain.getGraphicsContext2D();

        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                int time = Config.getInstance().time;
                
                Keyframe startKeyFrame = animManager.keyframes.get(0);
                Keyframe endKeyframe = animManager.keyframes.get(animManager.keyframes.size() - 1);
                if(time < startKeyFrame.time) {
                    startX = startKeyFrame.x;
                }

                if(time > endKeyframe.time) {
                    startX = endKeyframe.x;
                }

                for(int i=0; i<animManager.keyframes.size()-1; i++) {
                    Keyframe leftKey = animManager.keyframes.get(i);
                    Keyframe rightKey = animManager.keyframes.get(i+1);

                    if(time >= leftKey.time && time <= rightKey.time) {
                        double leftTime = leftKey.time;
                        double rightTime = rightKey.time;

                        double stX = leftKey.x + (((double)time-leftTime) / (rightTime - leftTime)) * (rightKey.x - leftKey.x);
                        double stY = leftKey.y + (((double)time-leftTime) / (rightTime - leftTime)) * (rightKey.y - leftKey.y);
                        startX = (int)stX;
                        startY = (int)stY;
                    }
                }

                gc.clearRect(0, 0, canvasMain.getWidth(), canvasMain.getHeight());
                //gc = canvasMain.getGraphicsContext2D();
                gc.setFill(Color.BLUE);
                gc.fillRect(startX, startY, 150, 100);
                
                Config.getInstance().time++;
            }
        };

        anim.start();
    }

    @FXML
    void move() {
        startX += 20;
    }
}
