package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Directory;

import java.io.IOException;

import static org.junit.Assert.*;

public class PwdTest extends OutputTest{

    @Override
    public String setAppName() {
        return "pwd";
    }

    // Functionality
    @Test
    public void printWorkingDirectory() throws IOException {
        Directory dir = Directory.getInstance();
        dir.setCurrentDirectory(String.valueOf(dir.getPathTo("testDir")));
        testOutput(createArgs(), null, dir.getCurrentDirectory()+lineSeparator);
    }

    @Test
    public void ignoreInput() throws IOException {
        Directory dir = Directory.getInstance();
        dir.setCurrentDirectory(String.valueOf(dir.getPathTo("testDir")));
        testOutput(createArgs(), "ignoreMe", dir.getCurrentDirectory()+lineSeparator);
    }

    // Exceptions

}