package uk.ac.ucl.shell.Parse;
import java.util.ArrayList;

public class ParsedCall {
    private String app;
    private ArrayList<String> args;
    private String inputRedirect;
    private String outputRedirect;

    public ParsedCall(String app, ArrayList<String> args, String inputRedirect, String outputRedirect){
        this.app = app;
        this.args = args;
        this.inputRedirect = inputRedirect;
        this.outputRedirect = outputRedirect;
    }

    public String getApp(){
        return app;
    }

    public ArrayList<String> getArgs(){
        return args;
    }

    public boolean hasInput(){
        return inputRedirect != null;
    }

    public String getInput(){
        return inputRedirect;
    }

    public boolean hasOutput(){
        return outputRedirect != null;
    }

    public String getOutput(){
        return outputRedirect;
    }
}
