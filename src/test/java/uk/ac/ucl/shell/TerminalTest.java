package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TerminalTest {

    private Scanner scn;
    private PipedInputStream in;
//    private PipedOutputStream out;
    private Terminal ter;
    private String lineSeparator;
    private OutputStreamWriter output;

    @Before
    public void setUp() throws IOException {
        in = new PipedInputStream();
//        out = new PipedOutputStream(in);
        scn = new Scanner(in);
        ter = Terminal.getInstance();
        lineSeparator = System.getProperty("user.lineseparator");
        output = new OutputStreamWriter(System.out);
    }

    @After
    public void tearDown() throws Exception {
        scn.close();
    }

    // getInstance()
    @Test
    public void uniqueInstanceOnly() {
        Terminal instance2 = Terminal.getInstance();
        assertSame(instance2, ter);
    }

    // writeLine()
    @Test
    public void expectedOutput() throws IOException {
        ter.writeLine("foo bar", output, lineSeparator);
//        assertEquals("foo bar", scn.next());
    }
}