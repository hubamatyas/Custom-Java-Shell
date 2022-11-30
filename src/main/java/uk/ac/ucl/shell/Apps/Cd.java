package uk.ac.ucl.shell.Apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

public class Cd extends Application {
    public Cd(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter writer) {
        super(appName, args, input, writer);
    }

    @Override
    protected void checkArgs() {
        if (this.args.isEmpty()) {
            throw new MissingArgumentsException(appName);
        } else if (this.args.size() > 1 || this.input != null) {
            throw new TooManyArgumentsException(appName);
        }
    }

    @Override
    protected void eval() throws IOException {
        String subDirectory = this.args.get(0);
        this.directory.checkDirectoryExists(this.appName, subDirectory);
        this.directory.setCurrentDirectory(this.directory.getPathTo(subDirectory).toString());
    }

    @Override
    protected void redirect() {}

    @Override
    protected void app(BufferedReader reader) {}

}
