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

        MaxSubarrayClip max_sub_clip=new MaxSubarrayClip(gc);
        PrefixSumClip prefix_sum=new PrefixSumClip(gc);
        BubbleSortClip bubble_clip=new BubbleSortClip(gc);
        BinarySearchClip binary_search_clip=new BinarySearchClip(gc);
        DijkstraClip dijkstra_clip=new DijkstraClip(gc);
        LinearSearchClip linear_clip=new LinearSearchClip(gc);
        BfsClip bfs_clip=new BfsClip(gc);
        DfsClip dfs_clip=new DfsClip(gc);
        SelectionSortClip sortClip = new SelectionSortClip(gc);
        //sortClip.generateFrames(800, 600, sortClip.lastT, 20, "hello");
       // VideoUtils.createVideoFromFrames("hello", 40);


        sortClip.play();
       // dfs_clip.play();
       //bfs_clip.play();
      // dijkstra_clip.play();
    // binary_search_clip.play();
     // linear_clip.play();
     // bubble_clip.play();
    //  prefix_sum.play();
       // max_sub_clip.play();
    }
}
