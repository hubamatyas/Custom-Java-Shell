package uk.ac.ucl.shell.Apps;

import java.io.*;
import java.util.ArrayList;

import uk.ac.ucl.shell.Exceptions.TooManyArgumentsException;

public class Ls extends Application {
    private String path;

    public Ls(String appName, ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(appName, args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (this.args.size() > 1 || this.input != null) {
            throw new TooManyArgumentsException(appName);
        }
    }

    @Override
    protected void eval() throws IOException {
        getDirectoryPath();
        ls();
    }

    private void getDirectoryPath() {
        if (this.args.isEmpty()) {
            this.path = "";
        } else {
            this.path = this.args.get(0);
        }
    }

    private void ls() throws IOException {
        this.directory.checkDirectoryExists("ls", this.path);
        ArrayList<File> listOfFiles = this.directory.getContent("ls", this.path);
        outputFiles(listOfFiles);
        if (!listOfFiles.isEmpty()) {
            this.terminal.writeLine("", writer, lineSeparator);
        }
    }

    private void outputFiles (ArrayList<File> listOfFiles) throws IOException {
        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                this.terminal.writeLine(file.getName(), writer, "\t");
            }
        }
    }

    @Override
    protected void redirect() throws IOException {}

    @Override
    protected void app(BufferedReader reader) throws IOException {}
}
