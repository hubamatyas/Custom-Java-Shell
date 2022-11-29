package uk.ac.ucl.shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
    private final String root;

    private Directory() {
        fileSeparator = File.separator;
        encoding = StandardCharsets.UTF_8;
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
        return this.currentDirectory;
    }

    public void setCurrentDirectory(String newDirectory) {
        this.currentDirectory = newDirectory;
    }

    public Path getPathTo(String arg) {
        String[] paths = getPaths(arg);
        String currentPath = getCurrentDirectory();
        for (String path : paths) {
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
//        System.out.println(currentPath);
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

    public ArrayList<File> getContent(String appName, String arg) {
        checkDirectoryExists(appName, arg);
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
            return Files.newBufferedReader(getPathTo(fileName), encoding);
        }
        catch (IOException e) {
            throw new RuntimeException(appName + ": cannot open " + fileName);
        }
    }

    public void checkFileExists(String appName, String fileName) {
        if (!existsFile(fileName)) {
            throw new RuntimeException(appName + ": " + fileName + " does not exist");
        }
    }

    public void checkDirectoryExists(String appName, String directoryName) {
        if (!existsDirectory(directoryName)) {
            throw new RuntimeException(appName + ": " + directoryName + " directory does not exist");
        }
    }

    public boolean existsFile(String arg) {
        return new File(String.valueOf(getPathTo(arg))).exists();
    }

    public boolean existsDirectory(String arg) {
        return new File(String.valueOf(getPathTo(arg))).isDirectory();
    }

}
