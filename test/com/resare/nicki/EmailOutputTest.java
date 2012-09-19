package com.resare.nicki;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests EmailOutput
 */
public class EmailOutputTest {


  @Test
  public void testCharsetForString() {
    Assert.assertEquals("US-ASCII", EmailOutput.getCharsetForString("pup"));
    Assert.assertEquals("ISO-8859-1", EmailOutput.getCharsetForString("lår"));
    Assert.assertEquals("UTF-8", EmailOutput.getCharsetForString("Rīga"));

  }


  public void testSend() {
    EmailOutput eo = new EmailOutput("ralf.resare.com:10025");
    eo.send("noa@resare.com", "€13 is a lot of money", "låda läger löv");
  }

  public static void main(String[] args) {
    EmailOutputTest eot = new EmailOutputTest();
    eot.testSend();
  }
}
