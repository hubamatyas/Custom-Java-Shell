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
import java.util.List;
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

    public ArrayList<File> getListOfFiles(String appName, String arg) {
        checkDirectoryToHandle(appName, arg);
        File directory = new File(String.valueOf(getPathTo(arg)));
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
    }

    public List<String> readFile(String appName, String fileName) throws IOException {
        checkFileToHandle(appName, fileName);
        BufferedReader reader = createBufferedReader(appName, fileName);
        return readLines(reader);
    }

    private BufferedReader createBufferedReader(String appName, String fileName) throws IOException {
        try {
            return Files.newBufferedReader(getPathTo(fileName), encoding);
        }
        catch (IOException e) {
            throw new RuntimeException(appName + ": cannot open " + fileName);
        }
    }

    private List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public void writeFile(String appName, String fileName, List<String> lines, OutputStreamWriter writer) throws IOException {
        checkFileToHandle(appName, fileName);
        writeLines(writer, lines);
    }

    private void writeLines(OutputStreamWriter writer, List<String> lines) throws IOException {
        for (String line : lines) {
            writer.write(line);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }

    private void checkFileToHandle(String appName, String fileName) {
        if (!existsFile(fileName)) {
            throw new RuntimeException(appName + ": " + fileName + " does not exist");
        }
    }

    private void checkDirectoryToHandle(String appName, String directoryName) {
        if (!existsDirectory(directoryName)) {
            throw new RuntimeException(appName + ": " + directoryName + " does not exist");
        }
    }



}
