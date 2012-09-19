package com.resare.nicki.http;

import com.resare.nicki.JSONTools;
import com.resare.nicki.NickiService;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class NickiHandler extends AbstractHandler {

  private NickiService nickiService;

  public NickiHandler(NickiService nickiService) {
    this.nickiService = nickiService;
  }

  public void handle(String target, Request baseRequest,
                     HttpServletRequest request, HttpServletResponse resp)
      throws IOException
  {

    resp.setContentType("text/plain");

    if (target.equals("/1/ping")) {
      resp.setStatus(200);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      String now = sdf.format(new Date());
      resp.setContentLength(now.length());
      resp.getWriter().write(now);
    } else if (target.equals("/1/alert")) {
      if (!request.getMethod().equals("POST")) {
        simple_return(resp, 500, "You can only POST alerts");
        return;
      }
      String data = IOUtils.toString(request.getInputStream());
      nickiService.submit(JSONTools.parseAlert(data));
      simple_return(resp, 200, "OK");
    } else {
      simple_return(resp, 404, String.format("Don't know how to handle '%s'", target));
    }
  }

  private void simple_return(HttpServletResponse resp, int status, String message)
          throws IOException {
    if (!message.endsWith("\n")) {
      message += "\n";
    }
    resp.setStatus(status);

    resp.setContentLength(message.length());
    resp.getWriter().write(message);
  }
}
