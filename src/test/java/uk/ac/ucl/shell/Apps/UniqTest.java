package uk.ac.ucl.shell.Apps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class UniqTest extends OutputTest{

    String fileName;
    String content;

    @Override
    public String setAppName() {
        return "uniq";
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        fileName = "uniqTest";
        String[] lines = new String[]{"Zzz", "abba", "Abba", "zzz", "zzz", "Zzz", "abba", "abba"};
        content = buildContent(lines);

        generateTestFile(fileName, content);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        new File("uniqTest").delete();
    }

    private String buildContent(String[] lines) {
        StringBuilder contentBuilder = new StringBuilder();
        for (String line : lines) {
            contentBuilder.append(line).append(lineSeparator);
        }
        return contentBuilder.toString();
    }

    // Functionality
    @Test
    public void uniqTest() throws IOException {
        String expectedOutput = buildContent(new String[]{"Zzz", "abba", "Abba", "zzz", "Zzz", "abba"});
        testOutput(createArgs(fileName), null, expectedOutput);
    }


}