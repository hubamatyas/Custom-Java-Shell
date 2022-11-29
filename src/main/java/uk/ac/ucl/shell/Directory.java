package uk.ac.ucl.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Directory {
    private static Directory instance;
    private String currentDirectory;
    private final Charset encoding;
    private final String fileSeparator;

    private Directory() {
        this.encoding = StandardCharsets.UTF_8;
        this.fileSeparator = File.separator;
        this.currentDirectory = System.getProperty("user.dir");
    }

    // Singleton class
    public static Directory getInstance() {
        if (instance == null) {
            instance = new Directory();
        }
        return instance;
    }

    public String getCurrentDirectory() {
        return this.currentDirectory;
    }

    public void setCurrentDirectory(String newDirectory) {
        this.currentDirectory = String.valueOf(getPathTo(newDirectory));
    }

    public ArrayList<File> getContent(String appName, String arg) {
        checkDirectoryToHandle(appName, arg);
        File directory = new File(String.valueOf(getPathTo(arg)));
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
    }

    public ArrayList<String> getSubDirectories(String appName, String arg) {
        ArrayList<String> subDirs = new ArrayList<>();
        for (File file : getContent(appName, arg)) {
            if (file.isDirectory()) {
                subDirs.add(file.getName());
            }
        }
        return subDirs;
    }

    public ArrayList<String> getFiles(String appName, String arg) {
        ArrayList<String> files = new ArrayList<>();
        for (File file : getContent(appName, arg)) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }
        return files;
    }

    public BufferedReader createBufferedReader(String appName, String fileName) {
        try {
            return Files.newBufferedReader(getPathTo(fileName), this.encoding);
        }
        catch (IOException e) {
            throw new RuntimeException(appName + ": cannot open " + fileName);
        }
    }

    public void writeLine(String line, OutputStreamWriter writer, String separator) throws IOException {
        writer.write(line);
        writer.write(separator);
        writer.flush();
    }

    public void checkFileToHandle(String appName, String fileName) {
        if (!existsFile(fileName)) {
            throw new RuntimeException(appName + ": " + fileName + " does not exist");
        }
    }

    public void checkDirectoryToHandle(String appName, String directoryName) {
        if (!existsDirectory(directoryName)) {
            throw new RuntimeException(appName + ": " + directoryName + " directory does not exist");
        }
    }

    private Path getPathTo(String arg) {
        return Paths.get(this.currentDirectory + this.fileSeparator + arg);
    }

    private boolean existsFile(String arg) {
        return new File(String.valueOf(getPathTo(arg))).exists();
    }

    private boolean existsDirectory(String arg) {
        return new File(String.valueOf(getPathTo(arg))).isDirectory();
    }
}
