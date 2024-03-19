import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/*
Example Usage:
SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileViewer("C:\\Users\\18054\\Downloads\\cityDownload");
            }
        });
 */

public class FileViewer extends JFrame {
    private JPanel panel;
    private JButton confirmButton;
    private JButton exitButton;
    private String folderPath;

    public FileViewer(String folderPath) {
        this.folderPath = folderPath;

        setTitle("File Viewer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        displayFiles(textArea);

        add(panel);
        setVisible(true);
    }

    private void displayFiles(JTextArea textArea) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                textArea.append(file.getName() + "\n");
            }
        } else {
            textArea.append("No files found.");
        }
    }
}
