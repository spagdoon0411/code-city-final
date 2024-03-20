package org.example;

import javax.swing.*;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        String repoUrl = JOptionPane.showInputDialog("GitHub repository:");
        String downloadPath = JOptionPane.showInputDialog("Absolute download path:");
        if(repoUrl == null || downloadPath == null) {
            exit(0);
        }
        FileDownloader fileDownloader = new FileDownloader(repoUrl, downloadPath);
        fileDownloader.downloadFiles();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FileViewer(downloadPath);
            }
        });
    }
}