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
        System.out.println(ctx.getText());
        return super.visitCall(ctx);
    }

    @Override
    public Void visitRedirection(RedirectionContext ctx) throws RuntimeException{
        if(ctx.REDIRECTIN() != null){
            if(inputRedirect == null){
                inputRedirect = ctx.argument().getText();
            }else{
                throw new RuntimeException("More than one output redirection");
            }
        }else{
            if(outputRedirect == null){
                outputRedirect = ctx.argument().getText();
            }else{
                throw new RuntimeException("More than one output redirection");
            }
        }
        return null;
    }

    @Override
    public Void visitArgument(ArgumentContext ctx){
        args.add(ctx.getText());
        return super.visitArgument(ctx);
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
