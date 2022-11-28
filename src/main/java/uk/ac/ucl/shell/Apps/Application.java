package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Directory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public abstract class Application implements IApplication {
    public InputStream input;
    public OutputStreamWriter writer;
    public ArrayList<String> args;
    protected Directory directory;
    protected String lineSeparator;

    public Application(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        this.args = args;
        this.input = input;
        this.writer = output;
        this.directory = Directory.getInstance();
        this.lineSeparator = System.getProperty("line.separator");
    }

    public void exec() throws IOException {
        checkArgs();
        eval();
    }

    protected abstract void checkArgs();
    protected abstract void eval() throws IOException;
}
