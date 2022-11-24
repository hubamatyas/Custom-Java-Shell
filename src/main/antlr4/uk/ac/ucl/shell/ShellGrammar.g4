grammar ShellGrammar;

/*
 * Parser Rules
 */

//commands

command : seq;

seq :  atomicCommand SEMICOLON? | atomicCommand SEMICOLON seq*; 

atomicCommand : pipe | call;

pipe : pipe PIPEOP call | call PIPEOP call  ;

// call 

call : redirection* argument atom*;

atom : redirection | argument;

redirection : REDIRECTIN argument | REDIRECTOUT argument;

argument : (UNQUOTED | DOUBLEQUOTED | SINGLEQUOTED | BACKQUOTE)+;


/*
 * Lexer Rules
 */


SEMICOLON : ';';
PIPEOP : '|';
REDIRECTIN : '<';
REDIRECTOUT : '>';

fragment BACKQUOTE_fragment : '`' (~[\n`])* '`';

UNQUOTED : ~[\n'"`;|]+;
SINGLEQUOTED : '\'' (~[\n"])* '\'';
BACKQUOTE : BACKQUOTE_fragment;
DOUBLEQUOTED : '"' ( BACKQUOTE_fragment | ~[\n"])* '"';
