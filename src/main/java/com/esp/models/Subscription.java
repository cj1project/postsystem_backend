package com.esp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

@Entity
public class Subscription implements Serializable {
  @Id
  @Column
  private long id;
  @Column
  private  String endpoint;
  @Column
  private  long expirationTime;
  @OneToOne
  public  SubscriptionKeys keys;

  public Subscription( String endpoint,  Long expirationTime, SubscriptionKeys keys) {
    this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);
    this.endpoint = endpoint;
    this.expirationTime = expirationTime;
   this.keys = keys;
  }

  public Subscription() {
    this.id = ThreadLocalRandom.current().nextLong(1000000000, 1000000000000L);

  }

  public Subscription(Subscription subscription) {
    this.id = subscription.getId();
    this.endpoint = subscription.getEndpoint();
    this.expirationTime = subscription.getExpirationTime();
    this.keys = subscription.getKeys();
  }

  public void setSubscription(Subscription subscription) {
    this.id = subscription.getId();
    this.endpoint = subscription.getEndpoint();
    this.expirationTime = subscription.getExpirationTime();
    this.keys = subscription.getKeys();
  }


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint= endpoint;
  }

  public long getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(long ExpirationTime) {
    this.expirationTime= expirationTime;
  }


   public SubscriptionKeys getKeys() {
    return keys;
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
    Subscription other = (Subscription) obj;
    if (this.endpoint == null) {
      if (other.endpoint != null) {
        return false;
      }
    }
    else if (!this.endpoint.equals(other.endpoint)) {
      return false;
    }
   /* if (this.expirationTime == null) {
      if (other.expirationTime != null) {
        return false;
      }
    }
    else if (!this.expirationTime.equals(other.expirationTime)) {
      return false;
    }*/
    if (this.keys == null) {
      if (other.keys != null) {
       return false; } } else if (!this.keys.equals(other.keys)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Subscription{" +
            "id=" + id +
            ", endpoint=" + endpoint + + '\'' +
            ", expirationTime=" + expirationTime + + '\'' +
            ", keys_p256dh=" + keys.getP256dh() +
            ", keys_auth=" + keys.getAuth() +
            '}';
  }
}
