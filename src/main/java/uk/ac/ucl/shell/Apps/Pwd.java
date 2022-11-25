package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

// TODO refactor by using Directory and abstract Application class
public class Pwd extends Application {

    public Pwd(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        output.write(Shell.getDirectory());
        output.write(System.getProperty("line.separator"));
        output.flush();
    }

    @Override
    protected void checkArgs() {

    }

    @Override
    protected void eval() throws IOException {

    }
}
