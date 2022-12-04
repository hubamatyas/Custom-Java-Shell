package uk.ac.ucl.shell.Apps;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

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
        String expectedOutput = "."+fileSeparator+getFileNames()[4]+lineSeparator+"."+fileSeparator+getFileNames()[3]+lineSeparator+"."+fileSeparator+getFileNames()[1]+lineSeparator+"."+fileSeparator+getFileNames()[0]+lineSeparator;
        testOutput(createArgs("-name", "foo*"), null, expectedOutput);
    }

    @Test
    public void nestedDirFiles() throws IOException {
        String expectedOutput = getFileNames()[4]+lineSeparator+getFileNames()[3]+lineSeparator;
        testOutput(createArgs("testDir", "-name", "foo*"), null, expectedOutput);
    }

    // Exceptions
    @Test(expected = MissingArgumentsException.class)
    public void emptyArgumentsException() throws IOException {
        testOutput(createArgs(), null, null);
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingArgumentsException() throws IOException {
        testOutput(createArgs("-name"), null, null);
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingArgumentsExceptionWithSubdir() throws IOException {
        testOutput(createArgs("subdir", "-name"), null, null);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void tooManyArgumentsException() throws IOException {
        testOutput(createArgs("-name", "foo", "bar"), null, null);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void tooManyArgumentsExceptionWithSubdir() throws IOException {
        testOutput(createArgs("subdir", "-name", "foo", "bar"), null, null);
    }
}