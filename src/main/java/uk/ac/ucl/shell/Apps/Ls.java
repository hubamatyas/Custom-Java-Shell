package uk.ac.ucl.shell.Apps;

import uk.ac.ucl.shell.Shell;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

// TODO refactor by using Directory and abstract Application class
public class Ls extends Application {

    public Ls(ArrayList<String> args, InputStream input, OutputStreamWriter output) {
        super(args, input, output);
    }

    public void exec(ArrayList<String> args, InputStream input, OutputStreamWriter output)
        throws IOException{
        File currDir;
        if (args.isEmpty()) {
            currDir = new File(Shell.getDirectory());
        } else if (args.size() == 1) {
            currDir = new File(args.get(0));
        } else {
            throw new RuntimeException("ls: too many arguments");
        }
        try {
            File[] listOfFiles = currDir.listFiles();
            boolean atLeastOnePrinted = false;
            assert listOfFiles != null;
            for (File file : listOfFiles) {
                if (!file.getName().startsWith(".")) {
                    output.write(file.getName());
                    output.write("\t");
                    output.flush();
                    atLeastOnePrinted = true;
                }
            }
            if (atLeastOnePrinted) {
                output.write(System.getProperty("line.separator"));
                output.flush();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }
    }

    @Override
    protected void checkArgs() {

    }

    @Override
    protected void eval() throws IOException {

    }
}
