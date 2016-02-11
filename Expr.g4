grammar Expr;

@header {
package tleScanner;
}


// ========== LEXER GRAMMAR ==============
//-#LOOP-------------------------------------
TLEStartLoop : '#LOOP' ;
TLEEndLoop : '#ENDLOOP' ;
//-#INCLUDE----------------------------------
TLEInclude : '#INCLUDE' ;
//-#DEREFER----------------------------------
TLEDereferer : '#DEREFER' ;
//-#LOCAL & SET----------------------------------
TLEStartLocal : '#LOCAL' ;
TLEEndLocal : '#ENDLOCAL' ;
TLESET : '#SET' ;
//-#IF#ELSIF#ELSE ----------------------------------
TLEStartIf: '#IF';
TLEElsif: '#ELSIF';
TLEElse: '#ELSE';
TLEEndIf: '#ENDIF';

//-stuff----------------------------------
Modulo: '%';
Equal: 'EQ';
NotEqual: 'NE';
BoolAnd: 'AND';
BoolOr: 'OR';
BoolNot: 'NOT';
TLEURL: '"' 'http' 's'? '://www.' [a-zA-Z0-9]+ '.' [a-z]+ [a-zA-Z0-9/]* '?'* [a-zA-Z0-9&=]* '"';
TLEString: '"' [a-zA-Z0-9]* '"';
TLEVariable: '#' [a-zA-Z0-9._\-]+;

//HTML: STARTTAG HTML ENDTAG ->skip;
//HTML_AND_JAVASCRIPT: ([a-zA-Z0-9 <>/:"'=.&!?;-{}_\-,#+])+ ->skip;
//STARTTAG: '<' [a-zA-Z0-9 ]* ([a-zA-Z0-9]* '=' ([a-zA-Z0-9\" ]|TLEURL)*)* '>';
//ENDTAG: '<' '/' [a-zA-Z0-9]* '>';
WS : [\n\r\t ]+ -> skip;
// ========== PARSER GRAMMAR ==============




tle : (
    tleLoop
    | tleVariable
    | tleInclude
    | tleDereferer
    | tleSet
    | tleLocal
    | tleIf
)+;
//HTML_AND_JAVASCRIPT: ('<'[a-zA-Z0-9 ]+ '>' ([a-zA-Z0-9 <>/:"'=.&!?;-{}_\-,]|'#+')+) -> skip;
/* variable ######################################## */
tleVariable: TLEVariable;
/* ******************************************************* */
tleLoop: TLEStartLoop '(' loopParams ')' (tle)* TLEEndLoop;
loopParams: TLEVariable;
/* ******************************************************* */
tleInclude: TLEInclude '(' includeParams ')';
includeParams: TLEString;
/* ******************************************************* */
tleDereferer: TLEDereferer'(' derefererParams ')';
derefererParams: (TLEVariable|TLEURL)+;
/* ******************************************************* */
tleSet: TLESET '(' setNameParam ',' setKeyParam ')';
setNameParam: TLEString;
setKeyParam: (TLEString|TLEVariable);
tleLocal: TLEStartLocal '(' localParam ')' tle* TLEEndLocal;
localParam: TLEString;
/* ******************************************************* */
tleIf: TLEStartIf '(' tleIfParams ')' tleIfBody TLEEndIf;
tleIfBody: (tle|tleElsif|tleElse)*;
tleElsif: TLEElsif '(' tleIfParams ')' tleElsifBody;
tleElsifBody: (tle|tleElsif|tleElse)*;
tleElse: TLEElse tle*;
tleIfParams: stapledgoupedStmd;
stapledgoupedStmd: boolNot* '('* goupedStmd ')'*;
goupedStmd: stapledstms (boolOperators stapledstms)*;
stapledstms: boolNot* '('* stmd ')'*;
stmd: boolNot* stmdParamOne (operator boolNot* stmdParamTwo)*;
stmdParamOne: (TLEString|TLEVariable)+;
stmdParamTwo: (TLEString|TLEVariable|'2')+;
operator
    : Modulo
    | Equal
    | NotEqual
    ;
boolNot: BoolNot;
boolOperators
    : BoolAnd
    | BoolOr
    ;
