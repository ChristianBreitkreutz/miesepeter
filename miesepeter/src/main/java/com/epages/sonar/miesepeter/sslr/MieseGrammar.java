
package com.epages.sonar.miesepeter.sslr;

import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.HASH;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.PAREN_L;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.PAREN_R;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.SQBRACE_L;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.SQBRACE_R;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.TLEIF;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.TLEIFEND;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.COLON;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.SEMICOLON;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.DOT;
import static com.epages.sonar.miesepeter.sslr.MieseLexer.Punctuators.COMMA;
import static com.sonar.sslr.api.GenericTokenType.EOF;
import static com.sonar.sslr.api.GenericTokenType.IDENTIFIER;

import org.sonar.sslr.grammar.GrammarRuleKey;
import org.sonar.sslr.grammar.LexerfulGrammarBuilder;

import com.epages.sonar.miesepeter.sslr.MieseLexer.Literals;
import com.sonar.sslr.api.Grammar;

public enum MieseGrammar implements GrammarRuleKey {

	COMPILATION_UNIT, EXPRESSION,

	TLE, TLEVar, MODIFIER, BODY, TLECondition,

	//formaters
	FORMATER,
	BYTE_FORMATER,
	CASE_FORMATER,
	DATETIME_FORMATER,
	HTML_FORMATER,
	MARKDOWN_FORMATER,
	NUMBER_FORMATER,
	PASSWORD_FORMATER,
	PLURAL_FORMATER,
	WIKI2HTML_FORMATER,
	SLICE_FORMATER,
	SPACE_FORMATER,
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
    				b.oneOrMore(TLEVar),
    				TLECondition
    ));
    b.rule(COMPILATION_UNIT).is(b.zeroOrMore(TLE), EOF);
    
    b = variableRules(b);

    b.setRootRule(TLE);

    return b.build();
  }

	private static LexerfulGrammarBuilder variableRules(LexerfulGrammarBuilder b) {
		
		b.rule(TLEVar).is(
	    		HASH,
	    		b.sequence(IDENTIFIER, b.zeroOrMore(b.sequence(DOT, IDENTIFIER))),
	    		b.optional(MODIFIER)
	    		);
	    b.rule(MODIFIER).is(
				SQBRACE_L,
				b.sequence(FORMATER, b.zeroOrMore(b.sequence(COMMA, FORMATER))),
				SQBRACE_R
		);
	    b.rule(FORMATER).is(
	    		b.firstOf(
	    				b.oneOrMore(Literals.INTEGER),
	    				BYTE_FORMATER,
	    				CASE_FORMATER,
	    				DATETIME_FORMATER,
	    				HTML_FORMATER,
	    				MARKDOWN_FORMATER,
	    				NUMBER_FORMATER,
	    				PASSWORD_FORMATER,
	    				PLURAL_FORMATER,
	    				WIKI2HTML_FORMATER,
	    				SLICE_FORMATER,
	    				SPACE_FORMATER
	    				)
	    		);
	    b.rule(BYTE_FORMATER).is( //
	    		b.firstOf( //
	    				"KB", //
	    				"MB", //
	    				"GB", //
	    				"Bit" //
	    				) //
	    );
	    b.rule(CASE_FORMATER).is( //
	    		b.firstOf( //
	    				"UC", //
	    				"LC", //
	    				"UCFirst", //
	    				"LCFirst" //
	    				) //
	    );
	    b.rule(DATETIME_FORMATER).is( //
	    		b.firstOf( //
	    				"date", //
	    				"time", //
	    				"datetime" //
	    				) //
	    );
	    b.rule(HTML_FORMATER).is( //
	    		b.firstOf( //
	    				"html", //
	    				"decodehtml", //
	    				"nohtml", //
	    				"js", //
	    				"preline", //
	    				"uri", //
	    				"url", //
	    				"urljsarray", //
	    				"color", //
	    				"correcthtml", //
	    				"nomedia", //
	    				"noactivemedia", //
	    				"noembed"
	    				) //
	    		);
	    
	    b.rule(MARKDOWN_FORMATER).is( "markdown" );
	    b.rule(NUMBER_FORMATER).is( //
	    		b.firstOf( //
	    				"integer", //
	    				"float", //
	    				"money", //
	    				"px", //
	    				"neg", //
	    				"lazyinteger" //
	    				) //
	    		);
	    b.rule(PASSWORD_FORMATER).is( "password" );
	    b.rule(PLURAL_FORMATER).is( "plural" );
	    b.rule(WIKI2HTML_FORMATER).is( "wiki2html" );
	    b.rule(SLICE_FORMATER).is(
	    		b.firstOf("slice", "substr"),
	    		COLON,
	    		b.oneOrMore(Literals.INTEGER),
	    		b.optional(
	    				COLON,
	    				b.oneOrMore(Literals.INTEGER)
	    	    )
	    );
	    b.rule(SPACE_FORMATER).is(
				b.firstOf(
						"nobreak",
						b.sequence(
								"space",
								b.optional( SEMICOLON, b.oneOrMore(Literals.INTEGER) )
						)
				)
		);
		return b;
	}

}
