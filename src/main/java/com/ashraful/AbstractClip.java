package com.ashraful;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.embed.swing.SwingFXUtils;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class AbstractClip {
    GraphicsContext gc;

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    public abstract void play();
    
    /**
     * Draws a single frame of the animation at the current time.
     * This method should handle all the drawing logic for the current frame,
     * including clearing the canvas and updating positions based on the
     * current time in Config.getInstance().time
     */
    protected abstract void drawFrame();

    /**
     * Generates frames from the animation and saves them as PNG files
     * @param width Width of the output frames
     * @param height Height of the output frames
     * @param duration Duration in milliseconds up to which frames should be generated
     * @param fps Frames per second
     * @param outputFolder Folder where frames will be saved
     */
    public void generateFrames(int width, int height, int duration, int fps, String outputFolder) {
        // Create output directory if it doesn't exist
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Get the canvas from current GraphicsContext
        Canvas canvas = gc.getCanvas();
        
        // Store original dimensions and time
        double originalWidth = canvas.getWidth();
        double originalHeight = canvas.getHeight();
        int originalTime = Config.getInstance().time;
        
        try {
            // Set canvas to requested frame size
            canvas.setWidth(width);
            canvas.setHeight(height);
            
            // In our system, time goes from 0 to duration, incrementing by 1 each frame
            for (int time = 0; time < duration; time++) {
                // Set the current time
                Config.getInstance().time = time;
                
                // Clear canvas
                gc.clearRect(0, 0, width, height);
                
                // Draw the frame
                drawFrame();
                
                // Take snapshot
                WritableImage snapshot = new WritableImage(width, height);
                canvas.snapshot(null, snapshot);
                
                // Save frame (use time as frame number since they're 1:1)
                File outputFile = new File(outputFolder, String.format("frame_%06d.png", time));
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Restore original canvas size and time
            canvas.setWidth(originalWidth);
            canvas.setHeight(originalHeight);
            Config.getInstance().time = originalTime;
        }
    }
}
