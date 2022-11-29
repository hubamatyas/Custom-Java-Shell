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

/**
 * Implements methods related to directory functions
 * and tracks the current working directory
 */
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

    /**
     * Initializes a singleton instance of Directory class
     *
     * @return singleton instance of Directory class
     */
    public static Directory getInstance() {
        if (instance == null) {
            instance = new Directory();
        }
        return instance;
    }

    /**
     * Returns the path of the current directory
     *
     * @return the current directory's String path
     */
    public String getCurrentDirectory() {
        return this.currentDirectory;
    }

    /**
     * Sets the path of the new current directory
     *
     * @param newDirectory relative String path to
     *                     new current directory
     */
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

    /**
     * Returns the content of the {@code arg} directory.
     * Content includes both files and subdirectories.
     *
     * @param appName   name of the Unix Shell Application
     *                  where this method was called from
     * @param arg       relative String path to {@code arg}
     *                  directory to get content of
     * @return          the content of the {@code arg} directory
     *                  as a String list
     */
    public ArrayList<File> getContent(String appName, String arg) {
        checkDirectoryExists(appName, arg);
        File directory = new File(String.valueOf(getPathTo(arg)));
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));
    }

    /**
     * Returns the subdirectories of the {@code arg} directory.
     * Only includes subdirectories, not files.
     *
     * @param appName   name of the Unix Shell Application
     *                  where this method was called from
     *  @param arg      relative String path to {@code arg}
     *                  directory to get subdirectories of
     * @return          the subdirectories of the {@code arg}
     *                  directory as a String list
     */
    public ArrayList<String> getSubDirectories(String appName, String arg) {
        ArrayList<String> subDirs = new ArrayList<>();
        for (File file : getContent(appName, arg)) {
            if (file.isDirectory()) {
                subDirs.add(file.getName());
            }
        }
        return subDirs;
    }

    /**
     * Returns the files in the {@code arg} directory.
     * Only includes files, not subdirectories.
     *
     * @param appName   name of the Unix Shell Application
     *                  where this method was called from
     *  @param arg      relative String path to {@code arg}
     *                  directory to get files of
     * @return          the files of the {@code arg}
     *                  directory as a String list
     */
    public ArrayList<String> getFiles(String appName, String arg) {
        ArrayList<String> files = new ArrayList<>();
        for (File file : getContent(appName, arg)) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }
        return files;
    }

    /**
     * Safely creates and returns a {@code BufferedReader}
     * object to read the content of {@code fileName}.
     *
     * @param appName   name of the Unix Shell Application
     *                  where this method was called from
     * @param fileName  relative String path to {@code fileName}
     * @return          the {@code BufferedReader} for {@code fileName}
     */
    public BufferedReader createBufferedReader(String appName, String fileName) {
        try {
            return Files.newBufferedReader(getPathTo(fileName), encoding);
        }
        catch (IOException e) {
            throw new RuntimeException(appName + ": cannot open " + fileName);
        }
    }

    /**
     * Checks if {@code fileName} exists and is a file.
     * Raises a RuntimeException if it does not exist.
     *
     * @param appName   name of the Unix Shell Application
     *                  where this method was called from
     * @param fileName  relative String path to {@code fileName}
     */
    public void checkFileExists(String appName, String fileName) {
        if (!existsFile(fileName)) {
            throw new RuntimeException(appName + ": " + fileName + " does not exist");
        }
    }

    /**
     * Checks if {@code directoryName} exists and is a directory.
     * Raises a RuntimeException if it does not exist.
     *
     * @param appName           name of the Unix Shell Application
     *                          where this method was called from
     * @param directoryName     relative String path to {@code directoryName}
     */
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
