package uk.ac.ucl.shell;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import uk.ac.ucl.shell.Apps.ApplicationFactory;

public class Pipe implements Command {

    @Override
    public void eval(String cmdLine, InputStream input, OutputStream output) {
        /*
        command, rest = splitatpipe(cmdLine)

        input = command.parseforinputrederict;
        outptu = command.parseforOutputredirect

        command = accountforbacktick()

        apps, args = command.parse()
        pipeIn = new PipedInputStream()
        pipeOut =  new PipedOutputStreamWriter(pipeIn);

        ApplicationFactory.getApp(apps).exec(args, input, pipeOut)


        nextCommand = commandFac.get(rest)
        nextCommand.eval(rest, pipIn, output)


        //commnad factory
        if(rest is pipe):
            return new Pipe()
        else:
        return enw Call()
        */


    }

}
