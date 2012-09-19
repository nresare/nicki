package com.resare.nicki;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Sends an email to a user as a result of an incoming Alert.
 */
public class EmailOutput {

  //private String EMAIL_TEMPLATE = "templates/mail/alert.vm";

  private Session javaMailSession;

  public EmailOutput(String smtpServer) {
    Properties p = new Properties();

    String smtpPort = "25";
    int i = smtpServer.indexOf(':');
    if (i != -1) {
      smtpPort = smtpServer.substring(i + 1);
      smtpServer = smtpServer.substring(0,i);
    }
    p.setProperty("mail.host", smtpServer);
    p.setProperty("mail.smtp.port", smtpPort);
    try {
      p.setProperty("mail.smtp.localhost",
                     InetAddress.getLocalHost().getHostName());
    } catch (UnknownHostException e) {
      throw new Error(e);
    }
    javaMailSession = Session.getDefaultInstance(p);
  }


  public void send(String to, String subject, String message) {
    MimeMessage mm = new MimeMessage(javaMailSession);
    try {
      if (subject != null) {
        mm.setSubject(subject, getCharsetForString(subject));
      }
      mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      mm.setContent(message, "text/plain");
      mm.saveChanges();
      Transport.send(mm);
    } catch (MessagingException e) {
      throw new Error(e);
    }
  }

  /**
   * Returns name of the simplest possible charset that can be used to encode
   * input.
   *
   * @param input the string to check for interesting characters in
   * @return US-ASCII, ISO-8859-1 or UTF-8
   */
  static String getCharsetForString(String input) {
    String s = "US-ASCII";
    for (char c : input.toCharArray()) {
      if (c > 127) {
        s = "ISO-8859-1";
      }
      if (c > 255) {
        return "UTF-8";
      }
    }
    return s;
  }

}
