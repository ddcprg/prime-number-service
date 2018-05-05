package com.company.prime.service.number.ws.service;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.prime.service.number.app.PrimeNumberGenerator;

@Component
public class PrimeNumberGeneratorSupplier {

  private final BeanFactory factory;

  @Autowired
  public PrimeNumberGeneratorSupplier(BeanFactory factory) {
    this.factory = factory;
  }

  public PrimeNumberGenerator get(String name) {
    try {
      return factory.getBean(name, PrimeNumberGenerator.class);
    } catch (NoSuchBeanDefinitionException e) {
      throw new IllegalArgumentException("Prime number generator '" + name + "' not found", e);
    }
  }
}
