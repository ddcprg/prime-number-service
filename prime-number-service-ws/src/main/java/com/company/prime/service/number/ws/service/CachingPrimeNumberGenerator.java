package com.company.prime.service.number.ws.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.company.prime.service.number.app.PrimeNumberGenerator;

public class CachingPrimeNumberGenerator implements PrimeNumberGenerator {

  public static final String PRIMES_CACHE_NAME = "primes";

  private PrimeNumberGenerator delegate;

  public CachingPrimeNumberGenerator(PrimeNumberGenerator delegate) {
    this.delegate = delegate;
  }

  @Override
  @Cacheable(PRIMES_CACHE_NAME)
  public List<Integer> primesTill(int limit) {
    return delegate.primesTill(limit);
  }
}
