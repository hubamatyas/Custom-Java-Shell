package uk.ac.ucl.shell.Apps;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import uk.ac.ucl.shell.Evaluator;

public class Ls {
    
    public void eval(ArrayList<String> appArgs, OutputStreamWriter writer)
        throws IOException{
        File currDir;
        if (appArgs.isEmpty()) {
            currDir = new File(Evaluator.getInstance().getDirectory());
        } else if (appArgs.size() == 1) {
            currDir = new File(appArgs.get(0));
        } else {
            throw new RuntimeException("ls: too many arguments");
        }
        try {
            File[] listOfFiles = currDir.listFiles();
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
        }
    }
}
