package com.ashraful;

import com.ashraful.shapes.Arrow;
import com.ashraful.shapes.Circle;
import com.ashraful.shapes.Rectangle;
import com.ashraful.shapes.Star;
import com.ashraful.shapes.Triangle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

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
    AnimationManager rotationManager = new AnimationManager();

    GraphicsContext gc;

    int startX = 50, startY = 50, endX, endY;

    @FXML
    void initialize() {
        System.out.println("Hello World");
        gc = canvasMain.getGraphicsContext2D();

        int t = 0;
        for (int i = 1; i <= 15; i++) {
            t += 250;
            Keyframe key1 = new Keyframe(t, 100); // ;, 100);
            Keyframe ykey1 = new Keyframe(t, 100);

            Keyframe redColorKey1 = new Keyframe(t, 255);
            Keyframe greenColorKey1 = new Keyframe(t, 0);
            Keyframe blueColorKey1 = new Keyframe(t, 0);
            t += 250;
            Keyframe key2 = new Keyframe(t, 225); // , 0);
            Keyframe ykey2 = new Keyframe(t, 0);

            Keyframe redColorKey1_5 = new Keyframe(t, 255);
            Keyframe greenColorKey1_5 = new Keyframe(t, 0);
            Keyframe blueColorKey1_5 = new Keyframe(t, 0);

            Keyframe redColorKey2 = new Keyframe(t, 0);
            Keyframe greenColorKey2 = new Keyframe(t, 0);
            Keyframe blueColorKey2 = new Keyframe(t, 255);

            t += 250;
            Keyframe key3 = new Keyframe(t, 350); // , 100);
            Keyframe ykey3 = new Keyframe(t, 100);
            Keyframe redColorKey3 = new Keyframe(t, 0);
            Keyframe greenColorKey3 = new Keyframe(t, 255);
            Keyframe blueColorKey3 = new Keyframe(t, 0);
            t += 250;
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

        Star star = new Star(gc, 150, 150, 40, 20, 255, 255, 23);
        rect.addChildren(star);

        Arrow arrow = new Arrow(gc, 300, 200, 120, 80, 0, 150, 255);
        rect.addChildren(arrow);

        Triangle equilateralTriangle = new Triangle(gc, 200, 150, 80, Triangle.TriangleType.EQUILATERAL, 255, 0, 0);
        Triangle rightTriangle = new Triangle(gc, 350, 200, 60, Triangle.TriangleType.RIGHT, 0, 255, 0);

        rect.addChildren(equilateralTriangle);
        rect.addChildren(rightTriangle);

        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                // int time = Config.getInstance().time;

                startX = animManager.getProperty();
                startY = animYManager.getProperty();

                gc.clearRect(0, 0, canvasMain.getWidth(), canvasMain.getHeight());
                // gc = canvasMain.getGraphicsContext2D();
                int redColorValue = redColorManager.getProperty();
                int greenColorValue = greenColorManager.getProperty();
                int blueColorValue = blueColorManager.getProperty();
                gc.setFill(Color.rgb(redColorValue, greenColorValue, blueColorValue));
                // 0 0 0
                // 100 100 100
                // 150 150 150
                // 255 255 255
                // gc.fillRect(startX, startY, 150, 100);

                circ.setRadius(waveManager.getProperty() % 50);

                rect.setX(animManager.getProperty());
                rect.setY(animYManager.getProperty());
                rect2.setColorR(redColorValue);
                rect2.setColorG(greenColorValue);
                rect2.setColorB(blueColorValue);

                star.setOuterRadius(30 + (waveManager.getProperty() % 20)); // Pulsing effect
                star.setColorR(redColorValue);
                star.setColorG(255 - redColorValue);
                star.setColorB(blueColorValue);

                arrow.setWidth(100 + (waveManager.getProperty() % 50)); // Size animation
                arrow.setColorR(redColorManager.getProperty());
                arrow.setColorG(greenColorManager.getProperty());
                arrow.setColorB(blueColorManager.getProperty());

                equilateralTriangle.setSize(60 + (waveManager.getProperty() % 40));
                equilateralTriangle.setColorR(redColorManager.getProperty());

                rect.setRotation(Config.getInstance().time * 0.5); // THIS IS THE ANGLE OF ROTATION THAT IS CHANGING
                                                                   // WITH TIME FOR SMOOTH ANIMATION

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
