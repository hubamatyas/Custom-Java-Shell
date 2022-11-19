package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Echo extends Application{
    
    public void exec(ArrayList<String> appArgs, OutputStreamWriter writer)
    throws IOException {
        boolean atLeastOnePrinted = false;
        for (String arg : appArgs) {
            writer.write(arg);
            writer.write(" ");
            writer.flush();
            atLeastOnePrinted = true;
        }
        if (atLeastOnePrinted) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
