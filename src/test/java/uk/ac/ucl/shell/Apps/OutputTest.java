package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class OutputTest extends ApplicationTest{

    protected void testOutput(ArrayList<String> args, String input, String expectedOutput) throws IOException {
        execApp(args, input);
        assertEquals(expectedOutput, getOut().toString());
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

}
