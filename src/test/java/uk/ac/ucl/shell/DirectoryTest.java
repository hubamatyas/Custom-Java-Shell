package uk.ac.ucl.shell;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

public class DirectoryTest {

    Directory dir;

    @Before
    public void setUp() throws Exception {
        dir = Directory.getInstance();
    }

    @Test
    public void uniqueInstanceOnly() {
        Directory newDir = Directory.getInstance();
        assertSame(dir, newDir);
    }

    @Test
    public void getCurrentDirectory() {
        assertEquals("C:\\Users\\asus\\OneDrive - University College London\\Year 2\\COMP0010 Software Engineering\\Coursework\\java-shell-j3", dir.getCurrentDirectory());
    }

    @Test
    public void getPathToRoot() {
        assertEquals("C:\\Users\\asus\\OneDrive - University College London\\Year 2\\COMP0010 Software Engineering\\Coursework", String.valueOf(dir.getPathTo("..")));
    }

    @Test
    public void existsDirectory() {
    }

    @Test
    public void checkDirectoryToHandle() {
    }

    @Test
    public void setCurrentDirectory() {
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

    @Test
    public void checkFileToHandle() {
    }
}