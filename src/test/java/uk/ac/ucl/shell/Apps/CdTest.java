package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.Directory;

import java.io.IOException;

public class CdTest extends ApplicationTest{

    private Directory dir;
    private String currentDir;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dir = Directory.getInstance();
        currentDir = System.getProperty("user.dir");
    }

    @Override
    public String setAppName() {
        return "cd";
    }

    // Test functionality
    @Test
    public void cdCurrentDir() throws IOException {
        execApp(createArgs(""), null);
        assertEquals(currentDir, dir.getCurrentDirectory());
    }

    // Exceptions


}