package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import uk.ac.ucl.shell.Directory;

public class DirectoryTest {

    @Test
    public void UniqueInstanceOnly() {
        Directory instance1 = Directory.getInstance();
        Directory instance2 = Directory.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void getCurrentDirectory() {
    }

    @Test
    public void setCurrentDirectory() {
    }

    @Test
    public void getEncoding() {
    }

    @Test
    public void getPathTo() {
    }

    @Test
    public void existsFile() {
    }

    @Test
    public void existsDirectory() {
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
    public void writeLine() {
    }

    @Test
    public void writeNewLine() {
    }

    @Test
    public void checkFileToHandle() {
    }

    @Test
    public void checkDirectoryToHandle() {
    }
}