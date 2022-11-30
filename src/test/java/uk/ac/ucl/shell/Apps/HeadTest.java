package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import uk.ac.ucl.shell.Exceptions.InvalidOptionException;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

public class HeadTest extends OutputTest{

    @Override
    public String setAppName() {
        // TODO Auto-generated method stub
        return "head";
    }

    @Test
    public void fileArg() throws IOException{
        testOutput(createArgs(getFileNames()[0]), null, getContents()[0]);
    }

    @Test
    public void fileArgOptions() throws IOException{
        testOutput(createArgs("-n", "5", getFileNames()[1]), null, getContents()[1]);
    }

    @Test
    public void fileArgNoLines() throws IOException{
        testOutput(createArgs("-n", "0", getFileNames()[2]), null, "");
    }


    @Test
    public void pipeNoArg() throws IOException{
        testOutput(createArgs(), getContents()[1], getContents()[1]);
    }

    @Test
    public void pipeOptions() throws IOException{
        testOutput(createArgs("-n", "10"), getContents()[1], getContents()[1]);
    }

    @Test(expected = InvalidOptionException.class)
    public void wrongNumberOptions() throws IOException{
        testOutput(createArgs("-n", "t", getFileNames()[1]), null, "");
    }

    @Test(expected = InvalidOptionException.class)
    public void wrongOptionsAmount() throws IOException{
        testOutput(createArgs("-n", getFileNames()[2]), null, "");
    }

    @Test(expected = InvalidOptionException.class)
    public void noFileOptions() throws IOException{
        testOutput(createArgs("-n", "6"), null, "");
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingArg() throws IOException{
        testOutput(createArgs(), null, "");
    }






}