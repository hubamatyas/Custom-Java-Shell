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
        int i = currentDirPath.length() - 1;
        while (i-- > 0) {
            if (String.valueOf(currentDirPath.charAt(i)).equals(fileSeparator)) {
                break;
            }
        }
        return currentDirPath.substring(0, i);
    }

    @Before
    public void setUp() throws Exception {
        dir = Directory.getInstance();
        currentDirPath = System.getProperty("user.dir");
        fileSeparator = File.separator;
        root = currentDirPath.substring(0, 2);
    }

    @After
    public void tearDown() throws Exception {
        dir.setCurrentDirectory(currentDirPath);
    }

    // getInstance()
    @Test
    public void uniqueInstanceOnly() {
        System.out.println("Creating instance");
        Directory newDir = Directory.getInstance();
        assertSame(dir, newDir);
    }

    // getCurrentDirectory()
    @Test
    public void getCurrentDirectory() {
        assertEquals(currentDirPath, dir.getCurrentDirectory());
    }

    // getPathTo()
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

    // existsDirectory()
    @Test
    public void doesExistDirectories() {
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

    // setCurrentDir()
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

    // getContent()
    @Test
    public void getCurrentDirContent() {
        assertEquals(17, dir.getContent("ls", "").size());
    }

    @Test
    public void getNestedDirContent() {
        assertEquals(2, dir.getContent("ls", "src").size());
    }

    @Test(expected = RuntimeException.class)
    public void exceptionDirNotFound() {
        try {
            dir.getContent("ls", "foo");
        } catch (RuntimeException re) {
            assertEquals("ls: foo directory does not exist", re.getMessage());
            throw re;
        }
    }

    // getSubDirectories
    @Test
    public void getCurrentSubDirContent() {
        assertEquals(10, dir.getSubDirectories("find", "").size());
    }

    @Test
    public void getNestedSubDirContent() {
        assertEquals(2, dir.getSubDirectories("find", "src").size());
    }

    @Test(expected = RuntimeException.class)
    public void exceptionSubDirNotFound() {
        try {
            dir.getSubDirectories("find", "foo");
        } catch (RuntimeException re) {
            assertEquals("find: foo directory does not exist", re.getMessage());
            throw re;
        }
    }

    // getFiles
    @Test
    public void getCurrentFiles() {
        assertEquals(7, dir.getFiles("find", "").size());
    }

    @Test
    public void getNestedFiles() {
        assertEquals(1, dir.getFiles("find", "system_test").size());
    }

    @Test(expected = RuntimeException.class)
    public void exceptionDirOfFilesNotFound() {
        try {
            dir.getFiles("find", "foo");
        } catch (RuntimeException re) {
            assertEquals("find: foo directory does not exist", re.getMessage());
            throw re;
        }
    }

    // checkFileExists
    @Test
    public void fileExists() {
        assertTrue(dir.existsFile("README.md"));
    }

    @Test(expected = RuntimeException.class)
    public void exceptionFileDoesNotExist() {
        try {
            dir.checkFileExists("grep", "foo");
        } catch (RuntimeException re) {
            assertEquals("grep: foo does not exist", re.getMessage());
            throw re;
        }
    }
}