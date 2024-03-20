package org.example;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;

/* Example usage:
// Downloads all files and folders from public GitHub repository
        FileDownloader fileDownloader = new FileDownloader(repoUrl, downloadPath);
        fileDownloader.downloadFiles();
*/
public class FileDownloader {

    private File folder;
    private String remoteURL;
    private String folderDownloadPath;

    /**
     * Fetches the files and folders from the remote GitHub repo and places them in a directory
     * defined by the user. Creates the directory if it does not exist.
     * @param remoteURL
     * @param directoryPath
     */
    public FileDownloader(String remoteURL, String directoryPath) {
        this.folderDownloadPath = directoryPath;
        this.remoteURL = remoteURL;
    }

    public void downloadFiles() {
        // Make a folder for download if it doesn't exist
        Path folderPath = Path.of(this.folderDownloadPath);
        if (!Files.exists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        this.folder = new File(this.folderDownloadPath);
        this.downloadFromUrl("");
    }

    private void downloadFromUrl(String path) {
        String url = "https://api.github.com/repos/" + this.remoteURL.substring(19) + "/contents" + path;
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            // Get a list of files and folders from the given repository
            HttpRequest fileListRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> fileListResponse = httpClient.send(fileListRequest, HttpResponse.BodyHandlers.ofString());
            if (fileListResponse.statusCode() == 200) {
                String fileListJson = fileListResponse.body();
                String[] entries = fileListJson.split("\\{");
                for (int i = 1; i < entries.length; i++) {
                    String entry = "{" + entries[i];
                    int typeIndex = entry.indexOf("\"type\":\"");
                    if (typeIndex != -1) {
                        String type = entry.substring(typeIndex + 8, entry.indexOf("\",", typeIndex));
                        if (type.equals("file")) {
                            int downloadUrlIndex = entry.indexOf("\"download_url\":\"");
                            if (downloadUrlIndex != -1) {
                                String downloadUrl = entry.substring(downloadUrlIndex + 16, entry.indexOf("\",", downloadUrlIndex)).replace("\\", "");
                                downloadFile(httpClient, downloadUrl, path);
                            }
                        } else if (type.equals("dir")) {
                            int nameIndex = entry.indexOf("\"name\":\"");
                            if (nameIndex != -1) {
                                String folderName = entry.substring(nameIndex + 8, entry.indexOf("\",", nameIndex));
                                String folderPath = path + "/" + folderName;
                                downloadFromUrl(folderPath);
                            }
                        }
                    }
                }
            } else {
                System.err.println("Failed to fetch file list. Status code: " + fileListResponse.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(HttpClient httpClient, String downloadUrl, String path) {
        try {
            HttpRequest downloadRequest = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .build();
            HttpResponse<byte[]> downloadResponse = httpClient.send(downloadRequest, HttpResponse.BodyHandlers.ofByteArray());
            if (downloadResponse.statusCode() == 200) {
                // Get the file name from the URL
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
                // Create the necessary directories if they don't exist
                Path filePath = Path.of(folderDownloadPath + path, fileName);
                Path parentPath = filePath.getParent();
                if (!Files.exists(parentPath)) {
                    Files.createDirectories(parentPath);
                }
                // Download the file to the local machine's specified folder
                Files.write(filePath, downloadResponse.body(), StandardOpenOption.CREATE);
                System.out.println("Downloaded: " + filePath);
            } else {
                System.err.println("Failed to download file. Status code: " + downloadResponse.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getFolder() {
        return this.folder;
    }
}