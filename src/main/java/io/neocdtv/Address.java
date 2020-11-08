package io.neocdtv;

import java.math.BigInteger;

public class Address {

  public static final String SEQUENCE_NAME = "sq_address";

  private BigInteger id;

  private String city;

  private Person person;

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }
}
