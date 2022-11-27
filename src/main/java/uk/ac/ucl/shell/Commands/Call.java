package uk.ac.ucl.shell.Commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import uk.ac.ucl.shell.Apps.ApplicationFactory;
import uk.ac.ucl.shell.Parse.ParsedCall;
import uk.ac.ucl.shell.Parse.Parser;

public class Call extends Command {

    public Call(String inputString) {
        super(inputString);
    }

    @Override
    public void eval(InputStream input, OutputStream output) throws IOException{
        ParsedCall parsedCall = Parser.parseCall(getInputString());
        
        if(parsedCall.hasInput()){
            input = new FileInputStream(parsedCall.getInput());
        }if(parsedCall.hasOutput()){
            output = new FileOutputStream(parsedCall.getOutput());
        }
        OutputStreamWriter writer = new OutputStreamWriter(output);
        ApplicationFactory.getApp(parsedCall.getApp()).exec(parsedCall.getArgs(), input, writer);
    }

    
}
