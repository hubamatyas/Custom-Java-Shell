package uk.ac.ucl.shell.Apps;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class FindTest extends OutputTest{

    @Override
    public String setAppName() {
        return "find";
    }
    private String fileSeparator;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        fileSeparator = System.getProperty("file.separator");
    }

    // Functionality
    @Test
    public void currentDirFiles() throws IOException {
        String expectedOutput = "."+fileSeparator+getFileNames()[2]+lineSeparator;
        testOutput(createArgs("-name", "lorem*"), null, expectedOutput);
    }

    @Test
    public void allCurrentDirFiles() throws IOException {
        String expectedOutput = "."+fileSeparator+getFileNames()[3]+lineSeparator+"."+fileSeparator+getFileNames()[4]+lineSeparator+"."+fileSeparator+getFileNames()[0]+lineSeparator+"."+fileSeparator+getFileNames()[1]+lineSeparator;
        testOutput(createArgs("-name", "foo*"), null, expectedOutput);
    }

    @Test
    public void nestedDirFiles() throws IOException {
        String expectedOutput = getFileNames()[3]+lineSeparator+getFileNames()[4]+lineSeparator;
        testOutput(createArgs("testDir", "-name", "foo*"), null, expectedOutput);
    }

    // Exceptions

}