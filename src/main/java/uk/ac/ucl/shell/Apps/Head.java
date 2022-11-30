package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Exceptions.InvalidOptionException;
import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;

public class Head extends Application {
    private int numOfLines;

    public Head(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
        this.numOfLines = 10;
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new MissingArgumentsException(appName);
        }
        if (args.size() > 1  && !args.get(0).equals("-n")) {
            throw new InvalidOptionException(appName, args.get(0));
        }
        if (args.size() == 2 && input == null) {
            throw new InvalidOptionException(appName, args.get(1));
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOption();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            this.pipedCall();
        } else {
            this.simpleCall(args.get(0));
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        int count = 0;
        while (reader.ready() && count < this.numOfLines) {
            String line = reader.readLine();
            this.terminal.writeLine(line, writer, lineSeparator);
            count++;
        }
    }

    private void loadOption() {
        if (!this.args.isEmpty() && this.args.get(0).equals("-n")) {
            this.numOfLines = parseNumber(this.args.get(1));
            this.args.remove(0);
            this.args.remove(0);
        }
    }

    private int parseNumber(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw new InvalidOptionException(appName, str);
        }
    }
}
