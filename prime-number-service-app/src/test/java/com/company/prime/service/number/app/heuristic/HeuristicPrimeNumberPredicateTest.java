package com.company.prime.service.number.app.heuristic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HeuristicPrimeNumberPredicateTest {

  private final HeuristicPrimeNumberPredicate predicate = new HeuristicPrimeNumberPredicate();

  @Test(expected = IllegalArgumentException.class)
  public void zeroThrowsException() {
    predicate.test(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeNumberThrowsException() {
    predicate.test(-1);
  }

  @Test
  public void oneIsPrime() {
    assertThat(predicate.test(1)).isTrue();
  }

  @Test
  public void twoIsPrime() {
    assertThat(predicate.test(2)).isTrue();
  }

  @Test
  public void threeIsPrime() {
    assertThat(predicate.test(3)).isTrue();
  }

  @Test
  public void fourIsNotPrime() {
    assertThat(predicate.test(4)).isFalse();
  }

  @Test
  public void fiveIsNotPrime() {
    assertThat(predicate.test(5)).isTrue();
  }

  @Test
  public void sixIsNotPrime() {
    assertThat(predicate.test(6)).isFalse();
  }

  @Test
  public void sevenIsPrime() {
    assertThat(predicate.test(7)).isTrue();
  }

  @Test
  public void eightIsNotPrime() {
    assertThat(predicate.test(8)).isFalse();
  }

  @Test
  public void nineIsNotPrime() {
    assertThat(predicate.test(9)).isFalse();
  }

  @Test
  public void tenIsNotPrime() {
    assertThat(predicate.test(10)).isFalse();
  }
}
