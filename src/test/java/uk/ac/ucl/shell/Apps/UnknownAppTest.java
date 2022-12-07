package uk.ac.ucl.shell.Apps;

import org.junit.Test;
import uk.ac.ucl.shell.Exceptions.UnknownAppException;

import java.util.ArrayList;

import static org.junit.Assert.fail;

public class UnknownAppTest {

    @Test(expected = UnknownAppException.class)
    public void throwUnknown() {
        ApplicationFactory.getApp("foo", new ArrayList<String>(), null, null);
        fail();
    }

}
