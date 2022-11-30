package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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
    }

    @After
    public void tearDown() throws Exception {
        scn.close();
    }

    @Test
    public void basicEchoEval() throws IOException {
        Shell.eval("echo foo", out);
        assertEquals("foo", scn.next());
    }

}