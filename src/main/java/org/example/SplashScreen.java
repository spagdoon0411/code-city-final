import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.JTextComponent;

public class SplashScreen extends JWindow {

    private Image image;
    private JProgressBar progressBar;


    public SplashScreen(String path) {
        this.image = loadImage(path);
        if (image != null) {
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            setSize(width, height); // Set the size of the window to match the image
            setLocationRelativeTo(null); // Center the window on the screen
        }
        setLayout(new GridLayout(2, 1));
        JPanel panel = new JPanel(new BorderLayout());
        // Add empty space on top for the image
        panel.add(new JPanel(), BorderLayout.CENTER);

        // Add the progress bar to the bottom
        progressBar = new JProgressBar(0, 100); // Set the range of progress bar
        progressBar.setForeground(Color.DARK_GRAY);
        progressBar.setBorderPainted(false);
        panel.add(progressBar, BorderLayout.SOUTH);


        // Create a JPanel to hold a list of labels. 
        JPanel scrollPanel = new JPanel(); 
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS)); 
        scrollPanel.setOpaque(false);
        
        // Add a large number of labels to the panel. 
        for (int i = 1; i <= 50; i++) { 
            
            JLabel label = new JLabel("Label " + i); 
            label.setOpaque(false);
            scrollPanel.add(label); 
        } 
        
        // Create a JScrollPane and set the panel as its viewport. 
        JScrollPane scrollPane = new JScrollPane(scrollPanel); 
        setBackground(Color.black);
        panel.setBackground(Color.red);
        add(scrollPane);
        add(panel);// Add the panel to the window
    }

    public void updateProgress(int progress) {
        progressBar.setValue(progress);
        if (progress >= 100) {
            dispose();
        }
    }

    public Boolean isComplete() {
        return progressBar.getValue() >= 100;
    }

    private Image loadImage(String imagePath) {
        try {
            Image image = ImageIO.read(new File(imagePath));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void run(Boolean auto) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        device.setFullScreenWindow(this);
        setVisible(true);

        if (auto) {
            for (int i = 0; i <= 100; i++) {
                try {

                    Thread.sleep(20);
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
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            g.drawImage(image, 0, 0, width, height, null); // Draw the scaled image
            // Set font and color
            g.setFont(new Font("Arial", Font.BOLD, 100)); // Set font type, style, and size
            g.setColor(Color.WHITE); // Set text color

            String text = "Code City";
            int x = this.getX() ; // X-coordinate
            int y = this.getY() + 100; // Y-coordinate
            g.drawString(text, x, y);
        }
    }
}

