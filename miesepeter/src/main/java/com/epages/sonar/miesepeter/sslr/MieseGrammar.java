
package com.epages.sonar.miesepeter.sslr;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.HASH;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.PAREN_L;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.PAREN_R;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.TLEIF;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.TLEIFEND;
import static com.sonar.sslr.api.GenericTokenType.EOF;
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import com.sonar.sslr.api.Grammar;

public enum MieseGrammar implements GrammarRuleKey {

  COMPILATION_UNIT,
  EXPRESSION,

  TLE,
  TLEVar,
  BODY,
  TLECondition,

  PRIMARY_EXPRESSION;

  public static Grammar create() {
    LexerfulGrammarBuilder b = LexerfulGrammarBuilder.create();

    b.rule(EXPRESSION).is(
    		b.firstOf(
    				TLEVar,
    				TLE
    ));
    b.rule(TLECondition).is(TLEIF, PAREN_L, EXPRESSION, PAREN_R, b.optional(TLE),TLEIFEND);
    b.rule(TLE).is(
    		b.firstOf(
    				TLEVar,
    				TLECondition
    ));
    b.rule(COMPILATION_UNIT).is(b.zeroOrMore(TLE), EOF);
    b.rule(TLEVar).is(HASH, IDENTIFIER);
//    b.rule(DEFINITION).is(b.firstOf(
//        STRUCT_DEFINITION,
//        FUNCTION_DEFINITION,
//        VARIABLE_DEFINITION));
//
//    b.rule(STRUCT_DEFINITION).is(STRUCT, IDENTIFIER, BRACE_L, b.oneOrMore(STRUCT_MEMBER, SEMICOLON), BRACE_R);
//
//    b.rule(STRUCT_MEMBER).is(BIN_TYPE, IDENTIFIER);
//
//    b.rule(FUNCTION_DEFINITION).is(BIN_TYPE, BIN_FUNCTION_DEFINITION, PAREN_L, b.optional(PARAMETERS_LIST), PAREN_R, COMPOUND_STATEMENT);
//
//    b.rule(VARIABLE_DEFINITION).is(BIN_TYPE, BIN_VARIABLE_DEFINITION, b.optional(VARIABLE_INITIALIZER), SEMICOLON);
//
//    b.rule(PARAMETERS_LIST).is(PARAMETER_DECLARATION, b.zeroOrMore(COMMA, PARAMETER_DECLARATION));
//
//    b.rule(PARAMETER_DECLARATION).is(BIN_TYPE, BIN_PARAMETER);
//
//    b.rule(COMPOUND_STATEMENT).is(BRACE_L, b.zeroOrMore(VARIABLE_DEFINITION), b.zeroOrMore(STATEMENT), BRACE_R);
//
//    b.rule(VARIABLE_INITIALIZER).is(EQ, EXPRESSION);
//
//    b.rule(ARGUMENT_EXPRESSION_LIST).is(EXPRESSION, b.zeroOrMore(COMMA, EXPRESSION));
//
//    // Statements
//
//    b.rule(STATEMENT).is(b.firstOf(
//        EXPRESSION_STATEMENT,
//        COMPOUND_STATEMENT,
//        RETURN_STATEMENT,
//        CONTINUE_STATEMENT,
//        BREAK_STATEMENT,
//        IF_STATEMENT,
//        WHILE_STATEMENT,
//        NO_COMPLEXITY_STATEMENT));
//
//    b.rule(EXPRESSION_STATEMENT).is(EXPRESSION, SEMICOLON);
//
//    b.rule(RETURN_STATEMENT).is(RETURN, EXPRESSION, SEMICOLON);
//
//    b.rule(CONTINUE_STATEMENT).is(CONTINUE, SEMICOLON);
//
//    b.rule(BREAK_STATEMENT).is(BREAK, SEMICOLON);
//
//    b.rule(IF_STATEMENT).is(IF, CONDITION_CLAUSE, STATEMENT, b.optional(ELSE_CLAUSE));
//
//    b.rule(WHILE_STATEMENT).is(WHILE, CONDITION_CLAUSE, STATEMENT);
//
//    b.rule(CONDITION_CLAUSE).is(PAREN_L, EXPRESSION, PAREN_R);
//
//    b.rule(ELSE_CLAUSE).is(ELSE, STATEMENT);
//
//    b.rule(NO_COMPLEXITY_STATEMENT).is("nocomplexity", STATEMENT);
//
//    // Expressions
//
//    b.rule(EXPRESSION).is(ASSIGNMENT_EXPRESSION);
//
//    b.rule(ASSIGNMENT_EXPRESSION).is(RELATIONAL_EXPRESSION, b.optional(EQ, RELATIONAL_EXPRESSION)).skipIfOneChild();
//
//    b.rule(RELATIONAL_EXPRESSION).is(ADDITIVE_EXPRESSION, b.optional(RELATIONAL_OPERATOR, RELATIONAL_EXPRESSION)).skipIfOneChild();
//
//    b.rule(RELATIONAL_OPERATOR).is(b.firstOf(
//        EQEQ,
//        NE,
//        LT,
//        LTE,
//        GT,
//        GTE));
//
//    b.rule(ADDITIVE_EXPRESSION).is(MULTIPLICATIVE_EXPRESSION, b.optional(ADDITIVE_OPERATOR, ADDITIVE_EXPRESSION)).skipIfOneChild();
//
//    b.rule(ADDITIVE_OPERATOR).is(b.firstOf(
//        ADD,
//        SUB));
//
//    b.rule(MULTIPLICATIVE_EXPRESSION).is(UNARY_EXPRESSION, b.optional(MULTIPLICATIVE_OPERATOR, MULTIPLICATIVE_EXPRESSION)).skipIfOneChild();
//
//    b.rule(MULTIPLICATIVE_OPERATOR).is(b.firstOf(
//        MUL,
//        DIV));
//
//    b.rule(UNARY_EXPRESSION).is(b.firstOf(
//        b.sequence(UNARY_OPERATOR, PRIMARY_EXPRESSION),
//        POSTFIX_EXPRESSION)).skipIfOneChild();
//
//    b.rule(UNARY_OPERATOR).is(b.firstOf(
//        INC,
//        DEC));
//
//    b.rule(POSTFIX_EXPRESSION).is(b.firstOf(
//        b.sequence(PRIMARY_EXPRESSION, POSTFIX_OPERATOR),
//        b.sequence(BIN_FUNCTION_REFERENCE, PAREN_L, b.optional(ARGUMENT_EXPRESSION_LIST), PAREN_R),
//        PRIMARY_EXPRESSION)).skipIfOneChild();
//
//    b.rule(POSTFIX_OPERATOR).is(b.firstOf(
//        INC,
//        DEC));
//
//    b.rule(PRIMARY_EXPRESSION).is(b.firstOf(
//        INTEGER,
//        BIN_VARIABLE_REFERENCE,
//        b.sequence(PAREN_L, EXPRESSION, PAREN_R)));

    b.setRootRule(TLE);

    return b.build();
  }

}
