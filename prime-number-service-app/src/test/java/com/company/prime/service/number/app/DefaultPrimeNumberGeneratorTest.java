package com.company.prime.service.number.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPrimeNumberGeneratorTest {

  private @Mock PrimeNumberPredicate predicate;

  private DefaultPrimeNumberGenerator generator;

  @Before
  public void init() {
    generator = new DefaultPrimeNumberGenerator(predicate);
  }

  @Test
  public void generateEmptyList() {
    List<Integer> result = generator.primesTill(10);
    assertThat(result).isNotNull().isEmpty();
    for (int i = 1; i <= 10; ++i) {
      verify(predicate).test(i);
    }
  }

  @Test
  public void generate() {
    when(predicate.test(1)).thenReturn(true);
    when(predicate.test(2)).thenReturn(true);
    when(predicate.test(3)).thenReturn(true);
    when(predicate.test(4)).thenReturn(false);
    List<Integer> result = generator.primesTill(4);
    verify(predicate, times(4)).test(anyInt());
    assertThat(result).contains(1).contains(2).contains(3).doesNotContain(4);
  }
}
