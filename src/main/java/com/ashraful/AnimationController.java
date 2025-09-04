package com.ashraful;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.image.WritableImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

public class AnimationController {
    @FXML
    Canvas canvasMain;

    @FXML
    ComboBox<String> cmbAlgorithms;

    @FXML
    Button btnPlay;

    @FXML
    Button btnExportVideo;

    @FXML
    ProgressBar progressVideoExport;

    GraphicsContext gc;
    AbstractClip currentClip;

    @FXML
    void initialize() {
        gc = canvasMain.getGraphicsContext2D();

        // Initialize the ComboBox with algorithm choices
        cmbAlgorithms.setItems(FXCollections.observableArrayList(
            "Max Subarray",
            "Prefix Sum",
            "Bubble Sort",
            "Binary Search",
            "Dijkstra",
            "Linear Search",
            "BFS",
            "DFS",
            "Selection Sort"
        ));

        // Set up the play button action
        btnPlay.setOnAction(event -> playSelectedAlgorithm());

        // Set up the export video button action
        btnExportVideo.setOnAction(event -> exportVideo());
    }

    private void playSelectedAlgorithm() {
        if (cmbAlgorithms.getValue() == null) {
            return;
        }

        // Stop previous animation if any
        if (currentClip != null) {
            currentClip.stop();
        }

        // Reset animation time
        Config.getInstance().reset();

        // Clear previous animation
        gc.clearRect(0, 0, canvasMain.getWidth(), canvasMain.getHeight());

        // Create and play the selected algorithm animation
        switch (cmbAlgorithms.getValue()) {
            case "Max Subarray":
                currentClip = new MaxSubarrayClip(gc);
                break;
            case "Prefix Sum":
                currentClip = new PrefixSumClip(gc);
                break;
            case "Bubble Sort":
                currentClip = new BubbleSortClip(gc);
                break;
            case "Binary Search":
                currentClip = new BinarySearchClip(gc);
                break;
            case "Dijkstra":
                currentClip = new DijkstraClip(gc);
                break;
            case "Linear Search":
                currentClip = new LinearSearchClip(gc);
                break;
            case "BFS":
                currentClip = new BfsClip(gc);
                break;
            case "DFS":
                currentClip = new DfsClip(gc);
                break;
            case "Selection Sort":
                currentClip = new SelectionSortClip(gc);
                break;
        }

        if (currentClip != null) {
            currentClip.play();
        }
    }

    private void exportVideo() {
        if (currentClip == null) {
            return;
        }

        // Create file chooser for saving the video
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Video");
        fileChooser.getExtensionFilters().add(
            new ExtensionFilter("MP4 files (*.mp4)", "*.mp4")
        );

        // Show save dialog
        File outputFile = fileChooser.showSaveDialog(canvasMain.getScene().getWindow());
        if (outputFile == null) {
            return;
        }

        try {
            // Create temporary directory for frames
            Path tempDir = Files.createTempDirectory("animation_frames");
            String framesPath = tempDir.toString();

            // Start frame generation in a background thread
            Thread exportThread = new Thread(() -> {
                try {
                    // Generate frames (assuming 30fps and using the current clip's last time)
                    Platform.runLater(() -> progressVideoExport.setProgress(0.0));
                    
                    int duration = currentClip instanceof SelectionSortClip ? 
                        ((SelectionSortClip)currentClip).lastT : 1200;
                    
                    // Generate each frame on the JavaFX thread
                    for (int time = 0; time < duration; time++) {
                        final int currentTime = time;
                        CountDownLatch latch = new CountDownLatch(1);
                        
                        Platform.runLater(() -> {
                            try {
                                // Set time and clear canvas
                                Config.getInstance().time = currentTime;
                                gc.clearRect(0, 0, 800, 600);
                                
                                // Draw the frame
                                currentClip.drawFrame();
                                
                                // Take snapshot
                                WritableImage snapshot = new WritableImage(800, 600);
                                canvasMain.snapshot(null, snapshot);
                                
                                // Save frame
                                File outputFrame = new File(framesPath, String.format("frame_%06d.png", currentTime));
                                javax.imageio.ImageIO.write(
                                    javafx.embed.swing.SwingFXUtils.fromFXImage(snapshot, null),
                                    "png",
                                    outputFrame
                                );
                                
                                // Update progress (0.0 to 0.5 for frame generation)
                                progressVideoExport.setProgress(currentTime / (double)(duration * 2));
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        });
                        
                        // Wait for frame to be generated
                        latch.await();
                    }
                    
                    Platform.runLater(() -> progressVideoExport.setProgress(0.5));

                    // Convert frames to video
                    VideoUtils.createVideoFromFrames(framesPath, 30);
                    
                    // Copy final video to chosen location
                    Files.copy(
                        Path.of(framesPath, "output.mp4"),
                        outputFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );

                    // Clean up temp directory
                    Files.walk(tempDir)
                        .map(Path::toFile)
                        .forEach(File::delete);
                    Files.delete(tempDir);

                    Platform.runLater(() -> progressVideoExport.setProgress(1.0));
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> progressVideoExport.setProgress(0.0));
                }
            });

            exportThread.start();
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> progressVideoExport.setProgress(0.0));
        }
    }
}
