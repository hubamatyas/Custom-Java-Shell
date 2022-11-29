package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class DirectoryTest {

    Directory dir;
    String currentDirPath;
    String fileSeparator;
    String root;

    private String getPathToParentHelper() {
        int i = currentDirPath.length()-1;
        while (i-->0) {
            if (String.valueOf(currentDirPath.charAt(i)).equals(fileSeparator)) {
                break;
            }
        }
        root = currentDirPath.substring(0,i);
        return currentDirPath.substring(0,i);
    }

    @Before
    public void setUp() throws Exception {
        dir = Directory.getInstance();
        currentDirPath = System.getProperty("user.dir");
        fileSeparator = File.separator;
        root = currentDirPath.substring(0,2);
    }

    @After
    public void tearDown() throws Exception {
        dir.setCurrentDirectory(currentDirPath);
    }

    @Test
    public void uniqueInstanceOnly() {
        System.out.println("Creating instance");
        Directory newDir = Directory.getInstance();
        assertSame(dir, newDir);
    }

    @Test
    public void getCurrentDirectory() {
        assertEquals(currentDirPath, dir.getCurrentDirectory());
    }

    @Test
    public void getPathToParent() {
        String rootDir = getPathToParentHelper();
        assertEquals(rootDir, String.valueOf(dir.getPathTo("..")));
    }

    @Test
    public void getPathToCurrentDir() {
        assertEquals(currentDirPath, String.valueOf(dir.getPathTo("")));
        assertEquals(currentDirPath, String.valueOf(dir.getPathTo(".")));
    }

    @Test
    public void doesExistDirectories() {
        System.out.println("Current directory: " + dir.getCurrentDirectory());
        assertTrue(dir.existsDirectory(""));
        assertTrue(dir.existsDirectory("src"));
        assertTrue(dir.existsDirectory("system_test"));
    }

    @Test
    public void doesNotExistDirectories() {
        assertFalse(dir.existsDirectory("foo"));
        assertFalse(dir.existsDirectory("bar"));
    }

    @Test
    public void doesNotExistHiddenDirectories() {
        assertFalse(dir.existsDirectory(".gitignore"));
    }

    @Test
    public void doesExistNestedDirectories() {
        assertTrue(dir.existsDirectory("src/main"));
        assertTrue(dir.existsDirectory("src/main/../main"));
        assertTrue(dir.existsDirectory("src/main/../../src"));
    }

    @Test
    public void setCurrentDirToCurrentDir() {
        dir.setCurrentDirectory(String.valueOf(dir.getPathTo("")));
        assertEquals(currentDirPath, dir.getCurrentDirectory());
        dir.setCurrentDirectory(String.valueOf(dir.getPathTo(".")));
        assertEquals(currentDirPath, dir.getCurrentDirectory());
    }

    @Test
    public void setCurrentDirToParent() {
        dir.setCurrentDirectory(getPathToParentHelper());
        assertTrue(dir.existsDirectory("java-shell-j3"));
    }

    @Test
    public void setCurrentDirToParentOfRoot() {
        dir.setCurrentDirectory(root);
        assertEquals(root, String.valueOf(dir.getPathTo("..")));
    }

    @Test
    public void existsFile() {
    }

    @Test
    public void getListOfFiles() {
    }

    @Test
    public void readFile() {
    }

    @Test
    public void writeFile() {
    }
}