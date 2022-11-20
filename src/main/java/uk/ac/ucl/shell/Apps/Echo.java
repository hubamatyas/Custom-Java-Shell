package uk.ac.ucl.shell.Apps;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import uk.ac.ucl.shell.Evaluator;

public class Echo extends Application{

    public void exec(ArrayList<String> appArgs, Evaluator evaluator) throws IOException {

        OutputStreamWriter writer = evaluator.getWriter();
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
