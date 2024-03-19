package org.example;

import javax.swing.*;
import java.awt.*;

/* 
*   By: Eric Berber
*/
class ImagePanel extends JPanel {

    private final Image img;

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size); // Set Panel size to image dimensions
        setLayout(null); // Center Screen on Panel
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null); // Draw the image with scaled size
    }

}
