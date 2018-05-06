package com.company.prime.service.number.ws.metrics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;

public class MetricFactoryTest {

  private final MetricFactory factory = new MetricFactory(this);

  private LinkedHashMap<String, String> tags;

  @Before
  public void init() {
    tags = new LinkedHashMap<>();
    tags.put("z", "26");
    tags.put("a", "1");
    tags.put("b", "2");
  }

  @Test
  public void counter() {
    assertThat(factory.counter("method", tags)).isNotNull();
  }

  @Test
  public void timer() {
    assertThat(factory.timer("method", tags)).isNotNull();
  }

  @Test
  public void metricName() {
    assertThat(factory.metricName("method", "metric"))
        .isEqualTo(this.getClass().getName() + ".method.metric");
  }

  @Test
  public void tags() {
    assertThat(factory.flatten(tags)).isEqualTo(new String[] {"z", "26", "a", "1", "b", "2"});
  }
}
