package com.ashraful;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

class Keyframe {
    int time;
    
    int x;

    Keyframe(int time, int x) {
        this.time = time;
        this.x = x;
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

    public int getProperty() {
        int time = Config.getInstance().time;

        Keyframe startKeyFrame = this.keyframes.get(0);
        Keyframe endKeyframe = this.keyframes.get(this.keyframes.size() - 1);

        int X = 0;
        if(time < startKeyFrame.time) {
            X = startKeyFrame.x;
        }

        if(time > endKeyframe.time) {
            X = endKeyframe.x;
        }

        for(int i=0; i<this.keyframes.size()-1; i++) {
            Keyframe leftKey = this.keyframes.get(i);
            Keyframe rightKey = this.keyframes.get(i+1);

            if(time >= leftKey.time && time <= rightKey.time) {
                double leftTime = leftKey.time;
                double rightTime = rightKey.time;

                double stX = leftKey.x + (((double)time-leftTime) / (rightTime - leftTime)) * (rightKey.x - leftKey.x);
                X = (int)stX;
            }
        }

        return X;
    }
}

public class MovementController {
    @FXML
    Canvas canvasMain;

    @FXML
    Button buttonDraw;

    AnimationManager animManager = new AnimationManager();
    AnimationManager animYManager = new AnimationManager();

    AnimationManager redColorManager = new AnimationManager();
    AnimationManager greenColorManager = new AnimationManager();
    AnimationManager blueColorManager = new AnimationManager();

    AnimationManager waveManager = new AnimationManager();

    GraphicsContext gc;

    int startX = 50, startY = 50, endX, endY;

    @FXML
    void initialize() {
        System.out.println("Hello World");
        gc = canvasMain.getGraphicsContext2D();

        int t = 0;
        for(int i=1; i<=15; i++) {
            t+=250;
            Keyframe key1 = new Keyframe(t, 100); //;, 100);
            Keyframe ykey1 = new Keyframe(t, 100);

            Keyframe redColorKey1 = new Keyframe(t, 255);
            Keyframe greenColorKey1 = new Keyframe(t, 0);
            Keyframe blueColorKey1 = new Keyframe(t, 0);
            t+=250;
            Keyframe key2 = new Keyframe(t, 225); //, 0); 
            Keyframe ykey2 = new Keyframe(t, 0);

            Keyframe redColorKey1_5 = new Keyframe(t, 255);
            Keyframe greenColorKey1_5 = new Keyframe(t, 0);
            Keyframe blueColorKey1_5 = new Keyframe(t, 0);

            Keyframe redColorKey2 = new Keyframe(t, 0);
            Keyframe greenColorKey2 = new Keyframe(t, 0);
            Keyframe blueColorKey2 = new Keyframe(t, 255);

            t+=250;
            Keyframe key3 = new Keyframe(t, 350); //, 100);
            Keyframe ykey3 = new Keyframe(t, 100);
            Keyframe redColorKey3 = new Keyframe(t, 0);
            Keyframe greenColorKey3 = new Keyframe(t, 255);
            Keyframe blueColorKey3 = new Keyframe(t, 0);
            t+=250;
            Keyframe key4 = new Keyframe(t, 225); // , 200);
            Keyframe ykey4 = new Keyframe(t, 200);

            Keyframe redColorKey4 = new Keyframe(t, 0);
            Keyframe greenColorKey4 = new Keyframe(t, 0);
            Keyframe blueColorKey4 = new Keyframe(t, 0);
            
            animManager.addKeyFrame(key1);
            animManager.addKeyFrame(key2);
            animManager.addKeyFrame(key3);
            animManager.addKeyFrame(key4);

            animYManager.addKeyFrame(ykey1);
            animYManager.addKeyFrame(ykey2);
            animYManager.addKeyFrame(ykey3);
            animYManager.addKeyFrame(ykey4);

            redColorManager.addKeyFrame(redColorKey1);
            redColorManager.addKeyFrame(redColorKey1_5);
            redColorManager.addKeyFrame(redColorKey2);
            redColorManager.addKeyFrame(redColorKey3);
            redColorManager.addKeyFrame(redColorKey4);

            greenColorManager.addKeyFrame(greenColorKey1);
            greenColorManager.addKeyFrame(greenColorKey1_5);
            greenColorManager.addKeyFrame(greenColorKey2);
            greenColorManager.addKeyFrame(greenColorKey3);
            greenColorManager.addKeyFrame(greenColorKey4);

            blueColorManager.addKeyFrame(blueColorKey1);
            blueColorManager.addKeyFrame(blueColorKey1_5);
            blueColorManager.addKeyFrame(blueColorKey2);
            blueColorManager.addKeyFrame(blueColorKey3);
            blueColorManager.addKeyFrame(blueColorKey4);

            waveManager.addKeyFrame(new Keyframe(0, 0));
            waveManager.addKeyFrame(new Keyframe(10000, 10050));
        }
        draw();
    }

    @FXML
    private void draw() {
        gc = canvasMain.getGraphicsContext2D();

        Rectangle rect = new Rectangle(gc, 10, 10, 100, 100, 255, 0, 0);
        Rectangle rect2 = new Rectangle(gc, 10, 10, 100, 100, 0, 255, 0);

        rect.addChildren(rect2);

        Circle circ = new Circle(gc, 100, 100, 50, 0, 0, 150);
        rect2.addChildren(circ);

        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                //int time = Config.getInstance().time;

                startX = animManager.getProperty();
                startY = animYManager.getProperty();

                gc.clearRect(0, 0, canvasMain.getWidth(), canvasMain.getHeight());
                //gc = canvasMain.getGraphicsContext2D();
                int redColorValue = redColorManager.getProperty();
                int greenColorValue = greenColorManager.getProperty();
                int blueColorValue = blueColorManager.getProperty();
                gc.setFill(Color.rgb(redColorValue, greenColorValue, blueColorValue));
                // 0 0 0
                // 100 100 100
                // 150 150 150
                // 255 255 255
                //gc.fillRect(startX, startY, 150, 100);

                circ.setRadius(waveManager.getProperty()%50);

                rect.setX(animManager.getProperty());
                rect.setY(animYManager.getProperty());
                rect2.setColorR(redColorValue);
                rect2.setColorG(greenColorValue);
                rect2.setColorB(blueColorValue);
                rect.draw();
                
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
