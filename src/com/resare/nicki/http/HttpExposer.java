package com.resare.nicki.http;

import com.resare.nicki.NickiService;

import org.eclipse.jetty.server.Server;


/**
 * An instance of this class exposes the Nicki service via http
 */
public class HttpExposer {

  public HttpExposer(int port, NickiService nickiService) {
    Server server = new Server(port);
    server.setHandler(new NickiHandler(nickiService));
    try {
      server.start();
    } catch (Exception e) {
      throw new Error(e);
    }


  }

}
