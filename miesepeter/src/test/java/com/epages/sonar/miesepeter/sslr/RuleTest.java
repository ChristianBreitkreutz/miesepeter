package com.epages.sonar.miesepeter.sslr;

import com.sonar.sslr.api.Grammar;
import com.sonar.sslr.api.Rule;
import com.sonar.sslr.impl.Parser;

public abstract class RuleTest {

  protected final Parser<Grammar> p = MieseParser.create();
  protected final Grammar g = p.getGrammar();

  public final Rule getTestedRule() {
    return p.getRootRule();
  }

  public abstract void init();

}
