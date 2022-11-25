package uk.ac.ucl.shell;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import uk.ac.ucl.shell.Apps.ApplicationFactory;
import uk.ac.ucl.shell.Directory;


public class Shell {
    
    private static String currentDirectory = System.getProperty("user.dir");

    public static String getDirectory(){
        return currentDirectory;
    }

    public static void setDirectory(String dir){
        currentDirectory = dir;
    }


    public static void eval(String input, OutputStream output) throws IOException{

        OutputStreamWriter standardWriter = new OutputStreamWriter(output);
        ArrayList<String> rawCommands = Parsing.parse(input);

        for (String rawCommand : rawCommands) {
            // Shell functionality?
            ArrayList<String> tokens = Parsing.produceTokens(currentDirectory, rawCommand);
            String appName = tokens.get(0);
            ArrayList<String> appArgs = new ArrayList<String>(tokens.subList(1, tokens.size()));

            // Applications
            // TODO: compartmentalise each application
            // call functions in exec() and exec() itself seperately, not just one .extec() call
            ApplicationFactory.getApp(appName, appArgs, null , standardWriter).exec();
        }
    }

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
            Scanner scanner = new Scanner(System.in);
            try{
                while(true){
                    String prompt = Directory.getDirectory().getCurrentDirectory() + "> ";
                    System.out.print(prompt);
                    try {
                        String cmdline = scanner.nextLine();
                        eval(cmdline, System.out);
                    } catch (Exception e) {
                        System.out.println("COMP0010 shell: " + e.getMessage());
                    }
                }
            } finally {
                scanner.close();
            }
        }
    }
    

}
