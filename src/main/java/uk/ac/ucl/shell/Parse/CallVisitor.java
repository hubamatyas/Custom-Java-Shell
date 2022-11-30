package uk.ac.ucl.shell.Parse;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Token;
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
        ArrayList<String> cleanedArgs = getCleanedArguments(ctx.argument());

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
    public Void visitArgument(ArgumentContext ctx) throws RuntimeException {
        args.addAll(getCleanedArguments(ctx));
        return null;
    }

    private ArrayList<String> getCleanedArguments(ArgumentContext ctx){
        ArrayList<String> arguments = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for(ParseTree token : ctx.children){
            ArrayList<String> cleanedArgs;
            try {
                cleanedArgs = Parser.cleanArg(token.getText());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            builder.append(cleanedArgs.get(0));
            for(int i = 1; i < cleanedArgs.size(); i++){
                arguments.add(builder.toString());
                builder = new StringBuilder();
                builder.append(cleanedArgs.get(i));
            }
        }
        arguments.add(builder.toString());
        return arguments;
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
