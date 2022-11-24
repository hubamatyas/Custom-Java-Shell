package uk.ac.ucl.shell;

import java.io.InputStream;
import java.io.OutputStream;

public class Pipe extends Command {

    public Pipe(String inputString) {
        super(inputString);
    }

    @Override
    public void eval(InputStream input, OutputStream output) {
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
