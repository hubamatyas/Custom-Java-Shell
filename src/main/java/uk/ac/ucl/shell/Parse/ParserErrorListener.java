package uk.ac.ucl.shell.Parse;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;


public class ParserErrorListener extends BaseErrorListener{
    
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
      throws ParseCancellationException {
        //error containing all the information
        //throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);

        //error with less information
        throw new ParseCancellationException("invalid command structure at line " + line + ":" + charPositionInLine);
    }
}
