package uk.ac.ucl.shell.Parse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

import uk.ac.ucl.shell.Apps.ApplicationFactory;

public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
    }

    @After
    public void tearDown() throws Exception {

    }

    // Parser assertion
    private void assertParsedCall(String app, String[] args, String input, String output, ParsedCall parsedCall) {
        assertEqualApp(app, parsedCall);
        assertEqualArgs(args, parsedCall);
        assertEqualInputs(input, parsedCall);
        assertEqualOutputs(output, parsedCall);
    }

    // App assertions
    private void assertEqualApp(String expectedApp, ParsedCall parsedCall) {
        if (expectedApp == null) {
            assertNull(parsedCall.getApp());
        }
        assertEquals(expectedApp, parsedCall.getApp());
    }

    // Args assertions
    private void assertEqualArgs(String[] expectedArgs, ParsedCall parsedCall) {
        ArrayList<String> parsedArgs = parsedCall.getArgs();
        for (int i=0; i<expectedArgs.length; i++) {
            assertEquals(expectedArgs[i], parsedArgs.get(i));
        }
    }

    // Input assertions
    private void assertEqualInputs(String input, ParsedCall parsedCall) {
        if (input == null) {
            assertNull(parsedCall.getInput());
        }
        assertEquals(input, parsedCall.getInput());
    }

    // Output assertions
    private void assertEqualOutputs(String output, ParsedCall parsedCall) {
        if (output == null) {
            assertNull(parsedCall.getOutput());
        }
        assertEquals(output, parsedCall.getOutput());
    }

    // Command assertions

    // Parse call
    @Test
    public void parseAppOneArg() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo");
        assertParsedCall("echo", new String[]{"foo"}, null, null, parsedCall);
    }

    @Test
    public void parseAppMultiArgs() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo bar");
        assertParsedCall("echo", new String[]{"foo", "bar"}, null, null, parsedCall);
    }

    @Test
    public void parseAppEmptyArg() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo ");
        assertParsedCall("echo", new String[]{}, null, null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsOutputRight() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo bar > output");
        assertParsedCall("echo", new String[]{"foo", "bar"}, null, "output", parsedCall);
    }

    @Test
    public void parseAppMultiArgsOutputLeft() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("> output echo foo bar");
        assertParsedCall("echo", new String[]{"foo", "bar"}, null, "output", parsedCall);
    }

    @Test
    public void parseAppMultiArgsOutputMiddle() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo > output bar");
        assertParsedCall("echo", new String[]{"foo", "bar"}, null, "output", parsedCall);
    }

    @Test
    public void parseAppMultiArgsInputRight() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo bar < input");
        assertParsedCall("echo", new String[]{"foo", "bar"}, "input", null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsInputLeft() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("< input echo foo bar");
        assertParsedCall("echo", new String[]{"foo", "bar"}, "input", null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsInputMiddle() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo < input bar");
        assertParsedCall("echo", new String[]{"foo", "bar"}, "input", null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsInputOutputRight() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo bar < input > output");
        assertParsedCall("echo", new String[]{"foo", "bar"}, "input", "output", parsedCall);
    }

    @Test
    public void parseAppMultiArgsInputMiddleOutputRight() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo < input bar > output");
        assertParsedCall("echo", new String[]{"foo", "bar"}, "input", "output", parsedCall);
    }

    @Test
    public void parseAppBQArgs() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo `echo foo`");
        assertParsedCall("echo", new String[]{"foo"}, null, null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsBQInput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo < `echo input`");
        assertParsedCall("echo", new String[]{"foo"}, "input", null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsBQOutput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo > `echo output`");
        assertParsedCall("echo", new String[]{"foo"}, null, "input", parsedCall);
    }

    @Test
    public void parseAppMultiArgsBQNullInput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo `< input`");
        assertParsedCall("echo", new String[]{"foo"}, null, null, parsedCall);
    }

    @Test
    public void parseAppMultiArgsBQNullOutput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo `< output`");
        assertParsedCall("echo", new String[]{"foo"}, null, null, parsedCall);
    }

    // Parse command

}