package com.resare.nicki;

import com.resare.nicki.http.HttpExposer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * This is the main class starting up the application
 */
public class NickiDaemon {
  static final Logger log = LoggerFactory.getLogger(NickiDaemon.class);

  public static void main(String[] args) throws Exception {
    log.info("Starting NickiDaemon");

    NickiDaemon nd = new NickiDaemon();
    nd.init();


  }

  private void init() throws IOException {

    Map<String, String> config = readConfig(new File("nicki.yaml"));

    NickiServiceImpl ns = new NickiServiceImpl();
    ns.setFormatter(new Formatter());

    /*
         XMPPConduit xo = new XMPPConduit("resare.com", "nicki", "c2MjgDR8vc4B");

     xmpp_server: 'uma.resare.com'
xmpp_username: 'nicki'
xmpp_password: 'c2MjgDR8vc4B'

     */
    ns.setXmppConduit(new XMPPConduit(config.get("xmpp_server"),
                                      config.get("xmpp_username"),
                                      config.get("xmpp_password")));

    // this will spin up a non-daemon thread.
    new HttpExposer(8080, ns);

  }

  private static Map<String,String> readConfig(File input) throws IOException {
    log.info("Reading configuration file from {}", input.getAbsolutePath());
    Yaml yaml = new Yaml();
    FileInputStream fis = new FileInputStream(input);
    Object o = yaml.load(fis);
    fis.close();

    //noinspection unchecked
    return (Map<String,String>)o;
  }


}
