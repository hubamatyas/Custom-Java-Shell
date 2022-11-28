package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Ls extends Application {

    private String path;

    public Ls(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    @Override
    protected void checkArgs() {
        if (args.isEmpty() && input != null) {
            args.add(input.toString());
        }
        if (args.size() > 1) {
            throw new RuntimeException("ls: too many arguments");
        }
    }

    @Override
    protected void eval() throws IOException {
        getDirectoryPath();
        ls();
    }

    private void ls() throws IOException {
        directory.checkDirectoryToHandle("ls", path);
        ArrayList<File> listOfFiles = directory.getContent("ls", path);
        outputFiles(listOfFiles);
        if (!listOfFiles.isEmpty()) {
            directory.writeNewLine(writer);
        }
    }

    private void outputFiles (ArrayList<File> listOfFiles) throws IOException {
        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                directory.writeLine(file.getName(), writer, "\t");
            }
        }
    }

    private void getDirectoryPath() {
        if (args.isEmpty()) {
            path = "";
        } else {
            path = args.get(0);
        }
    }
}
