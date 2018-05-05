package com.company.prime.service.number.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.app.bruteforce.BruteForcePrimeNumberPredicate;
import com.company.prime.service.number.app.heuristic.HeuristicPrimeNumberPredicate;

@Configuration
public class AppContext {

  @Bean(Algorithms.BRUTE_FORCE)
  public PrimeNumberGenerator bruteForce() {
    return new PrimeNumberGenerator(new BruteForcePrimeNumberPredicate());
  }

  @Bean(Algorithms.HEURISTIC)
  public PrimeNumberGenerator heuristic() {
    return new PrimeNumberGenerator(new HeuristicPrimeNumberPredicate());
  }

}
