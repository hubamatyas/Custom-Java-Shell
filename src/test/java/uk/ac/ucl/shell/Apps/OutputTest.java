package uk.ac.ucl.shell.Apps;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class OutputTest extends ApplicationTest{

    private String[] fileNames;
    private String[] contents;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        fileNames = new String[]{"test1.txt", "test2.txt", "text3.txt"};
        contents = new String[]{
                "foo"+lineSeparator+"bar"+lineSeparator,
                "foobar"+lineSeparator,
                "Lorem ipsum"+lineSeparator+"dolar sit amet,"+lineSeparator+"consectetur adipiscing elit,"
        };
        generateTestFiles();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        for (String fileName : fileNames) {
            File f = new File(fileName);
            f.delete();
        }
    }

    protected String[] getFileNames() {
        return fileNames;
    }

    protected String[] getContents() {
        return contents;
    }

    protected void testOutput(ArrayList<String> args, String input, String expectedOutput) throws IOException {
        execApp(args, input);
        assertEquals(expectedOutput, getOut().toString());
    }

    protected void generateTestFiles() throws IOException{
        for (int i=0; i<fileNames.length; i++) {
            generateTestFile(fileNames[i], contents[i]);
        }
    }

    protected void generateTestFile(String name, String content) throws IOException {
        FileWriter fileOut = new FileWriter(name);
        fileOut.write(content);
        fileOut.close();
    }

}
