package com.resare.nicki;

/**
 * Standard NickiService implementation.
 */
public class NickiServiceImpl implements NickiService {

  private XMPPConduit xmppConduit;
  private Formatter formatter;

  @Override
  public void submit(Alert alert) {
    String s = formatter.format(alert, "templates/mail/alert.vm");
    xmppConduit.sendToOnline(s);
  }

  public void setXmppConduit(XMPPConduit xmppConduit) {
    this.xmppConduit = xmppConduit;
  }

  public void setFormatter(Formatter formatter) {
    this.formatter = formatter;
  }
}
