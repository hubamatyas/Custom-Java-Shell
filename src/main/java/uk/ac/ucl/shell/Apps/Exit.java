package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Exit extends Application {
    public Exit(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
    }

    @Override
    protected void checkArgs() {}

    @Override
    protected void eval() throws IOException {
        System.exit(0);
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
