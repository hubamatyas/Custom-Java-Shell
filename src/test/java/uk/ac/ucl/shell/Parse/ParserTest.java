package uk.ac.ucl.shell.Parse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
    }

    @After
    public void tearDown() throws Exception {

    }

    // Parse call
    @Test
    public void singleCallCommand() {
        ArrayList<ArrayList<String>> atomicCommands = Parser.parseCommand("echo foo");
    }


    // Parse command

}