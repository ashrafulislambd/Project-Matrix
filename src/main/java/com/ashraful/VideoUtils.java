package com.ashraful;

import java.io.File;
import java.io.IOException;

public class VideoUtils {
    /**
     * Creates a video from a directory of PNG frames using FFmpeg
     * @param framesDirectory Directory containing the PNG frames (named frame_XXXXXX.png)
     * @param fps Frames per second for the output video
     * @return Path to the created video file
     * @throws IOException if FFmpeg fails or is not available
     */
    public static String createVideoFromFrames(String framesDirectory, int fps) throws IOException {
        String outputPath = new File(framesDirectory, "output.mp4").getAbsolutePath();
        
        // Count total frames
        int totalFrames = new File(framesDirectory).list((dir, name) -> name.toLowerCase().endsWith(".png")).length;
        
        // Build FFmpeg command
        ProcessBuilder pb = new ProcessBuilder(
            "ffmpeg",
            "-y", // Overwrite output file if it exists
            "-framerate", String.valueOf(fps),
            "-i", new File(framesDirectory, "frame_%06d.png").getAbsolutePath(),
            "-c:v", "libx264",
            "-pix_fmt", "yuv420p",
            "-crf", "23", // Quality setting (0-51, lower is better)
            "-progress", "-", // Show progress on stderr
            "-stats", // Enable detailed statistics
            outputPath
        );

        // Redirect error stream to capture progress
        pb.redirectErrorStream(true);
        
        // Start the process
        Process process = pb.start();
        
        System.out.println("Starting video conversion...");
        
        // Create a thread to read progress and ensure process completion
        Thread processThread = new Thread(() -> {
            try (java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()))) {
                
                String line;
                int framesDone = 0;
                long lastUpdateTime = System.currentTimeMillis();
                
                while ((line = reader.readLine()) != null) {
                    if (line.contains("frame=")) {
                        try {
                            // Extract frame number
                            String frameStr = line.substring(line.indexOf("frame=")).split("\\s+")[1];
                            framesDone = Integer.parseInt(frameStr);
                            
                            // Update progress at most once every 500ms
                            long now = System.currentTimeMillis();
                            if (now - lastUpdateTime >= 500) {
                                int percentage = (framesDone * 100) / totalFrames;
                                System.out.print("\rConverting: [");
                                int progressBars = percentage / 2;
                                for (int i = 0; i < 50; i++) {
                                    System.out.print(i < progressBars ? "=" : " ");
                                }
                                System.out.printf("] %d%% (%d/%d frames)", 
                                    percentage, framesDone, totalFrames);
                                lastUpdateTime = now;
                            }
                        } catch (Exception e) {
                            // Ignore parsing errors
                        }
                    }
                }
                
                // Ensure we show 100% at the end
                System.out.print("\rConverting: [");
                for (int i = 0; i < 50; i++) System.out.print("=");
                System.out.printf("] 100%% (%d/%d frames)\n", totalFrames, totalFrames);
                
            } catch (IOException e) {
                System.err.println("\nError reading process output: " + e.getMessage());
            }
        });
        
        // Start the progress monitoring thread
        processThread.start();
        
        try {
            // Wait for FFmpeg to finish
            int exitCode = process.waitFor();
            
            // Wait for progress thread to finish
            processThread.join(2000); // Wait up to 2 seconds for thread to finish
            
            if (exitCode != 0) {
                throw new IOException("FFmpeg process failed with exit code: " + exitCode);
            }
            
            System.out.println("Video conversion completed successfully!");
            System.out.println("Output saved to: " + outputPath);
            
        } catch (InterruptedException e) {
            process.destroyForcibly();
            throw new IOException("FFmpeg process was interrupted", e);
        }
        
        return outputPath;
    }
}
