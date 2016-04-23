package com.epages.sonar.miesepeter.sslr;


import static org.sonar.sslr.tests.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;


public class VariableTest extends RuleTest {

  @Override
  @Before
  public void init() {
    p.setRootRule(g.rule(MieseGrammar.TLEVar));
  }

  @Test
  public void reallife() {
    assertThat(p)
    .matches("#Var[1]")
    .matches("#Var[22]")
    .matches("#Var[333]")
    .matches("#ByteFormater[KB]")
    .matches("#ByteFormater[MB]")
    .matches("#ByteFormater[GB]")
    .matches("#ByteFormater[Bit]")
    
    .matches("#CaseFormatter[UC]")
    .matches("#CaseFormatter[LC]")
    .matches("#CaseFormatter[UCFirst]")
    .matches("#CaseFormatter[LCFirst]")

    .matches("#DateTimeFormatter[date]")
    .matches("#DateTimeFormatter[time]")
    .matches("#DateTimeFormatter[datetime]")
    
    .matches("#HTMLFormator[html]")
    .matches("#HTMLFormator[decodehtml]")
    .matches("#HTMLFormator[nohtml]")
    .matches("#HTMLFormator[js]")
    .matches("#HTMLFormator[preline]")
    .matches("#HTMLFormator[uri]")
    .matches("#HTMLFormator[url]")
    .matches("#HTMLFormator[urljsarray]")
    .matches("#HTMLFormator[color]")
    .matches("#HTMLFormator[correcthtml]")
    .matches("#HTMLFormator[nomedia]")
    .matches("#HTMLFormator[noactivemedia]")
    .matches("#HTMLFormator[noembed]")
    
    .matches("#MarkdownFormator[markdown]")

    .matches("#NumberFormator[integer]")
    .matches("#NumberFormator[float]")
    .matches("#NumberFormator[money]")
    .matches("#NumberFormator[px]")
    .matches("#NumberFormator[neg]")
    .matches("#NumberFormator[lazyinteger]")

    .matches("#PasswordFormator[password]")

    .matches("#PluralFormator[plural]")

    .matches("#Wiki2htmlFormator[wiki2html]")
    
    .matches("#SliceFormator[slice:11]")
    .matches("#SliceFormator[substr:8]")
    .matches("#SliceFormator[substr:-10]")
    .matches("#SliceFormator[substr:0:8]")
    
    .matches("#SpaceFormator[space]")
    .matches("#SpaceFormator[nobreak]")
    .matches("#SpaceFormator[space;40]")
    .matches("#SpaceFormator[space;-40]")

    .matches("#LineItemPrice[neg,money]")
    .matches("#HistoryName[slice:70,html]")
    .matches("#Message[nohtml,decodehtml,slice:70,preline]")

    .matches("#Var.Chain666.with.MORE.Elements")
    .matches("#Var.Chain666.with.MORE.Elements[url]")
    .matches("#Var.Chain666.with.MORE.Elements[substr:0:8,url]")

    .matches("#Var");
  }

}