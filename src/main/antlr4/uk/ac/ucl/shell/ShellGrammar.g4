grammar ShellGrammar;

/*
 * Parser Rules
 */

//commands

command : seq EOF;

seq :  atomicCommand | atomicCommand SEMICOLON seq?; 

atomicCommand : pipe | call;

pipe : pipe PIPEOP call | call PIPEOP call;

// call 

call : WHITESPACE? (redirection WHITESPACE)* argument (WHITESPACE atom)* WHITESPACE?;

atom : redirection | argument;

redirection : REDIRECTIN WHITESPACE? argument | REDIRECTOUT WHITESPACE? argument;

argument : (UNQUOTED | DOUBLEQUOTED | SINGLEQUOTED | BACKQUOTE)+;


/*
 * Lexer Rules
 */


SEMICOLON : ';';
PIPEOP : '|';
REDIRECTIN : '<';
REDIRECTOUT : '>';

WHITESPACE : (' ' | '\t')+;

fragment BACKQUOTE_fragment : '`' (~[\n`])* '`';

UNQUOTED : ~[\n\t '"`;<>|]+;
SINGLEQUOTED : '\'' (~[\n'])* '\'';
BACKQUOTE : BACKQUOTE_fragment;
DOUBLEQUOTED : '"' ( BACKQUOTE_fragment | ~[\n"])* '"';
