package com.esp.webpush;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app")
@Component
public class AppProperties {

  private String serverPublicKeyPath ="../../../../../key.public";

  private String serverPrivateKeyPath = "../../../../../key.private";

  public String getServerPublicKeyPath() {
    return this.serverPublicKeyPath;
  }

  public void setServerPublicKeyPath(String serverPublicKeyPath) {
    this.serverPublicKeyPath = serverPublicKeyPath;
  }

  public String getServerPrivateKeyPath() {
    return this.serverPrivateKeyPath;
  }

  public void setServerPrivateKeyPath(String serverPrivateKeyPath) {
    this.serverPrivateKeyPath = serverPrivateKeyPath;
  }

}
