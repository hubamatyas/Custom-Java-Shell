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

    // Parsing calls

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
        assertParsedCall("echo", new String[]{"foo"}, null, "output", parsedCall);
    }

    // Exceptions
    @Test(expected = RuntimeException.class)
    public void invalidBackQuotedInput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("`< input`");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void invalidArgBackQuotedInput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo `< input`");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void invalidBackQuotedOutput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("`> output`");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void invalidArgBackQuotedOutput() throws IOException {
        ParsedCall parsedCall = Parser.parseCall("echo foo `> output`");
        fail("Invalid call exception not thrown");
    }


    // Parsing commands

    // Asserting size
    private void assertSizes(int[] size, ArrayList<ArrayList<String>> commands) {
        for (int i=0; i<commands.size(); i++) {
            assertEquals(size[i], commands.get(i).size());
        }
    }

    // Functionality
    @Test
    public void parseSingleSingleCall() {
        ArrayList<ArrayList<String>> parsedCommands = Parser.parseCommand("echo foo");
        assertSizes(new int[]{1}, parsedCommands);
    }

    @Test
    public void parseSemiColon() {
        ArrayList<ArrayList<String>> parsedCommands = Parser.parseCommand("echo foo; echo bar");
        assertSizes(new int[]{1, 1}, parsedCommands);
    }

    @Test
    public void parsePipe() {
        ArrayList<ArrayList<String>> parsedCommands = Parser.parseCommand("echo foo | grep fo");
        assertSizes(new int[]{2}, parsedCommands);
    }

    @Test
    public void parsePipeSemiColon() {
        ArrayList<ArrayList<String>> parsedCommands = Parser.parseCommand("echo foo | grep fo; echo bar | grep ba");
        assertSizes(new int[]{2, 2}, parsedCommands);
    }


    // Exceptions
    @Test(expected = RuntimeException.class)
    public void unfinishedBackQuotes() throws IOException {
        ArrayList<ArrayList<String>> commands = Parser.parseCommand("echo `echo bar");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void unfinishedSingleQuotes() throws IOException {
        ArrayList<ArrayList<String>> commands = Parser.parseCommand("echo 'echo bar");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void unfinishedDoubleQuotes() throws IOException {
        ArrayList<ArrayList<String>> commands = Parser.parseCommand("echo \"echo bar");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void invalidSingleInput() throws IOException {
        ArrayList<ArrayList<String>> commands = Parser.parseCommand("< input");
        fail("Invalid call exception not thrown");
    }

    @Test(expected = RuntimeException.class)
    public void invalidSingleOutput() throws IOException {
        ArrayList<ArrayList<String>> commands = Parser.parseCommand("> output");
        fail("Invalid call exception not thrown");
    }

}