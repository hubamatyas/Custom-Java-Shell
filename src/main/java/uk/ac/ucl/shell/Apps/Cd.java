package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Cd extends Application {
    public Cd(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
    }

    @Override
    protected void checkArgs() {
        if (this.args.isEmpty()) {
            throw new RuntimeException(appName + ": missing argument");
        } else if (this.args.size() > 1 || this.input != null) {
            throw new RuntimeException(appName + ": too many arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        String subDirectory = this.args.get(0);
        this.directory.checkDirectoryToHandle(this.appName, subDirectory);
        this.directory.setCurrentDirectory(String.valueOf(this.directory.getPathTo(subDirectory)));
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) {}

}
