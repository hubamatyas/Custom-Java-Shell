package uk.ac.ucl.shell;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Evaluator {

    private static volatile Evaluator instance;

    public static synchronized Evaluator getInstance(){
        if(instance == null){
            instance = new Evaluator();
        }
        return instance;
    }

    private String currentDirectory; 
    private OutputStream output;
    private ApplicationFactory apps;
    private boolean running;

    public Evaluator(){
        currentDirectory = System.getProperty("user.dir");  
        output = System.out;
        apps = new ApplicationFactory();
    }

    public void setOutputStream(OutputStream output){
        this.output = output;
    }

    public void run(){
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
        OutputStreamWriter writer = new OutputStreamWriter(output);
        ArrayList<String> rawCommands = Parser.parse(input);

        for (String rawCommand : rawCommands) {

            // Shell functionality?
            ArrayList<String> tokens = Parser.produceTokens(currentDirectory, rawCommand);
            String appName = tokens.get(0);
            ArrayList<String> appArgs = new ArrayList<String>(tokens.subList(1, tokens.size()));

            // Applications
            // TODO: compartmentalise each application
            apps.execApp(appName, appArgs, writer);
        }
        writer.close();
    }

    public void setDirectory(String dir){
        currentDirectory = dir;
    }

    public String getDirectory(){
        return currentDirectory;
    }

}
