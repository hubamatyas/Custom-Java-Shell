package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

public class Pwd extends Application {

    public Pwd(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (this.args.size() != 0 || this.input != null) {
            throw new TooManyArgumentsException(appName);
        }
    }

    @Override
    protected void eval() throws IOException {
        this.terminal.writeLine(this.directory.getCurrentDirectory(), writer, lineSeparator);
    }

    @Override
    protected void redirect() throws IOException {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
