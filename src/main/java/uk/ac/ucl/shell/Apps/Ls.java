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
        filesToOutput();
    }

    private void filesToOutput() {
        try {
            ArrayList<File> listOfFiles = directory.getListOfFiles("ls", path);
            System.out.println(listOfFiles);
            boolean atLeastOnePrinted = false;
            for (File file : listOfFiles) {
                if (!file.getName().startsWith(".")) {
                    writer.write(file.getName());
                    writer.write("\t");
                    writer.flush();
                    atLeastOnePrinted = true;
                }
            }
            if (atLeastOnePrinted) {
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        } catch (IOException e) {
            throw new RuntimeException("ls: cannot open " + directory.getCurrentDirectory() + path);
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
