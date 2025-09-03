package com.ashraful;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class AnimationController {
    @FXML
    Canvas canvasMain;

    GraphicsContext gc;

    @FXML
    void initialize() throws Exception {
        gc = canvasMain.getGraphicsContext2D();
        
        SelectionSortClip sortClip = new SelectionSortClip(gc);
        sortClip.generateFrames(800, 600, sortClip.lastT, 20, "hello");
        VideoUtils.createVideoFromFrames("hello", 40);
        sortClip.play();
    }
}
