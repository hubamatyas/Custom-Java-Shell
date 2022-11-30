package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.ShellGrammarBaseVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.ArgumentContext;
import uk.ac.ucl.shell.ShellGrammarParser.CallContext;
import uk.ac.ucl.shell.ShellGrammarParser.RedirectionContext;

public class CallVisitor extends ShellGrammarBaseVisitor<Void>{
    
    private String outputRedirect;
    private String inputRedirect;
    private ArrayList<String> args;

    @Override
    public Void visit(ParseTree tree) {
        outputRedirect = null;
        inputRedirect = null;
        args = new ArrayList<String>();
        return super.visit(tree);
    }

    @Override
    public Void visitCall(CallContext ctx) {    
        return super.visitCall(ctx);
    }

    @Override
    public Void visitRedirection(RedirectionContext ctx) throws RuntimeException {
        ArrayList<String> cleanedArgs;
        try {
            cleanedArgs = Parser.cleanArg(ctx.argument().getText());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if(ctx.REDIRECTIN() != null){
            if(inputRedirect == null){
                inputRedirect = cleanedArgs.remove(0);
            }else{
                throw new RuntimeException("More than one input redirection");
            }
        }else{
            if(outputRedirect == null){
                outputRedirect = cleanedArgs.remove(0);
            }else{
                throw new RuntimeException("More than one output redirection");
            }
        }
        args.addAll(cleanedArgs);
        return null;
    }

    @Override
    public Void visitArgument(ArgumentContext ctx) throws RuntimeException{
        ArrayList<String> cleanedArgs;
        try {
            cleanedArgs = Parser.cleanArg(ctx.getText());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        args.addAll(cleanedArgs);
        return null;
    }

    public String getInput(){
        return inputRedirect;
    }

    public String getOutput(){
        return outputRedirect;
    }

    public ArrayList<String> getArgs(){
        return args;
    }
}
