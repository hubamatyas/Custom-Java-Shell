package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Uniq extends Application {
    private boolean caseInsensitive;

    public Uniq(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
        this.caseInsensitive = false;
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input == null) {
            throw new RuntimeException(appName + ": missing arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        loadOptions();
        setIsPiped();
        redirect();
    }

    @Override
    protected void redirect() throws IOException {
        if (this.isPiped) {
            this.pipedCall();
        } else {
            this.simpleCall(this.args.get(0));
        }
    }

    @Override
    protected void app(BufferedReader reader) throws IOException {
        String prevLine = "";
        while (reader.ready()) {
            String line = reader.readLine();
            if (this.caseInsensitive) {
                line = line.toLowerCase();
            }
            if (!line.equals(prevLine)) {
                this.terminal.writeLine(line, writer, lineSeparator);
            }
            prevLine = line;
        }
    }

    private void loadOptions() {
        if (!this.args.isEmpty() && this.args.get(0).equals("-i")) {
            this.caseInsensitive = true;
            this.args.remove(0);
        }
    }


}
