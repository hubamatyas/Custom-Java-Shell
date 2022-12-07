package uk.ac.ucl.shell.Apps;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

import java.io.IOException;
import java.util.ArrayList;

public class CatTest extends OutputTest {

    @Override
    public String setAppName() {
        return "cat";
    }

    // Functionality
    @Test
    public void singleFile() throws IOException {
        testOutput(createArgs(getFileNames()[0]), null, getContents()[0]);
    }

    @Test
    public void multipleFiles() throws IOException {
        String expectedOutput = String.join("", getContents());
        testOutput(createArgs(getFileNames()), null, expectedOutput);
    }

    @Test
    public void filePiped() throws IOException {
        String expectedOutput = "foo"+lineSeparator+"bar"+lineSeparator;
        testOutput(new ArrayList<>(), expectedOutput, expectedOutput);
    }

    // Exceptions
    @Test(expected = MissingArgumentsException.class)
    public void missingArgumentsException() throws IOException {
        testOutput(createArgs(), null, "lipsum");
    }

    @Test(expected = RuntimeException.class)
    public void exceptionSingleFileDoesNotExist() throws IOException {
        testOutput(createArgs("doesNotExist"), null, null);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionNestedFileDoesNotExist() throws IOException {
        testOutput(createArgs(getFileNames()[0], "doesNotExist"), null, null);
    }

}