package com.resare.nicki;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA. User: noa Date: 9/19/12 Time: 13:56 To change this template use File
 * | Settings | File Templates.
 */
public class FormatterTest {

  @Test
  public void testFormat() {
    Alert a = new Alert();
    a.setType(AlertType.PROBLEM);
    a.setMessage("Houston, we have a problem");
    a.setEntity("houston");
    String s0 = "Subject: problem\n\nHouston, we have a problem";
    Formatter f = new Formatter();
    String s1 = f.format(a,"templates/mail/alert.vm");
    Assert.assertEquals(s0, s1);
  }



}
