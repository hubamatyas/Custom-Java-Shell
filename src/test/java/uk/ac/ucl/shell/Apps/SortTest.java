package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.shell.Directory;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

public class SortTest extends OutputTest{

    @Override
    public String setAppName() {
        // TODO Auto-generated method stub
        return "sort";
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Directory.getInstance().setCurrentDirectory(System.getProperty("user.dir"));
    }

    @Test
    public void fileArg() throws IOException{
        testOutput(createArgs(getFileNames()[0]), null, "bar"+lineSeparator+"foo"+lineSeparator);
    }

    @Test
    public void fileArgOptions() throws IOException{
        testOutput(createArgs("-r", getFileNames()[0]), null, "foo"+lineSeparator+"bar"+lineSeparator);
    }

    @Test
    public void pipeNoArg() throws IOException{
        testOutput(createArgs(), getContents()[0], "bar"+lineSeparator+"foo"+lineSeparator);
    }

    @Test
    public void pipeOptions() throws IOException{
        testOutput(createArgs("-r"), getContents()[0], "foo"+lineSeparator+"bar"+lineSeparator);
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingArg() throws IOException{
        testOutput(createArgs(), null, "");
    }

    
}