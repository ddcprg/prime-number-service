package com.company.prime.service.number.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.company.prime.service.number.app.PrimeNumberGenerator;
import com.company.prime.service.number.app.bruteforce.BruteForcePrimeNumberPredicate;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Bean
  public PrimeNumberGenerator bruteForce() {
    return new PrimeNumberGenerator(new BruteForcePrimeNumberPredicate());
  }

}
