package uk.ac.ucl.shell.Apps;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class CatTest extends OutputTest {

    @Override
    public String setAppName() {
        return "cat";
    }

    // Testing
    @Test
    public void singleFile() throws IOException {
        String expectedOutput = "foo"+lineSeparator+"bar"+lineSeparator;
        String fileName = "test.txt";
        generateTestFile(fileName, expectedOutput);
        testOutput(createArgs(fileName), null, expectedOutput);
    }

    @Test
    public void multipleFiles() throws IOException {
        String[] fileNames = new String[]{"test1.txt", "test2.txt"};
        String[] contents = new String[]{
                "foo"+lineSeparator+"bar"+lineSeparator,
                "foobar"+lineSeparator
        };
        String expectedOutput = String.join("", contents);
        generateTestFiles(fileNames, contents);
        testOutput(createArgs(fileNames), null, expectedOutput);
    }

    @Test
    public void filePiped() throws IOException {
        String expectedOutput = "foo"+lineSeparator+"bar"+lineSeparator;
        testOutput(new ArrayList<>(), expectedOutput, expectedOutput);
    }

    // Exceptions
    @Test(expected = RuntimeException.class)
    public void cannotReadFromInputAndArgs() throws IOException {
        testOutput(createArgs("foo", "bar"), "foobar", "lipsum");
    }

    @Test(expected = RuntimeException.class)
    public void exceptionSingleFileDoesNotExist() throws IOException {
        testOutput(createArgs("doesNotExist"), null, null);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionNestedFileDoesNotExist() throws IOException {
        String contents = "foobar";
        String fileName = "test.txt";
        generateTestFile(fileName, contents);
        testOutput(createArgs(fileName, "doesNotExist"), null, null);
    }

}