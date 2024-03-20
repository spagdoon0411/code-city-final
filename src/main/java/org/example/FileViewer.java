package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/* Example Usage:
// Displays all files from a file path
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileViewer(downloadPath);
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
        exitButton = new JButton("Cancel");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(exitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        displayFiles(textArea, folderPath, 0);
        add(panel);
        setVisible(true);
    }

    private void displayFiles(JTextArea textArea, String path, int indent) {
        File folder = new File(path);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                StringBuilder indentBuilder = new StringBuilder();
                for (int i = 0; i < indent; i++) {
                    indentBuilder.append("  ");
                }
                String indentString = indentBuilder.toString();
                if (file.isDirectory()) {
                    textArea.append(indentString + "[Folder] " + file.getName() + "\n");
                    displayFiles(textArea, file.getAbsolutePath(), indent + 1);
                } else {
                    textArea.append(indentString + "[File] " + file.getName() + "\n");
                }
            }
        } else {
            textArea.append("No files found.");
        }
    }
}