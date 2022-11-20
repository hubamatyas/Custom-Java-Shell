package uk.ac.ucl.shell;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import uk.ac.ucl.shell.Apps.ApplicationFactory;

public class Evaluator {

    private String currentDirectory; 
    OutputStreamWriter writer;
    private ApplicationFactory appFactory;
    private boolean running;

    public Evaluator(OutputStream output){
        currentDirectory = System.getProperty("user.dir");  
        writer = new OutputStreamWriter(output);
        appFactory = new ApplicationFactory();
    }



    public void run() throws Exception{
        if(writer == null){
            throw new Exception("No output stream has been setup");
        }
        running = true;
        Scanner scanner = new Scanner(System.in);
        try{
            while(running){
                String prompt = currentDirectory + "> ";
                System.out.print(prompt);
                try {
                    String cmdline = scanner.nextLine();
                    eval(cmdline);
                } catch (Exception e) {
                    System.out.println("COMP0010 shell: " + e.getMessage());
                }
            }
        } finally {
            scanner.close();
        }
    }

    public void eval(String input) throws IOException{

        ArrayList<String> rawCommands = Parsing.parse(input);

        for (String rawCommand : rawCommands) {
            // Shell functionality?
            ArrayList<String> tokens = Parsing.produceTokens(currentDirectory, rawCommand);
            String appName = tokens.get(0);
            ArrayList<String> appArgs = new ArrayList<String>(tokens.subList(1, tokens.size()));

            // Applications
            // TODO: compartmentalise each application
            appFactory.buildApplication(appName).exec(appArgs, this);
        }
    }

    public void setDirectory(String dir){
        currentDirectory = dir;
    }

    public String getDirectory(){
        return currentDirectory;
    }

    public OutputStreamWriter getWriter(){
        return writer;
    }

    public void exitProgram(){
        running = false;
    }

}
