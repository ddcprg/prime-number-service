package com.company.prime.service.number.ws.model;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement
@ApiModel(description = "Container for all prime number up to the initial value")
public class PrimeNumberResult {

  private int initial;
  private List<Integer> primes;

  public PrimeNumberResult() {}

  public PrimeNumberResult(int initial, List<Integer> primes) {
    this.initial = initial;
    this.primes = primes;
  }

  @ApiModelProperty("Service parameter")
  public int getInitial() {
    return initial;
  }

  public void setInitial(int initial) {
    this.initial = initial;
  }

  @ApiModelProperty("List of primes number up to initial")
  public List<Integer> getPrimes() {
    return primes;
  }

  public void setPrimes(List<Integer> primes) {
    this.primes = primes;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    PrimeNumberResult other = (PrimeNumberResult) obj;
    return this.initial == other.initial && Objects.equals(this.primes, other.primes);
  }

  @Override
  public String toString() {
    return new StringBuffer("PrimeNumberResult {")
        .append("initial: ")
        .append(initial)
        .append(", primes: ")
        .append(primes)
        .append("}")
        .toString();
  }
}
