package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Directory;
import uk.ac.ucl.shell.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public abstract class Application implements IApplication {
    protected final String appName;
    protected boolean isPiped;
    public InputStream input;
    protected Terminal terminal;
    public ArrayList<String> args;
    protected Directory directory;
    protected String lineSeparator;
    protected String fileSeparator;
    public OutputStreamWriter writer;

    public Application(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        this.args = args;
        this.input = input;
        this.isPiped = false;
        this.writer = output;
        this.appName = appName;
        this.terminal = Terminal.getInstance();
        this.directory = Directory.getInstance();
        this.lineSeparator = System.getProperty("line.separator");
        this.fileSeparator = System.getProperty("file.separator");
    }

    public void exec() throws IOException {
        checkArgs();
        eval();
    }

    protected abstract void checkArgs();
    protected abstract void eval() throws IOException;
    protected abstract void redirect() throws IOException;
    protected abstract void app(BufferedReader reader) throws IOException;

    protected void simpleCall(String arg) throws IOException {
        BufferedReader reader = this.directory.createBufferedReader(this.appName, arg);
        app(reader);
    }

    protected void pipedCall() throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(this.input));
        app(reader);
    }

    protected void setIsPiped() {
        this.isPiped = this.args.isEmpty();
    }
}
