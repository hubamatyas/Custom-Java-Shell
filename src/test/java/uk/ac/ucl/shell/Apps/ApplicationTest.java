package uk.ac.ucl.shell.Apps;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class ApplicationTest {

    protected String lineSeparator;
    private ByteArrayOutputStream out;
    private OutputStreamWriter writer;
    private String appName;
    private int minArgs;
    private int maxArgs;
//    static String[] exampleArgs = new String[]{"foo", "bar", "foobar", "lorem", "ipsum"};
//    private String[] tooFewArgs = new String[minArgs-1];
//    private String[] tooManyArgs = new String[maxArgs+1];

    @Before
    public void setUp() throws Exception {
        appName = setAppName();
        lineSeparator = System.getProperty("line.separator");
        out = new ByteArrayOutputStream();
        writer = new OutputStreamWriter(out);
//        minArgs = setMinArgs();
//        maxArgs = setMaxArgs();
//        generateArgs();
    }

    @After
    public void tearDown() throws Exception {
        writer.close();
    }

    public abstract String setAppName();
//    public abstract int setMinArgs();
//    public abstract int setMaxArgs();

    public void execApp(ArrayList<String> args, String input) throws IOException {
        ApplicationFactory.getApp(appName, args, createInputStream(input), writer).exec();
    }

    private InputStream createInputStream(String input) {
        if (input == null) {
            return null;
        }
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }

    protected ArrayList<String> createArgs(String... args) {
        return new ArrayList<>(List.of(args));
    }

    public ByteArrayOutputStream getOut() {
        return out;
    }

//    private void generateArgs() {
//        if (minArgs - 1 >= 0) System.arraycopy(exampleArgs, 0, tooFewArgs, 0, minArgs - 1);
//        if (maxArgs + 1 >= 0) System.arraycopy(exampleArgs, 0, tooManyArgs, 0, maxArgs + 1);
//    }
//
//    @Test(expected = MissingArgumentsException.class)
//    public void missingArguments() throws IOException {
//        execApp(createArgs(tooFewArgs), null);
//    }
//
//    @Test(expected = TooManyArgumentsException.class)
//    public void tooManyArguments() throws IOException {
//        execApp(createArgs(tooManyArgs), null);
//    }
}
