package com.company.prime.service.number.ws.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Container for all prime number up to the initial value")
public class PrimeNumberResult {

  private final int initial;
  private final List<Integer> primes;

  public PrimeNumberResult(int initial, List<Integer> primes) {
    this.initial = initial;
    this.primes = primes;
  }

  @ApiModelProperty("Service parameter")
  public int getInitial() {
    return initial;
  }

  @ApiModelProperty("List of primes number up to initial")
  public List<Integer> getPrimes() {
    return primes;
  }

}
