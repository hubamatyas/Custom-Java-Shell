grammar ShellGrammar;

/*
 * Parser Rules
 */

//commands

seq :  atomicCommand ';'? | atomicCommand ';' seq*; 

atomicCommand : pipe | call;

pipe : pipe '|' call | call '|' call  ;

// call 

call : redirection* argument atom*;

atom : redirection | argument;

redirection : '<' argument | '>' argument;

argument : (UNQUOTED | DOUBLEQUOTED | SINGLEQUOTED | BACKQUOTE)+;


/*
 * Lexer Rules
 */

fragment BACKQUOTE_fragment : '`' (~[\n`])* '`';

UNQUOTED : ~[\n'"`;|]+;
SINGLEQUOTED : '\'' (~[\n"])* '\'';
BACKQUOTE : BACKQUOTE_fragment;
DOUBLEQUOTED : '"' ( BACKQUOTE_fragment | ~[\n"])* '"';
