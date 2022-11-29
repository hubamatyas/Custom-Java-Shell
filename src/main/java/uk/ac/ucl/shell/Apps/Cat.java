package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Cat extends Application {
    public Cat(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
    }

    @Override
    protected void checkArgs() {
        if (this.args.isEmpty() && this.input == null) {
            throw new RuntimeException(this.appName + ": missing arguments");
        }
        if (!this.args.isEmpty() && this.input != null) {
            throw new RuntimeException(this.appName + ": cannot read from both file and input");
        }
    }

    @Override
    protected void eval() throws IOException {
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            this.pipedCall();
        } else {
            for (String arg : this.args) {
                this.simpleCall(arg);
            }
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String line = reader.readLine();
            this.terminal.writeLine(line, writer, lineSeparator);
        }
    }
}
