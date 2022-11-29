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
    private Terminal ter;
    private String lineSeparator;
    private OutputStreamWriter output;

    @Before
    public void setUp() throws IOException {
        in = new PipedInputStream();
        scn = new Scanner(in);
        output = new OutputStreamWriter(new PipedOutputStream(in));
        ter = Terminal.getInstance();
        lineSeparator = System.getProperty("line.separator");
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
    public void writeWord() throws IOException {
        ter.writeLine("foo", output, lineSeparator);
        assertEquals("foo", scn.next());
    }

    @Test
    public void writeMultipleWords() throws IOException {
        ter.writeLine("foo bar", output, lineSeparator);
        assertEquals("foo bar", scn.next());
    }

    @Test
    public void writeMultipleLines() throws IOException {
        ter.writeLine("foo bar \nfoobar", output, lineSeparator);
        assertEquals("foo bar \nfoobar", scn.next());
    }

}