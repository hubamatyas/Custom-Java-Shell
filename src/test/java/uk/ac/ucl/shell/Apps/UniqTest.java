package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

import java.io.IOException;

public class UniqTest extends OutputTest{

    private String testFileName;
    private String contents;

    @Override
    public String setAppName() {
        return "uniq";
    }

    // Functionality
    // File content: "abba", "ab", "ab", "zz", "ab", "Ab", "Zz", "zz", "zz", "abbba", "abba", "Abba"
    @Test
    public void fileArg() throws IOException{
        testOutput(createArgs(getFileNames()[6]), null, generateLines("abba", "ab", "zz", "ab", "Ab", "Zz", "zz", "abbba", "abba", "Abba"));
    }

    @Test
    public void fileArgOptions() throws IOException{
        testOutput(createArgs("-i", getFileNames()[6]), null, generateLines("abba", "ab", "zz", "ab", "Zz", "abbba", "abba"));
    }

    @Test
    public void pipeNoArg() throws IOException{
        testOutput(createArgs(), getContents()[6], generateLines("abba", "ab", "zz", "ab", "Ab", "Zz", "zz", "abbba", "abba", "Abba"));
    }

//    @Test
//    public void pipeOptions() throws IOException{
//        testOutput(createArgs("-i"), getContents()[6], generateLines("abba", "ab", "zz", "ab", "Zz", "abbba", "abba"));
//    }

    // Exceptions
    @Test(expected = MissingArgumentsException.class)
    public void missingArg() throws IOException {
        testOutput(createArgs(), null, "");
    }

}