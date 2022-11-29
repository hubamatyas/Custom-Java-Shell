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
    private String fileSeparator;
    private String lineSeparator;
    private String root;

    private Directory() {
        encoding = StandardCharsets.UTF_8;
        fileSeparator = File.separator;
        lineSeparator = System.getProperty("line.separator");
        currentDirectory = System.getProperty("user.dir");
        root = currentDirectory.substring(0,2);
    }

    // Singleton class
    public static Directory getInstance() {
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
        String[] paths = getPaths(arg);
        String currentPath = currentDirectory;
        for (String path : paths) {
            System.out.println("Current Path: " + currentPath);
            System.out.println("Path: " + path);
            if (arg.equals(".")) {
                continue;
            }
            if (arg.equals("..")) {
                if (currentPath.equals(root)) {
                    continue;
                }
                currentPath = getParentDirectory(currentPath);
            } else {
                currentPath += fileSeparator + path;
            }
        }
        System.out.println("Final path: " + currentPath);
        return Paths.get(currentPath);
    }

    private String[] getPaths(String arg) {
        if (fileSeparator.equals("\\")) {
            return arg.split("\\\\");
        }
        return arg.split(fileSeparator);
    }

    private String getParentDirectory(String path) {
        int i = path.length();
        while (i-->0) {
            if (String.valueOf(path.charAt(i)).equals(fileSeparator)) {
                break;
            }
        }
        return path.substring(0, i);
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

    public boolean existsdirectory(String arg) {
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

    public void writeFile(List<String> lines, OutputStreamWriter writer, String wordSeparator) throws IOException {
        for (String line : lines) {
            writeLine(line, writer, wordSeparator);
        }
    }

    public void writeLine(String line, OutputStreamWriter writer, String separator) throws IOException {
        writer.write(line);
        writer.write(separator);
        writer.flush();
    }

    public void writeNewLine(OutputStreamWriter writer) throws IOException {
        writer.write(lineSeparator);
        writer.flush();
    }

    public void checkFileToHandle(String appName, String fileName) {
        if (!existsFile(fileName)) {
            throw new RuntimeException(appName + ": " + fileName + " does not exist");
        }
    }

    public void checkDirectoryToHandle(String appName, String directoryName) {
        if (!existsdirectory(directoryName)) {
            throw new RuntimeException(appName + ": " + directoryName + " directory does not exist");
        }
    }



}
