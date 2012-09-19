package com.resare.nicki;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

/**
 * An XMPPConduit instance connects to the XMPP system with a username and
 * password. It is set up to let accept subscriptions from everyone, and
 * also to subscribe to everyone that subscribes to it. This enables the
 * XMPPConduit to keep track of available contacts and only send notifications
 * to those.
 *
 */
public class XMPPConduit {

  Connection connection;

  public XMPPConduit(String server, String username, String password) {


//    Connection.DEBUG_ENABLED = true;

    ConnectionConfiguration cc = new ConnectionConfiguration(server);
    cc.setSASLAuthenticationEnabled(true);

    connection = new XMPPConnection(cc);

    PacketListener subListener = new PacketListener() {
      @Override
      public void processPacket(Packet packet) {
        if (!(packet instanceof Presence)) {
          return;
        }
        Presence p = (Presence)packet;
        if (p.getType() == Presence.Type.subscribe) {
          Presence resp = new Presence(Presence.Type.subscribed);
          resp.setTo(p.getFrom());
          connection.sendPacket(resp);

          resp = new Presence(Presence.Type.subscribe);
          resp.setTo(p.getFrom());
          connection.sendPacket(resp);
        }
      }
    };

    connection.addPacketListener(subListener, new PacketTypeFilter(Presence.class));


    try {
      connection.connect();
      connection.login(username, password, "nicki");
    } catch (XMPPException e) {
      throw new Error(e);
    }
  }

  public void sendToOnline(String message) {
    Roster r = connection.getRoster();
    for (RosterEntry re : r.getEntries()) {
      Presence p = r.getPresence(re.getUser());
      if (p.isAvailable()) {
        Message m = new Message(re.getUser(), Message.Type.chat);
        m.setBody(message);
        connection.sendPacket(m);
      }
    }
  }

  public boolean isOnline(String xmppAddress) {
    Presence p = connection.getRoster().getPresence(xmppAddress);
    for (RosterEntry re : connection.getRoster().getEntries()) {
      System.out.println(re);
    }

    System.out.println("presence value: " + p);
    return true;
  }

  public void close() {
    connection.disconnect();
  }

  public static void main(String[] args) throws Exception {
    XMPPConduit xo = new XMPPConduit("resare.com", "nicki", "c2MjgDR8vc4B");
    Thread.sleep(1000 * 5);
    //xo.subscribe("noa@resare.com");

    System.out.printf("%s isOnline: %s\n", "noa@resare.com", xo.isOnline("noa@resare.com"));
    xo.close();
  }
}
