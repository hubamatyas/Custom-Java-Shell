package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TerminalTest {

    private Terminal ter;
    private String lineSeparator;
    private ByteArrayOutputStream out;
    private OutputStreamWriter outwriter;

    @Before
    public void setUp() throws IOException {
        out = new ByteArrayOutputStream();
        outwriter = new OutputStreamWriter(out);
        ter = Terminal.getInstance();
        lineSeparator = System.getProperty("line.separator");
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
        ter.writeLine("foo", outwriter, lineSeparator);
        String text = out.toString();
        assertEquals("foo"+lineSeparator, out.toString());
    }

    @Test
    public void writeMultipleWords() throws IOException {
        ter.writeLine("foo bar", outwriter, lineSeparator);
        assertEquals("foo bar"+lineSeparator, out.toString());
    }

    @Test
    public void writeMultipleLines() throws IOException {
        ter.writeLine("foo bar"+lineSeparator+"foobar", outwriter, lineSeparator);
        assertEquals("foo bar"+lineSeparator+"foobar"+lineSeparator, out.toString());
    }

}