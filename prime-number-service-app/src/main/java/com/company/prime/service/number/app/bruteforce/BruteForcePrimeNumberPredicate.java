package com.company.prime.service.number.app.bruteforce;

import static com.google.common.base.Preconditions.checkArgument;

import com.company.prime.service.number.app.PrimeNumberPredicate;

public class BruteForcePrimeNumberPredicate implements PrimeNumberPredicate {

  @Override
  public boolean test(int number) {
    checkArgument(number > 0, "Cannot verify if a number less than or equal to zero is prime");
    for (int i = 2; i < number; ++i) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }
}
