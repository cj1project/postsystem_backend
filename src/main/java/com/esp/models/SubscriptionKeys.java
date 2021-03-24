package com.esp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
public class SubscriptionKeys  implements Serializable {
  @Id
  @Column
  private long id;
  @Column
  private  String p256dh;
  @Column
  private  String auth;

  public SubscriptionKeys() {
    this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
  }

  public SubscriptionKeys(String p256dh, String auth) {
    this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
    this.p256dh = p256dh;
    this.auth = auth;
  }

  public SubscriptionKeys(SubscriptionKeys keys) {
    this.id = keys.getId();
    this.p256dh = keys.getP256dh();
    this.auth = keys.getAuth();
  }


  public String getP256dh() {
    return p256dh;
  }

  public String getAuth() {
    return auth;
  }

  public Long getId() {
    return id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.auth == null ? 0 : this.auth.hashCode());
    result = prime * result + (this.p256dh == null ? 0 : this.p256dh.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SubscriptionKeys other = (SubscriptionKeys) obj;
    if (this.auth == null) {
      if (other.auth != null) {
        return false;
      }
    }
    else if (!this.auth.equals(other.auth)) {
      return false;
    }
    if (this.p256dh == null) {
      if (other.p256dh != null) {
        return false;
      }
    }
    else if (!this.p256dh.equals(other.p256dh)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SubscriptionKeys{" +
            "id=" + id +
            ", p256dh=" + p256dh +
            ", auth=" + auth +
            '}';
  }
}