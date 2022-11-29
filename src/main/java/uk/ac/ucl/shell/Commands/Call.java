package uk.ac.ucl.shell.Commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import uk.ac.ucl.shell.Directory;
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
            File file = new File(Directory.getInstance().getCurrentDirectory(), parsedCall.getInput());
            input = new FileInputStream(file);
        }
        if(parsedCall.hasOutput()){
            File file = new File(Directory.getInstance().getCurrentDirectory(), parsedCall.getOutput());
            output = new FileOutputStream(file);
        }
        OutputStreamWriter writer = new OutputStreamWriter(output);
        ApplicationFactory.getApp(parsedCall.getApp(), parsedCall.getArgs(), input, writer).exec();
    }

    
}
