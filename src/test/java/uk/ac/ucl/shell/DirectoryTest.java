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
    public void doesExistDirectories() {
        System.out.println("Current directory: " + dir.getCurrentDirectory());
        assertTrue(dir.existsdirectory(""));
        assertTrue(dir.existsdirectory("src"));
        assertTrue(dir.existsdirectory("system_test"));
    }

    @Test
    public void doesNotExistDirectories() {
        assertFalse(dir.existsdirectory("foo"));
        assertFalse(dir.existsdirectory("bar"));
    }

    @Test
    public void doesNotExistHiddenDirectories() {
        assertFalse(dir.existsdirectory(".gitignore"));
    }

    @Test
    public void doesExistNestedDirectories() {
        assertTrue(dir.existsdirectory("src/main"));
        assertTrue(dir.existsdirectory("src/main/../main"));
        assertTrue(dir.existsdirectory("src/main/../../src"));
    }

    @Test
    public void setCurrentDirToCurrentDir() {
        assertEquals(currentDirPath, String.valueOf(dir.getPathTo("")));
        assertEquals(currentDirPath, String.valueOf(dir.getPathTo(".")));
    }

    @Test
    public void setCurrentDirToParent() {
        dir.setCurrentDirectory(getPathToParentHelper());
        assertTrue(dir.existsdirectory("java-shell-j3"));
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