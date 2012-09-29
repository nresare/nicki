package com.resare.nicki.http;

import com.resare.nicki.NickiService;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * An instance of this class exposes the Nicki service via http
 */
public class HttpExposer {
  static final Logger log = LoggerFactory.getLogger(HttpExposer.class);
  private Server server;

  public HttpExposer(int port, NickiService nickiService) {
    server = new Server(port);
    server.setHandler(new NickiHandler(nickiService));
  }

  public void run() {
    try {
      server.start();
      server.join();
      log.info("HTTP service was gracefully shut down");
    } catch (Exception e) {
      throw new Error(e);
    }
  }

  public void shutdown() {
    // shut down in one second
    server.setGracefulShutdown(1000);
    try {
      server.stop();
    } catch (Exception e) {
      throw new Error(e);
    }
  }
}
