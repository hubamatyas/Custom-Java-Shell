package uk.ac.ucl.shell;

import java.io.IOException;
import java.io.OutputStream;


public class Shell {

    public static void main(String[] args) {

        if (args.length > 0) {

            // When running in non-interactive mode
            if (args.length != 2) {
                System.out.println("COMP0010 shell: wrong number of arguments");
                return;
            }
            if (!args[0].equals("-c")) {
                System.out.println("COMP0010 shell: " + args[0] + ": unexpected argument");
                // TODO: return should be added here?
            }
            // Attempt to evaluate command
            try {
                // TODO: need to ensure quotes commands work?
                eval(args[1], System.out);
            } catch (Exception e) {
                System.out.println("COMP0010 shell: " + e.getMessage());
            }
        } else {
            try {
                new Evaluator(System.out).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void eval(String input, OutputStream output) throws IOException{
        new Evaluator(output).eval(input);
    }

}
