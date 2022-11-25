package uk.ac.ucl.shell;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Directory {
    private static Directory instance;
    private Charset encoding;
    private String currentDirectory;
    private String separator;

    private Directory() {
        encoding = StandardCharsets.UTF_8;
        separator = File.separator;
        currentDirectory = System.getProperty("user.dir");
    }

    // Singleton class
    public static Directory getDirectory() {
        if (instance == null) {
            instance = new Directory();
        }
        return instance;
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String newDirectory) {
        currentDirectory = newDirectory;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public Path getPathTo(String arg) {
        return Paths.get(currentDirectory + separator + arg);
    }

    /*
    public File getFile(String arg) {
        File file = new File(String.valueOf(getPathTo(arg)));
        if (!file.exists()) {
            throw new RuntimeException("File does not exist");
        }
        return file;
    }
    */

    public boolean existsFile(String arg) {
        return new File(String.valueOf(getPathTo(arg))).exists();
    }

    public boolean existsDirectory(String arg) {
        return new File(String.valueOf(getPathTo(arg))).isDirectory();
    }

    public ArrayList<File> getListOfFiles (String arg) {
        if (!existsDirectory(arg)) {
            throw new RuntimeException("Directory does not exist");
        }
        File directory = new File(String.valueOf(getPathTo(arg)));
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
    }
}
