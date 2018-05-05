package com.company.prime.service.number.app;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeNumberGenerator {

  private final PrimeNumberPredicate checker;

  public PrimeNumberGenerator(PrimeNumberPredicate checker) {
    this.checker = checker;
  }

  public List<Integer> primesTill(int limit) {
    checkArgument(limit > 0, "Parameter limit must be greater than 0");
    List<Integer> result =
        IntStream.rangeClosed(1, limit)
            .parallel()
            .filter(checker)
            .boxed()
            .collect(Collectors.toList());
    return result;
  }
}
