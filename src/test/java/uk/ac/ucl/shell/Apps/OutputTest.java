package uk.ac.ucl.shell.Apps;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;

public abstract class OutputTest extends ApplicationTest{

    private String[] fileNames;
    private String[] contents;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        String fileSeparator = System.getProperty("file.separator");
        fileNames = new String[]{"foo.txt", "foobar.txt", "lorem.txt", "testDir"+fileSeparator+"foo.txt", "testDir"+fileSeparator+"foobar.txt", "testDir"+fileSeparator+"bar.txt", "uniqtest"};
        contents = new String[]{
                generateLines("foo", "bar"),
                generateLines("foobar"),
                generateLines("Lorem ipsum", "dolar sit amet", "consectetur adipiscing elit"),
                generateLines("fo"),
                generateLines("fb"),
                generateLines("ba"),
                generateLines("abba", "ab", "ab", "zz", "ab", "Ab", "Zz", "zz", "zz", "abbba", "abba", "Abba")
        };
        String currentDir = System.getProperty("user.dir");
        File dir = new File(currentDir+fileSeparator+"testDir");
        dir.mkdir();
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
        new File("testDir").delete();
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

    protected String generateLines(String... lines) {
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            result.append(line+lineSeparator);
        }
        return result.toString();
    }

}
