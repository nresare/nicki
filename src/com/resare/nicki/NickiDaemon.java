package com.resare.nicki;

import com.resare.nicki.http.HttpExposer;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Map;



/**
 * This is the main class starting up the application
 */
public class NickiDaemon {
  private static final String DEFAULT_CONFIG_FN = "/etc/nicki.yaml";
  private static final Logger log = LoggerFactory.getLogger(NickiDaemon.class);

  public static void main(String[] args) throws Exception {
    log.info("Starting NickiDaemon");
    String configFileName = DEFAULT_CONFIG_FN;
    for (int i = 0; i < args.length; i++) {
      if ("-p".equals(args[i]) && args.length > i + 1) {
        writePidFile(args[i + 1]);
      }
      if ("-c".equals(args[i]) && args.length > i + 1) {
        configFileName = args[i + 1];
      }
    }
    NickiDaemon nd = new NickiDaemon();
    nd.init(configFileName);
  }

  private static void writePidFile(String filename) {
    int pid = getPid();
    log.info("Writing pid {} to file {}", pid, filename);
    try {
      FileUtils.writeStringToFile(new File(filename), String.format("%d\n", pid));
    } catch (IOException e) {
      throw new Error(e);
    }
  }

  private static int getPid() {
    // I know this is a hack, but hey it works.
    // http://blog.igorminar.com/2007/03/how-java-application-can-discover-its.html
    String s = ManagementFactory.getRuntimeMXBean().getName();
    return Integer.parseInt(s.substring(0, s.indexOf("@")));
  }

  private void init(String configFileName) throws IOException {


    Map<String, String> config = readConfig(new File(configFileName));

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

  @SuppressWarnings("unchecked")
  private static Map<String,String> readConfig(File input) throws IOException {
    log.info("Reading configuration file from {}", input.getAbsolutePath());
    Yaml yaml = new Yaml();
    FileInputStream fis = new FileInputStream(input);
    Object o = yaml.load(fis);
    fis.close();

    return (Map<String,String>)o;
  }
}
