package com.company.prime.service.number.app.heuristic;

import static com.google.common.base.Preconditions.checkArgument;

import com.company.prime.service.number.app.PrimeNumberPredicate;

public class HeuristicPrimeNumberPredicate implements PrimeNumberPredicate {

  @Override
  public boolean test(int number) {
    checkArgument(number > 0, "Cannot verify if a number less than or equal to zero is prime");
    if (number == 1 || number == 2) return true;
    if (number % 2 == 0) return false;
    for (int i = 3; i * i <= number; i += 2) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }
}
