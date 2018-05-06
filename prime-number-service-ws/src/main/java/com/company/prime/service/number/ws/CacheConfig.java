package com.company.prime.service.number.ws;

import static com.company.prime.service.number.ws.service.CachingPrimeNumberGenerator.PRIMES_CACHE_NAME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
  private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

  @CacheEvict(allEntries = true, cacheNames = PRIMES_CACHE_NAME)
  @Scheduled(fixedDelay = 60 * 60 * 1000) // 60 sec
  public void reportCacheEvict() {
    LOG.info("Flushing cache...");
  }
}
