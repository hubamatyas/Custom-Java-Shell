package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UnsafeCatTest extends OutputTest {

    @Override
    public String setAppName() {
        return "_cat";
    }

    @Test
    public void doesNotThrowException() throws IOException {
        try {
            execApp(createArgs(""), null);
        } catch (MissingArgumentsException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void runsAsStandard() throws IOException {
        testOutput(createArgs(getFileNames()[0]), null, getContents()[0]);
    }

}