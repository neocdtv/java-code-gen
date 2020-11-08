package io.neocdtv;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Person {

  private BigInteger id;
  private String firstName;
  private String lastName;
  private List<Address> addresses = new ArrayList<>();

  public BigInteger getId() {
    return id;
  }

  public void setId(BigInteger id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void addAddresses(final List<Address> addresses) {
    addresses.forEach(address -> {
      addAddress(address);
    });
  }

  public void addAddress(final Address address) {
    address.setPerson(this);
    addresses.add(address);
  }
}
