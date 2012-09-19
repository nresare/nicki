package com.resare.nicki;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.CharArrayWriter;

/**
 * Formats Alerts into text strings.
 */
public class Formatter {

  private VelocityEngine velocityEngine = new VelocityEngine();

  String format(Alert alert, String template) {
    Template t = velocityEngine.getTemplate(template);
    Context context = new VelocityContext();
    context.put("entity", alert.getEntity());
    context.put("message", alert.getMessage());
    context.put("type", alert.getType().toString().toLowerCase());

    CharArrayWriter caw = new CharArrayWriter();
    t.merge(context, caw);
    return caw.toString();
  }
}
