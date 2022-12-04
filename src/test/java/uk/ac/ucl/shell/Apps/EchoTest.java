package uk.ac.ucl.shell.Apps;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class EchoTest extends OutputTest{

    @Override
    public String setAppName() {
        return "echo";
    }

    // Functionality
    @Test
    public void noArg() throws IOException {
        testOutput(createArgs(""), null, lineSeparator);
    }

    @Test
    public void singleArg() throws IOException {
        testOutput(createArgs("foo"), null, "foo"+lineSeparator);
    }

    @Test
    public void multiArgs() throws IOException {
        testOutput(createArgs("foo", "bar"), null, "foo bar"+lineSeparator);
    }

    @Test
    public void ignoreInput() throws IOException {
        testOutput(createArgs(""), getFileNames()[0], lineSeparator);
    }

}