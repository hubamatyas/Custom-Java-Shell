package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.InvalidByteRangeException;
import uk.ac.ucl.shell.Exceptions.InvalidOptionException;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CutTest extends OutputTest{

    @Override
    public String setAppName() {
        return "cut";
    }

    // Functionality
    @Test
    public void byteRange() throws IOException {
        String expectedOutput = "fo"+lineSeparator+"ba"+lineSeparator;
        testOutput(createArgs("-b", "1-2", getFileNames()[0]), null, expectedOutput);
    }

    @Test
    public void multipleBytes() throws IOException {
        String expectedOutput = "fo"+lineSeparator+"br"+lineSeparator;
        testOutput(createArgs("-b", "1,3", getFileNames()[0]), null, expectedOutput);
    }

    @Test
    public void multipleByteRanges() throws IOException {
        String expectedOutput = "Loem p"+lineSeparator+"doar i"+lineSeparator+"cosece"+lineSeparator;
        testOutput(createArgs("-b", "1-2,4-6,8", getFileNames()[2]), null, expectedOutput);
    }

    @Test
    public void toEOL() throws IOException{
        String expectedOutput = generateLines("em ipsum", "ar sit amet", "sectetur adipiscing elit");
        testOutput(createArgs("-b", "4-,5-", getFileNames()[2]), null, expectedOutput);
    }

    @Test
    public void startTo() throws IOException {
        String expectedOutput = generateLines("Lor", "dol", "con");
        testOutput(createArgs("-b", "-2,-3", getFileNames()[2]), null, expectedOutput);
    }

    @Test
    public void filePiped() throws IOException {
        String input = "Lorem ipsum"+lineSeparator+"dolar sit amet,"+lineSeparator+"consectetur adipiscing elit,";
        String expectedOutput = "Loem p"+lineSeparator+"doar i"+lineSeparator+"cosece"+lineSeparator;
        testOutput(createArgs("-b", "1-2,4-6,8"), input, expectedOutput);
    }

    // Exceptions
    @Test(expected = MissingArgumentsException.class)
    public void missingBytesException() throws IOException {
        testOutput(createArgs("-b", getFileNames()[0]), null, "lipsum");
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingFileException() throws IOException {
        testOutput(createArgs("-b", "2,3"), null, "lipsum");
    }

    @Test(expected = TooManyArgumentsException.class)
    public void tooManyArgumentsException() throws IOException {
        testOutput(createArgs("-b", "2,3", getFileNames()[0], getFileNames()[1]), null, "lipsum");
    }

    @Test(expected = InvalidOptionException.class)
    public void invalidOptionException() throws IOException {
        testOutput(createArgs("-m", "2,3", getFileNames()[0]), null, "lipsum");
    }

    @Test(expected = InvalidByteRangeException.class)
    public void nonNumericalByteRangeException() throws IOException {
        testOutput(createArgs("-b", "2-b", getFileNames()[0]), null, "lipsum");
    }

    @Test(expected = InvalidByteRangeException.class)
    public void zeroByteRangeException() throws IOException {
        testOutput(createArgs("-b", "0-b", getFileNames()[0]), null, "lipsum");
    }

    @Test(expected = InvalidByteRangeException.class)
    public void negativeByteRangeException() throws IOException {
        testOutput(createArgs("-b", "-1-2", getFileNames()[0]), null, "lipsum");
    }

    @Test(expected = RuntimeException.class)
    public void exceptionSingleFileDoesNotExist() throws IOException {
        testOutput(createArgs("-b", "2,3", "doesNotExist"), null, null);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionNestedFileDoesNotExist() throws IOException {
        testOutput(createArgs("-b", "2-4", "src/doesNotExist"), null, null);
    }

}