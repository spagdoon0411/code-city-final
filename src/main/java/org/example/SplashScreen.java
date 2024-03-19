package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/* 
*   By: Eric Berber
*   Usage(Auto): 
*
*   splashScreen.run(true);
*   splashScreen.remove();
*
*   Usage(Non-Auto):
*   splashScreen.run(false);
*   splashScreen.updateProgress(50);
*   splashScreen.updateProgress(100);
*   splashScreen.remove();
*/
public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private int SLEEP_INTERVAL = 20; // Ms timing for auto run

    public SplashScreen(String path) {

        setLayout(new BorderLayout());

        ImagePanel imagePanel = new ImagePanel(path);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.DARK_GRAY);
        progressBar.setBorderPainted(false);
        progressBar.setStringPainted(false);

        add(imagePanel);
        add(progressBar, BorderLayout.SOUTH);
    }

    public void updateProgress(int progress) {
        progressBar.setValue(progress);
    }

    public Boolean isComplete() {
        return progressBar.getValue() >= 100;
    }

    public Boolean isRunning() {
        return progressBar.getValue() < 100;
    }

    public void remove() {
        dispose();
    }

    public void run(Boolean auto) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(this);
        setVisible(true);

        if (auto) {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(SLEEP_INTERVAL);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                updateProgress(i);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);

        g.setFont(new Font("Ariel", Font.PLAIN, 100));
        g.drawString("Code City", 0, 100);
    }

}
