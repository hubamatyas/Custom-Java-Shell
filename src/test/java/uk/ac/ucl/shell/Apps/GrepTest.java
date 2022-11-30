package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

import java.io.IOException;

import static org.junit.Assert.*;

public class GrepTest{

    @Override
    public String setAppName() {
        return "grep";
    }

    @Test
    public void currentDirFile() throws IOException {
        testOutput(createArgs("fo", getFileNames()[0]), null, "foo"+lineSeparator);
    }

    @Test
    public void currentDirsFile() throws IOException {
        testOutput(createArgs("fo", getFileNames()[0], getFileNames()[1]), null, "foo.txt: foo"+lineSeparator+"foobar.txt: foobar"+lineSeparator);
    }

    @Test
    public void inputFile() throws IOException {
        String inp = getContents()[0];
        testOutput(createArgs("fo"),inp, "foo"+lineSeparator);
    }

    // Exceptions
    @Test(expected = MissingArgumentsException.class)
    public void missingArgumentsException() throws IOException {
        testOutput(createArgs(), null, null);
    }

}