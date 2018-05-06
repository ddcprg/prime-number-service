package com.company.prime.service.number.ws.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.annotations.VisibleForTesting;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;

public class MetricFactory {

  private final Object invoker;

  public MetricFactory(Object invoker) {
    this.invoker = invoker;
  }

  @VisibleForTesting
  String metricName(String method, String type) {
    return String.format("%s.%s.%s", invoker.getClass().getName(), method, type);
  }

  @VisibleForTesting
  String[] flatten(Map<String, String> map) {
    return map.entrySet()
        .stream()
        .map(kv -> Arrays.asList(kv.getKey(), kv.getValue()))
        .flatMap(List::stream)
        .collect(Collectors.toList())
        .toArray(new String[0]);
  }

  public Counter counter(String methodName, Map<String, String> tags) {
    return Metrics.counter(metricName(methodName, "count"), flatten(tags));
  }

  public Timer timer(String methodName, Map<String, String> tags) {
    return Metrics.timer(metricName(methodName, "timer"), flatten(tags));
  }
}
