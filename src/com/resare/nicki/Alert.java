package com.resare.nicki;


import java.util.Map;

/**
 * Instances of this class holds information about an alert submitted to the
 * system.
 */
public class Alert {
  AlertType type;
  String message, detail, entity, id;
  Severity severity;
  Map<String,String> extraFields;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Severity getSeverity() {
    return severity;
  }

  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public AlertType getType() {
    return type;
  }

  public void setType(AlertType type) {
    this.type = type;
  }

  public Map<String, String> getExtraFields() {
    return extraFields;
  }

  public void setExtraFields(Map<String, String> extraFields) {
    this.extraFields = extraFields;
  }
}
