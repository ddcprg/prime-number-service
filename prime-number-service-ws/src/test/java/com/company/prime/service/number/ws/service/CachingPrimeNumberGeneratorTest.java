package com.company.prime.service.number.ws.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.company.prime.service.number.app.PrimeNumberGenerator;

@RunWith(MockitoJUnitRunner.class)
public class CachingPrimeNumberGeneratorTest {

  private @Mock PrimeNumberGenerator delegate;

  private CachingPrimeNumberGenerator generator;

  @Before
  public void itnit() {
    generator = new CachingPrimeNumberGenerator(delegate);
  }

  @Test
  public void primesTill() {
    generator.primesTill(6);
    verify(delegate).primesTill(6);
  }
}
