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
    protected void checkArgs() {}

    @Override
    protected void eval() throws IOException {
        String lastArg = this.args.get(this.args.size() - 1);
        for (String arg : this.args) {
            if (arg.equals(lastArg)) {
                this.terminal.writeLine(arg, writer, lineSeparator);
            } else {
                this.terminal.writeLine(arg, writer, " ");
            }
        }
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) {}
}
