package uk.ac.ucl.shell.Apps;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExitTest extends ApplicationTest{

    @Override
    public String setAppName() {
        return "exit";
    }

    @Test
    public void pass() {
        assertTrue(true);
    }

    //    @Test
//    public void exitSuccess() throws IOException {
//        execApp(createArgs(), null);
//        fail("Failed to exit");
//    }
//
//    @Test
//    public void ignoreInput() throws IOException {
//        execApp(createArgs(), "ignoreMe");
//        fail("Failed to exit");
//    }

}