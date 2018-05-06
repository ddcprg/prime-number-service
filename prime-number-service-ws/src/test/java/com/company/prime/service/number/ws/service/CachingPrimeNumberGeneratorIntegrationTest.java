package com.company.prime.service.number.ws.service;

import static com.company.prime.service.number.ws.service.CachingPrimeNumberGenerator.PRIMES_CACHE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.prime.service.number.app.DefaultPrimeNumberGenerator;
import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.ws.CacheConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(
  classes = {CacheConfig.class, CachingPrimeNumberGeneratorIntegrationTest.TestConfig.class}
)
public class CachingPrimeNumberGeneratorIntegrationTest {

  @Configuration
  static class TestConfig {
    @Bean
    public CacheManager cacheManager() {
      return new ConcurrentMapCacheManager(CachingPrimeNumberGenerator.PRIMES_CACHE_NAME);
    }

    @Bean
    public PrimeNumberGenerator generator() {
      return new CachingPrimeNumberGenerator(new DefaultPrimeNumberGenerator(n -> true));
    }
  }

  private @Autowired CacheManager cacheManager;
  private @Autowired PrimeNumberGenerator generator;

  @Test
  public void cacheResults() {
    Cache primes = cacheManager.getCache(PRIMES_CACHE_NAME);
    assertThat(primes).isNotNull();
    primes.clear();
    assertThat(primes.get(2)).isNull();
    List<Integer> primeNumbers = generator.primesTill(2);
    assertThat(primes.get(2).get()).isSameAs(primeNumbers);
  }
}
