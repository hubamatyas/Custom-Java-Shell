package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class OutputTest {

    private String appName;
    protected String lineSeparator;

    protected void setParameters(String appName) {
        this.appName = appName;
        lineSeparator = System.getProperty("line.separator");
    }

    protected void testOutput(ArrayList<String> args, String input, String expectedOutput) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out);
        ApplicationFactory.getApp(appName, args, createInputStream(input), writer).exec();
        checkOutput(expectedOutput, out);
    }

    private InputStream createInputStream(String input) {
        if (input == null) {
            return null;
        }
        return new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    }

    private void checkOutput(String expectedOutput, ByteArrayOutputStream output) {
        assertEquals(expectedOutput, output.toString());
    }

    protected void generateTestFiles(String[] names, String[] contents) throws IOException{
        for (int i=0; i<names.length; i++) {
            generateTestFile(names[i], contents[i]);
        }
    }

    protected void generateTestFile(String name, String content) throws IOException {
        FileWriter fileOut = new FileWriter(name);
        fileOut.write(content);
        fileOut.close();
    }


    protected ArrayList<String> createArgs(String... args) {
        return new ArrayList<>(List.of(args));
    }
}
