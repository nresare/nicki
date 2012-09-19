package com.resare.nicki;

/**
 * Classes implementing this interface is implementing a transport for
 * connecting to and performing operations on a NickiService implementation.
 *
 */
public interface Exposer {
  public void setNickiService(NickiService nickiService);
}
