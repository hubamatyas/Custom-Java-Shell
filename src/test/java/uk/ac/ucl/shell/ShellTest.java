package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.Scanner;

public class ShellTest {

    private Scanner scn;
    private PipedInputStream in;
    private PipedOutputStream out;

    @Before
    public void setUp() throws IOException {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        scn = new Scanner(in);
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(out));
    }

    @After
    public void tearDown() throws Exception {
        scn.close();
    }

//    private void setIn(String input) throws UnsupportedEncodingException {
//        InputStream testInput = new ByteArrayInputStream( input.getBytes("UTF-8") );
//        System.setIn(testInput);
//    }

    // Functionality
    @Test
    public void evalEcho() throws IOException {
        Shell.eval("echo foo", out);
        assertEquals("foo", scn.next());
    }

    @Test
    public void produceOutput() throws IOException {
        Shell.eval("echo foo > output.txt", out);
        File f = new File("output.txt");
        assertTrue(f.exists());
        f.delete();
    }

    @Test
    public void takeInput() throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        Shell.eval("cat < test/dir1/file1.txt", out);
        assertEquals("AAA", scn.next());
    }

    //    @Test
//    public void interactiveEval() throws IOException {
//        Shell.main(new String[]{});
//        setIn("echo foo\\n");
//        setIn("exit\\n");
//    }

    @Test
    public void nonInteractiveEval() throws IOException {
        Shell.main(new String[]{"-c", "echo foo"});
        assertEquals("foo", scn.next());
    }

    // Exceptions
    @Test
    public void wrongNumberOfArguments() {
        Shell.main(new String[]{"-c", "echo foo", "cat bar"});
        assertEquals(Terminal.ANSI_RED+"COMP0010", scn.next());
    }

    @Test
    public void wrongArgument() {
        Shell.main(new String[]{"-d", "echo foo"});
        assertEquals(Terminal.ANSI_RED+"COMP0010", scn.next());
    }

    @Test
    public void badEval() {
        Shell.main(new String[]{"-d", "cat"});
        assertEquals(Terminal.ANSI_RED+"COMP0010", scn.next());
    }

}