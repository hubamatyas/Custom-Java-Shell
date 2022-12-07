package uk.ac.ucl.shell.Apps;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.Directory;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

import java.io.File;
import java.io.IOException;

public class CdTest extends ApplicationTest{

    private Directory dir;
    private String currentDir;
    String fileSeparator;

    private String getPathToParentHelper(String dir) {
        int i = dir.length() - 1;
        while (i-- > 0) {
            if (String.valueOf(dir.charAt(i)).equals(fileSeparator)) {
                break;
            }
        }
        return dir.substring(0, i+1);
    }

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        dir = Directory.getInstance();
        currentDir = System.getProperty("user.dir");
        fileSeparator = File.separator;
    }

    @Override
    public String setAppName() {
        return "cd";
    }

    // Functionality
    @Test
    public void cdEmpty() throws IOException {
        execApp(createArgs(""), null);
        assertEquals(currentDir, dir.getCurrentDirectory());
    }

    @Test
    public void cdDot() throws IOException {
        execApp(createArgs("."), null);
        assertEquals(currentDir, dir.getCurrentDirectory());
    }

    @Test
    public void cdParentDir() throws IOException {
        execApp(createArgs(".."), null);
        assertEquals(getPathToParentHelper(currentDir), dir.getCurrentDirectory());
    }

    @Test
    public void cdChildDir() throws IOException {
        execApp(createArgs("src"), null);
        assertEquals(currentDir+fileSeparator+"src", dir.getCurrentDirectory());
    }

    // Exceptions
    @Test(expected = RuntimeException.class)
    public void dirDoesNotExist() throws IOException {
        try {
            execApp(createArgs("foo"), null);
        } catch (RuntimeException re) {
            assertEquals("cd: foo directory does not exist", re.getMessage());
            throw re;
        }
    }

    @Test(expected = MissingArgumentsException.class)
    public void missingArguments() throws IOException {
        execApp(createArgs(), null);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void tooManyArguments() throws IOException {
        execApp(createArgs("foo", "bar"), null);
    }

}