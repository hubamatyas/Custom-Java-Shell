package uk.ac.ucl.shell.Apps;

import org.junit.After;
import org.junit.Test;
import uk.ac.ucl.shell.Directory;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

import java.io.IOException;

import static org.junit.Assert.*;

public class LsTest extends OutputTest{

    @Override
    public String setAppName() {
        return "ls";
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        Directory.getInstance().setCurrentDirectory(System.getProperty("user.dir"));
    }

    // Functionality
    @Test
    public void lsCurrentDir() throws IOException {
        Directory dir = Directory.getInstance();
        dir.setCurrentDirectory(String.valueOf(dir.getPathTo("testDir")));
        testOutput(createArgs(), null, "bar.txt\tfoo.txt\tfoobar.txt\t"+lineSeparator);
    }

    @Test
    public void lsNestedDir() throws IOException {
        testOutput(createArgs("testDir"), null, "bar.txt\tfoo.txt\tfoobar.txt\t"+lineSeparator);
    }

    // Exceptions
    @Test(expected = TooManyArgumentsException.class)
    public void tooManyArgumentsException() throws IOException {
        testOutput(createArgs("foo", "bar"), null, null);
    }
}