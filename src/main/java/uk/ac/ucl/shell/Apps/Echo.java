package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Echo extends Application {
    public Echo(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (this.input != null) {
            throw new RuntimeException(appName + ": cannot pipe output");
        }
    }

    @Override
    protected void eval() throws IOException {
        for (String arg : this.args) {
            this.terminal.writeLine(arg, writer, " ");
        }
        if (!this.args.isEmpty()) {
            this.terminal.writeLine("", writer, lineSeparator);
        }
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) {}
}
